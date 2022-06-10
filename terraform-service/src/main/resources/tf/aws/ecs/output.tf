output "resourcetracker_ecs_task_definition"{
	value = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
}

output "resourcetracker_ecs_cluster" {
	value = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
}

output "resourcetracker_main_subnet" {
	value = module.vpc.resourcetracker_main_subnet_id
}

output "resourcetracker_security_group" {
	value = module.vpc.resourcetracker_security_group
}
