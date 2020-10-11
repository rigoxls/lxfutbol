package com.lxfutbol.integrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.integrator.repository.IntegratorEntity;
import com.lxfutbol.integrator.repository.IntegratorRepository;

@Service
public class IntegratorService {
	
	@Autowired
	private IntegratorRepository providerRepository;
	
	protected IntegratorService() {}
	
	public Optional<IntegratorEntity> getProviderById(long providerId) {
		Optional<IntegratorEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
