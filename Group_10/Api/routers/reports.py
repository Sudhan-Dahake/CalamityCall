from fastapi import APIRouter
from schemas.reports import DisasterReportCreate
from ...supabase_db.models import DisasterReportsModel

router = APIRouter()

@router.post("/disaster-reports/")
def create_disaster_report(report: DisasterReportCreate):
    report_model = DisasterReportsModel()
    return report_model.CreateReport(**report.dict())
