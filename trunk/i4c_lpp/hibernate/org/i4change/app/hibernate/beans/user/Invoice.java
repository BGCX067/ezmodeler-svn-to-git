package org.i4change.app.hibernate.beans.user;

import java.util.Date;

/**
 * 
 * @hibernate.class table="invoice"
 * lazy="false"
 *
 */
public class Invoice {
	
	private Long invoiceId;
	private Long invoiceNumber = 0L;
	private String invoiceString = "";
	private TransactionPaypal transactionPaypal = null;
	private String mergedXmlData_FileName;
	private String invoicePdfName;
	private String invoiceFileName;
	private Date inserted;
	private String insertedFormated;
	private String description;
	
	/**
     * 
     * @hibernate.id
     *  column="invoice_id"
     *  generator-class="increment"
     */
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
     * @hibernate.property
     *  column="invoice_number"
     *  type="long"
     */
	public Long getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
     * @hibernate.property
     *  column="invoice_string"
     *  type="string"
     */
	public String getInvoiceString() {
		return invoiceString;
	}
	public void setInvoiceString(String invoiceString) {
		this.invoiceString = invoiceString;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "transaction_id"
	 * class = "org.i4change.app.hibernate.beans.user.TransactionPaypal"
	 * insert="true"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public TransactionPaypal getTransactionPaypal() {
		return transactionPaypal;
	}
	public void setTransactionPaypal(TransactionPaypal transactionPaypal) {
		this.transactionPaypal = transactionPaypal;
	}

	/**
     * @hibernate.property
     *  column="mergedXmlData_FileName"
     *  type="string"
     */	
	public String getMergedXmlData_FileName() {
		return mergedXmlData_FileName;
	}
	public void setMergedXmlData_FileName(String mergedXmlData_FileName) {
		this.mergedXmlData_FileName = mergedXmlData_FileName;
	}

	/**
     * @hibernate.property
     *  column="invoiceFileName"
     *  type="string"
     */		
	public String getInvoiceFileName() {
		return invoiceFileName;
	}
	public void setInvoiceFileName(String invoiceFileName) {
		this.invoiceFileName = invoiceFileName;
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
     *  column="description"
     *  type="string"
     */	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
     * @hibernate.property
     *  column="insertedFormated"
     *  type="string"
     */	
	public String getInsertedFormated() {
		return insertedFormated;
	}
	public void setInsertedFormated(String insertedFormated) {
		this.insertedFormated = insertedFormated;
	}
	
	/**
     * @hibernate.property
     *  column="invoicePdfName"
     *  type="string"
     */	
	public String getInvoicePdfName() {
		return invoicePdfName;
	}
	public void setInvoicePdfName(String invoicePdfName) {
		this.invoicePdfName = invoicePdfName;
	}
	
}
