package com.lxfutbol.transformSoap.models;

import java.util.List;

public class Operations {

	public String endPoint;
	public List<String> parameters;
	public List<Mapping> properties;
	
	public Operations(String endPoint, List<String> parameters, List<Mapping> properties) {
		super();
		this.endPoint=endPoint;
		this.parameters=parameters;
		this.properties=properties;
	}

	
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public List<Mapping> getProperties() {
		return properties;
	}

	public void setProperties(List<Mapping> properties) {
		this.properties = properties;
	}
	
}
