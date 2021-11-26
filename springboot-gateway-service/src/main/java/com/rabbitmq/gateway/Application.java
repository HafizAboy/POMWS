package com.rabbitmq.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
	
}
