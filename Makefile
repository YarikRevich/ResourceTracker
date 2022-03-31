.PHONY: stub, build, install
.ONESHELL:

stub:
	@echo "Available commands: build, install"

build_deploy:
	@cd deploy && docker build -f src/main/resources/Dockerfile -t resourcetrackerdeployment .

build_cli:
	@cd cli && mvn clean compile assembly:single -T100 -DskipTests -q

build: build_cli

install:
	@touch /usr/local/bin/resourcetracker
	@chmod +x /usr/local/bin/resourcetracker
	@echo '' > /usr/local/bin/resourcetracker
	@echo '#!/bin/bash' >> /usr/local/bin/resourcetracker
	@echo 'java -jar $(PWD)/cli/target/lib/cli*' >> /usr/local/bin/resourcetracker
