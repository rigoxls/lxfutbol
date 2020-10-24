package com.lxfutbol.lodge.repository;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LODGE")
public class LodgeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idProvider;
	private String nameHotel;
	private String addressHotel;
	private String cityHotel;
	private String countryHotel;
	private String numberRoom;
	private BigDecimal priceRoom;
	private String typeRoom;
	private int hashCode;
	private java.util.Date dateUpdateLodge;
	private Date checkIn;
	private Date checkout;
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
	public String getNameHotel() {
		return nameHotel;
	}
	public void setNameHotel(String nameHotel) {
		this.nameHotel = nameHotel;
	}
	public String getAddressHotel() {
		return addressHotel;
	}
	public void setAddressHotel(String addressHotel) {
		this.addressHotel = addressHotel;
	}
	public String getCityHotel() {
		return cityHotel;
	}
	public void setCityHotel(String cityHotel) {
		this.cityHotel = cityHotel;
	}
	public String getCountryHotel() {
		return countryHotel;
	}
	public void setCountryHotel(String countryHotel) {
		this.countryHotel = countryHotel;
	}
	public String getNumberRoom() {
		return numberRoom;
	}
	public void setNumberRoom(String numberRoom) {
		this.numberRoom = numberRoom;
	}
	public BigDecimal getPriceRoom() {
		return priceRoom;
	}
	public void setPriceRoom(BigDecimal priceRoom) {
		this.priceRoom = priceRoom;
	}
	public String getTypeRoom() {
		return typeRoom;
	}
	public void setTypeRoom(String typeRoom) {
		this.typeRoom = typeRoom;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	public java.util.Date getDateUpdateLodge() {
		return dateUpdateLodge;
	}
	public void setDateUpdateLodge(java.util.Date dateUpdateLodge) {
		this.dateUpdateLodge = dateUpdateLodge;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckout() {
		return checkout;
	}
	public void setCheckout(Date checkout) {
		this.checkout = checkout;
	}	
}
	