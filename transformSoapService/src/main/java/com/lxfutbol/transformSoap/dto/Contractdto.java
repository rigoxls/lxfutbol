package com.lxfutbol.transformSoap.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

import com.lxfutbol.transformSoap.models.Operations;

public class Contractdto {
	


	@NotNull
	public long id;
	@NotNull
	public String providerName;
	@NotNull
	public String type;
	
	public List<Operations> operations;
	
	public Contractdto(@NotNull long id, @NotNull String providerName, @NotNull String type, List<Operations> operations) {
		super();
		this.id=id;
		this.providerName=providerName;
		this.type=type;
		this.operations=operations;
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Operations> getOperations() {
		return operations;
	}

	public void setOperations(List<Operations> operations) {
		this.operations = operations;
	}

	
}
