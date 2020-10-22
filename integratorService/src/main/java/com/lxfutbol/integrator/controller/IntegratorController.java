package com.lxfutbol.integrator.controller;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.integrator.dto.Providerdto;
import com.lxfutbol.integrator.kafka.KafkaIntegratorSender;
import com.lxfutbol.integrator.service.IntegratorService;
import com.lxfutbol.integrator.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class IntegratorController {

	@Autowired
	private IntegratorService integratorService;
	
	@Autowired
	private KafkaIntegratorSender kafkaIntegratorSender;	
	
	@Autowired
	private ProviderProxyService providerProxyService;
	
	@Value("${com.lxfutbol.integrator.kafka.topic-1}")
	private String topic_1;	
	
	/*
	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}*/
	
	@GetMapping("/integrator/transport/{departureCity}/{arrivalCity}/{departureDate}")	
	public String searchTransport(			
			@PathVariable String departureCity,
			@PathVariable String arrivalCity, 
			@PathVariable String departureDate) throws InterruptedException, ExecutionException, JSONException {
		
		JSONObject template = new JSONObject();
		
		JSONObject params = new JSONObject();
		params.put("operation", "search");
		params.put("departureCity", departureCity);
		params.put("arrivalCity", arrivalCity);
		params.put("departureDate", departureDate);
		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("request_providers", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj);
		
		return template.toString();		
	}
}
