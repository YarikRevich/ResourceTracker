variable "resourcetracker_context"{
	default = ""
	description = "Context gotten from CLI as a data to be processed in a remote resource"
}

variable "resourcetracker_shared_credentials_file" {
	default = "~/.aws/credentials"
	description = "File with credentials for AWS provider"
}

