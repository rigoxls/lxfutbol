package com.lxfutbol.transformSoap.controller;

import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.lxfutbol.transformSoap.dto.Providerdto;
import com.lxfutbol.transformSoap.repository.ProviderTemplateEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.service.ProviderProxyService;
import com.lxfutbol.transformSoap.service.RedisService;
import com.lxfutbol.transformSoap.service.TransformSoapService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformSoapController {
	
	@Autowired
	private TransformSoapService transformSoapService;	

	@Autowired
	private RedisService redisService;
	
	@GetMapping("/provider/get/{providerId}")
	public Optional<TransformSoapEntity> getProviderById(@PathVariable long providerId) throws JSONException {
		Optional<TransformSoapEntity> provider = transformSoapService.getProviderById(providerId);
		ProviderTemplateEntity em = new ProviderTemplateEntity();
		em.setId("1");
		em.setTemplate("{\"search\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}],\"mapping\":{\"properties\":{\"departureDate\":\"/Flight/departinDate\",\"arrivalDate\":\"/Flight/arrivingDate\",\"departureCity\":\"/Flight/departinCity\",\"flight\":\"/Flight/number\",\"class\":\"\",\"arrivalCity\":\"/Flight/arrivingCity\",\"price\":\"/Flight/price\",\"cabin\":\"/Flight/cabin\",\"meals\":\"/Flight/meals\"}}},\"book\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"name\":\"f\",\"type\":\"Flight\",\"isNull\":true,\"properties\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}]}],\"mapping\":{\"properties\":{\"result\":\"/\"}}},\"cancelBook\":{\"endpoint\":\"\",\"parameters\":[],\"mapping\":{\"properties\":{}}}}");		
		redisService.save(em);
		return provider;
	}
	
}
