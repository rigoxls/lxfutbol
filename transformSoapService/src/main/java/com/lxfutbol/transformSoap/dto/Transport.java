package com.lxfutbol.transformSoap.dto;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
@XmlRootElement(name = "searchFlightElement")
public class Transport {

	   @XmlAttribute
	    public String departinCity;

	    @XmlAttribute
	    public String arrivingCity;

	    @XmlAttribute
	    public String departinDate;
	    
	    @XmlValue
	    public String value;
}
