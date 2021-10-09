package com.demo.tradepro.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppConfig {
	private List<SalesTax> salestax;
	private Map<String, String> exemptcat;
	
	public void setSalestax(List<SalesTax> salestax) {
	    this.salestax = salestax;
    }
    
    public List<SalesTax> getSalestax() {
        return salestax;
    }
    
	public void setExemptcat(Map<String, String> exemptcat) {
	    this.exemptcat = exemptcat;
    }
    
    public Map<String, String> getExemptcat() {
        return exemptcat;
    }
	
    public static class exemptcat {
    	
    }
    
	public static class SalesTax {
		private String location;
		private String exempt;
		private double rate;
		
		public void setLocation(String location) {
		    this.location = location;
	    }
	    
	    public String getLocation() {
	        return location;
	    }
	    
		public void setExempt(String exempt) {
		    this.exempt = exempt;
	    }
	    
	    public String getExempt() {
	        return exempt;
	    }
	    
		public void setRate(double rate) {
		    this.rate = rate;
	    }
	    
	    public double getRate() {
	        return rate;
	    }
	    
	    @Override
	    public String toString() {
	    	return String.format("%s, %s, %s", location, exempt, rate);
	    }
	}
}
