package com.lxfutbol.provider.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
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
import com.lxfutbol.provider.repository.ProviderTemplateEntity;
import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.service.ProviderService;
import com.lxfutbol.provider.service.RedisService;

@RestController
public class ProviderController {

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private RedisService redisService;

	@GetMapping("/provider/{providerId}")
	public Optional<ProviderEntity> getProviderById(@PathVariable long providerId) throws JSONException {
		Optional<ProviderEntity> provider = providerService.getProviderById(providerId);
		
		ProviderTemplateEntity em = new ProviderTemplateEntity();
		em.setId("1");
		em.setTemplate("{\"search\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}],\"mapping\":{\"properties\":{\"departureDate\":\"/Flight/departinDate\",\"arrivalDate\":\"/Flight/arrivingDate\",\"departureCity\":\"/Flight/departinCity\",\"flight\":\"/Flight/number\",\"class\":\"\",\"arrivalCity\":\"/Flight/arrivingCity\",\"price\":\"/Flight/price\",\"cabin\":\"/Flight/cabin\",\"meals\":\"/Flight/meals\"}}},\"book\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"name\":\"f\",\"type\":\"Flight\",\"isNull\":true,\"properties\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}]}],\"mapping\":{\"properties\":{\"result\":\"/\"}}},\"cancelBook\":{\"endpoint\":\"\",\"parameters\":[],\"mapping\":{\"properties\":{}}}}");		
		redisService.save(em);
		
		redisService.findById("1");
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
