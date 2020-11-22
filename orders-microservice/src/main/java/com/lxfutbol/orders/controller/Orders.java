package com.lxfutbol.orders.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxfutbol.orders.dto.OrderDto;
import com.lxfutbol.orders.interfaces.IOrdersService;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/orders")
public class Orders {

	private final Logger LOG = LoggerFactory.getLogger(Orders.class);
	
	@Autowired
	private IOrdersService orderService;
	
	@PostMapping("/createOrder")
	public ResponseEntity<Boolean> createOrders(@RequestBody OrderDto orderDto)  {
		try {
			orderService.createOrder(orderDto);
		} catch (Exception ex) {
			LOG.info("Error creando la order");
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
		
	}
	
	@GetMapping("/findAll")
	public List<OrderDto> findAllOrders(){
		return orderService.findAllOrders();
	}
}
