#!/bin/bash
while true; do
    curl -s "http://localhost:8000" > /dev/null
    echo "Ping sent at $(date)"
    sleep 600   # 10 minutes
done