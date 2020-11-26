package com.lxfutbol.orders.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

	private Integer noOrder;
	private String nameUser;
	private String lastNameUser;
	private String numDocumentUser;
	private List<ItemDto> items;
	private BigDecimal totalValue;
	private String email;
	private String address;
	private String paidStatus;
	private String phone;

	
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
	
	public BigDecimal getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPaidStatus() {
		return paidStatus;
	}
	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
