terraform {
  backend "azurerm" {
        resource_group_name  = "tfstate"
        storage_account_name = "resourcetracker-backend-storage"
        container_name       = "tfstate"
        key                  = "terraform.tfstate"
    }
}

provider "azurerm" {
  features {}
}

module "aci"{
	source = "./aci"
}

