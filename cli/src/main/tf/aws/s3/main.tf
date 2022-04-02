resource "aws_s3_bucket" "resourcetracker_remote_terraform_state"{
    bucket = "resourcetracker_remote_terraform_state"
}

resource "aws_s3_bucket" "resourcetracker_ecs_log_bucket"{
    bucket = "resourcetracker_ecs_log_bucket"
}

resource "aws_s3_bucket_versioning" "resourcetracker_ecs_log_versioning" {
  bucket = aws_s3_bucket.resourcetracker_ecs_log_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_versioning" "resourcetracker_remote_terraform_state_versioning" {
  bucket = aws_s3_bucket.resourcetracker_remote_terraform_state.id
  versioning_configuration {
    status = "Enabled"
  }
}