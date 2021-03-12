# CpdssPortal

This project was generated using [Nx](https://nx.dev).

<p align="center"><img src="https://raw.githubusercontent.com/nrwl/nx/master/images/nx-logo.png" width="450"></p>

🔎 **Nx is a set of Extensible Dev Tools for Monorepos.**
This Workspace consist of two projects one Login App and CPDSS main application.
Run `npm install` command for intalling all dependencies and devDependencies.

## Quick Start & Documentation

[Nx Documentation](https://nx.dev/angular)

[10-minute video showing all Nx features](https://nx.dev/angular/getting-started/what-is-nx)

[Interactive Tutorial](https://nx.dev/angular/tutorial/01-create-application)

## Adding capabilities to your workspace

Nx supports many plugins which add capabilities for developing different types of applications and different tools.

These capabilities include generating applications, libraries, etc as well as the devtools to test, and build projects as well.

Below are our core plugins:

- [Angular](https://angular.io)
  - `ng add @nrwl/angular`
- [React](https://reactjs.org)
  - `ng add @nrwl/react`
- Web (no framework frontends)
  - `ng add @nrwl/web`
- [Nest](https://nestjs.com)
  - `ng add @nrwl/nest`
- [Express](https://expressjs.com)
  - `ng add @nrwl/express`
- [Node](https://nodejs.org)
  - `ng add @nrwl/node`

There are also many [community plugins](https://nx.dev/nx-community) you could add.

## Generate an application

Run `ng g @nrwl/angular:app my-app` to generate an application.

> You can use any of the plugins above to generate applications as well.

When using Nx, you can create multiple applications and libraries in the same workspace.

## Generate a library

Run `ng g @nrwl/angular:lib my-lib` to generate a library.

> You can also use any of the plugins above to generate libraries as well.

Libraries are sharable across libraries and applications. They can be imported from `@cpdss-portal/mylib`.

## Development server

Run following commands for a dev server. The app will automatically reload if you change any of the source files. We will be maintaining seperate configurations for ship and shore side applications.

`npm run start:login:ship` // Login app will be available on port 9001
`npm run start:login:shore` // Login app will be available on port 9002
`npm run start:cpdss:ship` // CPDSS main app will available on port 9003
`npm run start:cpdss:shore` // CPDSS main app will available on port 9004

## Code scaffolding

Run `ng g component my-component --project=my-app` to generate a new component.

## Test Build

Run following commands to build the project. The build artifacts will be stored in the `dist/` directory.

`npm run build:login:ship:test`
`npm run build:loginshore:tets`
`npm run build:cpdss:ship:test`
`npm run build:cpdss:shore:tets`

## Production Build

Run following commands to build the project. The build artifacts will be stored in the `dist/` directory.

`npm run build:login:ship`
`npm run build:login:shore`
`npm run build:cpdss:ship`
`npm run build:cpdss:shore`

## Running unit tests

Run following commands to execute the unit tests via [Jest](https://jestjs.io).

`npm run test:cpdss`
`npm run test:login`
`npm run test:cpdss:watch`
`npm run test:login:watch`
`npm run test:cpdss:coverage`
`npm run test:login:coverage`

Run `nx affected:test` to execute the unit tests affected by a change.

## Running end-to-end tests

Run `ng e2e my-app` to execute the end-to-end tests via [Cypress](https://www.cypress.io).

Run `nx affected:e2e` to execute the end-to-end tests affected by a change.

## Understand your workspace

Run `nx dep-graph` to see a diagram of the dependencies of your projects.

## Further help

Visit the [Nx Documentation](https://nx.dev/angular) to learn more.

## ☁ Nx Cloud

### Computation Memoization in the Cloud

<p align="center"><img src="https://raw.githubusercontent.com/nrwl/nx/master/images/nx-cloud-card.png"></p>

Nx Cloud pairs with Nx in order to enable you to build and test code more rapidly, by up to 10 times. Even teams that are new to Nx can connect to Nx Cloud and start saving time instantly.

Teams using Nx gain the advantage of building full-stack applications with their preferred framework alongside Nx’s advanced code generation and project dependency graph, plus a unified experience for both frontend and backend developers.

Visit [Nx Cloud](https://nx.app/) to learn more.

### Gulp commands

ENVIRONMENT VARIABLES

- `DOCKER_HOST <Docker host url> eg. tcp://127.0.0.1:2375`
- `DOCKER_USER <User name of Docker>`
- `DOCKER_PASSWORD <Password of Docker>`
- `TAG_NAME <Image Tag Name> default is latest`
- `CONTAINER_PORT <application portnumber> default is 80`
- `HOST_MAPPED_PORT <port mapped to the host> default is 80`
- `DOCKER_NETWORK_NAME <name of the docker network to attach>`

Optional
`DOCKER_VOLUME_NAME <Volume name for the Docker>`
`CONTAINER_VOLUME_PATH <Folder Path of the volume inside the container>`

Install gulp globally

```
npm install gulp -g
```

To install the packages use the following command

```
npm ci 
```

To build and run(Swarm Service) login application as ship with production enviornment

```
gulp build --login --ship --production
gulp runService --login --ship --production
```

To build and run(Swarm Service) cpdss application as ship with production enviornment

```
gulp build --cpdss --ship --production
gulp runService --cpdss --ship --production
```

To build and run(Swarm Service) login application as shore with production enviornment

```
gulp build --login --shore --production
gulp runService --login --shore --production
```

To build and run(Swarm Service) cpdss application as shore with production enviornment

```
gulp build --cpdss --shore --production
gulp runService --cpdss --shore --production
```

To build and run(Swarm Service) login application as ship with test enviornment

```
gulp build --login --ship --test
gulp runService --login --ship --test
```

To build and run(Swarm Service) cpdss application as ship with test enviornment

```
gulp build --cpdss --ship --test
gulp runService --cpdss --ship --test
```

To build and run(Swarm Service) login application as shore with test enviornment

```
gulp build --login --shore --test
gulp runService --login --shore --test
```

To build and run(Swarm Service) cpdss application as shore with test enviornment

```
gulp build --cpdss --shore --test
gulp runService --cpdss --shore --test
```

To remove the service use the following command with the parameters as described above

```
gulp removeService <parameters>
eg: gulp removeService --login --ship --production
```
