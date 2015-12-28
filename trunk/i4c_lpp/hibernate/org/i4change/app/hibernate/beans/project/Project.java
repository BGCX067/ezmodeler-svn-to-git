package org.i4change.app.hibernate.beans.project;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="project"
 *
 */
public class Project {
	
	private long projectId;
	private String name;
	private Organisation organisation;
	private String wizzardType;
	private Boolean buildByWizzard;
	private Integer wizzardStep;
	private Users owner;
	private String description;
	private Date inserted;
	private Date updated;
	private String deleted;
	private Long insertedby;
	private Long updatedby;
	
	/**
     * 
     * @hibernate.id
     *  column="project_id"
     *  generator-class="increment"
     */ 
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
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
     *  column="wizzardtype"
     *  type="string"
     */
	public String getWizzardType() {
		return wizzardType;
	}
	public void setWizzardType(String wizzardType) {
		this.wizzardType = wizzardType;
	}
	
	/**
     * @hibernate.property
     *  column="buildbywizzard"
     *  type="boolean"
     */
	public Boolean getBuildByWizzard() {
		return buildByWizzard;
	}
	public void setBuildByWizzard(Boolean buildByWizzard) {
		this.buildByWizzard = buildByWizzard;
	}
	
	/**
     * @hibernate.property
     *  column="wizzardstep"
     *  type="int"
     */
	public Integer getWizzardStep() {
		return wizzardStep;
	}
	public void setWizzardStep(Integer wizzardStep) {
		this.wizzardStep = wizzardStep;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "owner"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Users getOwner() {
		return owner;
	}
	public void setOwner(Users owner) {
		this.owner = owner;
	}
	
	/**
     * @hibernate.property
     *  column="description"
     *  type="text"
     */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
