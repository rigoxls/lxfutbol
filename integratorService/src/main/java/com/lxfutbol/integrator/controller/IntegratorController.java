package com.lxfutbol.integrator.controller;

import java.util.concurrent.ExecutionException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.lxfutbol.integrator.dto.ReserveLodge;
import com.lxfutbol.integrator.dto.ReserveSpectacle;
import com.lxfutbol.integrator.dto.ReserveTransport;
import com.lxfutbol.integrator.kafka.KafkaIntegratorSender;
import com.lxfutbol.integrator.service.IntegratorService;
import com.lxfutbol.integrator.service.ProviderProxyService;
import com.lxfutbol.integrator.service.ReserveService;
import com.netflix.discovery.converters.Auto;

//import com.lxfutbol.integratorDTO;

@CrossOrigin(origins="*")
@RestController
public class IntegratorController {

	@Autowired
	private IntegratorService integratorService;
	
	@Autowired
	private ReserveService reserveService;
	
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
		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("1", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj.get("providers"));
		
		String transportResponse = kafkaIntegratorSender.sendMessageWithCallback(template.toString(), "integrator-transport");
		
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(transportResponse);
        return ResponseEntity.ok(json);
		
	}
	
	@PostMapping("/integrator/transport")	
	public Boolean reserveTransport(@RequestBody  ReserveTransport transport) throws JSONException{
		
		JSONObject params = new JSONObject();	
		params.put("departureCity", transport.getOriginCity());
		params.put("arrivalCity", transport.getDestinationCity());
		params.put("departureDate", transport.getDepartureDate());
		params.put("arrivalDate", transport.getArrivalDate());
		params.put("flight", transport.getFlight());
		params.put("class", transport.getClassFlight());
		params.put("price", transport.getPrice());
		params.put("passengerName", transport.getPassengerName());
		params.put("passengerIdentityNumber", transport.getPassengerIdentityNumber());
		params.put("idProvider", transport.getIdProvider());
		
		return reserveService.reserve(params);
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

		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("2", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj.get("providers"));
		
		String transportResponse = kafkaIntegratorSender.sendMessageWithCallback(template.toString(), "integrator-lodge");
		
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(transportResponse);
        return ResponseEntity.ok(json);
		
	}
	
	@PostMapping("/integrator/transport")	
	public Boolean reserveLodge(@RequestBody  ReserveLodge lodge) throws JSONException{
		
		JSONObject params = new JSONObject();	
		params.put("guestName", lodge.getGuestName());
		params.put("roomNumber", lodge.getRoomNumber());
		params.put("checkout", lodge.getCheckout());
		params.put("checkin", lodge.getCheckin());
		params.put("type", lodge.getType());
		params.put("numberPeople", lodge.getNumberPeople());
		params.put("idProvider", lodge.getIdProvider());
		
		return reserveService.reserve(params);
	}
	
	@PostMapping("/integrator/spectacle")	
	public ResponseEntity<JsonNode> searchEspectacle(@RequestBody String request) 
					throws InterruptedException, ExecutionException, JSONException, JsonMappingException, JsonProcessingException {
		
		JSONObject data = (JSONObject) new JSONObject(request).getJSONObject("data");
		
		JSONObject template = new JSONObject();
		
		JSONObject params = new JSONObject();
		params.put("operation", "search");
		params.put("type", data.get("type"));
		params.put("date", data.get("date"));
		params.put("dateEnd", data.get("dateEnd"));
		params.put("city", data.get("city"));
		params.put("country", data.get("country"));
		
		String providersString = kafkaIntegratorSender.sendMessageWithCallback("3", topic_1);
		
		JSONObject providersObj = new JSONObject(providersString);
		
		template.put("params", params);
		template.put("providers", providersObj.get("providers"));
		
		String transportResponse = kafkaIntegratorSender.sendMessageWithCallback(template.toString(), "integrator-spectacle");
		
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(transportResponse);
        return ResponseEntity.ok(json);
		
	}	
	
	
	@PostMapping("/integrator/transport")	
	public Boolean reserveSpectacle(@RequestBody ReserveSpectacle spectacle) throws JSONException{
		
		JSONObject params = new JSONObject();	
		params.put("description", spectacle.getDescription());
		params.put("date", spectacle.getDate());
		params.put("city", spectacle.getCity());
		params.put("country", spectacle.getCountry());
		params.put("idProvider", spectacle.getIdProvider());
		
		return reserveService.reserve(params);
	}
	
	@GetMapping("/integrator/status")
	public String status() {
		return "status ok";
	}	
}
