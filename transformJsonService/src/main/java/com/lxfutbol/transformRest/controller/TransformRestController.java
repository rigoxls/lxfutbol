package com.lxfutbol.transformRest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transformRest.dto.Providerdto;
import com.lxfutbol.transformRest.service.ProviderProxyService;
import com.lxfutbol.transformRest.service.TransformRestService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformRestController {

	@Autowired
	private TransformRestService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}
