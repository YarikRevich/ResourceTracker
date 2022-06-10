terraform {
  backend "s3" {
    bucket = "resourcetracker-backend"
    key    = "terraform.tfstate"
  }
}

provider "aws" {
}

module "ecs"{
    source = "./ecs"
}
