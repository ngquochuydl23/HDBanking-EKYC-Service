from pydantic import BaseModel, Field
from typing import Optional


class IdCardDetails(BaseModel):
    id: str
    full_name: str
    date_of_birth: str
    sex: Optional[str] = None  # Optional field
    nationality: Optional[str] = None  # Optional field
    place_of_origin: str
    place_of_residence: str
    date_of_expiry: Optional[str] = None  # Optional field


class IdCardModel(BaseModel):
    id_card: IdCardDetails
    front_url: str = Field(..., alias="front-url")
    back_url: str = Field(..., alias="back-url")
    type: str