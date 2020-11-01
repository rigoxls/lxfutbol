package com.lxfutbol.transport.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "transform-json-service", url = "http://localhost:8086")
public interface TransformRestProxyService {

	@RequestMapping(method = RequestMethod.POST, path="/transform/{idProvider}")
	public String transfor(@PathVariable int idProvider, @RequestBody String menssage);	
}
