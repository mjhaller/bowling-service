# Scoring Model

![](bowling_class_diagram.png)

---


# Algorithm

- Simple object-oriented - rich domain model
- Handles placement of rolls in frames
- Handles scoring all frames, as you go, on each roll event


---

# Rest - Original


    POST /game            {"player" : "levi"} -> { "id" : 1 }
    POST /game/{id}/roll  {"pins" : 5}        -> { ... game ... }  (rpc-ish)

---

# Rest - V2 - Finer Grained 

_note:  HAL/HATEOS oriented_

    POST /players                  {"name" : "levi"}       -> {href}
    POST /games                    {"player" : {href} }    -> {... id ...}
    GET  /games/{id}                                       -> {... totalScore, nextFrame ...}
    POST /frames/{nextFrame}/rolls {"pins" : 5}            -> {... frameScore, nextFrame ...}
    GET  /games/{id}/frames                                -> {... "_embedded" : frames : [] ...}

    PUT  /frames/{id}               { rolls : []}          -> {... frameScore, nextFrame ...}
    
---

# Sequence Diagram - V2

_note: diagram does not show calls to the persistence layer_

![](bowling_sequence_diagram.png)


---

# Systems Architecture

![](system_architecture.png)
