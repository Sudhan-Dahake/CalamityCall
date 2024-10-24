from fastapi import APIRouter, Depends
from ...supabase_db import VerifyJWT

router = APIRouter()

@router.get("/verifytoken")
async def ProtectedTokenVerification(CurrentUser: str = Depends(VerifyJWT)):
    return {"message": "This is a protected route", "username": CurrentUser}