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

JWT_SECRET_KEY = os.getenv("JWT_SECRET_KEY")
REFRESH_SECRET_KEY = os.getenv("REFRESH_SECRET_KEY")
ALGORITHM = os.getenv("ALGORITHM")
ACCESS_TOKEN_EXPIRE_MINUTES = int(os.getenv("ACCESS_TOKEN_EXPIRE_MINUTES"))
REFRESH_TOKEN_EXPIRE_DAYS = int(os.getenv("REFRESH_TOKEN_EXPIRE_DAYS"))


# Login user (authentication)
def LoginUser(username: str, password: str):
    userModelObj = UserModel()

    user = userModelObj.GetUser(username=username)

    if user is None:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid Credentials")
    

    # Generate tokens after successful authentication
    access_token = CreateAccessToken(data={"sub": user['username']})
    refresh_token = CreateRefreshToken(data={"sub": user['username']})


    return {
        "access_token": access_token,
        "refresh_token": refresh_token,
        "token_type": "bearer",
        "user": user
    }


# Generate JWT access token
def CreateAccessToken(data: dict):
    ToEncode = data.copy()

    expire = datetime.now(timezone.utc) + timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)

    ToEncode.update({"exp": expire})

    EncodedJWT = jwt.encode(ToEncode, JWT_SECRET_KEY, algorithm=ALGORITHM)

    return EncodedJWT


# Generate Refresh token
def CreateRefreshToken(data: dict):
    ToEncode = data.copy()

    expire = datetime.now(timezone.utc) + timedelta(days=REFRESH_TOKEN_EXPIRE_DAYS)

    ToEncode.update({"exp": expire})

    EncodedRefreshToken = jwt.encode(ToEncode, REFRESH_SECRET_KEY, algorithm=ALGORITHM)

    return EncodedRefreshToken


# Validate JWT
def VerifyJWT(token: str = Depends(oauth2_scheme)):
    try:
        payload = jwt.decode(token, JWT_SECRET_KEY, algorithms=[ALGORITHM])

        username = payload.get("sub")

        if username is None:
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detial="Invalid Token")
        
        return username
    
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Token has expired")
    
    except jwt.PyJWTError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid Token")
    

# Generate New JWT using valid Refresh token
def RefreshToken(refresh_token: str):
    try:
        payload = jwt.decode(refresh_token, REFRESH_SECRET_KEY, algorithms=[ALGORITHM])

        username = payload.get("sub")

        if username is None:
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid Refresh Token")
        
        new_access_token = CreateAccessToken(data={"sub": username})

        return {"access_token": new_access_token, "token_type": "bearer"}
    
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Refresh Token has expired")
    
    except jwt.PyJWTError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid Refresh Token")