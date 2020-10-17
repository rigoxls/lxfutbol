package com.lxfutbol.integrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lxfutbol.integrator.repository.IntegratorEntity;
import com.lxfutbol.integrator.repository.IntegratorRepository;
import com.lxfutbol.integrator.kafka.*;

@Service
public class IntegratorService {
	
	@Autowired
	private IntegratorRepository providerRepository;
	
	@Autowired
	private KafkaSenderExample kafkaSenderExample;
	
	@Value("${com.lxfutbol.integrator.kafka.topic-1}")
	private String topic_1;
	
	protected IntegratorService() {		
	}
	
	public Optional<IntegratorEntity> getProviderById(long providerId) {
		kafkaSenderExample.sendMessage("XXXX", topic_1);
		Optional<IntegratorEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
