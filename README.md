# triber-sensor
## Development
![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/develop.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/develop.svg)
![Coverity scan status](https://img.shields.io/coverity/scan/6807.svg)
## Master
![Travis status](https://img.shields.io/travis/triberraar/triber-sensor/master.svg)
![Coveralls status](https://img.shields.io/coveralls/triberraar/triber-sensor/master.svg)

## Run with docker
To make the docker image, be sure to have docker tools installed and then run:

``
mvn clean package docker:build
``

To run the image use

``
docker run --name triber-sensor -d -p 8080:8080 triberraar/triber-sensor
``

This will run the application with an embedded elasticsearch and embedded H2 database.
It is also possible to run this in combination with a docker container running elasticsearch and a docker container running mysql. Run the following docker containers:

``
docker run --name triber-sensor-db -e MYSQL_ROOT_PASSWORD=triber-sensor-root -e MYSQL_DATABASE=triber-sensor -e MYSQL_USER=triber-sensor -e MYSQL_PASSWORD=triber-sensor -d -p 3306:3306 mysql:latest

docker run --name triber-sensor-elastic -d -p 9200:9200 -p 9300:9300 elasticsearch:1.7.3

docker run --name triber-sensor -d -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://<ip of docker host>:3306/triber-sensor -e SPRING_DATASOURCE_USERNAME=triber-sensor -e SPRING_DATASOURCE_PASSWORD=triber-sensor -e SPRING_DATA_ELASTICSEARCH_CLUSTER-NODES=<ip of docker host>:9300 -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_LOCAL=false -e SPRING_DATA_ELASTICSEARCH_PROPERTIES_NODE_DATA=false -e SPRING_DATA_ELASTICSEARCH_NODE_CLIENT=true triberraar/triber-sensor
``