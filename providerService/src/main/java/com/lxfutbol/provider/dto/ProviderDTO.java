package com.lxfutbol.provider.dto;

import javax.validation.constraints.NotNull;

import com.lxfutbol.provider.repository.OperationEntity;

public class ProviderDTO {

	@NotNull()
	private long id;
	@NotNull()
	public String name;
	public String lastname;
	public String email;
	public String address;
	public String phone;	
	public int status;
	
	private String search;	
	private String book;
	private String cancelBook;
	
	public OperationEntity operationEntity;

	public ProviderDTO() {
	}

	public ProviderDTO(Long id, String name, String lastname, String email, String address, String phone, int status) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phone = phone;		
		this.status = status;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getCancelBook() {
		return cancelBook;
	}

	public void setCancelBook(String cancelBook) {
		this.cancelBook = cancelBook;
	}

	public OperationEntity getOperationEntity() {
		return operationEntity;
	}

	public void setOperationEntity(OperationEntity operationEntity) {
		this.operationEntity = operationEntity;
	}

}
