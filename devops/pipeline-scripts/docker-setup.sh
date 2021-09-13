#!/bin/bash

sudo netstat -lntp | grep dockerd

sudo systemctl status docker

sudo mkdir -p /etc/systemd/system/docker.service.d

cd /etc/systemd/system/

sudo chmod -R 777 docker.service.d

sudo touch /etc/systemd/system/docker.service.d/options.conf

cd /etc/systemd/system/docker.service.d/

sudo chmod -R 777 options.conf
sudo cat >/etc/systemd/system/docker.service.d/options.conf <<EOL
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H unix:// -H tcp://0.0.0.0:2375
EOL

sudo systemctl daemon-reload

sudo systemctl restart docker

sudo systemctl status docker

sudo netstat -lntp | grep dockerd