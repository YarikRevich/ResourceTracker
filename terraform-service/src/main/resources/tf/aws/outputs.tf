output "resourcetracker_ecs_task_definition"{
	value = module.ecs.resourcetracker_ecs_task_definition
}

output "resourcetracker_ecs_cluster" {
	value = module.ecs.resourcetracker_ecs_cluster
}

output "resourcetracker_main_subnet" {
	value = module.ecs.resourcetracker_main_subnet
}

output "resourcetracker_security_group" {
	value = module.ecs.resourcetracker_security_group
}
