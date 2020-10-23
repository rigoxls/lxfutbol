package com.lxfutbol.provider.repository;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lxfutbol.provider.dto.ProviderDTO;

@Entity
@Table(name = "`provider`")
public class ProviderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nit;
	private String name;
	private String representative;
	private String email;
	private String address;
	private String phone;
	
	private int status;
	private int agreement;
	private int type;
	private String dataType;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_id")
	private OperationEntity operationEntity;
	
	protected ProviderEntity() {}
	
	public ProviderEntity(ProviderDTO newProvider) {
		super();
		this.nit = newProvider.nit;
		this.name = newProvider.name;
		this.representative = newProvider.representative;
		this.email = newProvider.email;
		this.address = newProvider.address;
		this.phone = newProvider.phone;		
		this.status = newProvider.status;
		this.agreement = newProvider.agreement;
		this.type = newProvider.type;
		this.dataType = newProvider.dataType;
	}	
	
	
	public ProviderEntity(long id, String nit, String name, String representative, String email, String address, String phone,
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
	
	public void setAll(ProviderDTO providerToUpdate) {
		this.nit = providerToUpdate.nit;
		this.name = providerToUpdate.name;
		this.representative = providerToUpdate.representative;
		this.email = providerToUpdate.email;
		this.address = providerToUpdate.address;
		this.phone = providerToUpdate.phone;
		this.status = providerToUpdate.status;
		this.agreement = providerToUpdate.agreement;
		this.type = providerToUpdate.type;
		this.dataType = providerToUpdate.dataType;
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
