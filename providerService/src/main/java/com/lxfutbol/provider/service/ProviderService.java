package com.lxfutbol.provider.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.repository.ProviderRepository;

@Service
public class ProviderService {
	
	private final Logger LOG = LoggerFactory.getLogger(ProviderService.class);
	
	@Autowired
	private ProviderRepository providerRepository;
	
	protected ProviderService() {}
	
	@KafkaListener(topics = "integrator-provider")
	public void listener(String message) {
		LOG.info("Listener [{}]", message);
	}
	
	@KafkaListener(topics = "integrator-provider")
	@SendTo("integrator-provider")
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		return "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	}
	
	
	public Optional<ProviderEntity> getProviderById(long providerId) {
		Optional<ProviderEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
