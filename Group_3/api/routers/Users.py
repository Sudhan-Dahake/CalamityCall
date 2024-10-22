from fastapi import APIRouter, HTTPException, Depends, status
from .. import UserModel
from ..schemas.Users import UserRequest, UserResponse, UserUpdate

router = APIRouter()

@router.post("/", status_code=status.HTTP_201_CREATED)
async def CreateUser(user: UserRequest):
    UserModelObj = UserModel()

    userid = UserModelObj.CreateUser(**user.model_dump())

    if userid:
        return {"UserID": userid}
    
    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Create new user")

@router.get("/get", response_model=UserResponse)
async def GetUser(userid: int):
    UserModelObj = UserModel()

    response = UserModelObj.GetUser(userid)

    if response:
        return response
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User Not Found")

@router.post("/update")
async def UpdateUser(userid: int, NewUserCreds: UserUpdate):
    UserModelObj = UserModel()

    NewUserCreds = NewUserCreds.model_dump(exclude_none=True)

    response = UserModelObj.UpdateUser(user_id=userid, **NewUserCreds)

    if response:
        return {"Message": "User Information Updated Successfully"}
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User Not Found")