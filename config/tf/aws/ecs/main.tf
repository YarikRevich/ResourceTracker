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
  wait_for_steady_state = true
  enable_ecs_managed_tags = true
  force_new_deployment = true

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
    assign_public_ip = true
  }

  depends_on = [module.iam]
}

data "aws_network_interface" "resourcetracker_ecs_instance_interface" {
  filter {
    name   = "tag:aws:ecs:serviceName"
    values = ["resourcetracker_ecs_instance"]
  }

  depends_on = [aws_ecs_service.resourcetracker_ecs_instance]
}

resource "aws_ecs_task_definition" "resourcetracker_ecs_instance_task_definitions" {
  family                   = "resourcetracker_ecs_instance_task_definition"
  network_mode             = "awsvpc"
  execution_role_arn       = module.iam.ecs_task_execution_role
  requires_compatibilities = ["FARGATE"]
  memory                   = 512
  cpu                      = 256

  container_definitions = jsonencode([
    {
      name: "resourcetracker-init",
      essential: true,
      command: ["/bin/ash", "-c", "echo 'ResourceTracker initialization ${var.resourcetracker_agent_version}' && sleep 60m"],
      image: "busybox"
    }
  ])
#  container_definitions = jsonencode([
#    {
#      name: "resourcetracker-agent",
#      essential: true,
#      depends_on: {
#        condition: "START",
#        container_name: "resourcetracker-kafka",
#      },
#      environment: [
#        {
#          name: "RESOURCETRACKER_AGENT_CONTEXT",
#          value: var.resourcetracker_agent_context,
#        },
#      ],
#      image: "ghcr.io/yarikrevich/resourcetracker-agent:${var.resourcetracker_agent_version}",
#      logConfiguration: {
#        "logDriver": "awslogs",
#        "options": {
#          "awslogs-group": "agent-logs-test",
#          "awslogs-region": "us-west-2",
#          "awslogs-stream-prefix": "ecs/resourcetracker-agent"
#        }
#      }
#    },
#    {
#      name: "resourcetracker-kafka",
#      essential: true,
#      environment: [
##        {
##          name: "KRAFT_CONTAINER_HOST_NAME",
##          value: data.aws_network_interface.resourcetracker_ecs_instance_interface.association[0].public_ip,
##        },
#        {
#          name: "KRAFT_CREATE_TOPICS",
#          value: "logs",
#        },
#        {
#          name: "KRAFT_PARTITIONS_PER_TOPIC",
#          value: "1"
#        }
#      ],
#      image: "ghcr.io/yarikrevich/resourcetracker-kafka-starter:latest",
#      portMappings: [
#        {
#          containerPort: 9093,
#          hostPort: 9093
#        }
#      ],
#      logConfiguration: {
#        "logDriver": "awslogs",
#        "options": {
#          "awslogs-group": "kafka-logs-test",
#          "awslogs-region": "us-west-2",
#          "awslogs-stream-prefix": "ecs/resourcetracker-kafka"
#        }
#      }
#    }
#  ])
}
