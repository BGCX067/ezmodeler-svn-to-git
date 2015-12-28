package org.i4change.app.hibernate.beans.mail;

import java.util.Date;

/**
 * 
 * @hibernate.class table="mailitem"
 *
 */
public class MailItem {
	
	private long mailItemId;
	private Date inserted;
	private Date updated;
	private Long insertedby;
	private Long updatedby;
	
	private String sender;
	private String receipent;
	private String subject;
	private String content;
	private String filePath;
	
	private Boolean send;
	private String deliverStatus;
	
	
	/**
     * 
     * @hibernate.id
     *  column="mailitem_id"
     *  generator-class="increment"
     */ 
	public long getMailItemId() {
		return mailItemId;
	}
	public void setMailItemId(long mailItemId) {
		this.mailItemId = mailItemId;
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
     * @hibernate.property
     *  column="insertedby"
     *  type="long"
     */
	public Long getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
	}

	/**
     * @hibernate.property
     *  column="updatedby"
     *  type="long"
     */
	public Long getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}

	/**
     * @hibernate.property
     *  column="sender"
     *  type="string"
     */	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/**
     * @hibernate.property
     *  column="receipent"
     *  type="string"
     */	
	public String getReceipent() {
		return receipent;
	}
	public void setReceipent(String receipent) {
		this.receipent = receipent;
	}
	
	/**
     * @hibernate.property
     *  column="subject"
     *  type="string"
     */	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
     * @hibernate.property
     *  column="content"
     *  type="text"
     */
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
     * @hibernate.property
     *  column="send"
     *  type="boolean"
     */
	public Boolean getSend() {
		return send;
	}
	public void setSend(Boolean send) {
		this.send = send;
	}
	
	/**
     * @hibernate.property
     *  column="deliverstatus"
     *  type="string"
     */	
	public String getDeliverStatus() {
		return deliverStatus;
	}
	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	
	/**
     * @hibernate.property
     *  column="filePath"
     *  type="string"
     */	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
