package com.lxfutbol.integrator.service;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReserveService {

	@Autowired
	private RedisService redisService;	
	
	public Boolean reserve(JSONObject params) throws JSONException {

		JSONObject template = redisService.findBookTemplateById(String.valueOf(params.get("idProvider")));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String uri = (String) template.get("endpoint");

		HttpEntity<String> request = new HttpEntity<String>(params.toString(), headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

		String response = result.getBody();

		return Boolean.valueOf(response);

	}
	
}
