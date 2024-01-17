output "ecs_task_definition"{
	value = module.ecs.ecs_task_definition
}

output "ecs_service" {
	value = module.ecs.ecs_service
}

output "ecs_cluster" {
	value = module.ecs.ecs_cluster
}

output "ecs_task_execution_role" {
	value = module.iam.ecs_task_execution_role
}

output "resourcetracker_security_group"{
	value = module.vpc.resourcetracker_security_group
}

output "resourcetracker_main_subnet_id"{
	value = module.vpc.resourcetracker_main_subnet_id
}