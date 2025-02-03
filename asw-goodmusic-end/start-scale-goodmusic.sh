#!/bin/bash

# Script per avviare l'applicazione GoodMusic

echo Running GOODMUSIC with more istances

docker compose up -d --build --scale recensioni=2 --scale connessioni=2 --scale recensioni-seguite=2

