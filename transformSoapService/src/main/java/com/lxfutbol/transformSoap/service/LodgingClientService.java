package com.lxfutbol.transformSoap.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxfutbol.transformSoap.config.LodgingClientConfiguration;
import com.lxfutbol.transformSoap.dto.TemplateDto;

@FeignClient(name="Hotel", url="${externalServer.url}",  configuration=LodgingClientConfiguration.class)
public interface LodgingClientService {

	@RequestMapping(method = RequestMethod.POST, value = "/bookRoom", consumes = "application/xml", produces = "application/xml")
    void bookRoom(@RequestBody String template1);

@RequestMapping(method = RequestMethod.POST, value = "/initiate", consumes = "application/xml", produces = "application/xml")
    void roomService(@RequestBody String template1);



}
