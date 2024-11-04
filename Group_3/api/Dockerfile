FROM python:3.11.4-slim

WORKDIR /app

COPY requirements.txt /app/requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

COPY Group_3/api/ /app/api/
COPY Group_3/supabase_db/ /app/supabase_db/
COPY Group_3/__init__.py /app/


EXPOSE 8000

CMD ["fastapi", "run", "api/main.py", "--host", "0.0.0.0", "--port", "8000"]