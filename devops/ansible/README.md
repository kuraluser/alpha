# CPDSS Ansible Scripts

## Files

```
ansible
│
├───Linux
│   ├───Ansible
│   │       docker-apt-playbook.yml
│   │       docker-yum-playbook.yml
│   └───Stack-Files
│           cpdss-prod-stack.yaml
└───Windows
    ├───ALGO-Essentials
    │       algo-run-setup.ps1
    │       requirements.txt
    │       start-algo.ps1
    ├───Ansible
    │       algo-playbook.yml
    │       loadicator-playbook.yml
    │       vars.yml
    └───WinRm
            ConfiguringRemoteForAnsible.ps1
```

## Linux Ansible Scripts

Ansible playbooks for setting up linux environment for CPDSS.

### Prerequisites
- Install Ansible
- Setup hosts file

### Inputs
- env_name: Environment name (eg. dev-ship, qa-ship)

#### How to use
1. Edit the hosts file
2. Run the playbook (apt for Ubuntu, yum for CentOS)

## Windows Ansible Scripts
Ansible playbooks for setting up windows environment for CPDSS.
### Prerequisites
- Run ConfiguringRemoteForAnsible.ps1 script to configure remote for Ansible on the Windows machine
- Install Ansible
- Setup hosts file
- Copy the following files to the Windows machine to the path mentioned in the playbooks
    - ampl.zip (if needed)
    - nssm.zip
    - requirements.txt
    - start-algo.ps1
    - algo-run-setup.ps1
    - loadicator.zip

### ALGO

#### Inputs
- ampl.zip path
- nssm.zip path

#### How to use
1. Edit the hosts file
2. Copy the files to the Windows machine to the path mentioned in the playbooks
3. Run the playbook

### Loadicator

#### Inputs
- loadicator.zip path

#### How to use
1. Edit the hosts file
2. Copy the files to the Windows machine to the path mentioned in the playbooks
3. Run the playbook
