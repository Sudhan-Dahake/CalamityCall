from fastapi import FastAPI
from .routers import Notifications

app = FastAPI()

# including the routers for each module
app.include_router(Notifications.router, prefix="/notifications", tags=["Notifications"])