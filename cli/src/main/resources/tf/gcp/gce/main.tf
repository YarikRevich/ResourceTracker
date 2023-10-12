locals {
  machine_type = "n1-standard-1"
}

module "gcn" {
  source = "../gcn"
}

module "gce-container-resourcetracker-deploy" {
  source  = "terraform-google-modules/container-vm/google"
  version = "~> 2.0"

  container = {
    image = "yariksvitlitskiy/resourcetracker_deploy:latest"
    env = [
      {
        name  = "RESOURCETRACKER_CONTEXT"
        value = var.resourcetracker-context
      }
    ],
  }

  restart_policy = "OnFailure"
}

resource "google_compute_instance" "resourcetracker-deploy" {
  name         = "resourcetracker-deploy"
  machine_type = local.machine_type

  boot_disk {
    initialize_params {
      image = module.gce-container-resourcetracker-deploy.source_image
    }
  }

  network_interface {
    subnetwork = module.gcn.resourcetracker_deploy_subnet_id
    access_config {}
  }

  metadata = {
    gce-container-declaration = module.gce-container-resourcetracker-deploy.metadata_value
    google-logging-enabled    = "true"
    google-monitoring-enabled = "true"
  }

  labels = {
    container-vm = module.gce-container-resourcetracker-deploy.vm_container_label
  }

  service_account {
    scopes = [
      "https://www.googleapis.com/auth/cloud-platform",
    ]
  }
}

module "gce-container-zookeeper" {
  source  = "terraform-google-modules/container-vm/google"
  version = "~> 2.0"

  container = {
    image = "bitnami/zookeeper:latest"
    env = [
      {
        name  = "ALLOW_ANONYMOUS_LOGIN"
        value = "yes"
      }
    ],

    #		"containerPort" : 2128,
    #		"hostPort" : 2128
  }

  restart_policy = "OnFailure"
}

resource "google_compute_instance" "resourcetracker-zookeeper" {
  name         = "resourcetracker-zookeeper"
  machine_type = local.machine_type

  boot_disk {
    initialize_params {
      image = module.gce-container-zookeeper.source_image
    }
  }

  network_interface {
    subnetwork = module.gcn.resourcetracker_zookeeper_subnet_id
    access_config {}
  }

  metadata = {
    gce-container-declaration = module.gce-container-zookeeper.metadata_value
    google-logging-enabled    = "true"
    google-monitoring-enabled = "true"
  }

  labels = {
    container-vm = module.gce-container-zookeeper.vm_container_label
  }

  service_account {
    scopes = [
      "https://www.googleapis.com/auth/cloud-platform",
    ]
  }
}

module "gce-container-kafka" {
  source  = "terraform-google-modules/container-vm/google"
  version = "~> 2.0"

  container = {
    image = "bitnami/kafka:latest"
    env = [
      {
        name  = "ALLOW_PLAINTEXT_LISTENER"
        value = "yes"
      },
      {
        name  = "KAFKA_CFG_ZOOKEEPER_CONNECT"
        value = "resourcetracker-zookeeper:2181"
      }
    ],

    #		"containerPort" : 9091,
    #		"hostPort" : 9091
  }

  restart_policy = "OnFailure"
}

resource "google_compute_instance" "resourcetracker-kafka" {
  name         = "resourcetracker-kafka"
  machine_type = local.machine_type

  boot_disk {
    initialize_params {
      image = module.gce-container-kafka.source_image
    }
  }

  network_interface {
    subnetwork = module.gcn.resourcetracker_kafka_subnet_id
    access_config {}
  }

  metadata = {
    gce-container-declaration = module.gce-container-kafka.metadata_value
    google-logging-enabled    = "true"
    google-monitoring-enabled = "true"
  }

  labels = {
    container-vm = module.gce-container-kafka.vm_container_label
  }

  service_account {
    email = var.client_email
    scopes = [
      "https://www.googleapis.com/auth/cloud-platform",
    ]
  }
}
