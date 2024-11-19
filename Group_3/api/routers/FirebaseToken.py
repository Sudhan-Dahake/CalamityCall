from fastapi import APIRouter, HTTPException, status
from ...supabase_db.models.FirebaseTokenStorage import FirebaseTokenStorageModel
from ..firebase.FirebaseNotificationManager import NotificationManager
from ..schemas.FirebaseToken import TokenRegistrationRequest
from ...supabase_db.schemas.FirebaseTokenStorage import FirebaseTokenCreate

router = APIRouter()

tokenStorage = FirebaseTokenStorageModel()
notificationManager = NotificationManager()

@router.post("/registertoken")
async def RegisterToken(request: TokenRegistrationRequest):
    success = tokenStorage.CreateToken(request)

    if success:
        return {"status": "Token registered successfully."}

    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Error storing token")