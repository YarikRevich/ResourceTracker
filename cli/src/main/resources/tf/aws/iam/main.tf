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
  name               = "resourcetracker_ecs_task_execution_role"
  assume_role_policy = data.aws_iam_policy_document.resourcetracker_ecs_task_execution_role.json
}

resource "aws_iam_role_policy_attachment" "resourcetracker_ecs_task_execution_role" {
  role       = aws_iam_role.resourcetracker_ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

#resource "aws_iam_role" "resourcetracker_ecs_service_role" {
#	name = "resourcetracker_ecs_service_role"
#
#	managed_policy_arns = ["arn:aws:iam::672733374731:role/ecsExternalInstanceRole"]
##	assume_role_policy = <<EOF
##{
##  "Version": "2012-10-17",
##  "Statement": [
##    {
##      "Sid": "",
##      "Effect": "Allow",
##      "Principal": {
##        "Service": "ecs.amazonaws.com"
##      },
##      "Action": "sts:AssumeRole"
##    }
##  ]
##}
##EOF
#}

#resource "aws_iam_role_policy" "resourcetracker_ecs_service_role" {
#	name = "resourcetracker_ecs_service_role"
#	role = aws_iam_role.resourcetracker_ecs_service_role.name
#
#	policy = <<EOF
#{
#  "Version": "2012-10-17",
#  "Statement": [
#    {
#      "Effect": "Allow",
#      "Action": [
#        "ec2:Describe*",
#        "elasticloadbalancing:DeregisterInstancesFromLoadBalancer",
#        "elasticloadbalancing:DeregisterTargets",
#        "elasticloadbalancing:Describe*",
#        "elasticloadbalancing:RegisterInstancesWithLoadBalancer",
#        "elasticloadbalancing:RegisterTargets"
#      ],
#      "Resource": "*"
#    }
#  ]
#}
#EOF
#}
#
#resource "aws_iam_role" "resourcetracker_ecs_service_role" {
#	name = "resourcetracker_ecs_service_role"
#
#	assume_role_policy = <<EOF
#{
#  "Version": "2008-10-17",
#  "Statement": [
#    {
#      "Sid": "",
#      "Effect": "Allow",
#      "Principal": {
#        "Service": "ecs.amazonaws.com"
#      },
#      "Action": "sts:AssumeRole"
#    }
#  ]
#}
#EOF
#}

#resource "aws_iam_user" "resourcetracker_user" {
#  name = "ResourceTracker"
#  path = "/resourcetracker/"
#}
#
#resource "aws_iam_user_policy" "resourcetracker_user_policy" {
#  name   = "resourcetracker_user_policy"
#  user   = aws_iam_user.resourcetracker_user.name
#  policy = <<EOF
#{
#  "Version": "2012-10-17",
#  "Statement": [
#    {
#        "Effect": "Allow",
#        "Action": [
#            "ecs:*",
#
#            "s3:CreateAccessPoint",
#            "s3:PutAnalyticsConfiguration",
#            "s3:PutAccelerateConfiguration",
#            "s3:PutAccessPointConfigurationForObjectLambda",
#            "s3:DeleteObjectVersion",
#            "s3:PutStorageLensConfiguration",
#            "s3:RestoreObject",
#            "s3:DeleteAccessPoint",
#            "s3:CreateBucket",
#            "s3:DeleteAccessPointForObjectLambda",
#            "s3:ReplicateObject",
#            "s3:PutEncryptionConfiguration",
#            "s3:DeleteBucketWebsite",
#            "s3:AbortMultipartUpload",
#            "s3:PutLifecycleConfiguration",
#            "s3:UpdateJobPriority",
#            "s3:DeleteObject",
#            "s3:CreateMultiRegionAccessPoint",
#            "s3:DeleteBucket",
#            "s3:PutBucketVersioning",
#            "s3:PutIntelligentTieringConfiguration",
#            "s3:PutMetricsConfiguration",
#            "s3:PutBucketOwnershipControls",
#            "s3:PutReplicationConfiguration",
#            "s3:DeleteMultiRegionAccessPoint",
#            "s3:PutObjectLegalHold",
#            "s3:InitiateReplication",
#            "s3:UpdateJobStatus",
#            "s3:PutBucketCORS",
#            "s3:PutInventoryConfiguration",
#            "s3:PutObject",
#            "s3:PutBucketNotification",
#            "s3:DeleteStorageLensConfiguration",
#            "s3:PutBucketWebsite",
#            "s3:PutBucketRequestPayment",
#            "s3:PutObjectRetention",
#            "s3:PutBucketLogging",
#            "s3:CreateAccessPointForObjectLambda",
#            "s3:PutBucketObjectLockConfiguration",
#            "s3:CreateJob",
#            "s3:ReplicateDelete"
#          ],
#        "Resource": "*"
#    }
#  ]
#}
#EOF
#}