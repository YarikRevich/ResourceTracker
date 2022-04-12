.PHONY: stub, build, install
.ONESHELL:

OS = $(shell uname -s)

stub:
	@echo "Available commands: build, install"

check_os:
ifeq ($(OS), Darwin)
else ifeq ($(OS), Linux)
else
	$(error Your OS is not supported)
endif

build_deploy:
	@cd deploy && docker build -f src/main/resources/Dockerfile -t resourcetrackerdeployment .

build_cli: check_os
	@cd cli && mvn clean compile assembly:single -T100 -DskipTests -q

build_web: check_os
	@echo "it works"

build_gui: check_os
	@echo "it works"

install: check_os
	@touch /usr/local/bin/resourcetracker
	@chmod +x /usr/local/bin/resourcetracker
	@echo '' > /usr/local/bin/resourcetracker
	@echo '#!/bin/bash' >> /usr/local/bin/resourcetracker
	@echo 'java -jar $(PWD)/cli/target/lib/cli*' >> /usr/local/bin/resourcetracker
