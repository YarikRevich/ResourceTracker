resource "azurerm_resource_group" "resourcetracker_resource_group"{
	name = "resourcetracker-resource-group"
	location = var.resourcetracker_resource_group_location
}
