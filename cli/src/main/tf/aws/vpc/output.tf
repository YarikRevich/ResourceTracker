output "allow_resourcetracker_api_calls_id"{
    value = "${aws_security_group.allow_resourcetracker_api_calls.id}"
}

output "resourcetracker_vpc_id"{
    value = "${aws_vpc.resourcetracker_vpc.id}"
}