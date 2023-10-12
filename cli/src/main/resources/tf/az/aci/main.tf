module "arg" {
  source = "./../arg"
}

resource "azurerm_container_group" "resourcetracker_container_group" {
  name                = "example-continst"
  location            = var.resourcetracker_aci_container_location
  resource_group_name = module.arg.resourcetracker_container_group_name
  ip_address_type     = "Public"
  dns_name_label      = var.resourcetracker_aci_container_dns_name
  os_type             = "Linux"

  container {
    name   = "resourcetracker-deploy"
    image  = "mcr.microsoft.com/azuredocs/aci-helloworld:latest"
    cpu    = "0.5"
    memory = "1.5"

    environment_variables = [
      {
        name : "RESOURCETRACKER_CONTEXT",
        value : var.resourcetracker-context,
      }
    ]
  }

  container {
    name   = "resourcetracker-kafka"
    image  = "bitnami/kafka:latest"
    cpu    = "0.5"
    memory = "1.5"

    environment_variables = [
      {
        name : "ALLOW_PLAINTEXT_LISTENER",
        value : "yes",
      },
      {
        name : "KAFKA_CFG_ZOOKEEPER_CONNECT",
        value : "resourcetracker-zookeeper:2181"
      }
    ]

    ports {
      port     = 9091
      protocol = "TCP"
    }
  }

  container {
    name  = "resourcetracker-zookeeper"
    image = "bitnami/zookeeper:latest"

    environment_variables = [
      {
        name : "ALLOW_ANONYMOUS_LOGIN",
        value : "yes",
      }
    ]
    cpu    = "0.5"
    memory = "1.5"

    ports {
      port     = 2128
      protocol = "TCP"
    }
  }
}
