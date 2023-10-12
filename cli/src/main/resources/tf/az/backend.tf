terraform {
  backend "azurerm" {
	resource_group_name = "cloud-shell-storage-westeurope"
	storage_account_name = "resourcetrackerbackend"
    container_name   = "tfstate"
    key              = "terraform.tfstate"
  }
}
