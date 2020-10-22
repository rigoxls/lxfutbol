package com.lxfutbol.transformSoap.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.service.TransportClientConfiguration;
 
@FeignClient(name="${feign.name}", url="${feign.url}",  configuration=TransportClientConfiguration.class)
public interface FlightClient {
	//@RequestMapping(method = RequestMethod.GET, value = "/bookFlight", consumes = "application/xml")
	 //public void getContract();
	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight", consumes = "application/xml", produces = "application/xml")
	    void bookFlight();
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "application/xml", produces = "application/xml")
        void searchFlight();
}
