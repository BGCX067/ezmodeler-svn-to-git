package org.i4change.paypal.beans;

public class Country {
	
	private String countryName;
	private String paypal;
	
	public Country(String countryName, String paypal) {
		super();
		this.countryName = countryName;
		this.paypal = paypal;
	}
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPaypal() {
		return paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
	
}
