package com.lxfutbol.spectacle.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.lxfutbol.spectacle.dto.PlacesDto;


@Service
public class SpectacleServicesDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<PlacesDto> findPlacesSpectacle() {
		
		List<PlacesDto> listCitys = new ArrayList<>();
		PlacesDto cityDto = null;
		List<Object[]> citys = entityManager.createNativeQuery("select DISTINCT city, country from espectacle").getResultList();
		
		for(Object[] city : citys) {
			cityDto = new PlacesDto();
			cityDto.setCity(String.valueOf(city[0]));
			cityDto.setCountry((String.valueOf(city[1])));
			listCitys.add(cityDto);
		}
		return listCitys;
	}
	
	public List<String> findCategorySpectacle() {
		
		List<String> listCategorys = new ArrayList<>();
		List<Object[]> citys = entityManager.createNativeQuery("select DISTINCT type, country from espectacle").getResultList();
		
		for(Object[] city : citys) {
			listCategorys.add(String.valueOf(city[0]));
		}
		return listCategorys;
	}
	
}
