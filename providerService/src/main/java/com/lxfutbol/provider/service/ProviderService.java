package com.lxfutbol.provider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.lxfutbol.provider.dto.ProviderDTO;
import com.lxfutbol.provider.exception.ProviderNotFoundException;
import com.lxfutbol.provider.repository.OperationEntity;
import com.lxfutbol.provider.repository.ProviderEntity;
import com.lxfutbol.provider.repository.ProviderRepository;

@Service
public class ProviderService {
	
	private final Logger LOG = LoggerFactory.getLogger(ProviderService.class);
	
	@Autowired
	private ProviderRepository providerRepository;
	
	protected ProviderService() {}	
	
	public ProviderEntity createProvider(ProviderDTO newProvider) {
		ProviderEntity provider = new ProviderEntity(newProvider);
		OperationEntity operation = new OperationEntity(
				newProvider.getSearch(), 
				newProvider.getBook(), 
				newProvider.getCancelBook()
		);
		
		provider.setOperationEntity(operation);	
		
		providerRepository.save(provider);
		return provider;
	}
	
	public ProviderEntity updateProvider(ProviderDTO providerToUpdate) throws ProviderNotFoundException {
		Long providerId = providerToUpdate.getId();
		try {
			ProviderEntity provider = providerRepository.getOne(providerId);
			provider.setAll(providerToUpdate);
			
			provider.getOperationEntity().setSearch(providerToUpdate.getSearch());
			provider.getOperationEntity().setBook(providerToUpdate.getBook());
			provider.getOperationEntity().setCancelBook(providerToUpdate.getCancelBook());
			
			providerRepository.save(provider);
			return provider;
		} catch (Exception err) {
			throw new ProviderNotFoundException("Provider not found : " + providerId, err);
		}
	}
	
	public ProviderEntity setProviderStatus(long providerId, int status) throws ProviderNotFoundException {		
		try {
			ProviderEntity provider = providerRepository.getOne(providerId);
			provider.setStatus(status);
			providerRepository.save(provider);
			return provider;
		} catch (Exception err) {
			throw new ProviderNotFoundException("Provider not found : " + providerId, err);
		}
	}	
	
	public Void deleteProvider(long providerId) {
		providerRepository.deleteById(providerId);
		return null;
	}	
	
	public Optional<ProviderEntity> getProviderById(long providerId) {
		Optional<ProviderEntity> provider = providerRepository.findById(providerId);
		return provider;
	}
	
	public List<ProviderEntity> listActiveProviders() {
		List<ProviderEntity> providers = providerRepository.findAll();
		List<ProviderEntity> activeProviders = new ArrayList<ProviderEntity>();
		
		for (ProviderEntity provider : providers) {
			if(provider.getStatus() == 1) {
				activeProviders.add(provider);
			}
		}
		
		return activeProviders;
	}
	
	/*******************************
	/* Kafka matters ***************
	/
	 * @throws JSONException ******************************/	
	@KafkaListener(topics = "integrator-provider", groupId = "integrator_group_1")
	@SendTo
	String listenAndReply(String type) throws JSONException {
		LOG.info("ListenAndReply [{}]", type);
		List<ProviderEntity> listProviders = this.listActiveProviders();
		
		ArrayList<Object> providersObjs = new ArrayList<Object>();
		
		for (ProviderEntity provider : listProviders) {			
			String pType = Integer.valueOf(provider.getType()).toString();

			if(type.equals(pType)) {
				LOG.info("***************************");
				LOG.info("***************************");
				JSONObject providerObj = new JSONObject();
				long providerId = provider.getId();
				providerObj.put("id", providerId);
				providerObj.put("dataType", provider.getDataType());
				providerObj.put("agreement", provider.getAgreement());				
				providersObjs.add(providerObj);	
			}
			
		}
		
		JSONObject template = new JSONObject();
		template.put("providers", providersObjs);
				
		return template.toString();
	}	

}
