import os
import sys
from ocr.readInfoIdCard import ReadInfo
from ocr.core.tool.predictor import Predictor
from ocr.core.tool.config import Cfg as Cfg_vietocr
from ocr.DetecInfoBoxes.GetBoxes import GetDictionary

sys.path.insert(0, 'ocr/DetecInfoBoxes')
script_dir = os.path.dirname(__file__)

class OcrExtractIdCard:
    def __init__(self):

        # load ocr model
        self.config_vietocr = Cfg_vietocr.load_config_from_file('ocr/core/config/vgg-seq2seq.yml')
        self.config_vietocr['weights'] = 'ocr/models/seq2seqocr.pth'
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
        scan_weight = 'ocr/models/cccdYoloV7.pt'
        imgsz, stride, device, half, model, names = get_dictionary.load_model(scan_weight, opt)
        self.read_info = ReadInfo(imgsz, stride, device, half, model, names, opt, self.ocr_predictor)

    def extract(self, path):
        return self.read_info.get_all_info(path)