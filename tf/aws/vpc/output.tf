output "resourcetracker_security_group"{
    value = aws_security_group.resourcetracker_security_group.id
}

output "resourcetracker_main_subnet_id"{
    value = aws_subnet.resourcetracker_subnet.id
}
