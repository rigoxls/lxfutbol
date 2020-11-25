package com.lxfutbol.transport.service;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import com.lxfutbol.transport.repository.TransportEntity;

@Service
public class TransportService {

	private final String TYPE_SOAP = "xml";

	@Autowired
	private TransformSoapProxyService proxyServiceSaop;
	
	@Autowired
	private TransformRestProxyService proxyServiceRest;

	@Autowired
	private TransportUtil transportUtil;

	private final Logger LOG = LoggerFactory.getLogger(TransportService.class);

	private JSONArray result;

	@KafkaListener(topics = "integrator-transport", groupId = "integrator-transport-group")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();
		// return "================================= RESPUESTA DESDE PROVIDER
		// ================================= ";
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
				if (resultTransport == null) {
					invocationTransform(jsonObject.get("id").toString(), jsonObject.get("name").toString(),  params, jsonObject.get("agreement").toString(),
							jsonObject.get("dataType").toString());
				} else {
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
		} catch (Exception exs) {
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

			int hashCode = (idProvider + departureCity + arrivalCity + departureDate).hashCode();
			resultTranspor = transportUtil.consultTransportHash(hashCode);

		} catch (Exception ex) {
			LOG.info("Error validando HashCode de la consulta", ex);
		}
		return resultTranspor;
	}

	private void invocationTransform(String idProvider, String name, JSONObject params, String agreement, String typeService) {
		LOG.info("Entra a invocationServicesSoa: ");
		try {
			
			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService =proxyServiceSaop.transfor(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService  = proxyServiceRest.transfor(Integer.valueOf(idProvider), param.toString());
			}		

			JSONArray temp = transportUtil.processReplyMessage(responseService);
			
			JSONObject resultTransport = new JSONObject();
			resultTransport.put("agreement", Integer.valueOf(agreement));
			resultTransport.put("idProvider", Integer.valueOf(idProvider));
			resultTransport.put("name", name);
			
			resultTransport.put("providerTransport", temp);
			result.put(resultTransport);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}


}
