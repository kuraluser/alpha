# Fluentd Configuration

## Installation

### Customize Fluentd Base Image

Build image

```
cd fluentd-base
docker build -t <ECR repository url>:<base-image-tag> .
```
Push Image to ECR

```
docker push <ECR repository url>:<base-image-tag>
```

### Build Fluentd Docker Image

Build image

```
docker build -t <ECR repository url>:<image-tag> .
```

Push Image to ECR

```
docker push <ECR repository url>:<image-tag>
```

### Install Fluentd Stack

- Create fluentd Directory in Volume Source Path
- Copy fluentd.conf to fluentd directory
- Create pos Directory inside flunetd Directory
- Deploy fluentd stack
```
docker stack deploy -c fluentd-stack.yaml fluentd-stack --with-registry-auth
```
