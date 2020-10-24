package com.lxfutbol.transformRest.service;

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
	
	public String getProviderData(int idProvider, String stringParams) throws JSONException {	
		
		JSONObject providerTemplate = redisService.findById(Long.toString(idProvider));	
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
	    final String uri = "http://localhost:3000/json-provider-1";
	    
	    HttpEntity<String> request = new HttpEntity<String>(stringParams, headers);
	    

	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
	    
		JSONObject params = new JSONObject(result.getBody());
		System.out.println("**************************");
		System.out.println(params);

	    LOG.info("*******************************+");
	    System.out.println(result);		
		
		return stringParams;		
	}
	
	public void mappingAttributes() {
		
	}

}
