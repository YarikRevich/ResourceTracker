.PHONY: stub, build, install
.ONESHELL

stub:
	@echo "Available commands: build, install"

build_deploy_docker_image:
	@cd ResourceTrackerDeployment
	@docker build -f src/main/resources/Dockerfile -t resourcetrackerdeploy:latest .

build:
	@cd ResourceTrackerCLI
	@mvn clean package -T100 -DskipTests -q

install:
	@touch /usr/local/bin/resourcetracker
	@chmod +x /usr/local/bin/resourcetracker
	@echo '#!/bin/bash' >> /usr/local/bin/resourcetracker
	@echo '/usr/local/java -jar ResourceTrackerCLI/target/libs/ResourceTrackerCLI-0.0.1-SNAPSHOT.jar' >> /usr/local/bin/resourcetracker
