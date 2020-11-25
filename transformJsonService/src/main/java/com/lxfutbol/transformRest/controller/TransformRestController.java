package com.lxfutbol.transformRest.controller;

import java.util.ArrayList;

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
	
		ArrayList<JSONObject> result = transformRestService.getProviderData(idProvider, params);	    
	    JSONObject transportResult = new JSONObject();
	    transportResult.put("transport", result);		
		
		return transportResult.toString();
	}
	
	@PostMapping("/transformLodge/{idProvider}")
	public String transforLodge(@PathVariable int idProvider, @RequestBody String params) throws JSONException {		
	
		ArrayList<JSONObject> result = transformRestService.getProviderData(idProvider, params);
	    JSONObject lodgeResult = new JSONObject();
	    lodgeResult.put("lodge", result);	
		
		return lodgeResult.toString();
	}
	
	@PostMapping("/transformSpectacle/{idProvider}")
	public String transforEspectacle(@PathVariable int idProvider, @RequestBody String params) throws JSONException {		
	
		ArrayList<JSONObject> result = transformRestService.getProviderData(idProvider, params);
	    JSONObject espectacleResult = new JSONObject();
	    espectacleResult.put("spectacle", result);	
		
		return espectacleResult.toString();
	}	
}
