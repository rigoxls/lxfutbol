package com.lxfutbol.transport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transport.dto.Providerdto;
import com.lxfutbol.transport.service.TransportService;
import com.lxfutbol.transport.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransportController {

	@Autowired
	private TransportService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}
