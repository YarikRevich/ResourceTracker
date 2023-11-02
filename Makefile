.PHONY: help, clean, prepare, test, clone, build-agent, build-cli, build-gui

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

.PHONY: clone
clone: ## Clone Terraform configuration files to local directory
	@mkdir $(HOME)/.resourcetracker 2> /dev/null
	@cp -r ./config/tf $(HOME)/.resourcetracker

.PHONY: build-agent
build-agent: clean ## Build Agent Docker image
	@mvn -pl agent -T10 compile jib:build \
        -Dimage=${DOCKER_IMAGE_NAME} \
        -Dtags=${DOCKER_IMAGE_TAG} \
        -Dusername=${DOCKER_REGISTRY_USERNAME} \
        -Dpassword=${DOCKER_REGISTRY_PASSWORD}

.PHONY: build-api-server
build-api-server: clean ## Build API Server application
	@mvn -pl api-server -T10 install

.PHONY: build-cli
build-cli: clean ## Build CLI application
	@mvn -pl cli -T10 install

.PHONY: build-gui
build-gui: clean ## Build GUI application
	@mvn -pl gui -T10 install


