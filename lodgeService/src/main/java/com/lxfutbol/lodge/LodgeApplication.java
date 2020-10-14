package com.lxfutbol.lodge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.lxfutbol.lodge")
@EnableDiscoveryClient
public class LodgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LodgeApplication.class, args);
	}

}
