# Bowling Service

Scoring as a Service for Bowling Alleys  (SaaSBA)

# Caveats

- No UI
- No Validation
- Marking stubbed

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

see [Sequence Diagram](bowling_sequence_diagram.png)

# Test
The following tests 
- `PlayingTest` - for scoring methods
- `GameControllerTest` for REST endpoints
- `GamePersistTest` - for persistence checks 
```
mvn test
```

# Model

- see [Class Diagram](bowling_class_diagram.png)


# Sequence 

- see [Sequence Diagram](bowling_sequence_diagram.png)
