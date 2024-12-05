FROM python:3.11.4-slim

WORKDIR /app

COPY requirements.txt /app/requirements.txt

RUN pip install --no-cache-dir -r requirements.txt

COPY Group_3/api/ /app/api/
COPY Group_3/supabase_db/ /app/supabase_db/
COPY Group_3/__init__.py /app/
COPY Group_3/scripts/ /app/scripts

RUN chmod +x /app/scripts/keep_alive.sh

# Install dependencies
RUN apt-get update && \
  apt-get install -y --no-install-recommends \
  curl \
  ca-certificates \
  cron \
  supervisor && \
  apt-get clean && \
  rm -rf /var/lib/apt/lists/*

# Update CA certificates during the build
RUN update-ca-certificates

# Add a cron job for periodic certificate updates
RUN echo "0 0 * * * root update-ca-certificates" > /etc/cron.d/update-certs && \
  chmod 0644 /etc/cron.d/update-certs && \
  crontab /etc/cron.d/update-certs

EXPOSE 8000

CMD ["supervisord", "-c", "/app/api/supervisord.conf"]