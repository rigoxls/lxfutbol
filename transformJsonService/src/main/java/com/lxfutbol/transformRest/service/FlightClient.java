package com.lxfutbol.transformRest.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@FeignClient(name="${feign.name}", url="${feign.url}")
public interface FlightClient {
	@RequestMapping(method = RequestMethod.GET, value = "/bookFlight", consumes = "application/json")
	 public void getContract();
}
