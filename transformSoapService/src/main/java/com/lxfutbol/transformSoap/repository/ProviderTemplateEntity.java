package com.lxfutbol.transformSoap.repository;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ProviderTemplateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String template;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

} 