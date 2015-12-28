package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="property_validation_type"
 * lazy="false"
 *
 */
public class PropertyValidationType {

	private long propertyValidationTypeId;
	private Long labelId;
	private String validationValue;
	
	private Date inserted;
	private Long insertedBy;
	
	private Integer height;
	
	/**
     * 
     * @hibernate.id
     *  column="property_validation_type_id"
     *  generator-class="increment"
     */
	public long getPropertyValidationTypeId() {
		return propertyValidationTypeId;
	}
	public void setPropertyValidationTypeId(long propertyValidationTypeId) {
		this.propertyValidationTypeId = propertyValidationTypeId;
	}
	
	/**
     * @hibernate.property
     *  column="labelid"
     *  type="long"
     */
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	
	/**
     * @hibernate.property
     *  column="validation_value"
     *  type="string"
     */
	public String getValidationValue() {
		return validationValue;
	}
	public void setValidationValue(String validationValue) {
		this.validationValue = validationValue;
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
     *  column="height"
     *  type="int"
     */
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	
}
