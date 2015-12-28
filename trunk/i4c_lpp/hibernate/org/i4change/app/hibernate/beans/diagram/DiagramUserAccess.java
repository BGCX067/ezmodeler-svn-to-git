package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="diagramuseraccess"
 * lazy="false"
 *
 */
public class DiagramUserAccess {
	
	private Long diagramuseraccessId;
	private Date inserted;
	private Date updated;
	private Long insertedBy;
	private Long updatedBy;
	private Diagram diagram;
	private Users user;
	private String deleted;
	
	private Boolean canRead;
	private Boolean canWrite;
	private Boolean onlyIssues;
	

	/**
     * 
     * @hibernate.id
     *  column="diagramuseraccess_id"
     *  generator-class="increment"
     */
	public Long getDiagramuseraccessId() {
		return diagramuseraccessId;
	}
	public void setDiagramuseraccessId(Long diagramuseraccessId) {
		this.diagramuseraccessId = diagramuseraccessId;
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
	public Long getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
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
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagram_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Diagram"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Diagram getDiagram() {
		return diagram;
	}
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
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
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
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
     *  column="canread"
     *  type="boolean"
     */
	public Boolean getCanRead() {
		return canRead;
	}
	public void setCanRead(Boolean canRead) {
		this.canRead = canRead;
	}

	/**
     * @hibernate.property
     *  column="canwrite"
     *  type="boolean"
     */	
	public Boolean getCanWrite() {
		return canWrite;
	}
	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}

	/**
     * @hibernate.property
     *  column="onlyissues"
     *  type="boolean"
     */	
	public Boolean getOnlyIssues() {
		return onlyIssues;
	}
	public void setOnlyIssues(Boolean onlyIssues) {
		this.onlyIssues = onlyIssues;
	}
	
}
