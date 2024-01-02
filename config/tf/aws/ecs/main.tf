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

#data "aws_region" "current" {}
#
#resource "aws_ssm_document" "cloud_init_wait" {
#  name = "cloud-init-wait"
#  document_type = "Command"
#  document_format = "YAML"
#  content = <<-DOC
#    schemaVersion: '2.2'
#    description: Wait for cloud init to finish
#    mainSteps:
#    - action: aws:runShellScript
#      name: StopOnLinux
#      precondition:
#        StringEquals:
#        - platformType
#        - Linux
#      inputs:
#        runCommand:
#        - cloud-init status --wait
#    DOC
#}

#resource "aws_ecs_service" "resourcetracker_ecs_instance" {
#  name                 = "resourcetracker_ecs_instance"
#  cluster              = aws_ecs_cluster.resourcetracker_ecs_cluster.id
#  task_definition      = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
#  launch_type          = "FARGATE"
#  desired_count        = 1
#  wait_for_steady_state = true
#  enable_ecs_managed_tags = true
#  force_new_deployment = true
#
#  lifecycle {
#    create_before_destroy = true
#  }
#  network_configuration {
#    subnets = [
#      module.vpc.resourcetracker_main_subnet_id
#    ]
#    security_groups = [
#      module.vpc.resourcetracker_security_group
#    ]
#    assign_public_ip = true
#  }
#
#  depends_on = [module.iam]

#  provisioner "local-exec" {
#    interpreter = ["/bin/bash", "-c"]
#    command = <<-EOF
#    set -Ee -o pipefail
#    export AWS_DEFAULT_REGION=${data.aws_region.current.name}
#    command_id=$(aws ssm send-command --document-name ${aws_ssm_document.cloud_init_wait.arn} --instance-ids ${self.id} --output text --query "Command.CommandId")
#    if ! aws ssm wait command-executed --command-id $command_id --instance-id ${self.id}; then
#      echo "Failed to start services on instance ${self.id}!";
#      echo "stdout:";
#      aws ssm get-command-invocation --command-id $command_id --instance-id ${self.id} --query StandardOutputContent;
#      echo "stderr:";
#      aws ssm get-command-invocation --command-id $command_id --instance-id ${self.id} --query StandardErrorContent;
#      exit 1;
#    fi;
#    echo "Services started successfully on the new instance with id ${self.id}!"
#    EOF
#  }
#}

#data "aws_network_interface" "resourcetracker_ecs_instance_interface" {
#  filter {
#    name   = "tag:aws:ecs:serviceName"
#    values = ["resourcetracker_ecs_instance"]
#  }
#
#  depends_on = [aws_ecs_service.resourcetracker_ecs_instance]
#}


#resource "aws_ecs_task_definition" "resourcetracker_ecs_instance_task_definitions" {
#  family                   = "resourcetracker_ecs_instance_task_definition"
#  network_mode             = "awsvpc"
#  execution_role_arn       = module.iam.resourcetracker_ecs_task_execution_role_arn
#  requires_compatibilities = ["FARGATE"]
#  memory                   = 1024
#  cpu                      = 256
#
#  container_definitions = jsonencode([])
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
#}
