package com.lxfutbol.lodge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.lodge.dto.Providerdto;
import com.lxfutbol.lodge.service.LodgeService;
import com.lxfutbol.lodge.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class LodgeController {

	@Autowired
	private LodgeService integratorService;
	
	@Autowired
	private ProviderProxyService providerProxyService;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
}