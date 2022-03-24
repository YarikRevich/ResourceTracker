provider "aws" {
  region  = "us-west-2"
  profile = "default"
}

resource "aws_instance" "resourcetracker_instance"{
	ami = "ami-0dcc0ebde7b2e00db"
	instance_type = "t2.micro"

	tags {
		Type = "integrated_tool"
	}
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
