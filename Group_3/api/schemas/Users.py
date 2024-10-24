from pydantic import BaseModel

class UserRequest(BaseModel):
    username: str
    password: str
    preferenceid: int
    age: int
    address: str
    zip_code: str
    city: str

class UserResponse(BaseModel):
    username: str
    preferenceid: int
    age: int
    address: str
    zip_code: str
    city: str

class UserUpdate(BaseModel):
    username: str | None = None
    password: str | None = None
    age: int | None = None
    address: str | None = None
    zip_code: str | None = None
    city: str | None = None