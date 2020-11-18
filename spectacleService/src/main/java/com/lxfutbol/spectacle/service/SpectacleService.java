package com.lxfutbol.spectacle.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.lxfutbol.spectacle.repository.SpectacleEntity;
import com.lxfutbol.spectacle.repository.SpectacleRepository;

@Service
public class SpectacleService {
	
	private final Logger LOG = LoggerFactory.getLogger(SpectacleService.class);
	private JSONArray result;
	private final String TYPE_SOAP = "xml";

	@KafkaListener(topics = "integrator-spectacle", groupId = "integrator-spectacle-group")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();
	}

	
	@Autowired
	private TransformRestProxyService proxyServiceRest;
	
	@Autowired
	private TransformSoapProxyService proxyServiceSoap;
	
	@Autowired
	private SpectacleRepository spectacleRepository;

	private void processMessage(String message) {

		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				invocationTransform(jsonObject.get("id").toString(), params, jsonObject.get("agreement").toString(),
						jsonObject.get("dataType").toString());
			}
		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());

	}
	
	private void invocationTransform(String idProvider, JSONObject params, String agreement, String typeService) {
		LOG.info("Entra a invocationServices: ");
		try {

			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService =proxyServiceSoap.transformSpectacle(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService  = proxyServiceRest.transformSpectacle(Integer.valueOf(idProvider), param.toString());
			}	

			JSONObject temp = processReplyMessage(responseService);
			temp.put("agreement", Integer.valueOf(agreement));

			JSONObject resultEspectacle = new JSONObject();
			resultEspectacle.put("providerEspectacle", temp);
			result.put(resultEspectacle);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}

	public JSONObject processReplyMessage(String response) {

		LOG.info("Entra a processReplyMessage: ");

		JSONObject result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONObject params = jsonObjectMessage.getJSONObject("spectacle");

			this.saveSpectacle(params);
			result = params;

		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	private SpectacleEntity saveSpectacle(JSONObject params) {

		LOG.info("Entra a save Espectacle: ");
		SpectacleEntity espectacle = new SpectacleEntity();
		try {
			String idProvider = (String) params.get("idProvider");
			String date = (String) params.get("date");
			BigDecimal price = new BigDecimal((Integer) params.get("price"));
			String type = (String) params.get("type");
			String city = (String) params.get("city");
			String country = (String) params.get("country");
			String description = (String) params.get("description");

			espectacle.setIdProvider(Long.valueOf(idProvider));;
			espectacle.setDate(Date.valueOf(date));
			espectacle.setPrice(price);
			espectacle.setType(type);
			espectacle.setCity(city);
			espectacle.setCountry(country);
			espectacle.setDescription(description);
			

			spectacleRepository.save(espectacle);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de Espectacle", ex);
		}
		return espectacle;

	}

}
