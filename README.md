# ResourceTracker

[![StandWithUkraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/badges/StandWithUkraine.svg)](https://github.com/vshymanskyy/StandWithUkraine/blob/main/docs/README.md)

## General Information

A cloud-native tool, which allows to remote execution for specific cloud resources.

```plantuml
title

  High-level design of "ResourceTracker"

end title

participant "Client" as client
participant "Daemon" as daemon
participant "Agent" as agent
entity "Cloud provider" as cloudprovider

client -> daemon: Request
client <- daemon: Response

note bottom: Client is able to send requests\nrelated to specific command execution\nand resonsible for both Daemon and Agent\nstart-up process

daemon <-- agent: Response
note right: Agent is able to send to daemon a state of a certain resource
agent -> cloudprovider: Retrieve resource state 
```

```plantuml
title

  Detailed design of "ResourceTracker"

end title

participant "CLI" as cli
participant "State Storage" as statestorage
participant "Daemon" as daemon

box "Cloud environment"
queue "Kafka" as kafka
participant "Agent" as agent
entity "Cloud provider" as cloudprovider
end box

note over kafka: Kafka is considered to be used in persisted mode
note over daemon: Replicates retrieved data into local persistence layer

opt "agent commands"
note over agent, kafka: Uses properties specified in a agent configuration file located in a common directory

opt "start"

end

opt "stop"
end

end

opt "daemon commands"
note over daemon: Uses properties specified in a daemon configuration file located in a common directory

opt "start"
daemon -> kafka: establish connection

alt "Local state is present"
daemon -> statestorage: retrieve local state
statestorage -> daemon: local state
kafka <-> daemon: synchronize local state
end

end

opt "stop"
daemon -> kafka: close connection
daemon --> statestorage: clean local state
end

opt "halt"
daemon -> kafka: close connection
end

end

opt "cli commands"
note over cli, statestorage: Uses properties specified in a cli\nconfiguration file located in a common directory

opt "logs"
cli -> statestorage: retrieve local state
statestorage -> cli: local state
end

end
agent --> kafka: push latest resource state
daemon -> kafka: pull latest resource state
```

## Setup

All setup related operations are processed via **Makefile** placed in the root directory. 


## Inspiration

Developers often want to execute some tasks with some frequency, but there aren't such tools, which will allow to do that remotely in a cloud environment.
That's why I decided to implement such kind of application!
