provider "aws" {
}

module "ecs"{
    source = "./ecs"
}

module "vpc"{
    source = "./vpc"
}
