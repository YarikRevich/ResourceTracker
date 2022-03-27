terraform {
  backend "s3" {
    bucket = "terraform_resourcetracker"
    key    = "terraform.tfstate"
    region = "us-west-2"
  }
}

provider "aws" {
  region  = "us-west-2"
  profile = "default"
}

module "ecs"{
    source = "./ecs"
}