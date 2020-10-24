package com.lxfutbol.transformSoap.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.config.TransportClientConfiguration;
import com.lxfutbol.transformSoap.dto.TemplateDto;

 
@FeignClient(name="bookFlight", url="${externalServer.url}",  configuration=TransportClientConfiguration.class)
public interface TransportClientService {

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	    void bookFlight(@RequestBody String template1);
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "application/xml", produces = "application/xml")
        void searchFlight(@RequestBody TemplateDto template);
	
	@RequestMapping(method = RequestMethod.POST, value = "/cancelFlight", consumes = "application/xml", produces = "application/xml")
       void cancelFlight(@RequestBody TemplateDto template);
}
