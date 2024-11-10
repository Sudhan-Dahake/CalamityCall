from fastapi import APIRouter, Depends, HTTPException, status
from ...supabase_db import AuthService, PreferencesModel, UserModel
from ..schemas.Auth import LoginRequest, SignupRequest

router = APIRouter()

@router.post("/login")
async def Login(request: LoginRequest):
    return AuthService().LoginUser(username=request.username, password=request.password)

@router.post("/signup")
async def Signup(request: SignupRequest):
    PreferenceModelObj = PreferencesModel()

    request.preferenceid = PreferenceModelObj.CreatePreferenceSet()

    if request.preferenceid is None:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Create a Preference for the user.")

    UserModelObj = UserModel()

    isSuccessful = UserModelObj.CreateUser(**request.model_dump())

    if isSuccessful:
        return {"message": "User Created Successfully"}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Create new user")