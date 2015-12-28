package org.i4change.app.dt.payment;

import java.util.Date;

public class InvoiceDTO {

	private Long invoiceId;
	private Long invoiceNumber = 0L;
	private String invoiceString = "";
	private TransactionPaypalDTO transactionPaypal = null;
	private String mergedXmlData_FileName;
	private String invoicePdfName;
	private String invoiceFileName;
	private Date inserted;
	private String insertedFormated;
	private String description;
	
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Long getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceString() {
		return invoiceString;
	}
	public void setInvoiceString(String invoiceString) {
		this.invoiceString = invoiceString;
	}
	public TransactionPaypalDTO getTransactionPaypal() {
		return transactionPaypal;
	}
	public void setTransactionPaypal(TransactionPaypalDTO transactionPaypal) {
		this.transactionPaypal = transactionPaypal;
	}
	public String getMergedXmlData_FileName() {
		return mergedXmlData_FileName;
	}
	public void setMergedXmlData_FileName(String mergedXmlData_FileName) {
		this.mergedXmlData_FileName = mergedXmlData_FileName;
	}
	public String getInvoicePdfName() {
		return invoicePdfName;
	}
	public void setInvoicePdfName(String invoicePdfName) {
		this.invoicePdfName = invoicePdfName;
	}
	public String getInvoiceFileName() {
		return invoiceFileName;
	}
	public void setInvoiceFileName(String invoiceFileName) {
		this.invoiceFileName = invoiceFileName;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public String getInsertedFormated() {
		return insertedFormated;
	}
	public void setInsertedFormated(String insertedFormated) {
		this.insertedFormated = insertedFormated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
