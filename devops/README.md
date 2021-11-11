# CPDSS Docker Image

## Semantic Versioning

```
Dev Ship: 1.x.x
Dev Shore: 2.x.x
Qa Ship: 3.x.x
Qa Shore: 4.x.x
Pr: 5.x.x
UAT Ship: 6.x.x
UAT Shor: 7.x.x
```

# CPDSS Docker Compose Stack

CPDSS Microservice deployment through docker-compose.

## Pre-Requisites 

1. Docker
2. Docker-Swarm

## Directory Structure

```
devops
    +---azure-yamls
    |       cpdss-ci-pr-angular-yaml.yml
    |       cpdss-ci-pr-comm-angular-yaml.yml
    |       cpdss-ci-pr-java-yaml.yml
    |       cpdss-ci-yaml.yml
    |
    +---pipeline-scripts
    |       docker-setup.sh
    |
    \---stack-files
        |   docker-stack-wait.sh
        |
        +---cargo-info
        |       cargo-info-dev-stack.yml
        |       cargo-info-stack.yml
        |
        +---company-info
        |       company-info-dev-stack.yml
        |       company-info-stack.yml
        |
        +---cpdss-ui
        |       cpdss-ui-dev-stack.yml
        |       cpdss-ui-stack.yml
        |
        +---discharge-plan
        |       discharge-plan-dev-stack.yml
        |       discharge-plan-stack.yml
        |
        +---envoy-client-comm
        |       envoy-client-comm-stack.yml
        |
        +---envoy-reader
        |       envoy-reader-dev-stack.yml
        |       envoy-reader-stack.yml
        |
        +---envoy-server-comm
        |       envoy-server-comm-stack.yml
        |
        +---envoy-writer
        |       envoy-writer-dev-stack.yml
        |       envoy-writer-stack.yml
        |
        +---gateway
        |       gateway-dev-stack.yml
        |       gateway-stack.yml
        |
        +---loadable-study
        |       loadable-study-dev-stack.yml
        |       loadable-study-stack.yml
        |
        +---loadicator-integration
        |       loadicator-integration-dev-stack.yml
        |       loadicator-integration-stack.yml
        |
        +---loading-plan
        |       loading-plan-dev-stack.yml
        |       loading-plan-stack.yml
        |
        +---login-ui
        |       login-ui-stack.yml
        |
        +---port-info
        |       port-info-dev-stack.yml
        |       port-info-stack.yml
        |
        +---task-manager
        |       task-manager-stack.yml
        |
        \---vessel-info
                vessel-info-dev-stack.yml
                vessel-info-stack.yml
```

## Configuration

There's 2 ways to configure settings needed for deployement on different environments.
1. Environment Variables
2. env file

Configurations settings are defined in docker stack files.
Example: 
```
environment: 
    - DB_HOST=${DB_HOST}
    - DB_NAME=${DB_NAME} 
    - DB_USER=${DB_USER} 
    - DB_PASSWORD=${DB_PASSWORD}
```
## Ship Installation

#### Run the stack

##### Development 
From the /devops/<service-name> directory run the following command:
```
docker stack deploy -c <(docker-compose -f <service-name>_stack.yml -f <service-name>_dev_stack.yml config) <service-name> --with-registry-auth
```
##### QA
From the /devops/<service-name> directory run the following command:
```
docker stack deploy -c <(docker-compose -f <service-name>_stack.yml config) <service-name> --with-registry-auth
```

In order to check the status of the newly created stack:

```
docker stack ps <service-name>
```

View running services:
```
docker service ls
```
View logs for a specific service

```
docker service logs prom_<service_name>
```

Test Pr