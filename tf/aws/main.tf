provider "aws" {
#	shared_credentials_file = var.resourcetracker_shared_credentials_file
}

module "ecs"{
    source = "./ecs"
}
