package com.lxfutbol.lodge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.lodge.repository.LodgeEntity;
import com.lxfutbol.lodge.repository.LodgeRepository;

@Service
public class LodgeService {
	
	@Autowired
	private LodgeRepository providerRepository;
	
	protected LodgeService() {}
	
	public Optional<LodgeEntity> getProviderById(long providerId) {
		Optional<LodgeEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
