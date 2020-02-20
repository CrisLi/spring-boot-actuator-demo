# Note

### Customer Actuator

```
GET /actuator/myendpoint

POST /actuator/myendpoint/{name}

GET /actuator/myrestcontrollerendpoint

POST /actuator/myrestcontrollerendpoint/{name}
```

### Security Config

Default web security are `ManagementWebSecurityAutoConfiguration` and `ReactiveManagementWebSecurityAutoConfiguration`

### Prometheus Metric

```
GET /actuator/prometheus
```

### Build and Startup via Docker Compose

##### Build
```
$ mvn clean package jib:dockerBuild
```

##### Startup
```
$ cd docker
$ docker-compose up
```

> Access http://localhost:9090 for Prometheus

> Access http://localhost:3000 for Grafana

### Grafana Dashboards

https://grafana.com/grafana/dashboards/4701

https://grafana.com/grafana/dashboards/10280
