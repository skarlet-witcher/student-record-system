KIBANA_URL=http://localhost:5601

go run export_dashboards.go -kibana $KIBANA_URL -dashboard 922ffeb0-e17d-11e7-9c68-0b9a0f0c183c -output dashboards/jvm-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard 13aa7630-e0f4-11e7-9c68-0b9a0f0c183c -output dashboards/load-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard b2c61940-e180-11e7-9c68-0b9a0f0c183c -output dashboards/circuit-breaker-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard d712f650-e0eb-11e7-9c68-0b9a0f0c183c -output dashboards/logs-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard 607a1ab0-e0ef-11e7-9c68-0b9a0f0c183c -output dashboards/metrics-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard 4161cd50-9c77-11e8-bfa6-a7fdcbb4d022 -output dashboards/alerting-dashboard.json
go run export_dashboards.go -kibana $KIBANA_URL -dashboard 81d34a00-bfd4-11e8-b5e5-cbdd0005bb7b -output dashboards/traces-dashboard.json

