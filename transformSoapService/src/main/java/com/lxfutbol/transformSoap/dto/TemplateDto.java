package com.lxfutbol.transformSoap.dto;

import java.util.List;

public class TemplateDto {
	
	public String endPoint;
	public List<properties> parameters;
	public List<properties> mapping;
	
	public TemplateDto( List<properties> parameters) {
		super();
		this.parameters = parameters;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public List<properties> getParameters() {
		return parameters;
	}
	public void setParameters(List<properties> parameters) {
		this.parameters = parameters;
	}
	public List<properties> getMapping() {
		return mapping;
	}
	public void setMapping(List<properties> mapping) {
		this.mapping = mapping;
	}

	


	
}
