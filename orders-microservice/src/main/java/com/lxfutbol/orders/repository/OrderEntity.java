package com.lxfutbol.orders.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noOrder;
	private String nameUser;
	private String lastNameUser;
	private String numDocumentUser;
	@OneToMany(targetEntity=ItemsEntity.class, mappedBy="orderEntity")
	private List<ItemsEntity> items;
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
		
	public List<ItemsEntity> getItems() {
		return items;
	}
	public void setItems(List<ItemsEntity> items) {
		this.items = items;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
