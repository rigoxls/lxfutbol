package com.lxfutbol.orders.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.stereotype.Service;

import com.lxfutbol.orders.dto.ItemDto;
import com.lxfutbol.orders.dto.OrderDto;
import com.lxfutbol.orders.interfaces.IOrdersService;
import com.lxfutbol.orders.repository.IItemsEntity;
import com.lxfutbol.orders.repository.IOrderEntity;
import com.lxfutbol.orders.repository.ItemsEntity;
import com.lxfutbol.orders.repository.OrderEntity;

@Service
public class OrdersService implements IOrdersService{

	@Autowired
	private IOrderEntity orderDb; 
	
	@Autowired
	private IItemsEntity itemDb;
	
	public void createOrder(OrderDto order) {

		OrderEntity orderCreate = new OrderEntity();
		ItemsEntity item = null;
		List<ItemsEntity> items = new ArrayList<>();

		for (ItemDto itemDto : order.getItems()) {
			item = new ItemsEntity();
			item.setName(itemDto.getName());
			item.setQuantity(itemDto.getQuantity());
			item.setValue(itemDto.getValue());
			item.setOrderEntity(orderCreate);
			items.add(item);
		}

		orderCreate.setItems(items);
		orderCreate.setLastNameUser(order.getLastNameUser());
		orderCreate.setNameUser(order.getNameUser());
		orderCreate.setNumDocumentUser(order.getNumDocumentUser());
		orderCreate.setTotalValue(order.getTotalValue());
		orderCreate.setEmail(order.getEmail());
		orderCreate.setAddress(order.getAddress());
		orderCreate.setPaidStatus(order.getPaidStatus());
		
		orderDb.save(orderCreate);
		itemDb.saveAll(items);

	}
	
	public List<OrderDto> findAllOrders(){
		List<OrderEntity> orders = orderDb.findAll();
		List<OrderDto> resultOrders = new ArrayList<>();
		List<ItemDto> listItems = null;
		OrderDto order = null;
		ItemDto items = null;

		for (OrderEntity orderE : orders) {
			order = new OrderDto();
			order.setLastNameUser(orderE.getLastNameUser());
			order.setNameUser(orderE.getNameUser());
			order.setNoOrder(orderE.getNoOrder());
			order.setNumDocumentUser(orderE.getNumDocumentUser());
			order.setTotalValue(orderE.getTotalValue());
			order.setEmail(orderE.getEmail());
			order.setAddress(orderE.getAddress());
			order.setPaidStatus(orderE.getPaidStatus());
			listItems = new ArrayList<>();
			for (ItemsEntity item : orderE.getItems()) {
				items = new ItemDto();
				items.setName(item.getName());
				items.setQuantity(item.getQuantity());
				items.setValue(item.getValue());
				listItems.add(items);
			}

			order.setItems(listItems);
			resultOrders.add(order);
		}

		return resultOrders;

	}
	
}
