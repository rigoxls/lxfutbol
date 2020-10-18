package com.lxfutbol.provider.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lxfutbol.provider.dto.ProviderDTO;

@Entity
@Table(name = "`provider`")
public class ProviderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String lastname;
	private String email;
	private String address;
	private String phone;	
	private int status;
	
	protected ProviderEntity() {}
	
	public ProviderEntity(ProviderDTO newProvider) {
		super();
		this.name = newProvider.name;
		this.lastname = newProvider.lastname;
		this.email = newProvider.email;
		this.address = newProvider.address;
		this.phone = newProvider.phone;		
		this.status = newProvider.status;		
	}	
	
	
	public ProviderEntity(long id, String name, String lastname, String email, String address, String phone,
			int status) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phone = phone;		
		this.status = status;
	}
	
	public void setAll(ProviderDTO providerToUpdate) {
		this.name = providerToUpdate.name;
		this.lastname = providerToUpdate.lastname;
		this.email = providerToUpdate.email;
		this.address = providerToUpdate.address;
		this.phone = providerToUpdate.phone;		
		this.status = providerToUpdate.status;	
	}	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
