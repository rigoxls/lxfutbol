package com.lxfutbol.spectacle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.spectacle.dto.PlacesDto;
import com.lxfutbol.spectacle.service.SpectacleServicesDao;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/spectacle")
public class SpectacleController {

		@Autowired
		private SpectacleServicesDao servicesDao;
		
		@GetMapping("/list")
		public List<PlacesDto> listPlaces(){
			return servicesDao.findPlacesSpectacle();
		}
		

		@GetMapping("/list/category")
		public List<String> listCategory(){
			return servicesDao.findCategorySpectacle();
		}
	
}
