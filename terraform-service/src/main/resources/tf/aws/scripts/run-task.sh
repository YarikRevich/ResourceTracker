#!/bin/bash

# This script is used with ECS cluster
# it helps to run set task definitions

CLUSTER=$1
TASK_DEFINITION=$2
SUBNET=$3
SECURITY_GROUP=$4

aws ecs run-task --task-definition "$TASK_DEFINITION"  --cluster "$CLUSTER" --network-configuration "awsvpcConfiguration={subnets=[$SUBNET],securityGroups=[$SECURITY_GROUP]}" --launch-type FARGATE
#aws ec2 describe-network-interfaces --network-interface-ids eni-xxxxxxxx
