# variable "aws_access_key"{
#     description = "Access key for aws account"
# }

# variable "aws_secret_key"{
#     description = "Secret key for aws account"
# }

# variable "aws_region"{
#     description = "Region for aws account"
# }

# variable "aws_profile"{
#     description = "Profile for aws account"
# }

variable "resourcetracker_shared_credentials_file" {
	default = "~/.aws/credentials"
	description = "File with credentials for AWS provider"
}

