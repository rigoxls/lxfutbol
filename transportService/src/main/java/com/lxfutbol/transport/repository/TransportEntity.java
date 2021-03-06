package com.lxfutbol.transport.repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSPORT")
public class TransportEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idProvider;
	private String flight;
	private String classFlight;
	private String departureCity;
	private String arrivalCity;
	private Date departureDate;
	private Date arrivalDate;
	private BigDecimal price;
	private int hashCode;
	private java.util.Date dateUpdateTransport;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProvider() {
		return idProvider;
	}

	public void setIdProvider(long idProvider) {
		this.idProvider = idProvider;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getClassFlight() {
		return classFlight;
	}

	public void setClassFlight(String classFlight) {
		this.classFlight = classFlight;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public java.util.Date getDateUpdateTransport() {
		return dateUpdateTransport;
	}

	public void setDateUpdateTransport(java.util.Date dateUpdateTransport) {
		this.dateUpdateTransport = dateUpdateTransport;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (getIdProvider() + getDepartureCity() + getArrivalCity() + getDepartureDate() ).hashCode();
	}
	

	
}