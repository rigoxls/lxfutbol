package com.lxfutbol.transformJson.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transformJson.dto.Providerdto;
import com.lxfutbol.transformJson.service.TransformJsonService;
import com.lxfutbol.transformJson.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformJsonController {

	@Autowired
	private TransformJsonService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}
