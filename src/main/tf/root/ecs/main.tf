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
    security_groups = [
      "${module.vpc.allow_resourcetracker_api_calls_id}",
    ]
  }
}

resource "aws_ecs_task_definition" "resourcetracker_ecs_instance_task_definition" {
  family                = "resourcetracker_ecs_instance_task_definition"
  container_definitions = <<DEFINITION
  [
    {
       "name": "reosourcetracker",
       "environment": [{context: "${var.resourcetracker_container_image_context}"}]
       "image": "${var.resourcetracker_container_image}",
       "portMappings": [
        {
          "containerPort": 10075,
          "hostPort": 10075
        },
        memory: 512,
        cpu: 128,
      ],
    }
  ]
  DEFINITION
}


# provider "gcp" {
#   region  = "us-west-2"
#   profile = "default"
# }

# resource "aws_s3_bucket" "resourcetracker_main_s3" {
#   bucket = "resourcetracker"
# }

# resource "aws_codebuild_project" "resourcetracker_code_build" {
#   name        = "resourcetracker"
#   description = ""

#   cache {
#     type  = "LOCAL"
#     modes = ["LOCAL_DOCKER_LAYER_CACHE", "LOCAL_SOURCE_CACHE"]
#   }

#   artifacts {
#     type = "S3"
#   }

#   environment {
#     compute_type                = "BUILD_GENERAL1_SMALL"
#     image                       = "aws/codebuild/standard:1.0"
#     type                        = "LINUX_CONTAINER"
#     image_pull_credentials_type = "CODEBUILD"
#   }

#   logs_config {
#     s3_logs {
#       status   = "ENABLED"
#       location = "${aws_s3_bucket.resourcetracker_main_s3.id}/codebuild_logs"
#     }
#   }

#   source {
#     type     = "S3"
#     location = "${aws_s3_bucket.resourcetracker_main_s3.id}/src"
#   }
# }
