package com.lxfutbol.integrator.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.integrator.dto.Providerdto;
import com.lxfutbol.integrator.kafka.KafkaIntegratorSender;
import com.lxfutbol.integrator.service.IntegratorService;
import com.lxfutbol.integrator.service.ProviderProxyService;

//import com.lxfutbol.integratorDTO;


@RestController
public class IntegratorController {

	@Autowired
	private IntegratorService integratorService;
	
	@Autowired
	private KafkaIntegratorSender kafkaIntegratorSender;	
	
	@Autowired
	private ProviderProxyService providerProxyService;
	
	@Value("${com.lxfutbol.integrator.kafka.topic-1}")
	private String topic_1;	

	@GetMapping("/provider/get/{providerId}")
	public Providerdto getProviderById(@PathVariable long providerId) {
		Providerdto provider = providerProxyService.getProviderById(providerId);
		return provider;
	}
	
	@GetMapping("/provider/send/message")
	public void sendMessage() {
		kafkaIntegratorSender.sendMessageWithCallback("{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}", topic_1);		
	}
}
