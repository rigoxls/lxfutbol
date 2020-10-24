package com.lxfutbol.transformSoap.service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.config.TransportClientConfiguration;
import com.lxfutbol.transformSoap.dto.TemplateDto;
import com.lxfutbol.transformSoap.dto.Transport;

 
@FeignClient(name="bookFlight", url="${externalServer.url}", configuration=TransportClientConfiguration.class)
public interface TransportClientService {

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight", consumes =  "text/xml", produces =  "text/xml")
	    void bookFlight();
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "text/xml", produces = "text/xml")
        void searchFlight(@RequestBody Transport transport);
	
	@RequestMapping(method = RequestMethod.POST, value = "/cancelFlight", consumes = "text/xml", produces = "text/xml")
       void cancelFlight(@RequestBody TemplateDto template);

}
