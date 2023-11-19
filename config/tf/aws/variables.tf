variable "resourcetracker_agent_version" {
	default = "latest"
	description = "ResourceTraacker Agent Docker image version, which will be used for deployment"
}

variable "resourcetracker_agent_context"{
	default = ""
	description = "Context gotten from CLI as a data to be processed in a remote resource"
}
