package com.lxfutbol.transformRest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.transformRest.repository.TransformRestEntity;
import com.lxfutbol.transformRest.repository.TransformRestRepository;

@Service
public class TransformRestService {
	
	@Autowired
	private TransformRestRepository providerRepository;
	
	protected TransformRestService() {}
	
	public Optional<TransformRestEntity> getProviderById(long providerId) {
		Optional<TransformRestEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
