import uuid
from pathlib import Path
def create_unique_filename(file):
    unique_filename = f"{uuid.uuid4().hex}{Path(file.filename).suffix}"
    return unique_filename