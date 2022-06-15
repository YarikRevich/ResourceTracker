terraform {
	backend "gcs" {
		bucket = "resourcetracker-backend"
		prefix = "terraform.tfstate"
	}
}
