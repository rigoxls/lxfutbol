package com.lxfutbol.integrator.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lxfutbol.integrator.dto.Providerdto;

//Feign sabe que tiene que hablar con Eureka server
//@FeignClient(name = "provider-service", url = "http://192.168.99.100:8080")
@FeignClient(name = "zuul-api-gateway-service") //zuul name app
public interface ProviderProxyService {

	@GetMapping("/provider-service/provider/{providerId}")
	public Providerdto getProviderById(@PathVariable("providerId") long providerId);	
}
