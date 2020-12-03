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
		try {

			PlacesDto cityDto = null;
			List<Object[]> citys = entityManager.createNativeQuery("select DISTINCT city, country from spectacles")
					.getResultList();

			for (Object[] city : citys) {
				cityDto = new PlacesDto();
				cityDto.setCity(String.valueOf(city[0]));
				cityDto.setCountry((String.valueOf(city[1])));
				listCitys.add(cityDto);
			}
		} catch (Exception ex) {
			System.out.print("Error consultando la lista de ciudades." + ex.getCause());
		}
		return listCitys;
	}

	public List<String> findCategorySpectacle() {

		List<String> listCategorys = new ArrayList<>();
		try {
			List<String> categorys = entityManager.createNativeQuery("select DISTINCT type from spectacles")
					.getResultList();

			for (String category : categorys) {
				listCategorys.add(category);
			}
		} catch (Exception ex) {
			System.out.print("Error consultando la lista de categorias." + ex.getCause());
		}
		return listCategorys;
	}
	
}