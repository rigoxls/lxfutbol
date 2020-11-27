package com.lxfutbol.transport.service;

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
import com.lxfutbol.transport.repository.TransportEntity;
import com.lxfutbol.transport.repository.TransportRepository;

@Service
public class TransportService {

	private final String TYPE_SOAP = "xml";
	private final Integer TIME = 10;
	@Autowired
	private TransformSoapProxyService proxyServiceSaop;
	@Autowired
	private TransformRestProxyService proxyServiceRest;
	@Autowired
	private TransportRepository transportRepository;
	@PersistenceContext
	private EntityManager entityManager;

	private final Logger LOG = LoggerFactory.getLogger(TransportService.class);

	private JSONArray result;

	@KafkaListener(topics = "integrator-transport", groupId = "integrator-transport-group")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();

	}

	/**
	 * Procesar mensajes leidos desde kafka
	 * @param message
	 */
	public void processMessage(String message) {
		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				Boolean resultTransport = consultTransport(params, jsonObject.get("id").toString());
				if (!resultTransport) {
					invocationTransform(jsonObject.get("id").toString(), jsonObject.get("name").toString(), params,
							jsonObject.get("agreement").toString(), jsonObject.get("dataType").toString());
				}
			}
		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());
	}

	/**
	 * Invocación de microservicios transformadores para comunicarse con los proveedores
	 * @param idProvider
	 * @param name
	 * @param params
	 * @param agreement
	 * @param typeService
	 */
	private void invocationTransform(String idProvider, String name, JSONObject params, String agreement,
			String typeService) {
		LOG.info("Entra a invocationServices: ");
		try {

			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService = proxyServiceSaop.transfor(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService = proxyServiceRest.transfor(Integer.valueOf(idProvider), param.toString());
			}

			JSONArray temp = processReplyMessage(responseService, idProvider);

			JSONObject resultTransport = new JSONObject();
			resultTransport.put("agreement", Integer.valueOf(agreement));
			resultTransport.put("idProvider", Integer.valueOf(idProvider));
			resultTransport.put("name", name);

			resultTransport.put("providerTransport", temp);
			result.put(resultTransport);

		} catch (Exception ex) {
			LOG.info("Error Invocando Los transformadores: " + ex.getMessage());
		}
	}

	/**
	 * Consulta de transportes registrados en base de datos
	 * @param params
	 * @param idProvider
	 * @return
	 */
	public Boolean consultTransport(JSONObject params, String idProvider) {
		TransportEntity resultTransport = null;
		try {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateFormat = format.format(date);

			String departureCity = (String) params.get("departureCity");
			String arrivalCity = (String) params.get("arrivalCity");
			String departureDate = (String) params.get("departureDate");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, -TIME);
			java.util.Date date2 = calendar.getTime();
			String dateFormat2 = format.format(date2);

			Query q = entityManager
					.createNativeQuery("SELECT id_provider, flight, class_flight, departure_city, arrival_city,"
							+ " departure_date, arrival_date, price, cabin, meals, type  "
							+ " FROM transport where departure_city = :departureCity and arrival_city ="
							+ " :arrivalCity and departure_date = :departureDate  and id_provider = :idProvider and  date_update_transport between :date and :date2");
			q.setParameter("departureCity", departureCity);
			q.setParameter("arrivalCity", arrivalCity);
			q.setParameter("departureDate", departureDate);
			q.setParameter("idProvider", idProvider);
			q.setParameter("date", dateFormat2);
			q.setParameter("date2", dateFormat);

			List<Object[]> transports = (List<Object[]>) q.getResultList();

			if (!transports.isEmpty()) {
				for (Object[] transport : transports) {
					resultTransport = new TransportEntity();
					resultTransport.setIdProvider(Long.valueOf(String.valueOf(transport[0])));
					resultTransport.setFlight(String.valueOf(transport[1]));
					resultTransport.setClassFlight(String.valueOf(transport[2]));
					resultTransport.setDepartureCity(String.valueOf(transport[2]));
					resultTransport.setArrivalCity(String.valueOf(transport[4]));
					resultTransport.setDepartureDate(Date.valueOf(String.valueOf(transport[5])));
					resultTransport.setArrivalDate(Date.valueOf(String.valueOf(transport[6])));
					resultTransport.setPrice(new BigDecimal(String.valueOf(transport[7])));
					resultTransport.setCabin(String.valueOf(transport[8]));
					resultTransport.setMeals(Integer.valueOf(String.valueOf(transport[9])));
					resultTransport.setType(Integer.valueOf(String.valueOf(transport[10])));
					senderResponse(resultTransport);
				}
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
	private void senderResponse(TransportEntity resultTransport) {
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
			result.put(transportResponse);
		} catch (Exception exs) {
			LOG.info("Error armando la respuesta de transacción en cache", exs);
		}
	}
	/**
	 * Procesar respuestas desde los microservicios transformadores. 
	 * @param response
	 * @return
	 */
	public JSONArray processReplyMessage(String response, String idProvider) {

		LOG.info("Entra a processReplyMessage: ");
		JSONArray result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONArray transportArray = jsonObjectMessage.getJSONArray("transport");

			for (int i = 0; i < transportArray.length(); i++) {
				JSONObject params = transportArray.getJSONObject(i);
				this.saveTransport(params, idProvider);
			}

			result = transportArray;
		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	/**
	 * guardar transporte en base de datos
	 * @param params
	 * @return
	 */
	private TransportEntity saveTransport(JSONObject params, String idProvider) {

		LOG.info("Entra a saveTransport: ");
		TransportEntity transport = new TransportEntity();
		try {
			String flight = Integer.valueOf(String.valueOf(params.get("flight"))).toString();
			String classFlight = (String) params.get("class");
			String departureCity = (String) params.get("departureCity");
			String arrivalCity = (String) params.get("arrivalCity");
			String departureDate = (String) params.get("departureDate");
			String arrivalDate = (String) params.get("arrivalDate");
			BigDecimal price = new BigDecimal( String.valueOf(params.get("price")));
			String cabin = (String) params.get("cabin");
			Integer meals = Integer.valueOf(String.valueOf(params.get("meals")));
			Integer type = Integer.valueOf(String.valueOf(params.get("type")));

			transport.setIdProvider(Long.valueOf(idProvider));
			transport.setFlight(flight);
			transport.setClassFlight(classFlight);
			transport.setDepartureCity(departureCity);
			transport.setArrivalCity(arrivalCity);
			transport.setDepartureDate(Date.valueOf(departureDate));
			transport.setArrivalDate(Date.valueOf(arrivalDate));
			transport.setPrice(price);
			transport.setDateUpdateTransport(Calendar.getInstance());
			transport.setCabin(cabin);
			transport.setMeals(meals);
			transport.setType(type);
			transportRepository.save(transport);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de transporte", ex);
		}
		return transport;

	}
}
