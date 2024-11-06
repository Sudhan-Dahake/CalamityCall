from pydantic import BaseModel

class TokenGenerateRequest(BaseModel):
    refresh_token: str