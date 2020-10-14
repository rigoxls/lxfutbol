package com.lxfutbol.transformSoap.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transformSoap.dto.Providerdto;
import com.lxfutbol.transformSoap.service.TransformSoapService;
import com.lxfutbol.transformSoap.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformSoapController {

	@Autowired
	private TransformSoapService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}
