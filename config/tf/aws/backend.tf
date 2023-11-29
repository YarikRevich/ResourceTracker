terraform {
  backend "s3" {
    bucket                  = "resourcetracker-backend"
    key                     = "terraform.tfstate"
    encrypt = true
  }
}