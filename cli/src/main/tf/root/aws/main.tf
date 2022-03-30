terraform {
  backend "s3" {
    bucket = "resourcetracker-backend"
    key    = "terraform.tfstate"
    region = "${var.aws_region}"
  }
}

provider "aws" {
  region  = "${var.aws_region}"
  access_key = "${var.aws_access_key}"
  secret_key = "${var.aws_secret_key}"
}

module "ecs"{
    source = "./ecs"
}