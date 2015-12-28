package org.i4change.app.hibernate.beans.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="organisation"
 * lazy="false"
 *
 */
public class Organisation {

	private Long organisation_id;
	private String name;
	private Long insertedby;
	private Long updatedby;
	private Date starttime;
	private Date updatetime;
	private String deleted;
	private String orgPatterns;
	//private Boolean isPending;
	//private Date expireDate;
	//private Long maxUsers;
	//private Long maxWorkDays;
	//private Long usedWorkDays;
	//private Date lastWorkDay;
	//this attribute is used by the Mail Spool to check if the last Mail in 
	//case of a Pending Organization is send within the last 24 hours
	//private Date lastMailSend;
	
	//This Object is not filled by hibernate, its filled by logic using 
	//XStream
	private Vector orgPatternsMap;
	//this Object is not fileld by Hibernate, its only used to display the Pending Org-List in the 
	//Dashboard of the Administrators
	private List<Users> user;
	
	private Float pricePerOrg;
	private Float pricePerUser;
	private String uuid;
	
	
	public Organisation() {
		super();
		// TODO Auto-generated constructor stub
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
     * 
     * @hibernate.id
     *  column="organisation_id"
     *  generator-class="increment"
     */ 
	public Long getOrganisation_id() {
		return organisation_id;
	}
	public void setOrganisation_id(Long organisation_id) {
		this.organisation_id = organisation_id;
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
     *  column="starttime"
     *  type="java.util.Date"
     */  	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
    
    /**
     * @hibernate.property
     *  column="updatetime"
     *  type="java.util.Date"
     */  	
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
     *  column="orgpatterns"
     *  type="text"
     */
	public String getOrgPatterns() {
		return orgPatterns;
	}
	public void setOrgPatterns(String orgPatterns) {
		this.orgPatterns = orgPatterns;
	}

	public Vector getOrgPatternsMap() {
		return orgPatternsMap;
	}
	public void setOrgPatternsMap(Vector orgPatternsMap) {
		this.orgPatternsMap = orgPatternsMap;
	}
	
	public List<Users> getUser() {
		return user;
	}
	public void setUser(List<Users> user) {
		this.user = user;
	}

	/**
     * @hibernate.property
     *  column="priceperorg"
     *  type="float"
     */
	public Float getPricePerOrg() {
		return pricePerOrg;
	}
	public void setPricePerOrg(Float pricePerOrg) {
		this.pricePerOrg = pricePerOrg;
	}

	/**
     * @hibernate.property
     *  column="priceperuser"
     *  type="float"
     */
	public Float getPricePerUser() {
		return pricePerUser;
	}
	public void setPricePerUser(Float pricePerUser) {
		this.pricePerUser = pricePerUser;
	}	
	
	
}
