package com.lxfutbol.transport.service;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.lxfutbol.transport.kafka.Sender.KafkaTransportSender;
import com.lxfutbol.transport.repository.TransportEntity;

@Service
public class TransportService {
	
	
	protected TransportService() {}
	
	@Value("${com.lxfutbol.transport.kafka.topic-4}")
	private String topic_4;
	
	@Value("${com.lxfutbol.transport.kafka.topic-5}")
	private String topic_5;
	
	private final String TYPE_SOAP = "xml";
	private final String TYPE_REST = "json";

	private KafkaTransportSender kafkaTransportSender;
	
	@Autowired
	private TransportUtil transportUtil;
		
	private final Logger LOG = LoggerFactory.getLogger(TransportService.class);
	
	private JSONArray result;
	
	@KafkaListener(topics = "integrator-transport", groupId = "integrator_group_1")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();
		//return "================================= RESPUESTA DESDE INTEGRATOR ================================= ";
	}		
	
	private void processMessage(String message) {

		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				TransportEntity resultTransport = validateConsult(params, jsonObject.get("id").toString());
				if(resultTransport == null) {
					if (jsonObject.get("dataType").toString().equals(TYPE_SOAP)) {
						invocationServicesSoa(jsonObject.get("id").toString(), params,
								jsonObject.get("agreement").toString());
					} else if (jsonObject.get("dataType").toString().equals(TYPE_REST)) {
	
						// invocationServicesRest(jsonObject.get("id").toString(), params);
					} else {
						LOG.info("Tipo de servicio invalido");
					}
				}else {
					result.put(senderResponse(resultTransport));
				}
					
			}

		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());

	}

	
	
	private JSONObject senderResponse(TransportEntity resultTransport) {
		JSONObject transport = new JSONObject();
		JSONObject transportResponse = new JSONObject();
		try {
			transport.put("idProvider", resultTransport.getIdProvider());
			transport.put("flight", resultTransport.getFlight());
			transport.put("class", resultTransport.getClassFlight());
			transport.put("departureCity", resultTransport.getDepartureCity());
			transport.put("arrivalCity", resultTransport.getArrivalCity());
			transport.put("departureDate", resultTransport.getDepartureDate());
			transport.put("arrivalDate", resultTransport.getArrivalDate());
			transport.put("price", resultTransport.getPrice());
			transportResponse.put("providerTransport", transport);
		}catch(Exception exs) {
			LOG.info("Error armando la respuesta de transacción en cache", exs);
		}
		return transportResponse;
	}

	private TransportEntity validateConsult(JSONObject params, String idProvider) {
		LOG.info("Entra a validar HashCode");
		TransportEntity resultTranspor = null;
		try {
			String departureCity = (String) params.get("departureCity");
			String arrivalCity = (String) params.get("arrivalCity");
			String departureDate = (String) params.get("departureDate");

			int hashCode = (idProvider + departureCity + arrivalCity + departureCity).hashCode();
			resultTranspor = transportUtil.consultTransportHash(hashCode);

		} catch (Exception ex) {
			LOG.info("Error validando HashCode de la consulta", ex);
		}
		return resultTranspor;
	}
	
	
	private void invocationServicesSoa(String idProvider, JSONObject params, String agreement) {
		LOG.info("Entra a invocationServicesSoa: ");
		try {
			
			 JSONObject json = new JSONObject(); 
			 json.put("idProvider", idProvider);
			 json.put("params", params);
			 
			 //String responseServiceSoa = kafkaTransportSender.sendMessageWithCallback(json.toString(), topic_4);
			 String responseServiceSoa = "{\n" + 
			 		"	\"transport\":{\n" + 
			 		"		\"idProvider\" = \"1\",\n" + 
			 		"		\"flight\" = \"avianca\",\n" + 
			 		"		\"class\" = \"2500\",\n" + 
			 		"	    \"departureCity\" = \"Bogota\",\n" + 
			 		"	    \"arrivalCity\" = \"Cartagena\",\n" + 
			 		"	    \"departureDate\" = \"2020-12-01\",\n" + 
			 		"	    \"arrivalDate\" = \"2020-12-15\",\n" + 
			 		"	    \"price\" = 2000412\n" + 
			 		"	}    \n" + 
			 		"}\n";
			 
			JSONObject temp = transportUtil.processReplyMessage(responseServiceSoa);
			temp.put("agreement", Integer.valueOf(agreement));
			
			JSONObject resultTransport = new JSONObject();
			resultTransport.put("providerTransport", temp);
			result.put(resultTransport);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}
	
	
	
	
	
	private void invocationServicesRest(String idProvider, JSONObject  params) {
		//kafkaTransportSender.sendMessageWithCallback("", topic_5);	
		
	}

}
