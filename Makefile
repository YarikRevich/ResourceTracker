.PHONY: check_os, build_deploy, build_cli, build_web, build_gui, install
.ONESHELL:

# Constants

CLI_TARGET_PATH := cli/target/lib/cli*
CLI_SHARED_TARGET_PATH := /usr/share/java/cli*

WEB_TARGET_PATH := web/target/lib/web*
WEB_SHARED_TARGET_PATH := /usr/share/java/web*

RESOURCETRACKER_CONFIG_PATH := /usr/local/etc/resourcetracker
RESOURCETRACKER_BINARY_FILE := /usr/local/bin/resourcetracker

DEFAULT_CONFIG_PATH := default/resourcetracker.yaml

# Init targets

prepare_pre_build:
	@mkdir -p $(RESOURCETRACKER_CONFIG_PATH)
	@cp $(DEFAULT_CONFIG_PATH) $(RESOURCETRACKER_CONFIG_PATH)

# $(1) path to target to make shared
define make_target_shared
	@cp --parent $(1) /usr/share/java
endef

# $(1): specification of binary: cli, gui, web
# $(2): path to target to attach to binary
define create_binary
	@touch $(RESOURCETRACKER_BINARY_FILE)
	@chmod +x $(RESOURCETRACKER_BINARY_FILE)
	@echo '#!/bin/bash' >> /usr/local/bin/resourcetracker_$(1)
	@echo 'java -jar $(2)' >> /usr/local/bin/resourcetracker_$(1)
endef



build: prepare_pre_build
	@mvn clean install -T100

install:
	$(call make_target_shared, $(CLI_TARGET_PATH))
	#$(call make_target_shared, $(WEB_TARGET_PATH))

	$(call create_binary, cli, $(CLI_SHARED_TARGET_PATH))
	#$(call create_binary, web, $(WEB_SHARED_TARGET_PATH))

