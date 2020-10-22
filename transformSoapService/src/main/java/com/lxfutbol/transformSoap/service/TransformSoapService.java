package com.lxfutbol.transformSoap.service;

import java.util.Optional;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapRepository;

@Service
public class TransformSoapService {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformSoapService.class);
	
	@Autowired
	private TransformSoapRepository providerRepository;
	
	 @Autowired
	 private FlightClient flightClient;
	
	protected TransformSoapService() {}
	
	@KafkaListener(topics = "transform-soap")
	public void listener(String message) throws JSONException {
		JSONObject jsonObjectMessage = new JSONObject(message); //String entrada
		JSONArray providers = jsonObjectMessage.optJSONArray("providers"); //Json contenido
		JSONObject params = jsonObjectMessage.getJSONObject("params");//Json contenido
		getProviderById(1);
		getContractById();
	}

	
	public Optional<TransformSoapEntity> getProviderById(long providerId) {
		Optional<TransformSoapEntity> provider = providerRepository.findById(providerId);
		return provider;
	}

	public void getContractById() {
		flightClient.bookFlight();
	}
}
