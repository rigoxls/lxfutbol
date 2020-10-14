package com.lxfutbol.spectacle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.spectacle.repository.SpectacleEntity;
import com.lxfutbol.spectacle.repository.SpectacleRepository;

@Service
public class SpectacleService {
	
	@Autowired
	private SpectacleRepository providerRepository;
	
	protected SpectacleService() {}
	
	public Optional<SpectacleEntity> getProviderById(long providerId) {
		Optional<SpectacleEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
