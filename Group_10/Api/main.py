from fastapi import FastAPI
from .routers import disaster_reports 

app = FastAPI()

# Register routers
app.include_router(disaster_reports.router, prefix="/report", tags=["Report"])

# Root route
# @app.get("/")
# async def home():
#     return {"message": "Welcome to Group 10's Disaster Report API"}
