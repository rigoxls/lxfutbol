package com.lxfutbol.transformRest.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transformRest.dto.Providerdto;
import com.lxfutbol.transformRest.service.ProviderProxyService;
import com.lxfutbol.transformRest.service.TransformRestService;

@RestController
public class TransformRestController {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformRestController.class);

	@Autowired
	private TransformRestService integratorService;
	/*
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}*/
	
	@PostMapping("/transform/{idProvider}")
	public String transfor(@PathVariable int idProvider, @RequestBody String menssage) {
		
		LOG.info("*******************************+");
		LOG.info(String.valueOf(idProvider));
		LOG.info(menssage);
		LOG.info("*******************************+");
		
		return "lo que retorna a nat";
	}	
}
