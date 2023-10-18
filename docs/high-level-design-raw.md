```plantuml
title

High-level design of "ResourceTracker"

end title

actor "Client"

component "Control plain" {
node "API Server"

cloud "Cloud environment" {
node "Kafka"
node "Agent"
hexagon "Cloud provider"

    [Agent] <--> [Cloud provider]: "Execute remote operation\nand save the result"
    [Agent] --> [Kafka]: "Push latest resource state"
}

[API Server] --> [Kafka]: "Retrieve latest resource state\t\t"
}

[Client] --> [API Server]: " Retrieve resource state"
```