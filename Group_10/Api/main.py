from fastapi import FastAPI
from routers import discussion, reports

app = FastAPI()

# Register routers
app.include_router(discussion.router, prefix="/api/discussion", tags=["Discussion Board"])
app.include_router(reports.router, prefix="/api/reports", tags=["Disaster Reports"])

# Run the application with `uvicorn main:app --reload`
