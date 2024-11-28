FROM python:3.11.4-slim

WORKDIR /app

COPY requirements.txt /app/requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

COPY Group_3/api/ /app/api/
COPY Group_3/supabase_db/ /app/supabase_db/
COPY Group_3/__init__.py /app/
COPY Group_3/scripts/ /app/scripts

RUN chmod +x /app/scripts/keep_alive.sh

RUN apt-get update && apt-get install -y supervisor && apt-get clean

EXPOSE 8000

CMD ["supervisord", "-c", "/app/api/supervisord.conf"]