provider "aws" {}

module "ecs" {
    source = "./ecs"
#    resourcetracker_agent_version = var.resourcetracker_agent_version
    resourcetracker_agent_context = var.resourcetracker_agent_context
}

module "vpc" {
    source = "./vpc"
}

module "iam" {
    source = "./iam"
}
