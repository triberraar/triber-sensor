# triber-sensor
## Development
[![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/develop.svg)](https://travis-ci.org/triberraar/triber-sensor)
[![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/develop.svg)](https://coveralls.io/github/triberraar/triber-sensor?branch=develop)
[![Codecov status](https://img.shields.io/codecov/c/github/triberraar/triber-sensor/develop.svg)](https://codecov.io/github/triberraar/triber-sensor?branch=develop)
[![Coverity scan status](https://img.shields.io/coverity/scan/6807.svg)](https://scan.coverity.com/projects/triberraar-triber-sensor?tab=overview)
[![Codacy code quality](https://img.shields.io/codacy/5bccde56346a4e62b1c3939e39dd04b4/develop.svg)](https://www.codacy.com/app/geertolaerts/triber-sensor/dashboard)
## Master
[![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/master.svg)](https://travis-ci.org/triberraar/triber-sensor)
[![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/master.svg)](https://coveralls.io/github/triberraar/triber-sensor?branch=master)
[![Codecov status](https://img.shields.io/codecov/c/github/triberraar/triber-sensor/master.svg)](https://codecov.io/github/triberraar/triber-sensor?branch=master)
[![Codacy code quality](https://img.shields.io/codacy/5bccde56346a4e62b1c3939e39dd04b4/master.svg)](https://www.codacy.com/app/geertolaerts/triber-sensor/dashboard)

## Used technologies
This application is build upon Spring boot. It uses a mysql database (in memory or persistent), an elasticsearch cluster and optional mqtt.
Other parts of Spring boot are used as well (eg liquibase, hibernate, jackson, integration, ...).

## Configuration
The configuration uses Spring boot as well. So all configuration can be overridden in the Spring boot way (config file, command line arguments, ...).
For the general properties have a look at the application.properties file.
### Profiles
#### MQTT
The profile 'MQTT' activates this application as an mqtt client. A broker has to be running for this. The default connection configuration for the broker is:
* mqtt.broker.ip=localhost
* mqtt.broker.port=1883

#### dev
The profile 'dev' activates the MQTT profile. It also uses the H2 database (stored in ~/triberSensor) and drops and reinitiates the whole database (using liquibase). After this it populates the database with some test data.
This is the profile that should be used during development

#### test
This profile is used during the integration tests. It clears the elasticsearch cluster.

## Run with docker
To make the docker image, be sure to have docker tools installed and then run:

```
mvn clean package docker:build
```

To run the image use

```
docker run --name triber-sensor -d -p 8080:8080 triberraar/triber-sensor
```

This will run the application with an embedded elasticsearch and embedded H2 database.
It is also possible to run this in combination with a docker container running elasticsearch and a docker container running mysql.
Just use Docker compose by saying docker-compose up
Unless you want to launch everything seperate (eg to run different containers on different machines):

###x86
[![image size](https://img.shields.io/imagelayers/image-size/triberraar/triber-sensor/latest.svg)](https://imagelayers.io/?images=triberraar%2Ftriber-sensor:latest)
[![layers](https://img.shields.io/imagelayers/layers/triberraar/triber-sensor/latest.svg)](https://imagelayers.io/?images=triberraar%2Ftriber-sensor:latest)

```
start a mysql container
docker run --name triber-sensor-db -e MYSQL_ROOT_PASSWORD=<root> -e MYSQL_DATABASE=triber-sensor -e MYSQL_USER=<user> -e MYSQL_PASSWORD=<password> -d -p 3306:3306 mysql:latest
```

```
start an elasticsearch container
docker run --name triber-sensor-elastic -d -p 9200:9200 -p 9300:9300 elasticsearch:1.7.3
```

```
start the application container
docker run --name triber-sensor -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://<ip of docker host>:3306/triber-sensor -e SPRING_DATASOURCE_USERNAME=<user> -e SPRING_DATASOURCE_PASSWORD=<password> -e SPRING_DATA_ELASTICSEARCH_CLUSTER-NODES=<ip of docker host>:9300 -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_LOCAL=false -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_DATA=false -e SPRING_DATA_ELASTICSEARCH_NODE_CLIENT=true triberraar/triber-sensor
```

```
start the application container with docker container linking
docker run --name triber-sensor --link triber-sensor-db --link triber-sensor-elastic -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://triber-sensor-db:3306/triber-sensor -e SPRING_DATASOURCE_USERNAME=<user> -e SPRING_DATASOURCE_PASSWORD=<password> -e SPRING_DATA_ELASTICSEARCH_CLUSTER-NODES=triber-sensor-elastic:9300 -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_LOCAL=false -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_DATA=false -e SPRING_DATA_ELASTICSEARCH_NODE_CLIENT=true triberraar/triber-sensor
```

###Raspberry pi
[![image size](https://img.shields.io/imagelayers/image-size/triberraar/rpi-triber-sensor/latest.svg)](https://imagelayers.io/?images=triberraar%2Frpi-triber-sensor:latest)
[![layers](https://img.shields.io/imagelayers/layers/triberraar/rpi-triber-sensor/latest.svg)](https://imagelayers.io/?images=triberraar%2Frpi-triber-sensor:latest)

Use a datadirectory (eg /triber-sensor/data/elastic)

```
start a mysql container
docker run --name triber-sensor-db -e MYSQL_ROOT_PASSWORD=<root> -e MYSQL_DATABASE=triber-sensor -e MYSQL_USER=<user> -e MYSQL_PASSWORD=<password> -d -p 3306:3306 -v <data-directory>:/var/lib/mysql hypriot/rpi-mysql:latest
```

```
start an elasticsearch container
docker run --name triber-sensor-elastic -d -p 9200:9200 -p 9300:9300 -v <data-directory>:/data rpidockers/elasticsearch:1.7.1
```

```
start application
docker run --name triber-sensor -d -p 8080:8080 triberraar/rpi-triber-sensor
```
