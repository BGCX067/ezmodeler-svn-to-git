package org.i4change.app.hibernate.beans.report;

import java.util.Date;

import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="report"
 *
 */
public class Report {
	
	private long reportId;
	private String name;
	private Users owner;
	private ReportType type;
	private Project project;
	private Date inserted;
	private Date updated;
	private String deleted;
	private Long insertedby;
	private Long updatedby;
	private Date startProcessing;
	private Date endProcessing;
	
	/**
     * 
     * @hibernate.id
     *  column="report_id"
     *  generator-class="increment"
     */
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
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
	 * @hibernate.many-to-one
	 * column = "project_id"
	 * class = "org.i4change.app.hibernate.beans.project.Project"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "report_type_id"
	 * class = "org.i4change.app.hibernate.beans.report.ReportType"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public ReportType getType() {
		return type;
	}
	public void setType(ReportType type) {
		this.type = type;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "owner_id"
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
     * @hibernate.property
     *  column="startprocessing"
     *  type="java.util.Date"
     */
	public Date getStartProcessing() {
		return startProcessing;
	}
	public void setStartProcessing(Date startProcessing) {
		this.startProcessing = startProcessing;
	}
	
	/**
     * @hibernate.property
     *  column="endprocessing"
     *  type="java.util.Date"
     */
	public Date getEndProcessing() {
		return endProcessing;
	}
	public void setEndProcessing(Date endProcessing) {
		this.endProcessing = endProcessing;
	}
	
}
