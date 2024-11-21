# from fastapi import FastAPI
# from .routers import discussion, reports, disaster_reports
# from routers import discussion, reports, disaster_reports

# app = FastAPI()



# # Register routers
# app.include_router(discussion.router, prefix="/api/discussion", tags=["Discussion Board"])
# app.include_router(reports.router, prefix="/api/reports", tags=["Disaster Reports"])
# app.include_router(disaster_reports.router, prefix="/api", tags=["Disaster Reports"])
# # Run the application with `uvicorn main:app --reload`


from fastapi import FastAPI
from .routers import disaster_reports  # Import only what is being used

app = FastAPI()

# Register routers
app.include_router(disaster_reports.router, prefix="/api/disaster-reports", tags=["Disaster Reports"])

# Root route
@app.get("/")
async def home():
    return {"message": "Welcome to Group 10's Disaster Report API"}
