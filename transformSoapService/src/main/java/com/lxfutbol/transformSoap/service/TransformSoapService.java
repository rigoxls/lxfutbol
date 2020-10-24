package com.lxfutbol.transformSoap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.lxfutbol.transformSoap.dto.TemplateDto;
import com.lxfutbol.transformSoap.dto.Transport;
import com.lxfutbol.transformSoap.dto.TransportResult;
import com.lxfutbol.transformSoap.dto.properties;
import com.lxfutbol.transformSoap.repository.ProviderTemplateEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapRepository;


@Service
public class TransformSoapService {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformSoapService.class);
	
	@Autowired
	private TransformSoapRepository providerRepository;
	
	@Autowired
	private TransportClientService transportClient;
	
	@Autowired
	private LodgingClientService lodgingClient;
	
	@Autowired
	private RedisService redisService;
	
	protected TransformSoapService() {}
	

	public void listener(int idProvider, String message) throws JSONException {
		JSONObject jsonObjectMessage = new JSONObject(message); //String entrada
		JSONObject parameters = jsonObjectMessage.getJSONObject("params");//Json contenido
		String operation = parameters.get("operation").toString();
		//JSONObject template = (JSONObject) getTemplate(idProvider,operation);
		String type = "Transport";
		checkProvider(type,operation,parameters);
	}


	private void checkProvider(String type, String operation,JSONObject parameters) {
		
		if (type == "Transport") {
			
			switch (operation) {
			case "search":
				requestSearch(parameters);
				break;

			case "book":
				requestBook(parameters);
				break;
			}
			
		} else if (type == "Lodging") {
			
			switch (operation) {
			case "search":
				requestRoom(parameters);
				break;

			case "book":
				requestService(parameters);
				break;
			}
		} 
	
		
	}


	public Optional<TransformSoapEntity> getProviderById(long providerId) {
		Optional<TransformSoapEntity> provider = providerRepository.findById(providerId);
		return provider;
	}
	
	public JSONObject getTemplate(long providerId, String operation) throws JSONException{
		return redisService.findById(Long.toString(providerId), operation);
	}

	public void requestBook(JSONObject parameters) {
		Transport trasport = new Transport();
		transportClient.bookFlight(trasport);
	}
	
	public void requestSearch(JSONObject parameters) {
		Transport trasnport = new Transport();
		transportClient.searchFlight(trasnport); 
	}
	
	public void requestRoom(JSONObject parameters) {
		lodgingClient.bookRoom(null);
	}
	
	public void requestService(JSONObject parameters) {
		lodgingClient.roomService(null);
	}
	

}
