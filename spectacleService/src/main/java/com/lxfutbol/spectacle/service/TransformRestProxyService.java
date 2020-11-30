package com.lxfutbol.spectacle.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "transform-json-service", url = "http://transform-json-service:8086")
public interface TransformRestProxyService {

	@PostMapping("/transformSpectacle/{idProvider}")
	public String transfor(@PathVariable int idProvider, @RequestBody String params);
}
