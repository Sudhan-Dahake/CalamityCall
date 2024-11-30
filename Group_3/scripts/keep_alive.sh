#!/bin/bash
while true; do
    curl -s "https://syncpoint-api.onrender.com" > /dev/null
    echo "Ping sent at $(date)"
    sleep 890   # 10 minutes
done