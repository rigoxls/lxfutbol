package com.lxfutbol.provider.dto;

import javax.validation.constraints.NotNull;

public class ProviderDTO {

	@NotNull()
	private long id;
	@NotNull()
	public String name;
	public String lastname;
	public String email;
	public String address;
	public String phone;
	public int role;
	public int status;

	public ProviderDTO() {
	}

	public ProviderDTO(Long id, String name, String lastname, String email, String address, String phone, int role, int status) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.role = role;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public int getRole() {
		return role;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", address="
				+ address + ", phone=" + phone + ", role=" + role + ", status=" + status + "]";
	}	

}
