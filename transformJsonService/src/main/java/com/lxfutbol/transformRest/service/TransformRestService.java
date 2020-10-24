package com.lxfutbol.transformRest.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lxfutbol.transformRest.controller.TransformRestController;
import com.lxfutbol.transformRest.repository.ProviderTemplateEntity;
import com.lxfutbol.transformRest.repository.TransformRestEntity;
import com.lxfutbol.transformRest.repository.TransformRestRepository;

@Service
public class TransformRestService {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformRestController.class);
	
	@Autowired
	private RedisService redisService;	
	
	@Autowired
	private TransformRestRepository providerRepository;
	
	protected TransformRestService() {}
	
	public Optional<TransformRestEntity> getProviderById(long providerId) {
		Optional<TransformRestEntity> provider = providerRepository.findById(providerId);
		return provider;
	}
	
	public JSONObject getProviderData(int idProvider, String stringParams) throws JSONException {
				
		//Getting params
		JSONObject params = new JSONObject(stringParams);
		JSONObject reqParams = (JSONObject) params.get("params");
		
		String operation = (String) reqParams.get("operation");
		
		JSONObject template = new JSONObject();
		
		switch(operation) {
		  case "search":
			  //Getting search template
			  template = redisService.findSearchTemplateById(Long.toString(idProvider));			  
		    break;
		    
		  case "book":
			  //Getting book template
			  template = redisService.findBookTemplateById(Long.toString(idProvider));		  
			  
		    break;
		}		
		
		return distpach(template, stringParams);				
	}
	
	public JSONObject distpach(JSONObject template, String stringParams) throws JSONException {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
	    String uri = "http://localhost:3000/json-provider-1"; //(String) searchValues.get("endpoint");
	    
	    HttpEntity<String> request = new HttpEntity<String>(stringParams, headers);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
	    JSONObject resParams = new JSONObject(result.getBody());
	    
	    JSONObject mapping = (JSONObject) template.get("mapping");
	    JSONObject mappingProp = (JSONObject) mapping.get("properties");
	    	    
	    JSONArray params = mappingProp.names();
	    
	    JSONObject mappedParams = new JSONObject();
	    
	    for (int i = 0; i < params.length(); i++) {	    	
	    	String key = params.getString(i);
	    	String value = mappingProp.getString(key);	    	
	    	mappedParams.put(key, resParams.get(value));
	    }
	    
	    return mappedParams;	    
	}
	
	public void mappingAttributes(JSONObject resParams) {
		
	}

}
