package com.lxfutbol.transformSoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.lxfutbol.transformSoap")
@EnableDiscoveryClient
public class TransformSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformSoapApplication.class, args);
	}

}
