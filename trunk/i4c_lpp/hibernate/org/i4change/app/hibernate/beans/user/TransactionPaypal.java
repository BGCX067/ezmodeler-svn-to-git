package org.i4change.app.hibernate.beans.user;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;

/**
 * 
 * @hibernate.class table="transaction"
 * lazy="false"
 *
 */
public class TransactionPaypal {
	
	private long transactionId;
	private String transActionPaypalId;
	private String amount;
	private Date paidDate;
	private String status;
	private Date inserted;
	private Date updated;
	private Users users;
	private Long numberOfLicenses;
	private Boolean isControlled;
	private Organisation organisation;
	
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
	
	//This is somehow duplicated, but we need to create a Invoice Object
	//Already BEFORE the Invoice actually is created, otherwise the 
	//User  cannot see the status of his Transactions, as long as it is not completed
	private Long invoiceId;
	
	/**
     * 
     * @hibernate.id
     *  column="transaction_id"
     *  generator-class="increment"
     */
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	/**
     * @hibernate.property
     *  column="transactionpaypalid"
     *  type="string"
     */
	public String getTransActionPaypalId() {
		return transActionPaypalId;
	}
	public void setTransActionPaypalId(String transActionPaypalId) {
		this.transActionPaypalId = transActionPaypalId;
	}
	
	/**
     * @hibernate.property
     *  column="amount"
     *  type="string"
     */ 
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	/**
     * @hibernate.property
     *  column="paiddate"
     *  type="java.util.Date"
     */ 
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	/**
     * @hibernate.property
     *  column="status"
     *  type="string"
     */	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */	
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
	/**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */		
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "user_id"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}

	/**
     * @hibernate.property
     *  column="isControlled"
     *  type="boolean"
     */	
	public Boolean getIsControlled() {
		return isControlled;
	}
	public void setIsControlled(Boolean isControlled) {
		this.isControlled = isControlled;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "organisation_id"
	 * class = "org.i4change.app.hibernate.beans.domain.Organisation"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}
	
	/**
     * @hibernate.property
     *  column="numberoflicenses"
     *  type="long"
     */	
	public Long getNumberOfLicenses() {
		return numberOfLicenses;
	}
	public void setNumberOfLicenses(Long numberOfLicenses) {
		this.numberOfLicenses = numberOfLicenses;
	}
	
	/**
     * @hibernate.property
     *  column="amountstarttransaction"
     *  type="string"
     */	
	public String getAmountStartTransaction() {
		return amountStartTransaction;
	}
	public void setAmountStartTransaction(String amountStartTransaction) {
		this.amountStartTransaction = amountStartTransaction;
	}
	
	/**
     * @hibernate.property
     *  column="starttransaction"
     *  type="java.util.Date"
     */	
	public Date getStartTransaction() {
		return startTransaction;
	}
	public void setStartTransaction(Date startTransaction) {
		this.startTransaction = startTransaction;
	}
	
	/**
     * @hibernate.property
     *  column="address_1"
     *  type="string"
     */	
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	
	/**
     * @hibernate.property
     *  column="address_2"
     *  type="string"
     */	
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}
	
	/**
     * @hibernate.property
     *  column="city"
     *  type="string"
     */	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
     * @hibernate.property
     *  column="countryCode"
     *  type="string"
     */	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	/**
     * @hibernate.property
     *  column="state"
     *  type="string"
     */	
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	/**
     * @hibernate.property
     *  column="zip"
     *  type="string"
     */	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	/**
     * @hibernate.property
     *  column="firstName"
     *  type="string"
     */
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
     * @hibernate.property
     *  column="lastName"
     *  type="string"
     */
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
     * @hibernate.property
     *  column="countryAsName"
     *  type="string"
     */
	public String getCountryAsName() {
		return countryAsName;
	}
	public void setCountryAsName(String countryAsName) {
		this.countryAsName = countryAsName;
	}
	
	/**
     * @hibernate.property
     *  column="firstNameBilling"
     *  type="string"
     */
	public String getFirstNameBilling() {
		return firstNameBilling;
	}
	public void setFirstNameBilling(String firstNameBilling) {
		this.firstNameBilling = firstNameBilling;
	}
	
	/**
     * @hibernate.property
     *  column="lastNameBilling"
     *  type="string"
     */
	public String getLastNameBilling() {
		return lastNameBilling;
	}
	public void setLastNameBilling(String lastNameBilling) {
		this.lastNameBilling = lastNameBilling;
	}
	
	/**
     * @hibernate.property
     *  column="invoice_id"
     *  type="long"
     */
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	
}
