package com.lxfutbol.transformSoap.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;
import javax.ws.rs.POST;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.lxfutbol.transformSoap.dto.Providerdto;
import com.lxfutbol.transformSoap.repository.ProviderTemplateEntity;
import com.lxfutbol.transformSoap.repository.TransformSoapEntity;
import com.lxfutbol.transformSoap.service.ProviderProxyService;
import com.lxfutbol.transformSoap.service.RedisService;
import com.lxfutbol.transformSoap.service.TransformSoapService;

//import com.lxfutbol.integratorDTO;


@RestController
public class TransformSoapController {
	
	private final Logger LOG = LoggerFactory.getLogger(TransformSoapController.class);

	@Autowired
	private TransformSoapService transformSoapService;	

	@Autowired
	private RedisService redisService;
	
	@GetMapping("/provider/get/{providerId}")
	public Optional<TransformSoapEntity> getProviderById(@PathVariable long providerId) throws JSONException {
		Optional<TransformSoapEntity> provider = transformSoapService.getProviderById(providerId);
		ProviderTemplateEntity em = new ProviderTemplateEntity();
		em.setId("1");
		em.setTemplate("{\"search\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}],\"mapping\":{\"properties\":{\"departureDate\":\"/Flight/departinDate\",\"arrivalDate\":\"/Flight/arrivingDate\",\"departureCity\":\"/Flight/departinCity\",\"flight\":\"/Flight/number\",\"class\":\"\",\"arrivalCity\":\"/Flight/arrivingCity\",\"price\":\"/Flight/price\",\"cabin\":\"/Flight/cabin\",\"meals\":\"/Flight/meals\"}}},\"book\":{\"endpoint\":\"http://192.168.1.100:8888/TuresBalonProviders-AA-context-root/AAFlightsServiceSoapHttpPort\",\"parameters\":[{\"name\":\"f\",\"type\":\"Flight\",\"isNull\":true,\"properties\":[{\"root\":\"/searchFlightElement\",\"name\":\"departinCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"arrivingCity\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"departinDate\",\"type\":\"dateTime\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"cabin\",\"type\":\"string\",\"isNull\":true},{\"root\":\"/searchFlightElement\",\"name\":\"PromotionCode\",\"type\":\"string\",\"isNull\":true}]}],\"mapping\":{\"properties\":{\"result\":\"/\"}}},\"cancelBook\":{\"endpoint\":\"\",\"parameters\":[],\"mapping\":{\"properties\":{}}}}");		
		redisService.save(em);
		return provider;
	}
	
	@PostMapping("/transform/{idProvider}")
	public String transfor(@PathVariable int idProvider, @RequestBody String menssage) {
		
		LOG.info("*******************************+");
		LOG.info(String.valueOf(idProvider));
		LOG.info(menssage);
		LOG.info("*******************************+");
		
		return "{\\n\" + \"	\\\"transport\\\":{\\n\" + \"		\\\"idProvider\\\" = \\\"1\\\",\\n\"\n" + 
				"					+ \"		\\\"flight\\\" = \\\"avianca\\\",\\n\" + \"		\\\"class\\\" = \\\"2500\\\",\\n\"\n" + 
				"					+ \"	    \\\"departureCity\\\" = \\\"Bogota\\\",\\n\" + \"	    \\\"arrivalCity\\\" = \\\"Cartagena\\\",\\n\"\n" + 
				"					+ \"	    \\\"departureDate\\\" = \\\"2020-12-01\\\",\\n\" + \"	    \\\"arrivalDate\\\" = \\\"2020-12-15\\\",\\n\"\n" + 
				"					+ \"	    \\\"price\\\" = 2000412\\n\" + \"	}    \\n\" + \"}\\n";
	}
	
	
	
}
