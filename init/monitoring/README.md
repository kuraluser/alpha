# CPDSS Monitoring

Docker stack implementation for CPDSS Monitoring using Prometheus, Alertmanager and Grafana.

## Installation

```
HOST_SMTP=<smtp-address>
USER_SMTP=user@testmail.com
PASS_SMTP=password
TO_MAIL=reciever@testmail.com
WIN_TARGET_URL=<windows-machine-ip>
VOL_SOURCE=<volume-path>

sed -i  "s/WIN_TARGET_URL/${WIN_TARGET_URL}/" prometheus.yml
sed -i "s/HOST_SMTP/${HOST_SMTP}/" alertmanager.yml
sed -i "s/FROM_SMTP/${FROM_SMTP}/" alertmanager.yml
sed -i "s/USER_SMTP/${USER_SMTP}/" alertmanager.yml
sed -i "s/PASS_SMTP/${PASS_SMTP}/" alertmanager.yml
sed -i "s/TO_MAIL/${TO_MAIL}/" alertmanager.yml

docker stack deploy -c prometheus-grafana-stack.yml prometheus-grafana-stack
```

Prerequisites:

- Docker 20.10.7
- Swarm cluster

Services:
- Prometheus(metrics database) `http://<swarm-ip>:6090`
- grafana (visualize metrics) `http://<swarm-ip>:6080`
- node-exporter (host metrics collector)
- nginx-exporter (nginx metrics collector)
- alertmanager (alerts dispatcher) `http://<swarm-ip>:6093`

## Setup Grafana
Navigate to `http://<swarm-ip>:6080` and login with user admin password admin. You can change the credentials in the compose file or by supplying the `ADMIN_USER` and `ADMIN_PASSWORD` environment variables at stack deploy.

## Configure alerting

The following alerts are configured:

*Instance Down*

Alerts when a job is down for 1 min

```
ALERT InstanceDown
    IF up == 0
    FOR 1m
    LABELS { severity = "critical" }
    ANNOTATIONS {
      summary = "Endpoint {{ $labels.instance }} down"
      description = "{{ $labels.instance }} of job {{ $labels.job }} has been down for more than 1 minutes."
    }
```

*Linux Node Out Of Memory Alert*

Alerts when a linux node Memory is filling up less than 25% for five minutes.

```
ALERT HostOutOfMemory
    IF node_memory_MemAvailable_bytes / node_memory_MemTotal_bytes * 100 < 25
    FOR 5m
    LABELS { severity = warning }
    ANNOTATIONS {
      summary: "Host out of memory (instance {{ $labels.instance }})"
      description: "Node memory is filling up (< 25% left)\n VALUE = {{ $value }}\n LABELS: {{ $labels }}"
    }
```

*Linux Host High CPU Load Alert*

Alerts when a linux node CPU usage goes over 80% for five minutes.

```
ALERT HostHighCpuLoad
    IF (sum by (instance) (irate(node_cpu_seconds_total{job="node_exporter",mode="idle"}[5m]))) > 80
    FOR 5m
    LABELS { severity = warning }
    ANNOTATIONS {
      summary: "Host high CPU load (instance {{ $labels.instance }})"
      description: "CPU load is > 80%\n VALUE = {{ $value }}\n LABELS: {{ $labels }}"
    }
```

*Windows High CPU Alert*

Alerts when a windows node CPU usage goes over 95% for fifteen minutes.

```
ALERT HighCPUUsage
    IF 100 * sum by (instance) (rate(windows_cpu_time_total{mode != 'idle'}[5m])) / count by (instance) (windows_cpu_core_frequency_mhz) > 95
    FOR 15m
    LABELS { severity = page}
    ANNOTATIONS {
      summary: High CPU usage in instance {{$labels.instance}}
    }
```

*Windows Memeory Alert*

Alerts when a windows node memory usage goes over 95% for fifteen minutes.

```
ALERT HighPhysicalMemoryUsage
    IF 100 * (windows_cs_physical_memory_bytes - windows_os_physical_memory_free_bytes) / windows_cs_physical_memory_bytes  > 95
    FOR 15m
    LABELS { severity = page}
    ANNOTATIONS {
      summary: High physical memory usage in instance {{$labels.instance}}
    }
```

*Windows Disk Alert*

Alerts when a windows node storage usage goes over 95% for fifteen minutes.

```
ALERT LogicalDiskFull
    IF 100 * (windows_logical_disk_size_bytes - windows_logical_disk_free_bytes) / windows_logical_disk_size_bytes  > 95
    FOR 15m
    LABELS { severity = page}
    ANNOTATIONS {
      summary: Disk {{$labels.volume}} full over 95% in instance {{$labels.instance}}
    }
```

*Windows Restart Alert*

Alerts when a windows node is restarted within 1 hour.

```
ALERT UpTimeLessThanOneHour
    IF time() - windows_system_system_up_time  < 3600
    FOR 15m
    LABELS { severity = page}
    ANNOTATIONS {
      summary: Up Time of less than 1 hour in instance {{$labels.instance}}
    }
```

## Monitoring Java Microservices

You can monitor Java microservices by adding the following to the `prometheus.yml` file:

```
job_name: "gateway-actuator"
metrics_path: "/actuator/prometheus"
scrape_interval: 5s
static_configs:
    - targets: ["gateway-service:8080"]
```