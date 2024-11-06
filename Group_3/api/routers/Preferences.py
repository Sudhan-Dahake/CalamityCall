from fastapi import APIRouter, HTTPException, Depends, status
from .. import PreferencesModel
from ..schemas.Preferences import PreferenceCreateResponse, PreferenceResponse, PreferenceUpdate
from ...supabase_db import AuthService, UserServices

router = APIRouter()

AuthServiceObj = AuthService()


# @router.post("/", response_model=PreferenceCreateResponse, status_code=status.HTTP_201_CREATED)
# async def InitializePreference(username: str = Depends(AuthServiceObj.VerifyJWT)):
#     PreferModel = PreferencesModel()

#     PreferenceID: int = PreferModel.CreatePreference()

#     if PreferenceID:
#         return {"PreferenceID": PreferenceID}

#     raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Initialize a Preference.")

@router.get("/get", response_model=PreferenceResponse)
async def GetPreference(username: str = Depends(AuthServiceObj.VerifyJWT)):
    userServicesObj = UserServices()

    preference_id = userServicesObj.GetPreferenceIDFromUser(username=username)

    if preference_id is None:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to retrieve Preferences")

    PreferModel = PreferencesModel()

    response = PreferModel.GetPreference(preference_id)

    if response:
        return response

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Preference Not Found")

@router.post("/update")
async def UpdatePreference(NewPreferences: PreferenceUpdate, username: str = Depends(AuthServiceObj.VerifyJWT)):
    userServicesObj = UserServices()

    preference_id = userServicesObj.GetPreferenceIDFromUser(username=username)

    if preference_id is None:
        raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to retrieve Preferences")

    PreferModel = PreferencesModel()

    NewPreferences = NewPreferences.model_dump(exclude_none=True)

    response = PreferModel.UpdatePreference(preference_id=preference_id, **NewPreferences)

    if response:
        return {"Message": "Preference Updated Successfully"}

    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Preference Not Found")