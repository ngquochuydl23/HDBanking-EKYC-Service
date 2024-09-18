import numpy as np
import torch


def L1_Distance(x, y, reduction = 'sum'):
    
    assert reduction in ['sum', 'mean']
    
    dis = torch.abs(x - y)
    
    if reduction == 'mean':
        return torch.mean(dis)
    
    else:
        return torch.sum(dis)
    
def Euclidean_Distance(x, y):
    # Add a small epsilon to avoid numerical issues in case of very small numbers
    dis = torch.sqrt(torch.sum(torch.square(x - y)) + 1e-8)
    return dis


def Cosine_Distance(x: torch.Tensor, y: torch.Tensor):
    x = x.view(-1)
    y = y.view(-1)

    dot_product = torch.dot(x, y)

    norm_x = torch.norm(x.float())
    norm_y = torch.norm(y.float())

    # Ensure the dot_product / (norm_x * norm_y) is within the valid range for acos [-1, 1]
    cosine_similarity = dot_product / (norm_x * norm_y)
    cosine_similarity = torch.clamp(cosine_similarity, -1.0, 1.0)

    rad = torch.acos(cosine_similarity)

    return rad

def findThreshold(model_name, distance_metric):
    base_threshold = {"cosine": 0.40, "euclidean": 0.55, "L1": 0.75}

    thresholds = {
        "VGG-Face1": {"cosine": 0.40, "euclidean": 0.31, "L1": 1.1}, # L1: sum
        "VGG-Face2": {"cosine": 0.35, "euclidean": 0.62, "L1": 1.4}, # In this case, it has not been tested yet
    }

    threshold = thresholds.get(model_name, base_threshold).get(distance_metric, 0.6)

    return threshold

if __name__ == '__main__':
    x = torch.rand(5)
    y = torch.rand(5)
    
    print(Cosine_Distance(x, y))
    print(L1_Distance(x, y))
    print(Euclidean_Distance(x, y))
     