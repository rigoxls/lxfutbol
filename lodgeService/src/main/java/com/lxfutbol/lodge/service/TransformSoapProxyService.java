package com.lxfutbol.lodge.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//Feign sabe que tiene que hablar con Eureka server
@FeignClient(name = "transform-soap-service", url = "http://transform-soap-service:80")
//@FeignClient(name = "zuul-api-gateway-service") //zuul name app
public interface TransformSoapProxyService {

	@RequestMapping(method = RequestMethod.POST, path="/transformLodge/{idProvider}")
	public String transforLodge(@PathVariable int idProvider, @RequestBody String menssage);	
}
