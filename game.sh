#!/bin/sh 

# simple curl script to create game 

curl -H "Content-type: application/json" -X POST localhost:8090/api/players -d '{ "name" : "mark"}'
curl -H "Content-type: application/json" -X POST localhost:8090/api/games -d '{ "player" : "http://localhost:8090/api/players/2"}'
curl -H "Content-type: application/json" -X POST localhost:8090/api/games -d '{ "player" : "http://localhost:8090/api/players/2"}'


