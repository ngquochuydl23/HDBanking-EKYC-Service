from fastapi import APIRouter
from fastapi.responses import JSONResponse, StreamingResponse
from fastapi import FastAPI, File, UploadFile, Request, Query, HTTPException

router = APIRouter()

@router.get("/id-card/")
async def get_idcard_by_no(request: Request, id_card_no: str = Query(...)):
    database = request.app.state.database
    try:
        if database is None:
            raise HTTPException(status_code=500, detail="Database connection not available")

        collection = database["IDCards"]  # Access the collection

        id_card = await collection.find_one({"id_card.id": id_card_no})
        if id_card:
            if "_id" in id_card:
                id_card["_id"] = str(id_card["_id"])

            return JSONResponse(content={
                "statusCode": 200,
                "result": id_card
            })

        return JSONResponse(status_code=404, content={
            "statusCode": 404,
            "error": f"ID card with number {id_card_no} not found"
        })

    except Exception as e:
        return JSONResponse(status_code=500, content={
            "statusCode": 500,
            "error": str(e)
        })
