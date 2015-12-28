package org.i4change.app.hibernate.beans.report;

import java.util.Date;

/**
 * 
 * @hibernate.class table="report_type"
 *
 */
public class ReportType {

	private long reportTypeId;
	private String name;
	private String comment;
	private Date inserted;
	private Date updated;
	private String deleted;
	private Long insertedby;
	private Long updatedby;
	
	/**
     * 
     * @hibernate.id
     *  column="report_type_id"
     *  generator-class="increment"
     */
	public long getReportTypeId() {
		return reportTypeId;
	}
	public void setReportTypeId(long reportTypeId) {
		this.reportTypeId = reportTypeId;
	}
	
	/**
     * @hibernate.property
     *  column="name"
     *  type="string"
     */  
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * @hibernate.property
     *  column="comment"
     *  type="text"
     */  
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
     *  column="deleted"
     *  type="string"
     */
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
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

}
