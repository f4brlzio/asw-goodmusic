#!/bin/bash

echo "Create topics for kafka"

docker exec -it kafka-container kafka-topics.sh --bootstrap-server kafka-container:9092 --create --topic connessioni-event-channel --replication-factor 1 --partitions 1
docker exec -it kafka-container kafka-topics.sh --bootstrap-server kafka-container:9092 --create --topic recensioni-event-channel --replication-factor 1 --partitions 1

sleep 2

docker exec -it kafka-container kafka-topics.sh --bootstrap-server kafka-container:9092 --list

echo "Topics created"
