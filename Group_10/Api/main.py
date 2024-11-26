from fastapi import FastAPI
from routers import discussion, reports, disaster_reports

app = FastAPI()

# Register routers
app.include_router(discussion.router, prefix="/api/discussion", tags=["Discussion Board"])
app.include_router(reports.router, prefix="/api/reports", tags=["Disaster Reports"])
app.include_router(disaster_reports.router, prefix="/api", tags=["Disaster Reports"])
# Run the application with `uvicorn main:app --reload`
