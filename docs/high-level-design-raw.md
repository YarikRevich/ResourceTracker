```plantuml
title

High-level design of "ResourceTracker"

end title

cloud "Cloud environment" {
node "Kafka"
node "Agent"
hexagon "Cloud provider"

    [Agent] <--> [Cloud provider]: "Execute remote operation\nand save the result"
    [Agent] --> [Kafka]: "Push latest resource state"
}

node "Client"

[Client] -> [Kafka]: Retrieve persisted\nresource state
```