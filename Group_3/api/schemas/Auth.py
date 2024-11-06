from pydantic import BaseModel

# Pydantic model for Auth route
class LoginRequest(BaseModel):
    username: str
    password: str

class SignupRequest(BaseModel):
    username: str
    password: str
    preferenceid: int = None
    age: int
    address: str
    zip_code: str
    city: str