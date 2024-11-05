from fastapi import APIRouter, Depends
from ...supabase_db import AuthService

router = APIRouter()

@router.get("/verifytoken")
async def ProtectedTokenVerification(CurrentUser: str = Depends(AuthService().VerifyJWT)):
    return {"message": "This is a protected route", "username": CurrentUser}