package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;

/**
 * 
 * @hibernate.class table="objecttype"
 * lazy="false"
 *
 */
public class ObjectType {
	
	private long objectTypeId;
	private String name;
	private Long labelid;
	private Date inserted;
	private Date updated;
	private Long insertedBy;
	private Long updatedBy;
	private Boolean isPublic;
	private String deleted;
	private Organisation organisation;
	private String comment;
	private Boolean isSidebarItem;
	//In this case this is only interesting if isSidebarItem  true
	private String objectTypeName;
	
	/**
     * 
     * @hibernate.id
     *  column="objecttype_id"
     *  generator-class="increment"
     */
	public long getObjectTypeId() {
		return objectTypeId;
	}
	public void setObjectTypeId(long objectTypeId) {
		this.objectTypeId = objectTypeId;
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
     *  column="ispublic"
     *  type="boolean"
     */
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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
     *  type="text"
     */
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
     * @hibernate.property
     *  column="issidebaritem"
     *  type="boolean"
     */
	public Boolean getIsSidebarItem() {
		return isSidebarItem;
	}
	public void setIsSidebarItem(Boolean isSidebarItem) {
		this.isSidebarItem = isSidebarItem;
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
     * @hibernate.property
     *  column="labelid"
     *  type="long"
     */
	public Long getLabelid() {
		return labelid;
	}
	public void setLabelid(Long labelid) {
		this.labelid = labelid;
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
