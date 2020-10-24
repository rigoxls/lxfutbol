package com.lxfutbol.spectacle.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "transform-json-service", url = "http://192.168.20.34:8087")
public interface TransformRestProxyService {

	@RequestMapping(method = RequestMethod.POST, path="/transformSpectacle/{idProvider}")
	public String transformSpectacle(@PathVariable int idProvider, @RequestBody String menssage);	
}
