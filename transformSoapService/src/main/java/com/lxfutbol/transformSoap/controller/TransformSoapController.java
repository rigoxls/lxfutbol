package com.lxfutbol.transformSoap.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;
import javax.ws.rs.POST;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.lxfutbol.transformSoap.dto.Providerdto;
import com.lxfutbol.transformSoap.repository.ProviderTemplateEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.service.ProviderProxyService;
import com.lxfutbol.transformSoap.service.RedisService;
import com.lxfutbol.transformSoap.service.TransformSoapService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformSoapController {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformSoapController.class);

	@Autowired
	private TransformSoapService transformSoapService;	

	@Autowired
	private RedisService redisService;
	
	@GetMapping("/provider/get/{providerId}")
	public Optional<TransformSoapEntity> getProviderById(@PathVariable long providerId) throws JSONException {
		Optional<TransformSoapEntity> provider = transformSoapService.getProviderById(providerId);
		return provider;
	}
	
	@PostMapping("/transform/{idProvider}")
	public String transform(@PathVariable int idProvider, @RequestBody String menssage) throws JSONException {	

		return transformSoapService.listener(idProvider,menssage);
	}
	
	
	
}
