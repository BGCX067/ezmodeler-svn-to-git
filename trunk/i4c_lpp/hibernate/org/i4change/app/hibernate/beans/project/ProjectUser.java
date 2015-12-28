package org.i4change.app.hibernate.beans.project;

import java.util.Date;

/**
 * 
 * @hibernate.class table="project_user"
 *
 */
public class ProjectUser {

	private long projectUserId;
	private long projectId;
	private long userId;
	private Date inserted;
	private String deleted;
	
	/**
     * 
     * @hibernate.id
     *  column="project_user_id"
     *  generator-class="increment"
     */
	public long getProjectUserId() {
		return projectUserId;
	}
	public void setProjectUserId(long projectUserId) {
		this.projectUserId = projectUserId;
	}
	
	/**
     * @hibernate.property
     *  column="project_id"
     *  type="long"
     */
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	/**
     * @hibernate.property
     *  column="user_id"
     *  type="long"
     */
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
     *  column="deleted"
     *  type="string"
     */ 
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
}
