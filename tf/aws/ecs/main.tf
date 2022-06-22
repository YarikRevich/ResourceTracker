module "vpc" {
  source = "./../vpc"
}

module "s3" {
  source = "./../s3"
}

module "iam" {
  source = "./../iam"
}

resource "aws_ecr_repository" "resourcetracker_ecr_repository" {
  name = "resourcetracker_ecr_repository"
}

resource "aws_ecs_cluster" "resourcetracker_ecs_cluster" {
  name = "resourcetracker_ecs_cluster"

  configuration {
    execute_command_configuration {
      logging = "OVERRIDE"
      log_configuration {
        s3_bucket_name = module.s3.resourcetracker_ecs_log_bucket_id
      }
    }
  }
}

resource "aws_ecs_service" "resourcetracker_ecs_instance" {
  name                 = "resourcetracker_ecs_instance"
  cluster              = aws_ecs_cluster.resourcetracker_ecs_cluster.id
  task_definition      = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
  launch_type          = "FARGATE"
  desired_count        = 1
  force_new_deployment = true

  iam_role             = module.iam.resourcetracker_ecs_service_role_name
  lifecycle {
    create_before_destroy = true
  }
  network_configuration {
    subnets = [
      module.vpc.resourcetracker_main_subnet_id
    ]
    security_groups = [
      module.vpc.resourcetracker_security_group
    ]
  }
}

resource "aws_ecs_task_definition" "resourcetracker_ecs_instance_task_definitions" {
  family                   = "resourcetracker_ecs_instance_task_definition"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE", "EC2"]
  memory                   = 512
  cpu                      = 256

  container_definitions = jsonencode([
    {
      name : "resourcetracker-deploy",
      essential : true,
      environment : [
        {
          name : "RESOURCETRACKER_CONTEXT",
          value : var.resourcetracker-context,
        }
      ],
      image : "yariksvitlitskiy/resourcetracker_deploy:latest",
    },
    {
      name : "resourcetracker-zookeeper",
      essential : true,
      environment : [
        {
          name : "ALLOW_ANONYMOUS_LOGIN",
          value : "yes",
        }
      ],
      image : "bitnami/zookeeper:latest",
      portMappings : [
        {
          "containerPort" : 2128,
          "hostPort" : 2128
        }
      ],
    },
    {
      name : "resourcetracker-kafka",
      essential : true,
      environment : [
        {
          name : "ALLOW_PLAINTEXT_LISTENER",
          value : "yes",
          }, {
          name : "KAFKA_CFG_ZOOKEEPER_CONNECT",
          value : "resourcetracker_zookeeper:2181"
        }
      ],
      image : "bitnami/kafka:latest",
      portMappings : [
        {
          "containerPort" : 9091,
          "hostPort" : 9091
        }
      ],
    }
  ])
}
