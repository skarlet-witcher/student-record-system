#!/bin/bash
# Trigger the Zipkin dependency job (necessary to see the dependency graph)
docker-compose exec jhipster-zipkin ./run-job.sh