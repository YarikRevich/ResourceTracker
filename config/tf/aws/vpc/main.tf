resource "aws_vpc" "resourcetracker_vpc" {
  cidr_block = "172.16.0.0/16"
}

resource "aws_subnet" "resourcetracker_subnet" {
  vpc_id     = aws_vpc.resourcetracker_vpc.id
  cidr_block = cidrsubnet(aws_vpc.resourcetracker_vpc.cidr_block, 8, 1)
}

resource "aws_route" "internet_access" {
	route_table_id         = aws_vpc.resourcetracker_vpc.main_route_table_id
	destination_cidr_block = "0.0.0.0/0"
	gateway_id             = aws_internet_gateway.resourcetracker_internet_gateway.id
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

resource "aws_route_table_association" "resourcetracker_route_table_association" {
	subnet_id      = aws_subnet.resourcetracker_subnet.id
	route_table_id = aws_route_table.resourcetracker_route_table.id
}

resource "aws_security_group" "resourcetracker_security_group" {
  name        = "allow_resourcetracker_api_calls"
  description = "Allow ResourceTracker external API calls"
  vpc_id      = aws_vpc.resourcetracker_vpc.id

  ingress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }

  lifecycle {
    replace_triggered_by = [aws_internet_gateway.resourcetracker_internet_gateway.id]
  }
}
