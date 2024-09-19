import cv2 as cv
import torch
from PIL import Image
import cv2
from ekyc.facenet.models.mtcnn import MTCNN
from ekyc.utils.distance import *
from ekyc.utils.functions import *
from ekyc.verification_models import VGGFace, VGGFace2
import math
import numpy as np
from scipy.spatial.distance import cosine


class FaceVerification:
    def __init__(self):
        self.device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

        self.detector_model = MTCNN(device=self.device)
        self.verifier_model = VGGFace2.load_model(device=self.device)

    def face_matching(self, face1, face2, distance_metric_name, model_name):
        distance_metric = {
            "cosine": Cosine_Distance,
            "L1": L1_Distance,
            "euclidean": Euclidean_Distance,
        }

        distance_func = distance_metric.get(distance_metric_name, Euclidean_Distance)

        device = self.verifier_model.device()

        face1 = face_transform(face1, model_name=model_name, device=device)
        face2 = face_transform(face2, model_name=model_name, device=device)

        result1 = self.verifier_model(face1)
        result2 = self.verifier_model(face2)

        result1 = self.normalize(result1)
        result2 = self.normalize(result2)
        dis = distance_func(result1, result2)
        threshold = findThreshold(model_name=model_name, distance_metric=distance_metric_name)

        print(f"Distance: {dis}")
        print(f"Threshold: {threshold}")

        verified = dis < threshold
        return {
            'distance': dis.item(),
            'threshold': threshold,
            'verified': verified.item()
        }

    def normalize(self, tensor):
        norm = torch.norm(tensor, p=2, dim=1, keepdim=True)
        return tensor / norm

    def verify(self, img1: np.ndarray, img2: np.ndarray, model_name='VGG-Face2', distance_metric_name='cosine'):
        face1, box1, landmarks = extract_face(img1, self.detector_model, padding=1.5)
        face2, box2, landmarks = extract_face(img2, self.detector_model, padding=1.5)

        matching_result = self.face_matching(face1, face2, distance_metric_name=distance_metric_name, model_name=model_name)

        return matching_result


if __name__ == '__main__':
    filename1 = "C:\\Users\\admin\\Desktop\\z5840606231660_89a6b9e479bcf6f72206cfab4acfd1ec.jpg"
    filename2 = "C:\\Users\\admin\\Desktop\\z5840597901060_0921ab67235de3e97695bb00a227f5bd.jpg"

    image1 = get_image(filename1)
    image2 = get_image(filename2)

    face_verification = FaceVerification()
    face_verification.verify(image1, image2)
