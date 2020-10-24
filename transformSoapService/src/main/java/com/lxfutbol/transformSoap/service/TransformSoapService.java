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
		String template1 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://services.aa.com/types/\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <typ:searchFlightElement>\r\n" + 
				"         <typ:departinCity>Bogotá</typ:departinCity>\r\n" + 
				"         <typ:arrivingCity>Cartagena</typ:arrivingCity>\r\n" + 
				"         <typ:departinDate>2020-10-19T00:00:00.000-05:00</typ:departinDate>\r\n" + 
				"         <typ:cabin>8E</typ:cabin>\r\n" + 
				"         <typ:PromotionCode xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\r\n" + 
				"      </typ:searchFlightElement>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		
		checkProvider(type,template1,operation);

	}


	private void checkProvider(String type, String templatetype, String operation) {
		
		if (type == "Transport") {
			
			switch (operation) {
			case "search":
				requestSearch(templatetype);
				break;

			case "book":
				requestBook(templatetype);
				break;
			}
			
		} else if (type == "Lodging") {
			
			switch (operation) {
			case "search":
				requestRoom(templatetype);
				break;

			case "book":
				requestService(templatetype);
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

	public void requestBook(String template1) {
		transportClient.bookFlight();
	}
	
	public void requestSearch(String template1) {
		Transport trasport = new Transport();
		transportClient.searchFlight(trasport);
	}
	
	public void requestRoom(String template1) {
		lodgingClient.bookRoom(template1);
	}
	
	public void requestService(String template1) {
		lodgingClient.roomService(template1);
	}
	

}
