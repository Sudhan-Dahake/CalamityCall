from fastapi import FastAPI
from .routers import discussion, reports, disaster_reports

app = FastAPI()

# Root route
@app.get("/")
async def home():
    return {"message": "Welcome to Group 10's Disaster Report API"}

# Register routers
# app.include_router(discussion.router, prefix="/api/discussion", tags=["Discussion Board"])
# app.include_router(reports.router, prefix="/api/reports", tags=["Disaster Reports"])
app.include_router(disaster_reports.router, prefix="/api", tags=["Disaster Reports"])

