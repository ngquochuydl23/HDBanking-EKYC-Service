import torch
import numpy as np
from deepface import DeepFace
from tensorflow.python.keras import layers, models
import os


class FaceVerification:
    def __init__(self, model_name='VGG-Face', weights_path='ekyc/weights/vgg_face_weights.h5'):
        self.model_name = model_name
        self.weights_path = weights_path

    def verify(self, img1_path, img2_path, metric='cosine'):
        # metrics = ["cosine", "euclidean", "euclidean_l2"]
        DeepFace.build_model('VGG-Face')
        result = DeepFace.verify(
            img1_path=img1_path,
            img2_path=img2_path,
            model_name='VGG-Face',
            enforce_detection=False
        )
        custom_threshold = 0.50

        return {
            'verified': result['distance'] < custom_threshold,
            'distance': result['distance'],
            'threshold': custom_threshold
        }
