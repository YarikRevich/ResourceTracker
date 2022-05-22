#!/bin/bash

#RUNS AWS TASK DEFINITION

CLUSTER=$1
TASK_DEFINITION=$2

aws ecs run-task --task-definitions $TASK_DEFINITION --cluster $CLUSTER
