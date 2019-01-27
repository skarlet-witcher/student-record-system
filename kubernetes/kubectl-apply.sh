#!/bin/bash
# Files are ordered in proper order with needed wait for the dependent custom resource definitions to get initialized.
# Usage: bash kubectl-apply.sh

kubectl label namespace default istio-injection=enabled
kubectl apply -f srs/
kubectl apply -f srs/
kubectl apply -f srs/
