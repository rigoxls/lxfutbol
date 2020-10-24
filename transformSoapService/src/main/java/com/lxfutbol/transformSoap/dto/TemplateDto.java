package com.lxfutbol.transformSoap.dto;

import java.util.List;

public class TemplateDto {
	
	public String endPoint;
	public List<properties> parameters;
	public List<properties> mapping;
	
	public TemplateDto(String endPoint, List<properties> parameters, List<properties> mapping) {
		super();
		this.endPoint = endPoint;
		this.parameters = parameters;
		this.mapping = mapping;
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
