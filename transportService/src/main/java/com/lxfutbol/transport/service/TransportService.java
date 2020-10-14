package com.lxfutbol.transport.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.transport.repository.TransportEntity;
import com.lxfutbol.transport.repository.TransportRepository;

@Service
public class TransportService {
	
	@Autowired
	private TransportRepository providerRepository;
	
	protected TransportService() {}
	
	public Optional<TransportEntity> getProviderById(long providerId) {
		Optional<TransportEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
