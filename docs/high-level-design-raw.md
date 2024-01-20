```plantuml
title

High-level design of "ResourceTracker"

end title

actor "Client"

component "Control plain" {
node "API Server"

cloud "Cloud environment" {
cloud "Agent container" {
node "Agent"
}

cloud "Kafka container" {
node "Kafka"
node "Kafka starter"
}
hexagon "Cloud provider"

    [Agent] <--> [Cloud provider]: "Execute remote operation\nand save the result"
    [Agent] --> [Kafka]: "Push latest resource state"
}

[API Server] --> [Agent]: " Perform infrastructure deployment\n"
[API Server] --> [Kafka starter]: " Initialize Kafka startup\n"
[Kafka starter] --> [Kafka]: "Start Kafka cluster\n"
[API Server] <--> [Kafka]: "Perform event filtering\t\t"
}

[Client] --> [API Server]: " Perform resource related operations"
```