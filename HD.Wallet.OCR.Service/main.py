import random
import os
import sys
import time
import uvicorn
import io
import shutil

import numpy as np
from readInfoIdCard import ReadInfo
from DetecInfoBoxes.GetBoxes import GetDictionary
from core.tool.predictor import Predictor
from core.tool.config import Cfg as Cfg_vietocr

from starlette.responses import RedirectResponse
from PIL import Image
from fastapi.responses import JSONResponse
from fastapi import FastAPI, File, UploadFile

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

app = FastAPI(title="Tensorflow FastAPI Start Pack")


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
async def predict_api(file: UploadFile = File(...)):
    
    extension = file.filename.split(".")[-1] in ("jpg", "jpeg", "png", "webp")
    if not extension:
        return "Image must be jpg or png format!"

    try:
        save_path = os.path.join('uploads/identity-cart/', file.filename)
        with open(save_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        result = readInfo.get_all_info(save_path)

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
                "filename": file.filename,
                "result": result
            })          

    except Exception as e:
        return JSONResponse(status_code=500, content={"error": str(e)})



if __name__ == "__main__":
    uvicorn.run(app, port=8000, log_level="debug")


