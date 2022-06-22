output "resourcetracker_deploy_subnet_id" {
	value = google_compute_subnetwork.resourcetracker_deploy_subnet.id
}

output "resourcetracker_zookeeper_subnet_id" {
	value = google_compute_subnetwork.resourcetracker_zookeeper_subnet.id
}

output "resourcetracker_kafka_subnet_id" {
	value = google_compute_subnetwork.resourcetracker_kafka_subnet.id
}
