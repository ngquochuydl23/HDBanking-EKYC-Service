import random
import os
import sys
import time
import uvicorn
import io
import shutil
import logging


from readInfoIdCard import ReadInfo
from DetecInfoBoxes.GetBoxes import GetDictionary
from core.tool.predictor import Predictor
from core.tool.config import Cfg as Cfg_vietocr

from starlette.responses import RedirectResponse
from fastapi.responses import JSONResponse
from fastapi import FastAPI, File, UploadFile, Form
from app.utils.uploaded_file_utils import check_file_extension
from app.db.mongodb import lifespan
from contextlib import asynccontextmanager

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
async def get_idcard_by_no():
    return JSONResponse(content={
        "filename": file.filename,
        "result": result
    })


@app.post("/id-card/extract")
async def predict_api(
        front_id_card: UploadFile = File(...),
        back_id_card: UploadFile = File(...),
        phone: str = Form(...)
):
    if not check_file_extension(front_id_card):
        return "Front IdCard must be jpg or png format!"

    if not check_file_extension(back_id_card):
        return "Back IdCard must be jpg or png format!"

    try:
        front_save_path = os.path.join('uploads/identity-cart/', front_id_card.filename)
        back_save_path = os.path.join('uploads/identity-cart/', back_id_card.filename)

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
            # mongo insert data
            # insert doc
            # {
            #    phoneNumber: 0868684961
            #    email: nguyenquochuydl123@gmail.com
            #    path: uploads/identity-cart/
            #    extract_result: {
            #
            #    }
            #
            # }
            return JSONResponse(content={
                "statusCode": 200,
                "result": {
                    "id-card": result,
                    "front-url": '/' + front_save_path,
                    "back-url": '/' + back_save_path,
                    "type": "CCCD" if is_cccd else "CMND",
                }
            })

    except Exception as e:
        return JSONResponse(status_code=500, content={
            "statusCode": 500,
            "error": str(e)
        })


if __name__ == "__main__":
    uvicorn.run(app, port=8000, log_level="debug")
