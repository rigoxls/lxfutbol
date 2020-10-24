package com.lxfutbol.integrator.controller;

import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	
	@PutMapping("/integrator/status/{providerId}/{status}")
	public Providerdto statusProviderById(@PathVariable long providerId, @PathVariable int status) {
		Providerdto provider = providerProxyService.statusProviderById(providerId, status);
		return provider;
	}
	
	@GetMapping("/integrator/transport/{departureCity}/{arrivalCity}/{departureDate}")	
	public ResponseEntity<JsonNode> searchTransport(
			@PathVariable String departureCity,
			@PathVariable String arrivalCity, 
			@PathVariable String departureDate) 
					throws InterruptedException, ExecutionException, JSONException, JsonMappingException, JsonProcessingException {
		
		JSONObject template = new JSONObject();
		
		JSONObject params = new JSONObject();
		params.put("operation", "search");
		params.put("departureCity", departureCity);
		params.put("arrivalCity", arrivalCity);
		params.put("departureDate", departureDate);
		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("request_providers", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj.get("providers"));
		
		String transportResponse = kafkaIntegratorSender.sendMessageWithCallback(template.toString(), "integrator-transport");
		
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(transportResponse);
        return ResponseEntity.ok(json);
		
	}
	
	@PostMapping("/integrator/lodge")	
	public ResponseEntity<JsonNode> searchLodge(@RequestBody String request) 
					throws InterruptedException, ExecutionException, JSONException, JsonMappingException, JsonProcessingException {
		
		JSONObject data = (JSONObject) new JSONObject(request).getJSONObject("data");
		
		JSONObject template = new JSONObject();
		
		JSONObject params = new JSONObject();
		params.put("operation", "search");
		params.put("city", data.get("city"));
		params.put("country", data.get("country"));
		params.put("checkIn", data.get("checkIn"));
		params.put("checkOut", data.get("checkOut"));
		params.put("rooms", data.get("room"));
		params.put("type", data.get("type"));

		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("request_providers", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj.get("providers"));
		
		String transportResponse = kafkaIntegratorSender.sendMessageWithCallback(template.toString(), "integrator-lodge");
		
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(transportResponse);
        return ResponseEntity.ok(json);
		
	}	
}
