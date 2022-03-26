provider "aws" {
  region  = "us-west-2"
  profile = "default"
}

module "ecs"{
    source = "./ecs"
}