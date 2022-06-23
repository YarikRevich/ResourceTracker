output "ecs_task_definition"{
	value = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
}

output "ecs_cluster" {
	value = aws_ecs_cluster.resourcetracker_ecs_cluster.id
}

#output "main_subnet" {
#	value = module.vpc.resourcetracker_main_subnet_id
#}
#
#output "security_group" {
#	value = module.vpc.resourcetracker_security_group
#}
