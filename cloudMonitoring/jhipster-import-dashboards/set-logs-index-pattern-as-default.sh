#!/usr/bin/env bash
# Set logs-* as the default index pattern
curl ${KIBANA_URL}/api/kibana/settings -H "Origin: ${KIBANA_URL}" -H 'kbn-version: 6.4.1' -H 'content-type: application/json' -H 'accept: application/json' -H "Referer: ${KIBANA_URL}/app/kibana" --data-binary '{"changes":{"defaultIndex":"logs-*"}}'
