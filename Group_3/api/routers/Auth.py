from fastapi import APIRouter, Depends
from ...supabase_db import LoginUser
from ..schemas.Auth import LoginRequest

router = APIRouter()

@router.post("/login")
async def Login(request: LoginRequest):
    return LoginUser(username=request.username, password=request.password)