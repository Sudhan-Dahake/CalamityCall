from fastapi import APIRouter, HTTPException, Depends, status
from .. import UserModel
from ..schemas.Users import UserRequest, UserResponse, UserUpdate

router = APIRouter()

@router.post("/", status_code=status.HTTP_201_CREATED)
async def CreateUser(user: UserRequest):
    UserModelObj = UserModel()

    isSuccessful = UserModelObj.CreateUser(**user.model_dump())

    if isSuccessful:
        return {"message": "User Created Successfully"}
    
    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Create new user")

@router.get("/get", response_model=UserResponse)
async def GetUser(userid: int | None = None, username: str | None = None):
    UserModelObj = UserModel()

    response = UserModelObj.GetUser(userID=userid, username=username)

    if response:
        return response
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User Not Found")

@router.post("/update")
async def UpdateUser(username: str, NewUserCreds: UserUpdate):
    UserModelObj = UserModel()

    NewUserCreds = NewUserCreds.model_dump(exclude_none=True)

    response = UserModelObj.UpdateUser(username=username, **NewUserCreds)

    if response:
        return {"Message": f"Information Updated Successfully for {username}"}
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User Not Found")