package com.lxfutbol.transformJson.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.transformJson.repository.TransformJsonEntity;
import com.lxfutbol.transformJson.repository.TransformJsonRepository;

@Service
public class TransformJsonService {
	
	@Autowired
	private TransformJsonRepository providerRepository;
	
	protected TransformJsonService() {}
	
	public Optional<TransformJsonEntity> getProviderById(long providerId) {
		Optional<TransformJsonEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
