data "aws_iam_policy_document" "resourcetracker_ecs_task_execution_role" {
  version = "2012-10-17"
  statement {
    sid     = ""
    effect  = "Allow"
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "resourcetracker_ecs_task_execution_role" {
  name_prefix               = "resourcetracker_ecs_task_execution"
  assume_role_policy = data.aws_iam_policy_document.resourcetracker_ecs_task_execution_role.json
}

resource "aws_iam_role_policy_attachment" "resourcetracker_ecs_task_execution_role" {
  role       = aws_iam_role.resourcetracker_ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}
