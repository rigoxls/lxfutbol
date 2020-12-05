package com.lxfutbol.integrator.dto;

import java.util.Date;

public class ReserveLodge {

	
	private String guestName;
	private String roomNumber;
	private Date checkout;
	private Date checkin;
	private Integer numberPeople;
	private String type;
	private Integer idProvider; 
	
	
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Date getCheckout() {
		return checkout;
	}
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}
	public Date getCheckin() {
		return checkin;
	}
	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}
	public Integer getNumberPeople() {
		return numberPeople;
	}
	public void setNumberPeople(Integer numberPeople) {
		this.numberPeople = numberPeople;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(Integer idProvider) {
		this.idProvider = idProvider;
	}
}
