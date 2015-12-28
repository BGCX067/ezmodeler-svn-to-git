package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="issueassignee"
 * lazy="false"
 *
 */
public class IssueAssignee {
	
	private long issueassigneeId;
	private DiagramObject diagramObject;
	private Users assignee;
	private Date inserted;
	private Users insertedby;
	private Date updated;
	private Users updatedBy;
	private String deleted;
	
	/**
     * 
     * @hibernate.id
     *  column="issueassignee_id"
     *  generator-class="increment"
     */ 
	public long getIssueassigneeId() {
		return issueassigneeId;
	}
	public void setIssueassigneeId(long issueassigneeId) {
		this.issueassigneeId = issueassigneeId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public DiagramObject getDiagramObject() {
		return diagramObject;
	}
	public void setDiagramObject(DiagramObject diagramObject) {
		this.diagramObject = diagramObject;
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
	 * @hibernate.many-to-one
	 * column = "insertedby"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Users getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Users insertedby) {
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
	 * @hibernate.many-to-one
	 * column = "updatedby"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */		
	public Users getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
	
}
