.PHONY: help, clean, prepare, test, build-dev, build
.ONESHELL:

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

.PHONY: build-dev
build-dev: clean ## Build the development project(includes agent Docker image build)
	@mvn jib:build -T10

.PHONY: build
build: clean ## Build the project
	@mvn install -T10
