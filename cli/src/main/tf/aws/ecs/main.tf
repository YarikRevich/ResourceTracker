module "vpc" {
  source = "./../vpc"
}

module "s3" {
  source = "./../s3"
}

resource "aws_ecs_cluster" "resourcetracker_ecs_cluster" {
  name = "resourcetracker_ecs_cluster"

  configuration {
    execute_command_configuration {
      log_configuration {
        s3_bucket_name = module.s3.resourcetracker_ecs_log_bucket_id
      }
    }
  }
}

resource "aws_ecs_service" "resourcetracker_ecs_instance" {
  name = "resourcetracker_ecs_instance"

  cluster         = aws_ecs_cluster.resourcetracker_ecs_cluster.id
  task_definition = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definition.arn

  network_configuration {
    subnets = [
      "${module.vpc.resourcetracker_vpc_id}"
    ]
    assign_public_ip = true
    security_groups = [
      "${module.vpc.allow_resourcetracker_api_calls_id}",
    ]
  }
}

resource "aws_ecs_task_definition" "resourcetracker_ecs_instance_task_definition" {
  family                = "resourcetracker_ecs_instance_task_definition"
  container_definitions = jsonencode([
    {
       "name": "resourcetracker",
       "environment": [{"RESOURCETRACKER_CONTEXT": "${var.context}"}]
       "image": "resourcetrackerdeploy:latest",
       "portMappings": [
        {
          "containerPort": 10075,
          "hostPort": 10075
        }],
        memory: 512,
        cpu: 128,
    }
  ])
}


resource "null_resource" "resourcetracker_ecs_instance_task_definition_run" {
  provisioner "local-exec" {
   command = "${aws_ecs_task_definition.resourcetracker_ecs_instance_task_definition.arn} ${aws_ecs_cluster.resourcetracker_ecs_cluster.id}"
   interpreter = ["/bin/bash", "./run-task.sh"]
  }

  depends_on = [
    aws_ecs_task_definition.resourcetracker_ecs_instance_task_definition,
  ]
}
