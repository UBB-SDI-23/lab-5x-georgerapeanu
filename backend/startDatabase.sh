#!/bin/bash

# Check that all required environmental variables are set
if [[ -z "${DATABASE_VOLUME}" ]]; then
  echo "DATABASE_VOLUME is not set"
  exit 1
fi

if [[ -z "${DATABASE_CONTAINER}" ]]; then
  echo "DATABASE_CONTAINER is not set"
  exit 1
fi

if [[ -z "${DATABASE_PASSWORD}" ]]; then
  echo "DATABASE_PASSWORD is not set"
  exit 1
fi

if [[ -z "${DATABASE_USER}" ]]; then
  echo "DATABASE_USER is not set"
  exit 1
fi

if [[ -z "${DATABASE_PORT}" ]]; then
  echo "DATABASE_PORT is not set"
  exit 1
fi

# Create container if necessary
if [[ $( docker ps -a -f name=$DATABASE_CONTAINER | wc -l ) -ne 2 ]]; then
  docker run --name $DATABASE_CONTAINER \
             -e POSTGRES_PASSWORD=$DATABASE_PASSWORD \
             -e POSTGRES_USER=$DATABASE_USER \
             -p $DATABASE_PORT:5432 \
             -v $DATABASE_VOLUME:/var \
             -d postgres
fi

# Start the container if innactive
if [[ $(docker container inspect -f '{{.State.Running}}' $DATABASE_CONTAINER | grep -v -q true) ]]; then
  docker start $DATABASE_CONTAINER
fi

# Wait for postgres to start

until PGPASSWORD=$DATABASE_PASSWORD psql -h "localhost" -U "$DATABASE_USER" -c '\q'; do
    sleep 1
done

echo "Postgres started"
