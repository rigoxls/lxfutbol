package com.lxfutbol.transformSoap.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lxfutbol.transformSoap.config.TransportClientConfiguration;
import com.lxfutbol.transformSoap.dto.TemplateDto;
import com.lxfutbol.transformSoap.dto.Transport;
import com.lxfutbol.transformSoap.dto.TransportResult;

 
@FeignClient(name="searchFlight", url="${externalServer.url}", configuration=TransportClientConfiguration.class)
public interface TransportClientService {

	@RequestMapping(method = RequestMethod.POST, value = "{operation.value}", consumes =  "text/xml", produces =  "text/xml")
	@ResponseBody List<Transport> bookFlight(@RequestBody Transport transport );
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "text/xml", produces = "text/xml")
	void searchFlight(@RequestBody Transport transport);
	
	@RequestMapping(method = RequestMethod.POST, value = "/cancelFlight", consumes = "text/xml", produces = "text/xml")
       void cancelFlight(@RequestBody TemplateDto template);

}
