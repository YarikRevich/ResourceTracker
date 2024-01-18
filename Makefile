.PHONY: help, clean, prepare, test, lint, create-local, clone-config, clone-terraform, clone-api-server, build-kafka-starter, build-agent, build-api-server, build-cli, build-gui

dev := $(or $(dev), 'false')

ifneq (,$(wildcard .env))
include .env
export
endif

.PHONY: help
.DEFAULT_GOAL := help
help:
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: clean
clean: ## Clean project area
	@mvn clean

.PHONY: prepare
prepare: ## Install prerequisites
	@ mvn org.apache.maven.plugins:maven-dependency-plugin:3.6.0:tree -Dverbose=true

.PHONY: test
test: clean ## Run both unit and integration tests
	@mvn test
	@mvn verify

.PHONY: lint
lint: ## Run Apache Spotless linter
	@mvn spotless:apply

.PHONY: create-local
create-local: ## Create ResourceTracker local directory
ifeq (,$(wildcard $(HOME)/.resourcetracker))
	@mkdir -p $(HOME)/.resourcetracker/config
	@mkdir -p $(HOME)/.resourcetracker/workspace
endif
ifeq (,$(wildcard $(HOME)/.resourcetracker/config))
	@mkdir -p $(HOME)/.resourcetracker/config
endif
ifeq (,$(wildcard $(HOME)/.resourcetracker/workspace))
	@mkdir -p $(HOME)/.resourcetracker/workspace
endif

.PHONY: clone-config
clone-config: create-local ## Clone configuration files to local directory
	@cp -r ./samples/config/* $(HOME)/.resourcetracker/config

.PHONY: clone-terraform
clone-terraform: create-local ## Clone Terraform configuration files to local directory
	@cp -r ./config/tf $(HOME)/.resourcetracker

.PHONY: clone-api-server
clone-api-server: create-local ## Clone API Server JAR into a ResourceTracker local directory
ifeq (,$(wildcard $(HOME)/.resourcetracker/bin/api-server))
	@mkdir -p $(HOME)/.resourcetracker/bin
endif
	@cp -r ./bin/api-server $(HOME)/.resourcetracker/bin/

#.PHONY: build-kafka-starter
#build-kafka-starter: clean ## Build Kafka starter Docker image
#

.PHONY: build-agent
build-agent: clean ## Build Agent Docker image
	@mvn -pl agent -T10 compile jib:build \
        -Djib.to.image=${DOCKER_IMAGE_NAME} \
        -Djib.to.tags=${DOCKER_IMAGE_TAG} \
        -Djib.auth.username=${DOCKER_REGISTRY_USERNAME} \
        -Djib.auth.password=${DOCKER_REGISTRY_PASSWORD}

.PHONY: build-api-server
build-api-server: clean clone-terraform ## Build API Server application
ifneq (,$(wildcard ./bin/api-server))
	@rm -r ./bin/api-server
endif
ifeq ($(dev), 'false')
	@mvn -pl api-server -T10 install
else
	@mvn -P dev -pl api-server -T10 install
endif

.PHONY: build-cli
build-cli: clean ## Build CLI application
ifneq (,$(wildcard ./bin/cli))
	@rm -r ./bin/cli
endif
ifeq ($(dev), 'false')
	@mvn -pl cli -T10 install
else
	@mvn -P dev -pl cli -T10 install
endif

.PHONY: build-gui
build-gui: clean build-api-server clone-api-server clone-terraform ## Build GUI application
ifneq (,$(wildcard ./bin/gui))
	@rm -r ./bin/gui
endif
ifeq ($(dev), 'false')
	@mvn -pl gui -T10 install
else
	@mvn -P dev -pl gui -T10 install
endif


