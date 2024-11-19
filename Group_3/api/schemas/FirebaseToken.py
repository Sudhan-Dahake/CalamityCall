from pydantic import BaseModel

class TokenRegistrationRequest(BaseModel):
    fcmtoken: str
    userid: int | None = None
    deviceid : str