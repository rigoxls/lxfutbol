package com.lxfutbol.transformJson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.lxfutbol.transformJson")
@EnableDiscoveryClient
public class TransformJsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformJsonApplication.class, args);
	}

}
