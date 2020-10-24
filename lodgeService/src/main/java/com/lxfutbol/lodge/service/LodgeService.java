package com.lxfutbol.lodge.service;

import java.math.BigDecimal;
import java.sql.Date;

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

import com.lxfutbol.lodge.repository.LodgeEntity;
import com.lxfutbol.lodge.repository.LodgeRepository;

@Service
public class LodgeService {

	private final Logger LOG = LoggerFactory.getLogger(LodgeService.class);
	private JSONArray result;
	private final Integer TIME = 8;
	private final String TYPE_SOAP = "xml";

	@PersistenceContext
	private EntityManager entityManager;

	@KafkaListener(topics = "integrator-lodge", groupId = "integrator-lodge-group")
	@SendTo
	String listenAndReply(String message) {
		LOG.info("ListenAndReply [{}]", message);
		processMessage(message);
		return result.toString();
	}

	@Autowired
	private LodgeRepository lodgeRepository;
	
	@Autowired
	private TransformRestProxyService proxyServiceSoap;
	
	@Autowired
	private TransformSoapProxyService proxyServiceRest;

	private void processMessage(String message) {

		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				// LodgeEntity resultLodge = validateConsult(params,
				// jsonObject.get("id").toString());
				// if (resultLodge == null) {
				invocationTransform(jsonObject.get("id").toString(), params, jsonObject.get("agreement").toString(),
						jsonObject.get("dataType").toString());
				/*
				 * } else { result.put(senderResponse(resultLodge)); }
				 */
			}
		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());

	}

	private JSONObject senderResponse(LodgeEntity resultLodge) {
		JSONObject lodge = new JSONObject();

		JSONObject hotel = new JSONObject();

		JSONObject lodgetResponse = new JSONObject();
		try {
			lodge.put("idProvider", resultLodge.getIdProvider());
			lodge.put("number", resultLodge.getNumberRoom());
			lodge.put("price", resultLodge.getPriceRoom());
			lodge.put("type", resultLodge.getTypeRoom());
			lodge.put("checkIn", resultLodge.getCheckIn());
			lodge.put("checkout", resultLodge.getCheckout());

			hotel.put("name", resultLodge.getNameHotel());
			hotel.put("address", resultLodge.getAddressHotel());
			hotel.put("city", resultLodge.getCityHotel());
			hotel.put("country", resultLodge.getCountryHotel());

			lodge.put("hotel", hotel);

			lodgetResponse.put("providerLodge", lodge);
		} catch (Exception exs) {
			LOG.info("Error armando la respuesta de transacción en cache", exs);
		}
		return lodgetResponse;
	}

	/*
	 * private LodgeEntity validateConsult(JSONObject params, String idProvider) {
	 * LOG.info("Entra a validar HashCode"); LodgeEntity resultTranspor = null; try
	 * { String city = (String) params.get("city"); String country = (String)
	 * params.get("country"); String checkIn = (String) params.get("checkIn");
	 * String checkout = (String) params.get("checkout"); String type = (String)
	 * params.get("type");
	 * 
	 * int hashCode = (idProvider + country + type + city + checkIn +
	 * checkout).hashCode(); resultTranspor = consultlodgetHash(hashCode);
	 * 
	 * } catch (Exception ex) { LOG.info("Error validando HashCode de la consulta",
	 * ex); } return resultTranspor; }
	 */

	private void invocationTransform(String idProvider, JSONObject params, String agreement, String typeService) {
		LOG.info("Entra a invocationServicesSoa: ");
		try {

			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService =proxyServiceSoap.transforLodge(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService  = proxyServiceRest.transforLodge(Integer.valueOf(idProvider), param.toString());
			}	

			JSONObject temp = processReplyMessage(responseService);
			temp.put("agreement", Integer.valueOf(agreement));

			JSONObject resultLodge = new JSONObject();
			resultLodge.put("providerLodge", temp);
			result.put(resultLodge);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}

	/*
	 * public LodgeEntity consultlodgetHash(int hashCode) { LodgeEntity
	 * resultTransport = null; try { java.util.Date date = new java.util.Date();
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); String
	 * dateFormat = format.format( date );
	 * 
	 * 
	 * Calendar calendar = Calendar.getInstance(); calendar.setTime(date);
	 * calendar.add(Calendar.HOUR, TIME); java.util.Date date2 = calendar.getTime();
	 * String dateFormat2 = format.format( date2 );
	 * 
	 * Query q = entityManager
	 * .createNativeQuery("SELECT id_provider, flight, class_flight, departure_city, arrival_city,"
	 * + " departure_date, arrival_date, price" +
	 * " FROM transport where hash_code = :hash or date_update_transport between :date and :date2"
	 * ); q.setParameter("hash", hashCode); q.setParameter("date", dateFormat);
	 * q.setParameter("date2", dateFormat2);
	 * 
	 * Object[] transport = (Object[]) q.getSingleResult(); resultTransport = new
	 * TransportEntity();
	 * 
	 * resultTransport.setIdProvider(Long.valueOf(String.valueOf(transport[0])));
	 * resultTransport.setFlight(String.valueOf(transport[1]));
	 * resultTransport.setClassFlight(String.valueOf(transport[2]));
	 * resultTransport.setDepartureCity(String.valueOf(transport[2]));
	 * resultTransport.setArrivalCity(String.valueOf(transport[4]));
	 * resultTransport.setDepartureDate(Date.valueOf(String.valueOf(transport[5])));
	 * resultTransport.setArrivalDate(Date.valueOf(String.valueOf(transport[6])));
	 * resultTransport.setPrice(new BigDecimal(String.valueOf(transport[7])));
	 * 
	 * 
	 * LOG.info("Transporte existe"); } catch (NoResultException ex) {
	 * LOG.info("No existen registros para el transporte consultado"); } catch
	 * (Exception ex) { LOG.info("Error consultando los registros de transporte",
	 * ex); }
	 * 
	 * return resultTransport;
	 * 
	 * }
	 */

	public JSONObject processReplyMessage(String response) {

		LOG.info("Entra a processReplyMessage: ");

		JSONObject result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONObject params = jsonObjectMessage.getJSONObject("lodge");

			this.saveLodge(params);
			result = params;

		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	private LodgeEntity saveLodge(JSONObject params) {

		LOG.info("Entra a saveTransport: ");
		LodgeEntity lodge = new LodgeEntity();
		try {
			String idProvider = (String) params.get("idProvider");
			String number = (String) params.get("number");
			BigDecimal price = new BigDecimal((Integer) params.get("price"));
			String type = (String) params.get("type");
			String checkIn = (String) params.get("checkIn");
			String checkout = (String) params.get("checkout");

			JSONObject hotel = params.getJSONObject("hotel");
			String name = (String) hotel.get("name");
			String address = (String) hotel.get("address");
			String city = (String) hotel.get("city");
			String country = (String) hotel.get("country");

			lodge.setIdProvider(Long.valueOf(idProvider));
			lodge.setNumberRoom(number);
			lodge.setPriceRoom(price);
			lodge.setCheckIn(Date.valueOf(checkIn));
			lodge.setCheckout(Date.valueOf(checkout));
			lodge.setNameHotel(name);
			lodge.setAddressHotel(address);
			lodge.setCityHotel(city);
			lodge.setCountryHotel(country);

			lodgeRepository.save(lodge);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de transporte", ex);
		}
		return lodge;

	}
}
