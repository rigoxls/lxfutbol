package com.lxfutbol.orders.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

	private Integer noOrder;
	private String nameUser;
	private String lastNameUser;
	private String numDocumentUser;
	private List<ItemDto> items;
	private BigDecimal value;
	
	public Integer getNoOrder() {
		return noOrder;
	}
	public void setNoOrder(Integer noOrder) {
		this.noOrder = noOrder;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getLastNameUser() {
		return lastNameUser;
	}
	public void setLastNameUser(String lastNameUser) {
		this.lastNameUser = lastNameUser;
	}
	public String getNumDocumentUser() {
		return numDocumentUser;
	}
	public void setNumDocumentUser(String numDocumentUser) {
		this.numDocumentUser = numDocumentUser;
	}
	public List<ItemDto> getItems() {
		return items;
	}
	public void setItems(List<ItemDto> items) {
		this.items = items;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
