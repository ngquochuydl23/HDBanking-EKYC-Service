FROM tensorflow/tensorflow:2.17.0
FROM pytorch/pytorch

WORKDIR /app

RUN apt-get update && apt-get install -y libglib2.0-0 libgl1-mesa-glx && rm -rf /var/lib/apt/lists/*

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY . .
EXPOSE 8000
ENV UVICORN_CMD="uvicorn main:app --host 0.0.0.0 --port 8000 --reload"
CMD ["sh", "-c", "$UVICORN_CMD"]