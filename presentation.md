# Scoring Model

![](https://raw.githubusercontent.com/mjhaller/bowling-service/master/bowling_class_diagram.png)

---


# Algorithm

- Simple object-oriented domain model
- Handles placement of rolls in frames
- Handles scoring all frames, as you go, on each roll event


---

# Rest - Course Grained


    POST /game  {"player" : "levi"} -> { "id" : 1 }
    POST /game/{id}/roll  {"pins" : 5}  -> { ... game ... }  (rpc-ish)

---
# Rest - Finer Grained

    POST /player  {"name" : "levi"} -> { "id" : 1 }
    POST /game  {"player" : {"id" : } -> { "id" : 1 }
    POST /game/{id}/roll  {"pins" : 5}  -> { ... game ... }


![](https://github.com/mjhaller/bowling-service/blob/course-grained/bowling_sequence_diagram.png)