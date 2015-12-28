package org.i4change.app.dt.payment;

import java.util.Date;

import org.i4change.app.dto.user.OrganisationDTO;
import org.i4change.app.dto.user.UserMinimalDTO;

public class TransactionPaypalDTO {

	private long transactionId;
	private String transActionPaypalId;
	private String amount;
	private Date paidDate;
	private String status;
	private Date inserted;
	private Date updated;
	private UserMinimalDTO users;
	private Long numberOfLicenses;
	private Boolean isControlled;
	private OrganisationDTO organisation;
	
	//These values are filled as soon as you open the Window for the Payment Process
	private String amountStartTransaction;
	private Date startTransaction;
	
	private String firstName;
	private String lastName;
	
	private String firstNameBilling;
	private String lastNameBilling;
	
	private String address_1;
	private String address_2;
	private String city;
	private String countryCode;
	private String countryAsName;
	private String State;
	private String zip;
	
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransActionPaypalId() {
		return transActionPaypalId;
	}
	public void setTransActionPaypalId(String transActionPaypalId) {
		this.transActionPaypalId = transActionPaypalId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public UserMinimalDTO getUsers() {
		return users;
	}
	public void setUsers(UserMinimalDTO users) {
		this.users = users;
	}
	public Long getNumberOfLicenses() {
		return numberOfLicenses;
	}
	public void setNumberOfLicenses(Long numberOfLicenses) {
		this.numberOfLicenses = numberOfLicenses;
	}
	public Boolean getIsControlled() {
		return isControlled;
	}
	public void setIsControlled(Boolean isControlled) {
		this.isControlled = isControlled;
	}
	public OrganisationDTO getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrganisationDTO organisation) {
		this.organisation = organisation;
	}
	public String getAmountStartTransaction() {
		return amountStartTransaction;
	}
	public void setAmountStartTransaction(String amountStartTransaction) {
		this.amountStartTransaction = amountStartTransaction;
	}
	public Date getStartTransaction() {
		return startTransaction;
	}
	public void setStartTransaction(Date startTransaction) {
		this.startTransaction = startTransaction;
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
	public String getFirstNameBilling() {
		return firstNameBilling;
	}
	public void setFirstNameBilling(String firstNameBilling) {
		this.firstNameBilling = firstNameBilling;
	}
	public String getLastNameBilling() {
		return lastNameBilling;
	}
	public void setLastNameBilling(String lastNameBilling) {
		this.lastNameBilling = lastNameBilling;
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
	public String getCountryAsName() {
		return countryAsName;
	}
	public void setCountryAsName(String countryAsName) {
		this.countryAsName = countryAsName;
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
