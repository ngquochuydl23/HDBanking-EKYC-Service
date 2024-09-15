import logging

from contextlib import asynccontextmanager
from motor.motor_asyncio import AsyncIOMotorClient
from pymongo import MongoClient
from fastapi import FastAPI
from typing import AsyncIterator

# Replace the URI with your MongoDB connection string
MONGODB_URL = "mongodb+srv://root:d23ca9b3-7e83-46b2-8419-0e8fafadf4e6@cloudmongodb.p6h7n.mongodb.net/HayugoV2?authSource=admin"

async def lifespan(app: FastAPI) -> AsyncIterator[None]:
    client = AsyncIOMotorClient(MONGODB_URL)
    app.state.database = client["eWallet-IdCard"]  # Replace with your database name

    collection = app.state.database["IDCards"]
    try:
        # Verify connection
        await collection.create_index([("id_card.id", 1)], unique=True)
        await app.state.database.list_collection_names()
        logging.info("Database connected successfully")
        yield
    except Exception as e:
        logging.error(f"Database connection failed: {e}")
        app.state.database = None
        raise RuntimeError("Failed to connect to the database")
    finally:
        client.close()
        logging.info("Database connection closed")