package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="roles"
 * lazy="false"
 *
 */
public class Role {

	private Long rolesId;
	private Date inserted;
	private Long insertedBy;
	private Date updated;
	private Long updatedBy;
	private String deleted;
	
	private Users assignee;
	//A User can be member of multiple Organizations/Customers but a Role is only
	//part of ONE Customer
	private Organisation organisation;
	private DiagramObject roleObject;
	
	/**
     * 
     * @hibernate.id
     *  column="roles_id"
     *  generator-class="increment"
     */ 
	public Long getRolesId() {
		return rolesId;
	}
	public void setRolesId(Long rolesId) {
		this.rolesId = rolesId;
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
	public Long getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
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
	 * @hibernate.many-to-one
	 * column = "diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */		
	public DiagramObject getRoleObject() {
		return roleObject;
	}
	public void setRoleObject(DiagramObject roleObject) {
		this.roleObject = roleObject;
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
	
}
