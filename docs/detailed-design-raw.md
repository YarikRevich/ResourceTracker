```plantuml
title

  Detailed design of "ResourceTracker"

end title

participant "Client" as client

box "Cloud environment"
queue "Kafka" as kafka
participant "Agent" as agent
entity "Cloud provider" as cloudprovider
end box

note over kafka: Kafka is considered to be used in persisted mode

opt "commands"
note over client: Uses properties specified in a client\nconfiguration file located in\n a common directory

opt "start"
client -> cloudprovider: deploy resource\ntracking infrostructure
client -> agent: configure agent properties
client -> kafka: deploy kafka cluster
client -> kafka: configure kafka cluster\nin persisted mode
end

opt "stop"
client -> agent: stop agent
client -> kafka: stop kafka cluster and clean up persisted data
client -> cloudprovider: clean up deployed\nresource tracking\ninfrostructure
end

opt "logs"
client -> kafka: retrieve resource state
kafka -> client: transform data stream\naccording to the specified\nfilters
end

end

opt "agent is running"
agent --> kafka: push latest resource state
agent <-> cloudprovider: execute remote operations
end
```