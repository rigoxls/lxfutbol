package com.lxfutbol.provider.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.repository.ProviderRepository;

@Service
public class ProviderService {
	
	@Autowired
	private ProviderRepository providerRepository;
	
	protected ProviderService() {}
	
	public Optional<ProviderEntity> getProviderById(long providerId) {
		Optional<ProviderEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
