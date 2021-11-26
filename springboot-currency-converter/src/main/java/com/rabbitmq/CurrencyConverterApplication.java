package com.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hafiz
 * @version 0.01
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class CurrencyConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
}
