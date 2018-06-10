#!/usr/bin/env bash

echo "******* Stopping"
docker-compose down

echo "******* Pulling source code"
git pull

echo "******* Building source code"
mvn clean package docker:build

echo "******* Running"
docker-compose up

