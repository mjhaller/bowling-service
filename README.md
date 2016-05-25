# Bowling Service

Scoring as a Service for Bowling Alleys  (SaaSBA)

# Caveats

- In memory scoring only (no persistence, but model is annotated for relational db)
- No UI
- No support for editing existing frames
- Basic REST API

# Installing

You need to install 

- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/download.cgi)

on mac:

```
brew install maven
```

Clone this repo (of course)

# Playing

## Run
```
$ mvn spring-boot:run
```
## Play

In another terminal: 
```
$ curl -H "Content-type: application/json" -d '{ "player" : "mark" }' -X POST localhost:8090/game
(repeat to start a new game)
$ curl -H "Content-type: application/json" -d '{ "pins" : 10 }' -X POST localhost:8090/game/1/roll
(repeat rolling until frame ten is complete)
```
# Test
The following tests 
- `PlayingTest` - for scoring methods
- `GameControllerTest` for REST endpoints
- `GamePersistTest` - stubbed 
```
mvn test
```

# Model

- see [Class Diagram](https://github.com/mjhaller/bowling-service/blob/master/bowling_class_diagram.png?raw=true)


# Sequence 

- see [Sequence Diagram](https://github.com/mjhaller/bowling-service/blob/master/bowling_sequence_diagram.png?raw=true)
