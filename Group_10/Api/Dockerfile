# FROM python:3.11.4-slim

# WORKDIR /app

# # Copy requirements.txt and install dependencies
# COPY ../requirements.txt /app/requirements.txt
# RUN pip install --no-cache-dir -r requirements.txt

# # Copy the Group_10/Api directory
# COPY ../Group_10/Api/ /app/Api/

# # Copy the Group_10/supabase_db directory
# COPY ../Group_10/supabase_db/ /app/supabase_db/

# # Copy the root-level __init__.py
# COPY ../Group_10/__init__.py /app/Group_10/

# EXPOSE 8000

# CMD ["uvicorn", "Api.main:app", "--host", "0.0.0.0", "--port", "8000"]


FROM python:3.11.4-slim

WORKDIR /app

COPY requirements.txt /app/requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

COPY Group_10/Api/ /app/api/
COPY Group_10/supabase_db/ /app/supabase_db/
COPY Group_10/__init__.py /app/


EXPOSE 8000

CMD ["fastapi", "run", "api/main.py", "--host", "0.0.0.0", "--port", "8000"]
