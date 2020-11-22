package com.lxfutbol.orders.interfaces;

import java.util.List;

import com.lxfutbol.orders.dto.OrderDto;

public interface IOrdersService {

	public void createOrder(OrderDto order);
	
	public List<OrderDto> findAllOrders();
}
