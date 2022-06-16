output "ecs_task_definition"{
	value = module.ecs.ecs_task_definition
}

output "ecs_cluster" {
	value = module.ecs.ecs_cluster
}

output "main_subnet" {
	value = module.ecs.main_subnet
}

output "security_group" {
	value = module.ecs.security_group
}
