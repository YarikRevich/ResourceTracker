variable "resourcetracker_agent_version" {
	type = string
	default = "latest"
	description = "ResourceTracker Agent Docker image version, which will be used for deployment"
	nullable = false
}

variable "resourcetracker_agent_context" {
	type = string
	description = "Context gotten from CLI as a data to be processed in a remote resource"
	nullable = false
}
