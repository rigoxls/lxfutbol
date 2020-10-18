package com.lxfutbol.provider.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lxfutbol.provider.dto.ProviderDTO;
import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.service.ProviderService;

@RestController
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	@GetMapping("/provider/{providerId}")
	public Optional<ProviderEntity> getProviderById(@PathVariable long providerId) {
		Optional<ProviderEntity> provider = providerService.getProviderById(providerId);
		return provider;
	}
	
	@GetMapping("/provider/list/active")
	public List<ProviderEntity> listActiveProviders() {
		List<ProviderEntity> providers = providerService.listActiveProviders();
		return providers;
	}	
	
	@PostMapping("/provider")
	public ResponseEntity<ProviderEntity> createProvider(@RequestBody ProviderDTO newProvider) throws IOException {
		ProviderEntity provider = providerService.createProvider(newProvider);
		if (provider == null) {
			return ResponseEntity.noContent().build();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(provider.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/provider")
	public ProviderEntity updateProvider(@Valid @RequestBody ProviderDTO providerToUpdate) throws Exception {
		ProviderEntity provider = providerService.updateProvider(providerToUpdate);
		return provider;
	}
	
	@DeleteMapping("/user/{providerId}")
	public ResponseEntity<Void> deleteUser(@PathVariable long providerId) {
		providerService.deleteProvider(providerId);
		return ResponseEntity.noContent().build();
	}	
}
