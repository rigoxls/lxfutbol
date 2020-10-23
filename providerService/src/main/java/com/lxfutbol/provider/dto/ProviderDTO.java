package com.lxfutbol.provider.dto;

import javax.validation.constraints.NotNull;

import com.lxfutbol.provider.repository.OperationEntity;

public class ProviderDTO {

	@NotNull()
	private long id;
	@NotNull()
	public String nit;
	public String name;
	public String representative;
	public String email;
	public String address;
	public String phone;	
	public int status;
	public int agreement;
	public int type;
	public String dataType;
	
	private String search;	
	private String book;
	private String cancelBook;
	
	public OperationEntity operationEntity;

	public ProviderDTO() {
	}

	public ProviderDTO(Long id, String nit, String name, String representative, String email, String address, String phone, 
			int status, int agreement, int type, String dataType) {
		super();
		this.id = id;
		this.nit = nit;
		this.name = name;
		this.representative = representative;
		this.email = email;
		this.address = address;
		this.phone = phone;		
		this.status = status;
		this.agreement = agreement;
		this.type = type;
		this.dataType = dataType;
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

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
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

	public int getAgreement() {
		return agreement;
	}

	public void setAgreement(int agreement) {
		this.agreement = agreement;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
		
}
