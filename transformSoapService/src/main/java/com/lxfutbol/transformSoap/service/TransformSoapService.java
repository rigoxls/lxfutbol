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
	
	@KafkaListener(topics = "transform-soap")
	public void listener(String message) throws JSONException {
		String jsonResponse = "{\"providers\":[{\"id\":\"1\",\"dataType\":\"json/xml\",\"agreement\":5}],\"params\":{\"operation\":\"search\",\"city\":\"/book/\",\"country\":\"\",\"checkIn\":\"\",\"checkout\":\"\",\"rooms\":2,\"type\":\"\"}}";
		JSONObject jsonObjectMessage = new JSONObject(jsonResponse); //String entrada
		JSONObject parameters = jsonObjectMessage.getJSONObject("params");//Json contenido
		JSONArray providers = (JSONArray) jsonObjectMessage.get("providers");
		JSONObject provider = (JSONObject) providers.get(0);
		String operation = parameters.get("operation").toString();
		long id =  provider.getLong("id");
		JSONObject template = (JSONObject) getTemplate(id,operation);
		String template1 = "<typ:bookFligthElement>\r\n" + 
				"         <typ:f>\r\n" + 
				"            <typ:cabin>8A</typ:cabin>\r\n" + 
				"            <typ:arrivingDate>2020-10-22T00:00:00.000-05:00</typ:arrivingDate>\r\n" + 
				"            <typ:price>500000</typ:price>\r\n" + 
				"            <typ:arrivingCity>Cartagena</typ:arrivingCity>\r\n" + 
				"            <typ:meals>2</typ:meals>\r\n" + 
				"            <typ:departinDate>2020-10-21T00:00:00.000-05:00</typ:departinDate>\r\n" + 
				"            <typ:departinCity>Bogotá</typ:departinCity>\r\n" + 
				"            <typ:number>2</typ:number>\r\n" + 
				"         </typ:f>\r\n" + 
				"         <typ:passengerName>Rodrigo Bastidas</typ:passengerName>\r\n" + 
				"      </typ:bookFligthElement>";
		requestBook(template1);
	}


	public Optional<TransformSoapEntity> getProviderById(long providerId) {
		Optional<TransformSoapEntity> provider = providerRepository.findById(providerId);
		return provider;
	}
	
	public JSONObject getTemplate(long providerId, String operation) throws JSONException{
		return redisService.findById(Long.toString(providerId), operation);
	}

	public void requestBook(String template1) {
		transportClient.bookFlight(template1);
	}
	
	public void requestSearch() {
		transportClient.searchFlight(null);
	}
	

	public void requestBookH(String template1) {
		lodgingClient.bookRoom(template1);
	}
	

}
