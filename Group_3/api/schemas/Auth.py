from pydantic import BaseModel

# Pydantic model for Auth route
class LoginRequest(BaseModel):
    username: str
    password: str