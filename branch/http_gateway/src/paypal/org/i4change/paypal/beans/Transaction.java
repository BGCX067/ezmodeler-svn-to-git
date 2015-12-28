package org.i4change.paypal.beans;

public class Transaction {

	private String amountstarttransaction;
	private String firstName;
	private String lastName;
	
	private String address_1;
	private String address_2;
	private String city;
	private String countryCode;
	private String State;
	private String zip;
	
	
	public String getAmountstarttransaction() {
		return amountstarttransaction;
	}
	public void setAmountstarttransaction(String amountstarttransaction) {
		this.amountstarttransaction = amountstarttransaction;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
