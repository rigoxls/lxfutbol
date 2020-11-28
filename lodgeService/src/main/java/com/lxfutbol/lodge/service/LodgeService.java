package com.lxfutbol.lodge.service;

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

import com.lxfutbol.lodge.repository.LodgeEntity;
import com.lxfutbol.lodge.repository.LodgeRepository;

@Service
public class LodgeService {

	private final Logger LOG = LoggerFactory.getLogger(LodgeService.class);
	private JSONArray result;
	private final Integer TIME = 10;
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
	private TransformRestProxyService proxyServiceRest;
	
	@Autowired
	private TransformSoapProxyService proxyServiceSoap;

	/**
	 * Procesar mensajes leidos desde Kafka
	 * @param message
	 */
	private void processMessage(String message) {

		result = new JSONArray();
		try {
			JSONObject jsonObjectMessage = new JSONObject(message);
			JSONArray providers = jsonObjectMessage.optJSONArray("providers");
			JSONObject params = jsonObjectMessage.getJSONObject("params");

			for (int i = 0; i < providers.length(); i++) {
				JSONObject jsonObject = providers.getJSONObject(i);
				Boolean resultConsult = consultLogde(params, jsonObject.get("id").toString(),
						jsonObject.get("name").toString(), jsonObject.get("agreement").toString());
				if (!resultConsult) {
					invocationTransform(jsonObject.get("id").toString(), params, jsonObject.get("agreement").toString(),
							jsonObject.get("dataType").toString(), jsonObject.get("name").toString());
				}
			}
		} catch (Exception ex) {
			LOG.info("Error leyendo datos del mensaje: " + ex.getMessage());
		}

		LOG.info(result.toString());

	}

	/**
	 * Creación mensaje de respuesta
	 * @param resultLodge
	 * @return
	 */
	private JSONObject senderResponse(LodgeEntity resultLodge) {
		JSONObject lodge = new JSONObject();

		JSONObject hotel = new JSONObject();
		try {
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
		} catch (Exception exs) {
			LOG.info("Error armando la respuesta de transacción en cache", exs);
		}
		return lodge;
	}

	/**
	 * Invocación de transformadores
	 * @param idProvider
	 * @param params
	 * @param agreement
	 * @param typeService
	 * @param name
	 */
	private void invocationTransform(String idProvider, JSONObject params, String agreement, String typeService, String name) {
		LOG.info("Entra a invocationServicesSoa: ");
		try {

			JSONObject param = new JSONObject().put("params", params);
			String responseService = "";
			if (typeService.equals(TYPE_SOAP)) {
				responseService =proxyServiceSoap.transforLodge(Integer.valueOf(idProvider), param.toString());
			} else {
				responseService  = proxyServiceRest.transforLodge(Integer.valueOf(idProvider), param.toString());
			}	

			JSONArray temp = processReplyMessage(responseService, idProvider);			
			JSONObject resultLodge = new JSONObject();
			
			resultLodge.put("agreement", Integer.valueOf(agreement));
			resultLodge.put("idProvider", Integer.valueOf(idProvider));			
			resultLodge.put("name", name);
			resultLodge.put("providerLodge", temp);
			result.put(resultLodge);

		} catch (Exception ex) {
			LOG.info("Error enviando mensaje : " + ex.getMessage());
		}
	}
	
	/**
	 * Leer mensaje de respuesta de los transformadores
	 * @param response
	 * @param idProvider
	 * @return
	 */
	public JSONArray processReplyMessage(String response, String idProvider) {

		LOG.info("Entra a processReplyMessage: ");

		JSONArray result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONArray lodgeArray = jsonObjectMessage.getJSONArray("lodge");
			
			for (int i = 0; i < lodgeArray.length(); i++) {
				JSONObject params = lodgeArray.getJSONObject(i);
				this.saveLodge(params, idProvider);
			}
			
			result = lodgeArray;

		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	/**
	 * Guardar Logde en base de datos
	 * @param params
	 * @param idProvider
	 * @return
	 */
	private LodgeEntity saveLodge(JSONObject params, String idProvider) {

		LOG.info("Entra a saveTransport: ");
		LodgeEntity lodge = new LodgeEntity();
		try {
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
			lodge.setDateCreate(Calendar.getInstance());

			lodgeRepository.save(lodge);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de transporte", ex);
		}
		return lodge;

	}
	
	/**
	 * Consultar en base de datos Logde disponibles en el rango de tiempo 
	 * @param params
	 * @param idProvider
	 * @param name
	 * @param agreement
	 * @return
	 */
	public Boolean consultLogde(JSONObject params, String idProvider, String name, String agreement) {
		LodgeEntity resulLodge = null;
		JSONObject resultObe = new JSONObject();
		JSONArray objectTemp = new JSONArray();;
		
		try {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateFormat = format.format(date);

			String city = (String) params.get("city");
			String country = (String) params.get("country");
			String checkIn = (String) params.get("checkIn");
			String checkout = (String) params.get("checkout");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, -TIME);
			java.util.Date date2 = calendar.getTime();
			String dateFormat2 = format.format(date2);

			Query q = entityManager
					.createNativeQuery("SELECT address_hotel, name_hotel, number_room, price_room, type_room"
							+ " FROM lodge where city_hotel = :city and country_hotel ="
							+ " :country and check_in = :checkIn and checkout = :checkout and id_provider = :idProvider and  date_create between :date and :date2");
			q.setParameter("city", city);
			q.setParameter("country", country);
			q.setParameter("checkIn", checkIn);
			q.setParameter("checkout", checkout);
			q.setParameter("idProvider", idProvider);
			q.setParameter("date", dateFormat2);
			q.setParameter("date2", dateFormat);

			List<Object[]> lodges = (List<Object[]>) q.getResultList();

			if (!lodges.isEmpty()) {
				for (Object[] lodge : lodges) {
					resulLodge = new LodgeEntity();
					
					resulLodge.setAddressHotel(String.valueOf(lodge[0]));
					resulLodge.setCheckIn(Date.valueOf(checkIn));
					resulLodge.setCheckout(Date.valueOf(checkout));
					resulLodge.setCityHotel(city);
					resulLodge.setCountryHotel(country);
					resulLodge.setNameHotel(String.valueOf(lodge[1]));
					resulLodge.setNumberRoom(String.valueOf(lodge[2]));
					resulLodge.setPriceRoom(new BigDecimal(String.valueOf(lodge[3])));
					resulLodge.setTypeRoom(String.valueOf(lodge[4]));
					objectTemp.put(senderResponse(resulLodge));
				}
				resultObe.put("agreement", Integer.valueOf(agreement));
				resultObe.put("idProvider", Integer.valueOf(idProvider));
				resultObe.put("name", name);
				resultObe.put("providerLodge", objectTemp);
				result.put(resultObe);
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			LOG.info("Error consultando los registros de transporte", ex);
		}
		return false;
	}
}
