from fastapi import APIRouter, Depends
from ...supabase_db import AuthService
from ..schemas.Auth import LoginRequest

router = APIRouter()

@router.post("/login")
async def Login(request: LoginRequest):
    return AuthService().LoginUser(username=request.username, password=request.password)