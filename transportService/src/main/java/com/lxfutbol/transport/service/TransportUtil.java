package com.lxfutbol.transport.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxfutbol.transport.repository.TransportEntity;
import com.lxfutbol.transport.repository.TransportRepository;

@Service
public class TransportUtil {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TransportRepository transportRepository;

	private final Logger LOG = LoggerFactory.getLogger(TransportService.class);
	
	private final Integer TIME = 8;

	public JSONObject processReplyMessage(String response) {
		
		LOG.info("Entra a processReplyMessage: ");
		
		
		JSONObject result = null;
		try {
			JSONObject jsonObjectMessage = new JSONObject(response);
			JSONObject params = jsonObjectMessage.getJSONObject("transport");

			this.saveTransport(params);
			result = params;

		} catch (Exception ex) {
			LOG.info("Error leyendo mensaje de respuesta : " + ex.getMessage());
		}
		return result;
	}

	
	private TransportEntity saveTransport(JSONObject params) {
		
		LOG.info("Entra a saveTransport: ");
		TransportEntity transport = new TransportEntity();
		try {
			String idProvider = (String) params.get("idProvider");
			String flight = (String) params.get("flight");
			String classFlight = (String) params.get("class");
			String departureCity = (String) params.get("departureCity");
			String arrivalCity = (String) params.get("arrivalCity");
			String departureDate = (String) params.get("departureDate");
			String arrivalDate = (String) params.get("arrivalDate");
			BigDecimal price = new BigDecimal( (Integer) params.get("price"));

			transport.setIdProvider(Long.valueOf(idProvider));
			transport.setFlight(flight);
			transport.setClassFlight(classFlight);
			transport.setDepartureCity(departureCity);
			transport.setArrivalCity(arrivalCity);
			transport.setDepartureDate(Date.valueOf(departureDate));
			transport.setArrivalDate(Date.valueOf(arrivalDate));
			transport.setPrice(price);			
			transport.setHashCode(transport.hashCode());	
			transport.setDateUpdateTransport(new java.util.Date());
			transportRepository.save(transport);
		} catch (Exception ex) {
			LOG.info("Error Guardando la información de transporte", ex);
		}
		return transport;

	}
	
	public TransportEntity consultTransportHash(int hashCode) {
		TransportEntity resultTransport = null;
		try {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateFormat = format.format( date   );
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, TIME);
			java.util.Date date2 = calendar.getTime();			
			String dateFormat2 = format.format( date2   );
			
			Query q = entityManager
					.createNativeQuery("SELECT id_provider, flight, class_flight, departure_city, arrival_city,"
							+ " departure_date, arrival_date, price"
							+ " FROM transport where hash_code = :hash or date_update_transport between :date and :date2");
			q.setParameter("hash", hashCode);
			q.setParameter("date", dateFormat);
			q.setParameter("date2", dateFormat2);

			Object[] transport = (Object[]) q.getSingleResult();
			resultTransport = new TransportEntity();

			resultTransport.setIdProvider(Long.valueOf(String.valueOf(transport[0])));
			resultTransport.setFlight(String.valueOf(transport[1]));
			resultTransport.setClassFlight(String.valueOf(transport[2]));
			resultTransport.setDepartureCity(String.valueOf(transport[2]));
			resultTransport.setArrivalCity(String.valueOf(transport[4]));
			resultTransport.setDepartureDate(Date.valueOf(String.valueOf(transport[5])));
			resultTransport.setArrivalDate(Date.valueOf(String.valueOf(transport[6])));
			resultTransport.setPrice(new BigDecimal(String.valueOf(transport[7])));		
					
			
			LOG.info("Transporte existe");
		} catch (NoResultException ex) {
			LOG.info("No existen registros para el transporte consultado");
		} catch (Exception ex) {
			LOG.info("Error consultando los registros de transporte", ex);
		}

		return resultTransport;

	}

}
