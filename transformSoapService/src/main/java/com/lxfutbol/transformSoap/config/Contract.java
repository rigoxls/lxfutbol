package com.lxfutbol.transformSoap.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cust.data.contract")
@Configuration("contractProperties")
public class Contract {
	
	public String endPoint;
	public String operation;
	public String customHeader;
	public String root;
	
	
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getCustomHeader() {
		return customHeader;
	}
	public void setCustomHeader(String customHeader) {
		this.customHeader = customHeader;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	
}
