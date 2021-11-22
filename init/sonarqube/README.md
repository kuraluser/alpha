# CP-DSS SonarQube Docker Stack

## Prerequisites

    - PostgreSQL - AWS RDS
    - EFS - AWS EFS

## Installation

- Create Volume Path for SonarQube in `AWS EFS` shared Directory
- Increase `Virtual Memory Limit` for SonarQube
```
sudo sysctl -w vm.max_map_count=262144
```
- Set Environment Variables for SonarQube
```
DB_HOST=<DB_HOST>
DB_USER=<DB_USER>
DB_NAME=<DB_NAME>
DB_PASSWORD=<DB_PASSWORD>
VOLUME_PATH=<VOLUME_PATH>
```
- Deploy SonarQube using Docker Stack Deploy
```
docker stack deploy -c <(docker-compose -f sonarqube-stack.yml config) sonarqube
```

## SonarQube Properties

### Gradle

Add the following properties to the `build.gradle` file in the `api/cp-dss-api` directory:

```
plugins {
    id "org.sonarqube" version "3.3"
}
.
.
.
subprojects {
    apply plugin: 'jacoco'
    sonarqube {
        jacocoTestReport {
        reports {
            xml.enabled true
            html.enabled true
            }
        }
        test.finalizedBy jacocoTestReport

        sonarqube {
        properties {
            property "sonar.sourceEncoding", "UTF-8"
            property "sonar.sources", "src/main/java"
            property "sonar.tests", "src/test"
            property "sonar.exclusions", "**/common/generated/**," +
                                         "**/common/config/**," +
                                         "**/common/exception/**," +
                                         "**/common/grpc/**," +
                                         "**/common/jsonbuilder/**," +
                                         "**/common/logging/**," +
                                         "**/common/redis/**," +
                                         "**/common/rest/**," +
                                         "**/common/scheduler/**," +
                                         "**/common/springdata/**," +
                                         "**/domain/**," +
                                         "**/repository/**," +
                                         "**/AppConfig.java," +
                                         "**/Application.java," +
                                         "**/*Constants.java," +
                                         "**/entity/*.java,"
            property "sonar.language", "java"
            property "sonar.binaries", "build/classes"
            property "sonar.dynamicAnalysis", "reuseReports"
            property "sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml"
        }
    }
}
```

### Angular

Install sonar-scanner using npm:
```
npm install sonar-scanner --save-dev
```
Add the following script to the `package.json` file in the `web/cpdss-portal` directory:
```
scripts {
    "sonar": "sonar-scanner"
}
```
Add the following properties to the `Prepare Analysis for SonarQube - Additional Properties` task in `Azure Pipelines`:
```
sonar.sourceEncoding=UTF-8
sonar.exclusions=**/node_modules/**, **/cpdss-e2e/**, **/login-e2e/**
```
