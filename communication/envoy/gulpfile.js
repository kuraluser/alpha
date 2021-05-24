const gulp = require('gulp');
const argv = require('yargs').argv;
const path = require('path');
const crypto = require('crypto');
const fs = require('fs')
const exec = require('child_process').exec;
const copyfiles = require('copyfiles');
const Docker = require('dockerode');

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
let appName = argv.client ? 'client' : argv.server ? 'server' : '';
let imageName = `${dockerRegistry}/${appName}:${tagName}`;
let contextPath = path.join(__dirname, '/dist/apps/');
let hostPortArr = val[1].split(':');
let host = hostPortArr[0];
let port = hostPortArr[1];
let containerPort = process.env.CONTAINER_PORT
  ? process.env.CONTAINER_PORT
  : 81;
let hostMappedPort = process.env.HOST_MAPPED_PORT
  ? parseInt(process.env.HOST_MAPPED_PORT)
  : 81;
let containerVolumePath = process.env.CONTAINER_VOLUME_PATH
  ? process.env.CONTAINER_VOLUME_PATH
  : undefined;
let volumeName = process.env.DOCKER_VOLUME_NAME ? process.env.DOCKER_VOLUME_NAME : undefined;
let hostMountPath = process.env.HOST_MOUNT_PATH ? process.env.HOST_MOUNT_PATH : undefined;
let containerName = `${appName}-container`;
let serviceName = `${appName}-service`;
let swarmMode = 'Replicated';
let replicas = 1;
let cpuSetLimit = 0.5;
let memoryLimitInMB = 256;
let dockerNetworkName = process.env.DOCKER_NETWORK_NAME? process.env.DOCKER_NETWORK_NAME : 'cpdss-network';
let dockerClient = new Docker({
  protocol: 'http',
  host: host,
  port: port,
});
//Sample Environment variable
let dockerEnvVars = [`CONTAINER_VOLUME_PATH=${containerVolumePath}`,`APP_TYPE=${appName}`, 
`LOG_LEVEL=info`, `PORT=${containerPort}`, `SHIP_ID=${process.env.SHIP_ID}`, 
`DB_HOST=${process.env.DB_HOST}`, `DB_PASSWORD=${process.env.DB_PASSWORD}`, 
`DB_USER=${process.env.DB_USER}`];
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
    dockerEnvVars.push(`APP_ENV=${argv.production}`);
    let cmd = `npm run build:${appName}:prod`;
    console.log(cmd);
    exec(cmd, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  } else if (argv.test) {
    dockerEnvVars.push(`APP_ENV=${argv.test}`);
    let cmd = `npm run build:${appName}:test`;
    console.log(cmd);
    exec(cmd, function (err, stdout, stderr) {
      console.log(stdout);
      console.log(stderr);
      cb(err);
    });
  }
}

function generateRSAKeys(cb) {
  //For server application no need to generate the publickey privatekey pair
  if(appName != "client"){
    cb();
    return
  }
  const { privateKey, publicKey } = crypto.generateKeyPairSync('rsa', {
    modulusLength: 2048,
    publicKeyEncoding: {
      type: 'spki',
      format: 'pem'
    },
    privateKeyEncoding: {
      type: 'pkcs8',
      format: 'pem'
    }
  }); 
  try {
    fs.writeFileSync(`${process.env.SHIP_ID}.pem`, publicKey);
    fs.writeFileSync('./dist/apps/client/keys/client_certificates/jwt/private_key.pem', privateKey);
    cb();
  } catch (err) {
    cb(err);
    console.error(err)
  }
}
//Copy files
function copyFiles(cb) {
  copyfiles(['-f', 'Dockerfile', './nginx-conf/*.conf', './dist/apps/'], () => {
    cb();
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
    let stream = await dockerClient.buildImage(
      {
        context: contextPath,
        src: ['Dockerfile', appName, 'nginx-conf'],
      },
      {
        t: imageName,
        buildargs: {
          app: appName,
        },
      }
    );

    let progress = await new Promise((resolve, reject) => {
      stream.on('data', (data) => console.log(data.toString('utf8')));
      stream.on('end', () =>
        console.log(`Image built task completed for ${appName}:${tagName}`)
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
        console.log(`Image pushing task completed for ${appName}:${tagName}`)
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
    if (hostMappedPort) {
       portBindings[`${containerPort}/tcp`] = [{ HostPort: `${hostMappedPort}` }];
    }
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
    } else if(hostMountPath && containerVolumePath) {
      hostConfig.Binds = [`${hostMountPath}:${containerVolumePath}`];
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
  } else if(hostMountPath && containerVolumePath) {
    //Adding host path as volume
    let mountSettings = [
      { Target: containerVolumePath, Source: hostMountPath, Type: 'bind' },
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
  generateRSAKeys,
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
