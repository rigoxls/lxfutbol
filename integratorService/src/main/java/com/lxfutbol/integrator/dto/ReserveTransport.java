package com.lxfutbol.integrator.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReserveTransport {

	private String passengerName;
	private String passengerIdentityNumber;
	private BigDecimal price;
	private String destinationCity;
	private String classFlight;
	private String flight;
	private String originCity;
	private Date departureDate;
	private Date arrivalDate;
	private Integer idProvider; 
	
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPassengerIdentityNumber() {
		return passengerIdentityNumber;
	}
	public void setPassengerIdentityNumber(String passengerIdentityNumber) {
		this.passengerIdentityNumber = passengerIdentityNumber;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getClassFlight() {
		return classFlight;
	}
	public void setClassFlight(String classFlight) {
		this.classFlight = classFlight;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Integer getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(Integer idProvider) {
		this.idProvider = idProvider;
	}
}
