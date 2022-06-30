terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.0.0"
    }
  }
  backend "azurerm" {
	resource_group_name = "cloud-shell-storage-westeurope"
	storage_account_name = "resourcetrackerbackend"
    container_name   = "tfstate"
    key              = "terraform.tfstate"
  }
}

provider "azurerm" {
  subscription_id = "b9f26c95-8d53-4134-814d-6b63218ad9e4"
  client_id       = "42f5667b-b579-460b-9ac6-6836da619491"
  client_secret   = "5fbd0f05-6793-4b49-9f98-4414a678bf9f"
  tenant_id       = "4de4f7be-5ea2-4fa0-ab6a-28716a1a0b5f"
  features {}
}

module "aci" {
  source = "./aci"
}

module "arg" {
  source = "./arg"
}

