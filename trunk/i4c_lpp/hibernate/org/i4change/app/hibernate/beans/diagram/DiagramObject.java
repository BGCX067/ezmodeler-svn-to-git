package org.i4change.app.hibernate.beans.diagram;

// Generated 06.05.2008 11:46:39 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.i4change.app.dto.diagram.DiagramObjectPropertyDTO;
import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="diagramobject"
 * lazy="false"
 *
 */
public class DiagramObject implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4945041981705328477L;
	private long diagramObjectId;
	private String deleted;
	private Date inserted;
	private Users insertedby;
	private Date updated;
	private Users updatedby;
	private String name;
	private String objectTypeName;
	private Organisation organisation;
	private String uuid;
	private String comment;
	
	private Set<DataCarrierDiagramObject> dataCarrierDiagramObject;
	
	private String objectKey;
	
	//Unmapped attributes, logical Objects
	//IssueAssignee = current User assigned to this Object (Issueflow or Pending Role 
	// .. maybe all Object can have an Assignee to confirm later on)
	private IssueAssignee issueassignee;
	private Diagram diagram;
	
	//only needed for Pending Roles (Later on maybe all Object have a Pending Status)
	private Boolean pending;
	private Assignee assignee;
	
	private List<DiagramObjectProperty> properties;
	
	//Filled by Application Logic in the Role Search
	private List<Role> roles;

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public DiagramObject() {
	}

	public DiagramObject(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	
    /**
     * @hibernate.property
     *  column="uuid"
     *  type="string"
     */ 
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
     * 
     * @hibernate.id
     *  column="diagramobject_id"
     *  generator-class="increment"
     */ 
	public long getDiagramObjectId() {
		return this.diagramObjectId;
	}
	public void setDiagramObjectId(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	
    /**
     * @hibernate.property
     *  column="deleted"
     *  type="string"
     */
	public String getDeleted() {
		return this.deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
    /**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */
	public Date getInserted() {
		return this.inserted;
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
		return this.insertedby;
	}
	public void setInsertedby(Users insertedby) {
		this.insertedby = insertedby;
	}

    /**
     * @hibernate.property
     *  column="name"
     *  type="text"
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	public Users getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(Users updatedby) {
		this.updatedby = updatedby;
	}

	/**
     * @hibernate.property
     *  column="objecttypename"
     *  type="string"
     */
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
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

	public IssueAssignee getIssueassignee() {
		return issueassignee;
	}

	public void setIssueassignee(IssueAssignee issueassignee) {
		this.issueassignee = issueassignee;
	}

	/**
     * @hibernate.property
     *  column="pending"
     *  type="boolean"
     */
	public Boolean getPending() {
		return pending;
	}
	public void setPending(Boolean pending) {
		this.pending = pending;
	}
	
	/**
     * @hibernate.property
     *  column="objectkey"
     *  type="string"
     */
	public String getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "assignee_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Assignee"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Assignee getAssignee() {
		return assignee;
	}
	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	
    /**
     * @hibernate.set 
     * table = "datacarrier_diagramobject" 
     * inverse = "true" 
     * cascade = "none"
     * lazy = "false"
     * @hibernate.one-to-many 
     * class = "org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject"
     * @hibernate.key 
     * column = "diagramobject_id"
     */
	public Set<DataCarrierDiagramObject> getDataCarrierDiagramObject() {
		return dataCarrierDiagramObject;
	}
	public void setDataCarrierDiagramObject(Set<DataCarrierDiagramObject> dataCarrierDiagramObject) {
		this.dataCarrierDiagramObject = dataCarrierDiagramObject;
	}

	public List<DiagramObjectProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<DiagramObjectProperty> properties) {
		this.properties = properties;
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
	
}
