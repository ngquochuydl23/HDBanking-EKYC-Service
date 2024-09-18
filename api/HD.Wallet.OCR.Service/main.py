import os
import sys
import uvicorn
import io
import shutil
import logging
import mimetypes
from readInfoIdCard import ReadInfo
from DetecInfoBoxes.GetBoxes import GetDictionary
from core.tool.predictor import Predictor
from core.tool.config import Cfg as Cfg_vietocr

from starlette.responses import RedirectResponse
from fastapi.responses import JSONResponse, StreamingResponse
from fastapi import FastAPI, File, UploadFile, Request, Query, HTTPException
from app.utils.uploaded_file_utils import check_file_extension
from app.db.mongodb import lifespan
from pymongo.errors import DuplicateKeyError
from pathlib import Path
from app.utils.unique_filename import create_unique_filename

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

getDictionary = GetDictionary()
sys.path.insert(0, 'DetecInfoBoxes')

opt = {
    "img-size": 800,
    "conf-thres": 0.5,
    "iou-thres": 0.15,
    "device": 'cpu',
}

# Load Ocr model
config_vietocr = Cfg_vietocr.load_config_from_file('core/config/vgg-seq2seq.yml')
config_vietocr['weights'] = 'models/seq2seqocr.pth'
config_vietocr['device'] = 'cpu'
ocrPredictor = Predictor(config_vietocr)

# Load Yolo model
scan_weight = 'models/cccdYoloV7.pt'
imgsz, stride, device, half, model, names = getDictionary.load_model(scan_weight, opt)

readInfo = ReadInfo(imgsz, stride, device, half, model, names, opt, ocrPredictor)
os.makedirs('uploads/identity-cart/', exist_ok=True)

app = FastAPI(title="OCR Identity Card Service", lifespan=lifespan)


@app.get("/", include_in_schema=False)
async def index():
    return RedirectResponse(url="/docs")


@app.get("/id-card/")
async def get_idcard_by_no(request: Request, id_card_no: str = Query(...)):
    database = request.app.state.database
    try:
        if database is None:
            raise HTTPException(status_code=500, detail="Database connection not available")

        collection = database["IDCards"]  # Access the collection

        id_card = await collection.find_one({"id_card.id": id_card_no})
        if id_card:
            if "_id" in id_card:
                id_card["_id"] = str(id_card["_id"])
            return JSONResponse(content={
                "statusCode": 200,
                "result": id_card
            })

        return JSONResponse(status_code=404, content={
            "statusCode": 404,
            "error": f"ID card with number {id_card_no} not found"
        })

    except Exception as e:
        return JSONResponse(status_code=500, content={
            "statusCode": 500,
            "error": str(e)
        })

@app.get("/files/{filename}")
async def get_file(filename: str):
    file_path = os.path.join("uploads/identity-cart/", filename)
    image_path = Path(file_path)

    if not image_path.is_file():
        return JSONResponse(status_code=404, content={"error": "File not found"})
    mime_type, _ = mimetypes.guess_type(file_path)

    with image_path.open("rb") as image_file:
        return StreamingResponse(image_file, media_type='image/jpeg')

@app.post("/id-card/extract")
async def predict_api(
        request: Request,
        front_id_card: UploadFile = File(...),
        back_id_card: UploadFile = File(...)
):
    if not check_file_extension(front_id_card):
        return "Front IdCard must be jpg or png format!"

    if not check_file_extension(back_id_card):
        return "Back IdCard must be jpg or png format!"

    try:
        database = request.app.state.database
        collection = database["IDCards"]

        front_save_path = os.path.join('uploads/identity-cart/', create_unique_filename(front_id_card))
        back_save_path = os.path.join('uploads/identity-cart/', create_unique_filename(back_id_card))

        with open(front_save_path, "wb") as buffer:
            shutil.copyfileobj(front_id_card.file, buffer)

        with open(back_save_path, "wb") as buffer:
            shutil.copyfileobj(back_id_card.file, buffer)

        result = readInfo.get_all_info(front_save_path)

        required_fields = ['id', 'full_name', 'date_of_birth', 'place_of_origin', 'place_of_residence']
        for field in required_fields:
            if not result.get(field):
                os.remove(front_save_path)
                os.remove(back_save_path)

                return JSONResponse(status_code=400, content={
                    "statusCode": 400,
                    "error": "Cannot recognize id-card. Please try again."
                })

        cccd_fields = ['sex', 'nationality', 'date_of_expiry']

        is_cccd = True
        for field in cccd_fields:
            if not result.get(field):  # Check if the field is empty or None
                is_cccd = False

        if result:
            id_card_result = {
                "id_card": result,
                "front-url": '/' + front_save_path,
                "back-url": '/' + back_save_path,
                "type": "CCCD" if is_cccd else "CMND",
            }

            update_result = await collection.update_one(
                {"id_card.id": result["id"]},  # Query to find the document
                {"$setOnInsert": id_card_result},  # Set the document fields
                upsert=True  # Upsert: insert if not exists, update if exists
            )

            return JSONResponse(content={
                "statusCode": 200,
                "result": id_card_result
            })

    except DuplicateKeyError:
        # Handle the case when the 'id_card.id' is not unique
        raise HTTPException(status_code=400, detail="ID card with this 'id_card.id' already exists")

    except Exception as e:
        return JSONResponse(status_code=500, content={
            "statusCode": 500,
            "error": str(e)
        })


if __name__ == "__main__":
    uvicorn.run(app, port=8000, log_level="debug")
