package com.lxfutbol.spectacle.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	@PersistenceContext
	private EntityManager entityManager;
	private final Integer TIME = 10;
	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private TransformRestProxyService proxyRest;
	@Autowired
	private TransformSoapProxyService proxySoa;
	

	@KafkaListener(topics = "integrator-spectacle", groupId = "integrator-spectacle-group")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();
	}

	public void processMessage(String message) {

		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				Boolean resultTransport = consultSpectacle(params, jsonObject.get("id").toString(), jsonObject.get("name").toString()
						,jsonObject.get("agreement").toString() );
				if (!resultTransport) {
					invocationTransform(jsonObject.get("id").toString(), params,
							jsonObject.get("agreement").toString(), jsonObject.get("dataType").toString(), jsonObject.get("name").toString());
				}
			}
		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());

	}
	
	private void invocationTransform(String idProvider, JSONObject params, String agreement, String typeService, String name) {
		LOG.info("Entra a invocationServices: ");
		try {

			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService =proxySoa.transfor(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService  = proxyRest.transfor(Integer.valueOf(idProvider), param.toString());
			}	

			JSONArray temp = processReplyMessage(responseService, idProvider);
			
			JSONObject resultEspectacle = new JSONObject();
			resultEspectacle.put("agreement", Integer.valueOf(agreement));
			resultEspectacle.put("idProvider", Integer.valueOf(idProvider));	
			resultEspectacle.put("name", name);
			resultEspectacle.put("providerEspectacle", temp);
			result.put(resultEspectacle);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}

	public JSONArray processReplyMessage(String response, String idProvider) {

		LOG.info("Entra a processReplyMessage: ");

		JSONArray result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONArray spectacleArray = jsonObjectMessage.getJSONArray("spectacle");
			
			for (int i = 0; i < spectacleArray.length(); i++) {
				JSONObject params = spectacleArray.getJSONObject(i);
				this.saveSpectacle(params, idProvider);
			}
			
			result = spectacleArray;


		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	private SpectacleEntity saveSpectacle(JSONObject params, String idProvider) {

		LOG.info("Entra a save Espectacle: ");
		SpectacleEntity espectacle = new SpectacleEntity();
		try {
			String date = (String) params.get("date");
			BigDecimal price = new BigDecimal((Integer) params.get("price"));
			String type = (String) params.get("type");
			String city = (String) params.get("city");
			String country = (String) params.get("country");
			String description = (String) params.get("description");

			espectacle.setIdProvider(Long.valueOf(idProvider));
			espectacle.setDate(Date.valueOf(date));
			espectacle.setPrice(price);
			espectacle.setType(type);
			espectacle.setCity(city);
			espectacle.setCountry(country);
			espectacle.setDescription(description);
			espectacle.setDateCreate(Calendar.getInstance());

			spectacleRepository.save(espectacle);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de Espectacle", ex);
		}
		return espectacle;

	}
	
	/**
	 * Consulta de transportes registrados en base de datos
	 * @param params
	 * @param idProvider
	 * @return
	 */
	public Boolean consultSpectacle(JSONObject params, String idProvider, String name, String agreement) {
		SpectacleEntity resultSpectacle = null;
		JSONObject resultObe = new JSONObject();
		JSONArray objectTemp = new JSONArray();
		try {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateFormat = format.format(date);

			String city = (String) params.get("city");
			String country = (String) params.get("country");
			String dateSpectacle = (String) params.get("date");
			String type = (String) params.get("type");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, -TIME);
			java.util.Date date2 = calendar.getTime();
			String dateFormat2 = format.format(date2);

			Query q = entityManager
					.createNativeQuery("SELECT price, description from spectacles where " 
							+ " city = :city and country = :country and type = :type and date = :dateSpectacle "
							+ " and id_provider = :idProvider and date_create between :date and :date2");
			q.setParameter("city", city);
			q.setParameter("country", country);
			q.setParameter("type", type);
			q.setParameter("dateSpectacle", dateSpectacle);
			q.setParameter("idProvider", idProvider);
			q.setParameter("date", dateFormat2);
			q.setParameter("date2", dateFormat);

			List<Object[]> spectacles = (List<Object[]>) q.getResultList();

			if (!spectacles.isEmpty()) {
				for (Object[] spectacle : spectacles) {
					resultSpectacle = new SpectacleEntity();
					resultSpectacle.setIdProvider(Long.valueOf(idProvider));
					resultSpectacle.setDate(Date.valueOf(dateSpectacle));
					resultSpectacle.setType(type);
					resultSpectacle.setPrice((new BigDecimal(String.valueOf(spectacle[0]))));
					resultSpectacle.setDescription(String.valueOf(spectacle[1]));
					resultSpectacle.setCity(city);
					resultSpectacle.setCountry(country);
					objectTemp.put(senderResponse(resultSpectacle));
				}
				
				resultObe.put("agreement", Integer.valueOf(agreement));
				resultObe.put("idProvider", Integer.valueOf(idProvider));
				resultObe.put("name", name);
				resultObe.put("providerEspectacle", objectTemp);
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			LOG.info("Error consultando los registros de transporte", ex);
		}
		return false;
	}
	
	/**
	 * Creación de resulta hacia kafka
	 * @param resultTransport
	 */
	private JSONObject senderResponse(SpectacleEntity spectacle) {
		JSONObject spectacleJson = new JSONObject();
		try {
			spectacleJson.put("date", spectacle.getDate());
			spectacleJson.put("type", spectacle.getType());
			spectacleJson.put("price", spectacle.getPrice());
			spectacleJson.put("description", spectacle.getDescription());
			spectacleJson.put("city", spectacle.getCity());
			spectacleJson.put("country", spectacle.getCountry());	
			
		} catch (Exception exs) {
			LOG.info("Error armando la respuesta de transacción en cache", exs);
		}
		return spectacleJson;
	}

}
