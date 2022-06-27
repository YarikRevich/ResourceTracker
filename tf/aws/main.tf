provider "aws" {
}

module "ecs"{
    source = "./ecs"
}
