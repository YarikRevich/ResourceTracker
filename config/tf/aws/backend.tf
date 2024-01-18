terraform {
  backend "s3" {
    bucket                  = "resourcetracker-state"
    key                     = "terraform.tfstate"
    encrypt = true
  }
}