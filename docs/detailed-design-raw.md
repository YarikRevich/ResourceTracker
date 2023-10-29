```plantuml
!pragma teoz true

title 
    Detailed design of "ResourceTracker" 
end title

actor "Client" as client

box "Control plain" #MOTIVATION
participant "API Server" as apiserver

box "Cloud environment" #Lavender
queue "Kafka" as kafka
participant "Agent" as agent
entity "Cloud provider" as cloudprovider
end box

end box

note over kafka: Kafka is considered to be used in persisted mode

opt "endpoints"
opt "/v1/state [GET]"
apiserver -> kafka: retrieve resource state
kafka -> apiserver: transform data stream\naccording to the specified\nfilters
end
opt "/v1/terraform/apply [POST]"
apiserver -> cloudprovider: deploy resource\ntracking infrostructure
apiserver -> agent: configure agent properties
apiserver -> kafka: deploy kafka cluster
apiserver -> kafka: configure kafka cluster\nin persisted mode
end
opt "/v1/terraform/destroy [POST]"
apiserver -> agent: stop agent
apiserver -> kafka: stop kafka cluster and clean up persisted data
apiserver -> cloudprovider: clean up deployed\nresource tracking\ninfrostructure
end
end

opt "execution flow"
agent -> cloudprovider: execute remote operations
agent <-- cloudprovider: remote operation result
agent --> kafka: push latest resource state
end

opt "requests"
note over client: Uses properties specified in a client\nconfiguration file located in\n a common directory

opt "infrustructure deployment"
client -> apiserver: /v1/terraform/apply [POST]
end
opt "infrustructure clean up"
client -> apiserver: /v1/terraform/destroy [POST]
end
opt "state retrieval"
client -> apiserver: /v1/state [GET]
client <-- apiserver: up-to-date state 
end 
end
```