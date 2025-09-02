#!make

.PHONY: help clean build jar run

help: ## Show all available commands
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z0-9_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' Makefile

clean: ## Remove all .class files
	@rm -rf ./out

removeJar: ## Delete jar
	@rm -rf *.jar

removeDB: ## Delete all .db files
	@rm -rf ./data/*.db

build: ## Compile all .java files
	@mkdir -p out
	@find ./src/presentefacil -name "*.java" | xargs javac -d out

jar: build ## Create .jar file with Main class as entry point
	@jar cfe presentefacil.jar Main -C out .
	@$(MAKE) clean

run: jar ## Run the application from .jar
	@java -jar presentefacil.jar
