.PHONY: help, clean, prepare, test, build
.ONESHELL:

.PHONY: help
.DEFAULT_GOAL := help
help:
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

.PHONY: clean
clean: ## Clean project area
	@mvn clean

.PHONY: prepare
prepare: ## Install prerequisites
	@mvn dependencies:resolve

.PHONY: test
test: clean ## Run both unit and integration tests
	@mvn test
	@mvn verify

.PHONY: build
build: clean ## Build the project
	@mvn install
