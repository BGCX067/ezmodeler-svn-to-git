package org.i4change.app.dto.user;

public class AddressDTO {

	public Long adresses_id;
	private String additionalname;
    private String comment;
    private String fax;
    private CountryDTO states;	
    private String street;  
    private String town;     
    private String zip;
    private String phone;
    private String mobile;
    private String email;
    
	public Long getAdresses_id() {
		return adresses_id;
	}
	public void setAdresses_id(Long adresses_id) {
		this.adresses_id = adresses_id;
	}
	public String getAdditionalname() {
		return additionalname;
	}
	public void setAdditionalname(String additionalname) {
		this.additionalname = additionalname;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public CountryDTO getStates() {
		return states;
	}
	public void setStates(CountryDTO states) {
		this.states = states;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}    
	
}
