.PHONY: stub, build, install
.ONESHELL:

include .env
export

stub:
	@echo "Available commands: build, install"

build_dev:
ifeq ($(RESOURCETRACKER_DEV), true)
	@echo "itworks"
	@cd ResourceTrackerDeployment
	@docker build -f ResourceTrackerDeployment/src/main/resources/Dockerfile -t resourcetrackerdeployment .
endif

build:
	@cd ResourceTrackerCLI
	@mvn clean package -T100 -DskipTests -q

install:
	@touch /usr/local/bin/resourcetracker
	@chmod +x /usr/local/bin/resourcetracker
	@echo '#!/bin/bash' >> /usr/local/bin/resourcetracker
	@echo '/usr/local/java -jar ResourceTrackerCLI/target/libs/ResourceTrackerCLI-0.0.1-SNAPSHOT.jar' >> /usr/local/bin/resourcetracker
