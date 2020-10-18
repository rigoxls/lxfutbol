package com.lxfutbol.provider.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.service.ProviderService;

//import com.lxfutbol.providerDTO;

@RestController
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	@GetMapping("/provider/get/{providerId}")
	public Optional<ProviderEntity> getProviderById(@PathVariable long providerId) {
		Optional<ProviderEntity> provider = providerService.getProviderById(providerId);
		return provider;
	}	
}
