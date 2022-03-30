terraform {
  backend "gcs" {
    bucket = "resourcetracker-backend"
    prefix = "terraform.tfstate"
  }
}

provider "google"{
    project = "${var.google_project}"
    region = "${var.google_region}"
    credentials = "${file("${var.google_credentials_file}")}"
}
