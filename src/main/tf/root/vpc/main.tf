resource  "aws_vpc" "resourcetracker_vpc"{ 
    cidr_block = "10.0.0.0/16"
}

resource "aws_security_group" "allow_resourcetracker_api_calls" {
  name        = "allow_resourcetracker_api_calls"
  description = "Allow ResourceTracker external API calls"
  vpc_id      = "${aws_vpc.resourcetracker_vpc.id}"

  ingress {
    description = ""
    from_port   = 0
    to_port     = 10075
    protocol    = "tcp"
    cidr_blocks = ["${aws_vpc.resourcetracker_vpc.cidr_block}"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Type = "integrated_tool"
  }
}