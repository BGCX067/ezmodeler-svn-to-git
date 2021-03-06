package org.i4change.app.hibernate.beans.domain;

import java.util.Date;

import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="organisation_users"
 * lazy="false"
 *
 */
public class Organisation_Users {

	private Long organisation_users_id;
	private Organisation organisation;
	private Long user_id;
	private Boolean isModerator;
	private Date starttime;
	private Date updatetime;
	private String deleted;
	private String comment;
	
	private Users us;

	public Organisation_Users() {
		super();
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
	
	/**
	 * @hibernate.many-to-one
	 * column = "user_id"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */	   
    public Users getUs() {
		return us;
	}
	public void setUs(Users us) {
		this.us = us;
	}

	/**
     * 
     * @hibernate.id
     *  column="organisation_users_id"
     *  generator-class="increment"
     */  
	public Long getOrganisation_users_id() {
		return organisation_users_id;
	}
	public void setOrganisation_users_id(Long organisation_users_id) {
		this.organisation_users_id = organisation_users_id;
	}

    /**
     * @hibernate.property
     *  column="user_id"
     *  type="long"
     */ 
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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
     *  column="comment"
     *  type="string"
     */ 	
    public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
     * @hibernate.property
     *  column="ismoderator"
     *  type="boolean"
     */ 
	public Boolean getIsModerator() {
		return isModerator;
	}
	public void setIsModerator(Boolean isModerator) {
		this.isModerator = isModerator;
	}	
	
	
}
