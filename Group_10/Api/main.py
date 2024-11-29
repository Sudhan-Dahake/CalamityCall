from fastapi import FastAPI
from .routers import disaster_reports
from .routers.forum_router import forum_router

app = FastAPI()

# Register routers
app.include_router(disaster_reports.router, prefix="/report", tags=["Report"])
app.include_router(forum_router, prefix="/forum", tags=["Forum"])

# Root route
# @app.get("/")
# async def home():
#     return {"message": "Welcome to Group 10's Disaster Report API"}
