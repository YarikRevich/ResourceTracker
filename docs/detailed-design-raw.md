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

opt "cli commands"
note over cli: Uses properties specified in a cli\nconfiguration file located in\n a common directory

opt "start"
cli -> cloudprovider: deploy resource\ntracking infrostructure
cli -> agent: configure agent properties
cli -> kafka: deploy kafka cluster
cli -> kafka: configure kafka cluster\nin persisted mode
end

opt "stop"
cli -> agent: stop agent
cli -> kafka: stop kafka cluster and clean up persisted data
cli -> cloudprovider: clean up deployed\nresource tracking\ninfrostructure
end

opt "logs"
cli -> kafka: retrieve resource state
kafka -> cli: transform data stream\naccording to the specified\nfilters
end

end

opt "agent is running"
agent --> kafka: push latest resource state
agent <-> cloudprovider: execute remote operations
end
```