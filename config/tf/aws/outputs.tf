output "ecs_task_definition"{
	value = module.ecs.ecs_task_definition
}

output "ecs_cluster" {
	value = module.ecs.ecs_cluster
}

output "resourcetracker_security_group"{
	value = module.vpc.resourcetracker_security_group
}

output "resourcetracker_main_subnet_id"{
	value = module.vpc.resourcetracker_main_subnet_id
}
#
#output "public_ip" {
#	value = module.ecs.public_ip
#}
