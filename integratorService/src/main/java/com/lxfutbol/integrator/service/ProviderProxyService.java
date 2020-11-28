package com.lxfutbol.integrator.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.lxfutbol.integrator.dto.Providerdto;

//Feign sabe que tiene que hablar con Eureka server
@FeignClient(name = "provider-service", url = "http://provider-service:8080")
//@FeignClient(name = "zuul-api-gateway-service") //zuul name app
public interface ProviderProxyService {

	//@PutMapping("/provider-service/provider/status/{providerId}/{status}")
	@PutMapping("/provider/status/{providerId}/{status}")
	public Providerdto statusProviderById(@PathVariable("providerId") long providerId, @PathVariable("status") int status);	
}
