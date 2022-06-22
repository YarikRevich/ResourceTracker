resource "google_compute_network" "resourcetracker_network" {
	name = "resourcetracker-network"
	auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "resourcetracker_deploy_subnet" {
	name          = "resourcetracker_deploy_subnet"
	ip_cidr_range = "10.1.0.0/16"
	network       = google_compute_network.resourcetracker_network.id
	secondary_ip_range {
		range_name    = "tf-test-secondary-range-update1"
		ip_cidr_range = "192.168.10.0/24"
	}
}

resource "google_compute_subnetwork" "resourcetracker_zookeeper_subnet" {
	name          = "resourcetracker_zookeeper_subnet"
	ip_cidr_range = "10.2.0.0/16"
	network       = google_compute_network.resourcetracker_network.id
	secondary_ip_range {
		range_name    = "tf-test-secondary-range-update1"
		ip_cidr_range = "192.168.10.0/24"
	}
}

resource "google_compute_subnetwork" "resourcetracker_kafka_subnet" {
	name          = "resourcetracker_kafka_subnet"
	ip_cidr_range = "10.3.0.0/16"
	network       = google_compute_network.resourcetracker_network.id
	secondary_ip_range {
		range_name    = "tf-test-secondary-range-update1"
		ip_cidr_range = "192.168.10.0/24"
	}
}

