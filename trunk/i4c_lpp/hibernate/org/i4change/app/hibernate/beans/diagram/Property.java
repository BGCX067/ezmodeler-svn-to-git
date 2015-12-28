 package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.domain.Organisation;

/**
 * 
 * @hibernate.class table="property"
 * lazy="false"
 *
 */
public class Property {
	
	private long propertyId;
	//same as help-labelid, they start with 2000
	private long labelid;
	private long toolTip;
	private Boolean isPublic;
	private Organisation organisation;
	private String objectTypeName;
	private String comment;
	private String deleted;
	private Date inserted;
	private Long insertedby;
	private Date updated;
	private Long updatedby;
	private PropertyValidationType propertyValidationType;
	private Long propertyValidationTypeId;
	private String uuid;
	private PropertyCategory propertyCategory;
	
	/**
     * 
     * @hibernate.id
     *  column="property_id"
     *  generator-class="increment"
     */
	public long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}
	
	/**
     * @hibernate.property
     *  column="labelid"
     *  type="long"
     */
	public long getLabelid() {
		return labelid;
	}
	public void setLabelid(long labelid) {
		this.labelid = labelid;
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
     *  column="tooltip_labelid"
     *  type="long"
     */
	public long getToolTip() {
		return toolTip;
	}
	public void setToolTip(long toolTip) {
		this.toolTip = toolTip;
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
	 * @hibernate.many-to-one
	 * column = "property_validation_type_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.PropertyValidationType"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public PropertyValidationType getPropertyValidationType() {
		return propertyValidationType;
	}
	public void setPropertyValidationType(
			PropertyValidationType propertyValidationType) {
		this.propertyValidationType = propertyValidationType;
	}
	
	/**
     * @hibernate.property
     *  column="property_validation_type_id"
     *  type="long"
     */
	public Long getPropertyValidationTypeId() {
		return propertyValidationTypeId;
	}
	public void setPropertyValidationTypeId(Long propertyValidationTypeId) {
		this.propertyValidationTypeId = propertyValidationTypeId;
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
	public Long getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
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
	public Long getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "property_category_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.PropertyCategory"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public PropertyCategory getPropertyCategory() {
		return propertyCategory;
	}
	public void setPropertyCategory(PropertyCategory propertyCategory) {
		this.propertyCategory = propertyCategory;
	}
	
	
}
