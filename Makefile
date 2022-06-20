.PHONY: check_os, build_deploy, build_cli, build_web, build_gui, install
.ONESHELL:

# Constants

CLI_TARGET_PATH := cli/target/lib/cli*
CLI_SHARED_TARGET_PATH := /usr/share/java/cli*

WEB_TARGET_PATH := web/target/lib/web*
WEB_SHARED_TARGET_PATH := /usr/share/java/web*

HOME_FOLDER_PATH := /usr/local/etc/resourcetracker
BINARY_FILES_PATH := /usr/local/bin/resourcetracker

DEFAULT_CONFIG_FILE_PATH := default/resourcetracker.yaml
TERRAFORM_CONFIG_FILES_PATH = tf

# $(1) path to target to make shared
define make_target_shared
	@cp --parent $(1) /usr/share/java
endef

# $(1): specification of binary: cli, gui, web
# $(2): path to target to attach to binary
define create_binary
	@mkdir $(BINARY_FILES_PATH)
	@touch $(BINARY_FILES_PATH)_$(1)
	@chmod +x $(BINARY_FILES_PATH)_$(1)
	@echo '#!/bin/bash' >> $(BINARY_FILES_PATH)_$(1)
	@echo 'java -jar $(2)' >> $(BINARY_FILES_PATH)_$(1)
endef


build: prepare_for_build
	@mvn clean install -T100

prepare_for_build:
	@#mkdir -p $(HOME_FOLDER_PATH)
	@#cp $(DEFAULT_CONFIG_FILE_PATH) $(HOME_FOLDER_PATH)
	@cp -r $(TERRAFORM_CONFIG_FILES_PATH) $(HOME_FOLDER_PATH)

install:
	$(call make_target_shared, $(CLI_TARGET_PATH))
	$(call create_binary, cli, $(CLI_SHARED_TARGET_PATH))

