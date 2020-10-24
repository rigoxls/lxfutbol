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
	private RedisService redisService;
	
	protected TransformSoapService() {}
	
	@KafkaListener(topics = "transform-soap")
	public void listener(String message) throws JSONException {
		String jsonResponse = "{\"providers\":[{\"id\":\"1\",\"dataType\":\"json/xml\",\"agreement\":5}],\"params\":{\"operation\":\"search\",\"city\":\"/book/\",\"country\":\"\",\"checkIn\":\"\",\"checkout\":\"\",\"rooms\":2,\"type\":\"\"}}";
		JSONObject jsonObjectMessage = new JSONObject(jsonResponse); //String entrada
		JSONObject parameters = jsonObjectMessage.getJSONObject("params");//Json contenido
		JSONArray providers = (JSONArray) jsonObjectMessage.get("long");
		JSONObject provider = (JSONObject) providers.get(0);
		String operation = parameters.get("operation").toString();
		long id =  (long) provider.get("id");
        getTemplate(id,operation);
		requestBook(parameters);
	}

	
	public Optional<TransformSoapEntity> getProviderById(long providerId) {
		Optional<TransformSoapEntity> provider = providerRepository.findById(providerId);
		return provider;
	}
	
	public TemplateDto getTemplate(long providerId, String operation) throws JSONException{
		JSONArray jsonArray = redisService.findById(Long.toString(providerId));
		TemplateDto template = new TemplateDto(null, null, null);
		return template;
		
	}

	public void requestBook(JSONObject parameters) {
		TemplateDto templateDto = null;
		transportClient.bookFlight(templateDto);
	}
	
	public void requestSearch() {
		transportClient.searchFlight(null);
	}
	
	public void cancelBook() {
		transportClient.cancelFlight(null);
	}
}
