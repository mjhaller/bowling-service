# Scoring Model

![](https://raw.githubusercontent.com/mjhaller/bowling-service/master/bowling_class_diagram.png)

---


# Algorithm

- Simple object-oriented - rich domain model
- Handles placement of rolls in frames
- Handles scoring all frames, as you go, on each roll event


---

# Rest - Original


    POST /game  {"player" : "levi"} -> { "id" : 1 }
    POST /game/{id}/roll  {"pins" : 5}  -> { ... game ... }  (rpc-ish)


---

# Rest - V2 - Finer Grained 

note:  HAL/HATEOS oriented

    POST /players                  {"name" : "levi"}       -> {href}
    POST /games                    {"player" : {href} }    -> {... nextFrame ...}
    POST /frames/{nextFrame}/roll  {"pins" : 5}            -> {... nextFrame ...}
    GET  /games/{id}/frames                                -> {... "_embedded" : frames : [] ...}


