# triber-sensor
## Development
![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/develop.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/develop.svg)
![Codecov status](https://img.shields.io/codecov/c/github/triberraar/triber-sensor/develop.svg)
![Coverity scan status](https://img.shields.io/coverity/scan/6807.svg)
![Codacy code quality](https://img.shields.io/codacy/e27821fb6289410b8f58338c7e0bc686.svg)
## Master
![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/master.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/master.svg)
![Codecov status](https://img.shields.io/codecov/c/github/triberraar/triber-sensor/master.svg)

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
