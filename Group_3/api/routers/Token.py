from fastapi import APIRouter, Depends
from ...supabase_db import AuthService
from ..schemas.Token import TokenGenerateRequest

router = APIRouter()

@router.post("/JWT")
async def RefreshJWT(token: TokenGenerateRequest):
    AuthServiceObj = AuthService()
    return AuthServiceObj.RefreshToken(token.refresh_token)