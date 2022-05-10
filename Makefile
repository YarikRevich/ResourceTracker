.PHONY: check_os, build_deploy, build_cli, build_web, build_gui, install
.ONESHELL:

check_os:
ifneq ($(filter $(shell uname -s), Darwin Linux)
    $(error Your OS is not supported)
endif

build_deploy: check_os
	@mvn -pl deploy clean compile jib:build

build_cli: check_os
	@mvn -pl cli clean compile assembly:single -T100 -DskipTests -q

build_web: check_os
	@echo "it works"

build_gui: check_os
	@echo "it works"

install: check_os
	@/bin/bash scripts/install.sh
