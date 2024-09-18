import cv2 as cv
import torch
from PIL import Image
import cv2
from facenet.models.mtcnn import MTCNN
from utils.distance import *
from utils.functions import *
from verification_models import VGGFace, VGGFace2
import math
from scipy.spatial.distance import cosine
def face_matching(face1, face2, model: torch.nn.Module, distance_metric_name, model_name, device = 'cpu'):
    """
    Perform face matching to verify the similarity of two faces using a given distance metric and model.

    Parameters:
        face1: The first face image for comparison.
        face2: The second face image for comparison.
        model (torch.nn.Module): The face recognition model.
        distance_metric_name: The name of the distance metric to be used ('cosine', 'L1', or 'euclidean').
        model_name: The name of the face recognition model.
        device (str, optional): The device on which the model should run (default is 'cpu').

    Returns:
        bool: True if the faces are considered a match, False otherwise.
    """
    distance_metric = {
        "cosine": Cosine_Distance,
        "L1": L1_Distance,
        "euclidean": Euclidean_Distance,
    }
    
    distance_func = distance_metric.get(distance_metric_name, Euclidean_Distance)
    
    device = model.device()
    
    face1 = face_transform(face1, model_name = model_name, device = device)
    face2 = face_transform(face2, model_name = model_name, device = device)

    result1 = model(face1)
    result2 = model(face2)
    result1 = normalize(result1)
    result2 = normalize(result2)
    dis = distance_func(result1, result2)
    threshold = findThreshold(model_name=model_name, distance_metric=distance_metric_name)

    print(f"Distance: {dis}")
    print(f"Threshold: {threshold}")
    return dis < threshold

def normalize(tensor):
    norm = torch.norm(tensor, p=2, dim=1, keepdim=True)
    return tensor / norm

def verify(img1: np.ndarray, img2: np.ndarray, detector_model: MTCNN, verifier_model, model_name = 'VGG-Face2'):
    """
    Verify the similarity between two face images.

    Parameters:
        img1 (np.ndarray): A numpy RGB image containing the first face.
        img2 (np.ndarray): A numpy RGB image containing the second face.
        detector_model (MTCNN): The face detection model used to locate faces in the images.
        verifier_model: The face verification model used for similarity comparison.
        model_name (str, optional): The name of the verification model (default is 'VGG-Face1').

    Returns:
        bool: True if the faces are verified to be similar, False otherwise.
    """
    
    face1, box1, landmarks = extract_face(img1, detector_model, padding = 1.5)
    face2, box2, landmarks = extract_face(img2, detector_model, padding = 1.5)

    verified = face_matching(face1, face2, verifier_model, distance_metric_name = 'cosine', model_name = model_name)
    
    return verified 

if __name__ == '__main__':
    
    filename1 = "E:\\OCR-EKYC-Banking\\api\\HD.Wallet.OCR.Service\\uploads\\tests\\danh-cmnd.jpg"
    filename2 = "E:\\OCR-EKYC-Banking\\api\\HD.Wallet.OCR.Service\\uploads\\tests\\danh-face.jpg"
    
    image1 = get_image(filename1)
    image2 = get_image(filename2)

    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
    
    detector_model = MTCNN(device = device)
    verifier_model = VGGFace2.load_model(device = device)
    
    results = verify(image1, image2, detector_model, verifier_model)
    
    print(results)