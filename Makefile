.PHONY: help, clean, prepare, test, lint, create-local, clone-terraform, clone-api-server, build-agent, build-api-server, build-cli, build-gui

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
	@mkdir $(HOME)/.resourcetracker
endif

.PHONY: clone-terraform
clone-terraform: create-local ## Clone Terraform configuration files to local directory
	@cp -r ./config/tf $(HOME)/.resourcetracker

.PHONY: clone-api-server
clone-api-server: create-local ## Clone API Server JAR into a ResourceTracker local directory
ifeq (,$(wildcard $(HOME)/.resourcetracker/bin/api-server))
	@mkdir -p $(HOME)/.resourcetracker/bin
endif
	@cp -r ./bin/api-server $(HOME)/.resourcetracker/bin/

.PHONY: build-agent
build-agent: clean ## Build Agent Docker image
	@mvn -pl agent -T10 compile jib:build \
        -Djib.to.image=${DOCKER_IMAGE_NAME} \
        -Djib.to.tags=${DOCKER_IMAGE_TAG} \
        -Djib.auth.username=${DOCKER_REGISTRY_USERNAME} \
        -Djib.auth.password=${DOCKER_REGISTRY_PASSWORD}

.PHONY: build-api-server
build-api-server: clean ## Build API Server application
	@mvn -pl api-server -T10 install

.PHONY: build-cli
build-cli: clean clone-terraform ## Build CLI application
	@mvn -pl cli -T10 install

.PHONY: build-gui
build-gui: clean build-api-server clone-api-server clone-terraform ## Build GUI application
	@mvn -pl gui -T10 install


