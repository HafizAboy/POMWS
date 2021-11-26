# POMWS
Springboot Microservices Application - using Springboot, Spring Cloud Technologies (Eureka server, Zuul gateway, Feign proxy), Maven, JPA, MYSQL, ZIPKIN and RabbitMQ

* Each services are using jdk11 - docker using openjdk11
- install RabbitMQ and Zipkin (docker/locally)

8801 - eureka	- discovery service
8802 - zuul		- zuul gateway/API gateway
8803 - order	- order service (CRUD, integration with RabbitMQ to deduct stock in product service everytime order was confirmed, FeignClient to currency service to get converted currency from MYR to USD and store in order DB)
8804 - product	- product service (CRUD and received message from RabbitMQ to deduct the stock)
8805 - currency - API to convert MYR to USD
9411 - zipkin	- to trace the transactions of the microservices.

* Each projects contains its own Dockerfile for us to create the image for the webservices

* Make changes to application.yml, application.properties and Dockerfile accordingly
- order and product application.yml can use localhost or docker (docker connection commented, change accordingly)
- db connection
- Zipkin and RabbitMQ config

RUN LOCALLY:
* import project into IDE as maven project.
* update maven
* start database and create database (datasource connection inside application.yml - order and product services)
* run projects -> discovery-service -> gateway-service -> (order-service, product-service, currency-converter-service)

RUN USING DOCKER:

* compile and package each of the maven project (run inside project folder)
- mvn clean compile
- mvn clean package -Dmaven.test.skip

* Create order db container
- docker container run --name rabbitmqordermysqldb --network springboot-rabbitmq-docker -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=rabbitmq_order -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:8

* Create product db container
- docker container run --name rabbitmqproductmysqldb --network springboot-rabbitmq-docker -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=rabbitmq_product -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:8

* Create images for each and every services (run inside project folder)
- docker build . -t springboot-discovery-service
- docker build . -t springboot-gateway-service
- docker build . -t springboot-rabbitmq-order
- docker build . -t springboot-rabbitmq-product
- docker build . -t springboot-currency-converter

* single docker-compose.yml in POMWS to handle all the containers and images to be run using docker (run inside POMWS folder)
- docker-compose up

* Swagger screens for all services in images folder