package com.lxfutbol.integrator.controller;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
	public String sendMessage() throws InterruptedException, ExecutionException {
		return kafkaIntegratorSender.sendMessageWithCallback("Rigoberto Giraldo", topic_1);		
	}
}
