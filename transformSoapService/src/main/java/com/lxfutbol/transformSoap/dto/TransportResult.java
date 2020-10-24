package com.lxfutbol.transformSoap.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "searchFlightResponseElement")
public class TransportResult {

	public List<TransportResult> list;
	
		   @XmlAttribute
		    public String departinCity;

		    @XmlAttribute
		    public String arrivingCity;

		    @XmlAttribute
		    public String departinDate;
		    
		    @XmlAttribute
	        public String cabin;
		    
		    @XmlAttribute 
	        public String arrivingDate;
		    
		    @XmlAttribute 
		    public String price;
		    
		    @XmlAttribute 
	        public String meals;

	        @XmlAttribute 
	        public int number;
}
