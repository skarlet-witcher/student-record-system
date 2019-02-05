#!/usr/bin/env bash

echo "Waiting for Elasticsearch to startup (max 5min)"
WAIT=0
while [ $WAIT -lt 300 ]; do
    curl ${ES_HOST}:${ES_PORT}/_cluster/health 2>/dev/null && echo "" && break
    sleep 1
    (( WAIT++ ))
done