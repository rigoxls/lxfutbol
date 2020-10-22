package com.lxfutbol.transformSoap.service;

import javax.validation.Payload;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.config.TransportClientConfiguration;

 
@FeignClient(name="bookFlight", url="http://localhost:8082",  configuration=TransportClientConfiguration.class)
public interface TransportClientService {

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight", consumes = "application/xml", produces = "application/xml")
	    void bookFlight(@RequestBody Payload template);
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "application/xml", produces = "application/xml")
        void searchFlight(@RequestBody Payload template);
}
