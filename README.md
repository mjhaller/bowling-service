# Bowling Service

Scoring as a Service for Bowling Alleys  (SaaSBA)

# Caveats

In memory scoring only (no persistence)
No UI
No support for editing existing frames
Basic REST API

# Installing

You need to install 

- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven]|(https://maven.apache.org/download.cgi)

on mac:

```
brew install maven
```

Clone this repo (of course)

# Playing

## Run
$ mvn spring-boot:run

## Roll
In another terminal: 

$ curl -H "Content-type: application/json" -d '{ "player" : "mark" }' -X POST localhost:8090/game
$ curl -H "Content-type: application/json" -d '{ "pins" : 10 }' -X POST localhost:8090/game/1/roll


# Model

see


# Sequence 

see 
