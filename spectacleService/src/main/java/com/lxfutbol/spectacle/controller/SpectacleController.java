package com.lxfutbol.spectacle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.spectacle.dto.Providerdto;
import com.lxfutbol.spectacle.service.SpectacleService;
import com.lxfutbol.spectacle.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class SpectacleController {

	@Autowired
	private SpectacleService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}
