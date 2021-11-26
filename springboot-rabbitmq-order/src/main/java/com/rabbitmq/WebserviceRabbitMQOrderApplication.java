package com.rabbitmq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hafiz
 * @version 0.01
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.rabbitmq.client")
public class WebserviceRabbitMQOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebserviceRabbitMQOrderApplication.class, args);
    }

}
