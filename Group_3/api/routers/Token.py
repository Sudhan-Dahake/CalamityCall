from fastapi import APIRouter, Depends
from ...supabase_db import RefreshToken
from ..schemas.Token import TokenGenerateRequest

router = APIRouter()

@router.post("/JWT")
async def RefreshJWT(token: TokenGenerateRequest):
    return RefreshToken(token.refresh_token)