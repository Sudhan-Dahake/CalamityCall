from fastapi import FastAPI
from .routers import Notifications
from .routers import Preferences

app = FastAPI()

# including the routers for each module
app.include_router(Notifications.router, prefix="/notifications", tags=["Notifications"])
app.include_router(Preferences.router, prefix="/preferences", tags=["Preferences"])