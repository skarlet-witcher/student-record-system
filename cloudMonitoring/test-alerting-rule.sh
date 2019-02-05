#!/bin/bash
# Example use: ./test-alerting-rule log_error.yaml
docker exec -it jhipster-console_jhipster-alerter_1 elastalert-test-rule rules/$1
