package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="assignee"
 * lazy="false"
 *
 */
public class Assignee {
	
	private long assigneeId;
	private Users assignee;
	private Date inserted;
	private Long insertedby;
	private Date updated;
	private Long updatedBy;
	private String deleted;
	
	/**
     * 
     * @hibernate.id
     *  column="assignee_id"
     *  generator-class="increment"
     */ 
	public long getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(long assigneeId) {
		this.assigneeId = assigneeId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "assignee"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Users getAssignee() {
		return assignee;
	}
	public void setAssignee(Users assignee) {
		this.assignee = assignee;
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
     *  column="updatedby"
     *  type="long"
     */		
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	

}
