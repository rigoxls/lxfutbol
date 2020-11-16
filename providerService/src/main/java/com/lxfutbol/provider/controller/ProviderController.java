package com.lxfutbol.provider.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lxfutbol.provider.dto.ProviderDTO;
import com.lxfutbol.provider.repository.ProviderTemplateEntity;
import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.service.ProviderService;
import com.lxfutbol.provider.service.RedisService;

@CrossOrigin(origins="*")
@RestController
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	@Autowired
	private RedisService redisService;

	ProviderController() {
		// Providers in memory
		CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
			List<ProviderEntity> providers = providerService.listActiveProviders();
			
			for (ProviderEntity provider : providers) {		
				String search = provider.getOperationEntity().getSearch();
				String book = provider.getOperationEntity().getBook();
				String cancelBook = provider.getOperationEntity().getCancelBook();
				JSONObject template = new JSONObject();
				
				try {					
					JSONObject searchTemplate = new JSONObject(search);
					JSONObject bookTemplate = new JSONObject(book);
					JSONObject cancelTemplate = new JSONObject(cancelBook);
					
					template.put("search", searchTemplate.get("search"));
					template.put("book", bookTemplate.get("book"));
					template.put("cancelBook", cancelTemplate.get("cancelBook"));
										
					ProviderTemplateEntity providerTemplate = new ProviderTemplateEntity();
					providerTemplate.setId(Long.toString(provider.getId()));
					providerTemplate.setTemplate(template.toString());
					redisService.save(providerTemplate);	
										
					System.out.println("*********************************");
					System.out.println(provider.getId());					
					
				} catch(Exception  e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	@GetMapping("/provider/{providerId}")
	public Optional<ProviderEntity> getProviderById(@PathVariable long providerId) throws JSONException {
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
	
	@PutMapping("/provider/status/{providerId}/{status}")
	public ProviderEntity statusProvider(@PathVariable long providerId, @PathVariable int status) throws Exception {
		ProviderEntity provider = providerService.setProviderStatus(providerId, status);
		return provider;
	}	

	@DeleteMapping("/provider/{providerId}")
	public ResponseEntity<Void> deleteUser(@PathVariable long providerId) {
		providerService.deleteProvider(providerId);
		return ResponseEntity.noContent().build();
	}
}
