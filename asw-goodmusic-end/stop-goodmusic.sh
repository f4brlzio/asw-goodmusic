#!/bin/bash

echo Halting GOODMUSIC   

#pkill -f 'recensioni-seguite.jar'
#pkill -f 'recensioni.jar'
#pkill -f 'connessioni.jar'
#pkill -f 'api-gateway.jar'

#sleep 4

# docker stop asw-consul
# docker rm asw-consul

docker compose down -v
# docker compose stop # (per arrestare solo)

sleep 4

#jps

docker ps

echo Stopping GOODMUSIC completed

