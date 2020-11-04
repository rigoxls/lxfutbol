package com.lxfutbol.transformSoap.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "searchFlightResponseElement")
public class TransportResult {

	       @XmlElement(required = true)
			public List<result> result;
	
			@XmlRootElement(name="result")	
			public static class result {

				 @XmlElement
			     public List<flights> flights;
			     
			     @XmlRootElement(name = "flights")
			     public static class flights {
			    	 
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
			     
						
			}

	}
