import os
import sys
import uvicorn
import shutil
import logging
import mimetypes
from ocr.readInfoIdCard import ReadInfo
from DetecInfoBoxes.GetBoxes import GetDictionary
from ocr.core.tool.predictor import Predictor
from ocr.core.tool.config import Cfg as Cfg_vietocr
from ocr.DetecInfoBoxes.GetBoxes import GetDictionary

sys.path.insert(0, 'DetecInfoBoxes')
script_dir = os.path.dirname(__file__)

class OcrExtractIdCard:
    def __init__(self):

        # load ocr model

        config_path = os.path.join(script_dir, 'core/config/vgg-seq2seq.yml')

        self.config_vietocr = Cfg_vietocr.load_config_from_file('core/config/vgg-seq2seq.yml')
        self.config_vietocr['weights'] = './models/seq2seqocr.pth'
        self.config_vietocr['device'] = 'cpu'
        self.ocr_predictor = Predictor(self.config_vietocr)

        # load yolo model
        get_dictionary = GetDictionary()
        opt = {
            "img-size": 800,
            "conf-thres": 0.5,
            "iou-thres": 0.15,
            "device": 'cpu',
        }
        scan_weight = 'models/cccdYoloV7.pt'
        imgsz, stride, device, half, model, names = get_dictionary.load_model(scan_weight, opt)
        self.read_info = ReadInfo(imgsz, stride, device, half, model, names, opt, self.ocr_predictor)

    def extract(self, path):
        return self.read_info.get_all_info(path)

if __name__ == '__main__':
    ocr_extract_idcard = OcrExtractIdCard()
    result = ocr_extract_idcard.extract("C:\\Users\\admin\\Desktop\\z5840606231660_89a6b9e479bcf6f72206cfab4acfd1ec.jpg")
    print(result)
