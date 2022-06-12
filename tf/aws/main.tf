terraform {
  backend "s3" {
    bucket = "resourcetracker-backend"
    key    = "terraform.tfstate"
  }
}

provider "aws" {
	shared_credentials_file = var.resourcetracker_shared_credentials_file
}

module "ecs"{
    source = "./ecs"
}
