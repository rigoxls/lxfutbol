package com.lxfutbol.transformSoap.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapRepository;

@Service
public class TransformSoapService {
	
	@Autowired
	private TransformSoapRepository providerRepository;
	
	protected TransformSoapService() {}
	
	public Optional<TransformSoapEntity> getProviderById(long providerId) {
		Optional<TransformSoapEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

}
