#!/bin/sh 

# simple curl script to create game 

curl -H "Content-type: application/json" -X POST localhost:8090/api/players -d '{ "player" : {"name" : "mark"}}'
curl -H "Content-type: application/json" -X POST localhost:8090/api/games -d '{ "player" : {"id" : 2}}'

