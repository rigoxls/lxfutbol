package com.lxfutbol.transformSoap.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.config.LodgingClientConfiguration;
import com.lxfutbol.transformSoap.dto.TemplateDto;

@FeignClient(name="Hotel", url="${externalServer.url}",  configuration=LodgingClientConfiguration.class)
public interface LodgingClientService {

	@RequestMapping(method = RequestMethod.POST, value = "/bookFlight", consumes = "application/xml", produces = "application/xml")
    void bookFlight(@RequestBody TemplateDto template);

@RequestMapping(method = RequestMethod.POST, value = "/searchFlight", consumes = "application/xml", produces = "application/xml")
    void searchFlight(@RequestBody TemplateDto template);

@RequestMapping(method = RequestMethod.POST, value = "/cancelFlight", consumes = "application/xml", produces = "application/xml")
   void cancelFlight(@RequestBody TemplateDto template);
}
