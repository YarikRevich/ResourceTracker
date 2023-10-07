# ResourceTracker

![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
[![StandWithUkraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://github.com/vshymanskyy/StandWithUkraine/blob/main/docs/README.md)

## General Information

A cloud-native tool resource state tracking.

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

node "CLI"

[CLI] -> [Kafka]: Retrieve persisted\nresource state

```

```plantuml
title

  Detailed design of "ResourceTracker"

end title

participant "CLI" as cli

box "Cloud environment"
queue "Kafka" as kafka
participant "Agent" as agent
entity "Cloud provider" as cloudprovider
end box

note over kafka: Kafka is considered to be used in persisted mode

opt "agent commands"
note over agent: Uses properties specified in a agent\nconfiguration file located in a common directory

opt "start"
agent -> cloudprovider: deploy resource\ntracking infrostructure
agent -> kafka: deploy kafka cluster
agent -> kafka: configure kafka cluster\nin persisted mode
end

opt "stop"
agent -> cloudprovider: clean up deployed\nresource tracking\ninfrostructure
agent -> kafka: clean up deployed kafka cluster
end

end

opt "cli commands"
opt "logs"
note over cli: Uses properties specified in a cli\nconfiguration file located in\n a common directory

cli -> kafka: retrieve resource state
kafka -> cli: transform data stream\naccording to the specified\nfilters

end

end

opt "agent is running"
agent --> kafka: push latest resource state
agent <-> cloudprovider: execute remote operations
end
```

## Setup

All setup related operations are processed via **Makefile** placed in the root directory.

In order to build the project it's required to execute the following command. Initially it cleans the environment and build Java project using **Maven**
```shell
make build
```

After the execution of command given above all the executables will be generated and placed into **bin** folder in the root directory of the project