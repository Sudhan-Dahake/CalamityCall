from fastapi import APIRouter, HTTPException, Depends, status
from .. import PreferencesModel
from ..schemas.Preferences import PreferenceCreateResponse, PreferenceResponse, PreferenceUpdate

router = APIRouter()


@router.post("/", response_model=PreferenceCreateResponse, status_code=status.HTTP_201_CREATED)
async def InitializePreference():
    PreferModel = PreferencesModel()

    PreferenceID: int = PreferModel.CreatePreference()

    if PreferenceID:
        return {"PreferenceID": PreferenceID}
    
    raise HTTPException(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR, detail="Failed to Initialize a Preference.")

@router.get("/get", response_model=PreferenceResponse)
async def GetPreference(preference_id: int):
    PreferModel = PreferencesModel()

    response = PreferModel.GetPreference(preference_id)

    if response:
        return response
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Preference Not Found")

@router.post("/update")
async def UpdatePreference(preference_id: int, NewPreferences: PreferenceUpdate):
    PreferModel = PreferencesModel()

    NewPreferences = NewPreferences.model_dump(exclude_none=True)

    response = PreferModel.UpdatePreference(preference_id=preference_id, **NewPreferences)

    if response:
        return {"Message": "Preference Updated Successfully"}
    
    raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Preference Not Found")