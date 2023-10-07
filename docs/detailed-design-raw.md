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