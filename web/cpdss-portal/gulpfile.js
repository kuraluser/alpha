var gulp = require('gulp');
var argv = require('yargs').argv;
var path = require('path');
var exec = require('child_process').exec;
var copyfiles = require('copyfiles');
var Docker = require('dockerode');

//Configuring Docker
let val = process.env.DOCKER_HOST.split('//');
let dockerRegistry = process.env.DOCKER_REGISTRY;
let dockerUser = process.env.DOCKER_USER;
let dockerPassword = process.env.DOCKER_PASSWORD;
let tagName = process.env.TAG_NAME ? process.env.TAG_NAME : 'latest';
let swarmServiceUpdateFailureAction = process.env
  .SWARM_SERVICE_UPDATE_FAILURE_ACTION
  ? process.env.SWARM_SERVICE_UPDATE_FAILURE_ACTION
  : 'rollback';
let appName = argv.login ? 'login' : argv.cpdss ? 'cpdss' : '';
let docName = argv.mol ? 'mol' : argv.synergy ? 'synergy' : '';
let imageName = `${dockerRegistry}/${appName}:${tagName}`;
let contextPath = path.join(__dirname, '/dist/apps/');
let hostPortArr = val[1].split(':');
let host = hostPortArr[0];
let port = hostPortArr[1];
let containerPort = process.env.CONTAINER_PORT
  ? process.env.CONTAINER_PORT
  : 80;
let hostMappedPort = process.env.HOST_MAPPED_PORT
  ? parseInt(process.env.HOST_MAPPED_PORT)
  : 80;
let containerVolumePath = process.env.CONTAINER_VOLUME_PATH
  ? process.env.CONTAINER_VOLUME_PATH
  : undefined;
let volumeName = process.env.DOCKER_VOLUME_NAME ? process.env.DOCKER_VOLUME_NAME : undefined;
let containerName = `${appName}-container`;
let serviceName = `${appName}-service`;
let swarmMode = 'Replicated';
let replicas = 1;
let cpuSetLimit = 0.5;
let memoryLimitInMB = 256;
let dockerNetworkName = process.env.DOCKER_NETWORK_NAME ? process.env.DOCKER_NETWORK_NAME : 'cpdss-network';
let dockerClient = new Docker({
  protocol: 'http',
  host: host,
  port: port,
});
//Sample Environment variable
let dockerEnvVars = ['MY_EVN=test'];
//Registry Authentication
const registryAuth = {
  auth: '',
  password: dockerPassword,
  serveraddress: `https://${dockerRegistry}`,
  username: dockerUser,
};

//Building application
function buildApp(cb) {
  if (argv.production) {
    let cmd = `npm run build:${appName}:${argv.shore ? 'shore' : argv.ship ? 'ship' : ''
      }`;
    console.log(cmd);
    exec(cmd, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  } else if (argv.staging) {
    let cmd = `npm run build:${appName}:${argv.shore ? 'shore' : argv.ship ? 'ship' : ''
      }`;
    console.log(`${cmd}:staging`);
    exec(`${cmd}:staging`, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  } else if (argv.test) {
    let cmd = `npm run build:${appName}:${argv.shore ? 'shore' : argv.ship ? 'ship' : ''
      }`;
    console.log(`${cmd}:test`);
    exec(`${cmd}:test`, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  } else if (argv.uat) {
    let cmd = `npm run build:${appName}:${argv.shore ? 'shore' : argv.ship ? 'ship' : ''
      }`;
    console.log(`${cmd}:uat`);
    exec(`${cmd}:uat`, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  }
}
//Copy files
function copyFiles(cb) {
  if (docName) {
    copyfiles(['-f', 'login.Dockerfile', './nginx/*.conf', './dist/apps/'], () => {
      copyDirectory(cb)
    });
  }
  else {
    copyfiles(['-f', 'cpdss.Dockerfile', './nginx/*.conf', './dist/apps/'], () => {
      cb();
    });
  }
}
function copyDirectory(cb) {
  exec(`cp -r ../../help/${docName}/ ./dist/apps/`, function (err, stdout, stderr) {
    console.log(stdout);
    console.log(stderr);
    cb(err);
  });
}

//Check registry authentication
function checkRegistryAuth(cb) {
  dockerClient.checkAuth(registryAuth, (err, res) => {
    if (err) console.log('Error', err);
    console.log(res);
    cb();
  });
}

//Dockerize the application
async function dockerize(cb) {
  try {
    let stream = undefined;
    if (docName) {
      stream = await dockerClient.buildImage(
        {
          context: contextPath,
          src: ['login.Dockerfile', appName, 'nginx', docName],
        },
        {
          t: imageName,
          dockerfile: 'login.Dockerfile',
          buildargs: {
            app: appName,
            doc: docName
          },
        }
      );
    }
    else {
      stream = await dockerClient.buildImage(
        {
          context: contextPath,
          src: ['cpdss.Dockerfile', appName, 'nginx'],
        },
        {
          t: imageName,
          dockerfile: 'cpdss.Dockerfile',
          buildargs: {
            app: appName
          },
        }
      );
    }
    let progress = await new Promise((resolve, reject) => {
      stream.on('data', (data) => console.log(data.toString('utf8')));
      stream.on('end', () =>
        console.log(`Successfully built ${appName}:${tagName}`)
      );
      dockerClient.modem.followProgress(stream, (err, res) =>
        err ? reject(err) : resolve(res)
      );
    });
  } catch (err) {
    console.log(err);
  }
  cb();
}

//Push image
async function pushImage(cb) {
  var image = dockerClient.getImage(imageName);
  try {
    let stream = await image.push({
      authconfig: registryAuth,
    });
    let progress = await new Promise((resolve, reject) => {
      stream.on('data', (data) => console.log(data.toString('utf8')));
      stream.on('end', () =>
        console.log(`Successfully pushed ${appName}:${tagName}`)
      );
      dockerClient.modem.followProgress(stream, (err, res) =>
        err ? reject(err) : resolve(res)
      );
    });
  } catch (err) {
    console.log(err);
  }
  cb();
}

// Method to pull an Image
async function pullImage(cb) {
  //Pulling image
  let stream = await dockerClient.pull(imageName, {
    authconfig: registryAuth,
  });
  let progress = await new Promise((resolve, reject) => {
    stream.on('data', (data) => console.log(data.toString('utf8')));
    stream.on('end', () =>
      console.log(`Successfully pulled ${appName}:${tagName}`)
    );
    dockerClient.modem.followProgress(stream, (err, res) =>
      err ? reject(err) : resolve(res)
    );
  });
  cb();
}

// Method to run a container
async function runContainer(cb) {
  try {
    //Adding port bindings
    let portBindings = {};
    portBindings[`${containerPort}/tcp`] = [{ HostPort: `${hostMappedPort}` }];
    //Adding host config
    let hostConfig = {
      PortBindings: portBindings,
      RestartPolicy: {
        Name: 'on-failure',
        MaximumRetryCount: 10,
      },
      CpuPeriod: 100000,
      CpuQuota: cpuSetLimit * 100000,
      Memory: memoryLimitInMB * 1000000,
      NetworkMode: dockerNetworkName ? dockerNetworkName : 'bridge',
    };
    if (volumeName && containerVolumePath) {
      hostConfig.Binds = [`${volumeName}:${containerVolumePath}`];
    }
    //Adding exposed ports
    let exposedPorts = {};
    exposedPorts[`${containerPort}/tcp`] = {};

    let container = await dockerClient.createContainer({
      Image: imageName,
      Tty: false,
      name: containerName,
      HostConfig: hostConfig,
      Env: dockerEnvVars,
      ExposedPorts: exposedPorts,
    });
    await container.start();
    console.log(`Started the container ${appName}-container`);
    cb();
  } catch (err) {
    console.log(err);
  }
}

// Method to remove a container
async function removeContainer(cb) {
  let container = dockerClient.getContainer(containerName);
  await container.stop();
  console.log('Stopping the container ' + containerName);
  await container.remove();
  console.log('Successfully removed the container ' + containerName);
}

// Method to run a service
async function runSwarmService(cb) {
  //Adding resource limits
  let resourceLimit = {};
  if (cpuSetLimit) {
    resourceLimit['NanoCPUs'] = cpuSetLimit * 1000000000;
  }
  if (memoryLimitInMB) {
    resourceLimit['MemoryBytes'] = memoryLimitInMB * 1000000;
  }
  let resourceRequirements = {};
  if (Object.keys(resourceLimit).length != 0) {
    resourceRequirements['Limits'] = resourceLimit;
  }
  //Adding taskspec for imagename, enviroment variables
  let taskSpec = { Resources: resourceRequirements };
  taskSpec['ContainerSpec'] = {
    Image: imageName,
    Env: dockerEnvVars,
    Tty: false,
  };
  if (volumeName && containerVolumePath) {
    //Adding volumes
    let mountSettings = [
      { Target: containerVolumePath, Source: volumeName, Type: 'volume' },
    ];
    taskSpec['ContainerSpec'].Mounts = mountSettings;
  }
  //Adding swarm mode
  let serviceMode = {};
  if (swarmMode === 'Replicated') {
    serviceMode['Replicated'] = { Replicas: replicas };
  } else if (swarmMode === 'Global') {
    serviceMode['Global'] = {};
  }
  //Adding updateconfig
  let updateconfig = {
    Parallelism: 1,
    Delay: 2,
    FailureAction: swarmServiceUpdateFailureAction,
  };

  //Adding network
  let networks = [];
  if (dockerNetworkName) {
    let networkName = {};
    networkName['Target'] = dockerNetworkName;
    networks.push(networkName);
  }

  //Adding Swarmpit network
  let networkName = {};
  networkName['Target'] = "swarmpit_net";
  networks.push(networkName)

  //Adding ports for publishing
  let portConfigArray = [];
  if (hostMappedPort) {
    let portConfig = {};
    portConfig['Protocol'] = 'tcp';
    portConfig['TargetPort'] = containerPort;
    portConfig['PublishedPort'] = hostMappedPort;
    portConfig['PublishMode'] = 'ingress';
    portConfigArray.push(portConfig);
  }
  let endPointSpec = {};
  endPointSpec['Ports'] = portConfigArray;

  const auth = {
    username: dockerUser,
    password: dockerPassword,
  };

  let serviceResponse = await dockerClient.createService(auth, {
    Name: serviceName,
    TaskTemplate: taskSpec,
    Mode: serviceMode,
    UpdateConfig: updateconfig,
    Networks: networks,
    EndpointSpec: endPointSpec,
  });
  console.log('Successfully started service ' + serviceName);
}

// Method to remove a service
async function removeSwarmService(cb) {
  let service = await dockerClient.getService(serviceName);
  await service.remove();
  console.log('Successfully removed service ' + serviceName);
}

const build = gulp.series(
  buildApp,
  copyFiles,
  checkRegistryAuth,
  dockerize,
  pushImage
);

const runDocker = gulp.series(pullImage, runContainer);
const removeDocker = gulp.series(removeContainer);

const runService = gulp.series(pullImage, runSwarmService);
const removeService = gulp.series(removeSwarmService);

exports.build = build;
exports.runDocker = runDocker;
exports.removeDocker = removeDocker;

exports.runService = runService;
exports.removeService = removeService;
