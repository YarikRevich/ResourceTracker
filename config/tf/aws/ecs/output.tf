#output "ecs_task_definition"{
#	value = aws_ecs_task_definition.resourcetracker_ecs_instance_task_definitions.arn
#}

output "ecs_cluster" {
	value = aws_ecs_cluster.resourcetracker_ecs_cluster.id
}

#output "public_ip" {
#	value = data.aws_network_interface.resourcetracker_ecs_instance_interface.association[0].public_ip
##	value = aws_network_interface.interface_tags.association[0].public_ip
#}