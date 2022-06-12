resource "aws_vpc" "resourcetracker_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "resourcetracker_subnet" {
  vpc_id     = aws_vpc.resourcetracker_vpc.id
  cidr_block = "10.0.1.0/24"
}

resource "aws_internet_gateway" "resourcetracker_internet_gateway" {
  vpc_id = aws_vpc.resourcetracker_vpc.id
}

resource "aws_route_table" "resourcetracker_route_table" {
  vpc_id = aws_vpc.resourcetracker_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.resourcetracker_internet_gateway.id
  }
}

resource "aws_security_group" "resourcetracker_security_group" {
  name        = "allow_resourcetracker_api_calls"
  description = "Allow ResourceTracker external API calls"
  vpc_id      = aws_vpc.resourcetracker_vpc.id


  #  ingress {
  #    description = ""
  #    from_port   = 0
  #    to_port     = 10075
  #    protocol    = "tcp"
  #    cidr_blocks = ["${aws_vpc.resourcetracker_vpc.cidr_block}"]
  #  }

  ingress {
    from_port   = 0
    protocol    = "-1"
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
