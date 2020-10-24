package com.lxfutbol.transformRest.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.transformRest.service.RedisService;
import com.lxfutbol.transformRest.service.TransformRestService;

@RestController
public class TransformRestController {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformRestController.class);

	@Autowired
	private TransformRestService transformRestService;
		
	@PostMapping("/transform/{idProvider}")
	public String transfor(@PathVariable int idProvider, @RequestBody String params) throws JSONException {		
	
		transformRestService.getProviderData(idProvider, params);
		
		return "lo que retorna a nat";
	}	
}
