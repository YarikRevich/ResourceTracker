locals {

}


module "gce-container-resourcetracker-deploy" {
	source = "terraform-google-modules/container-vm/google"
	version = "~> 2.0"

	container = {
		image="yariksvitlitskiy/resourcetracker_deploy:latest"
		env = [
			{
				name = "RESOURCETRACKER_CONTEXT"
				value = var.resourcetracker-context
			}
		],
	}

	restart_policy = "OnFailure"
}


resource "google_compute_instance" "resourcetracker-deploy" {
	project      = var.project_id
	name         = local.instance_name
	machine_type = "n1-standard-1"
	zone         = var.zone

	boot_disk {
		initialize_params {
			image = module.gce-container-resourcetracker-deploy.source_image
		}
	}

	network_interface {
		subnetwork_project = var.subnetwork_project
		subnetwork         = var.subnetwork
		access_config {}
	}

	tags = ["container-vm-example"]

	metadata = merge(
		{
			gce-container-declaration = module.gce-container.metadata_value
			google-logging-enabled    = "true"
			google-monitoring-enabled = "true"
		},
		var.additional_metadata,
	)

	labels = {
		container-vm = module.gce-container.vm_container_label
	}

	service_account {
		email = var.client_email
		scopes = [
			"https://www.googleapis.com/auth/cloud-platform",
		]
	}

	metadata_startup_script = data.template_file.startup_script.rendered
}

module "gce-container-zookeeper" {
	source = "terraform-google-modules/container-vm/google"
	version = "~> 2.0"

	container = {
		image="bitnami/zookeeper:latest"
		env = [
			{
				name = "ALLOW_ANONYMOUS_LOGIN"
				value = "yes"
			}
		],

		"containerPort" : 2128,
		"hostPort" : 2128
	}

	restart_policy = "OnFailure"
}

resource "google_compute_instance" "resourcetracker-zookeeper" {
	project      = var.project_id
	name         = local.instance_name
	machine_type = "n1-standard-1"
	zone         = var.zone

	boot_disk {
		initialize_params {
			image = module.gce-container-zookeeper.source_image
		}
	}

	network_interface {
		subnetwork_project = var.subnetwork_project
		subnetwork         = var.subnetwork
		access_config {}
	}

	tags = ["container-vm-example"]

	metadata = merge(
		{
			gce-container-declaration = module.gce-container.metadata_value
			google-logging-enabled    = "true"
			google-monitoring-enabled = "true"
		},
		var.additional_metadata,
	)

	labels = {
		container-vm = module.gce-container.vm_container_label
	}

	service_account {
		email = var.client_email
		scopes = [
			"https://www.googleapis.com/auth/cloud-platform",
		]
	}

	metadata_startup_script = data.template_file.startup_script.rendered
}

module "gce-container-kafka" {
	source = "terraform-google-modules/container-vm/google"
	version = "~> 2.0"

	container = {
		image="bitnami/kafka:latest"
		env = [
			{
				name = "ALLOW_PLAINTEXT_LISTENER"
				value = "yes"
			},
			{
				name = "KAFKA_CFG_ZOOKEEPER_CONNECT"
				value = "resourcetracker-zookeeper:2181"
			}
		],

		"containerPort" : 9091,
		"hostPort" : 9091
	}

	restart_policy = "OnFailure"
}

resource "google_compute_instance" "resourcetracker-kafka" {
	project      = var.project_id
	name         = local.instance_name
	machine_type = "n1-standard-1"
	zone         = var.zone

	boot_disk {
		initialize_params {
			image = module.gce-container-kafka.source_image
		}
	}

	network_interface {
		subnetwork_project = var.subnetwork_project
		subnetwork         = var.subnetwork
		access_config {}
	}

	tags = ["container-vm-example"]

	metadata = merge(
		{
			gce-container-declaration = module.gce-container.metadata_value
			google-logging-enabled    = "true"
			google-monitoring-enabled = "true"
		},
		var.additional_metadata,
	)

	labels = {
		container-vm = module.gce-container.vm_container_label
	}

	service_account {
		email = var.client_email
		scopes = [
			"https://www.googleapis.com/auth/cloud-platform",
		]
	}

	metadata_startup_script = data.template_file.startup_script.rendered
}
