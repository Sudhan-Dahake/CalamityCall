import jwt
from datetime import datetime, timedelta, timezone
from fastapi import HTTPException, Depends, status
from fastapi.security import OAuth2PasswordBearer
from passlib.context import CryptContext
from ...supabase_db import UserModel
import os
from dotenv import load_dotenv

# using OAuth2PasswordBearer to extract the token from Authorization Header
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

load_dotenv()


class AuthService:
    JWT_SECRET_KEY = os.getenv("JWT_SECRET_KEY")
    REFRESH_SECRET_KEY = os.getenv("REFRESH_SECRET_KEY")
    ALGORITHM = os.getenv("ALGORITHM")
    ACCESS_TOKEN_EXPIRE_MINUTES = int(os.getenv("ACCESS_TOKEN_EXPIRE_MINUTES"))
    REFRESH_TOKEN_EXPIRE_DAYS = int(os.getenv("REFRESH_TOKEN_EXPIRE_DAYS"))

    def __init__(self):
        from ...supabase_db import UserServices
        self.userModelObj = UserModel()
        self.userServices = UserServices(userModelObj=self.userModelObj)

    # Login user (authentication)
    def LoginUser(self, username: str, password: str):
        if not self.userServices.AuthenticateUser(username=username, password=password):
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid Credentials")

        user = self.userModelObj.GetUser(username=username)

        user_data = {key: value for key,
                     value in user.items() if key != "password"}

        # Generate tokens after successful authentication
        access_token = self.CreateAccessToken(
            data={"sub": user_data['username']})
        refresh_token = self.CreateRefreshToken(
            data={"sub": user_data['username']})

        return {
            "access_token": access_token,
            "refresh_token": refresh_token,
            "token_type": "bearer",
            "user": user_data
        }

    # Generate JWT access token

    def CreateAccessToken(self, data: dict):
        ToEncode = data.copy()

        expire = datetime.now(timezone.utc) + \
            timedelta(minutes=self.ACCESS_TOKEN_EXPIRE_MINUTES)

        ToEncode.update({"exp": expire})

        EncodedJWT = jwt.encode(
            ToEncode, self.JWT_SECRET_KEY, algorithm=self.ALGORITHM)

        return EncodedJWT

    # Generate Refresh token

    def CreateRefreshToken(self, data: dict):
        ToEncode = data.copy()

        expire = datetime.now(timezone.utc) + \
            timedelta(days=self.REFRESH_TOKEN_EXPIRE_DAYS)

        ToEncode.update({"exp": expire})

        EncodedRefreshToken = jwt.encode(
            ToEncode, self.REFRESH_SECRET_KEY, algorithm=self.ALGORITHM)

        return EncodedRefreshToken

    # Validate JWT

    def VerifyJWT(self, token: str = Depends(oauth2_scheme)):
        try:
            payload = jwt.decode(token, self.JWT_SECRET_KEY,
                                 algorithms=[self.ALGORITHM])

            username = payload.get("sub")

            if username is None:
                raise HTTPException(
                    status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid JWT", headers={"error_code": "Invalid_JWT"})

            return username

        except jwt.ExpiredSignatureError:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED, detail="JWT has expired", headers={"error_code": "JWT_Expired"})

        except jwt.PyJWTError:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid JWT", headers={"error_code": "Invalid_JWT"})

    # Generate New JWT using valid Refresh token

    def RefreshToken(self, refresh_token: str):
        try:
            payload = jwt.decode(
                refresh_token, self.REFRESH_SECRET_KEY, algorithms=[self.ALGORITHM])

            username = payload.get("sub")

            if username is None:
                raise HTTPException(
                    status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid Refresh Token", headers={"error_code": "Invalid_Refresh_Token"})

            new_access_token = self.CreateAccessToken(data={"sub": username})

            return {"access_token": new_access_token, "token_type": "bearer"}

        except jwt.ExpiredSignatureError:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED, detail="Refresh Token has expired", headers={"error_code": "Refresh_Token_Expired"})

        except jwt.PyJWTError:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid Refresh Token", headers={"error_code": "Invalid_Refresh_Token"})
