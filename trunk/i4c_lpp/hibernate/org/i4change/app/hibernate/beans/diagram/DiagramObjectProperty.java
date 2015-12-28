package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="diagramobject_property"
 * lazy="false"
 *
 */
public class DiagramObjectProperty {

	private long diagramObjectPropertyId;
	private Long propertyId;
	private Property property;
	private Long diagramObjectId;
	private Long diagramNo;
	private String value;
	
	private Date inserted;
	private Long insertedBy;
	private Date updated;
	private Long updatedBy;
	
	/**
     * 
     * @hibernate.id
     *  column="diagramobject_property_id"
     *  generator-class="increment"
     */ 
	public long getDiagramObjectPropertyId() {
		return diagramObjectPropertyId;
	}
	public void setDiagramObjectPropertyId(long diagramObjectPropertyId) {
		this.diagramObjectPropertyId = diagramObjectPropertyId;
	}
	
	/**
     * @hibernate.property
     *  column="property_id"
     *  type="long"
     */
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "property_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Property"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	
	/**
     * @hibernate.property
     *  column="diagramobject_id"
     *  type="long"
     */
	public Long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(Long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	
	/**
     * @hibernate.property
     *  column="diagramNo"
     *  type="long"
     */
	public Long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}
	
	/**
     * @hibernate.property
     *  column="value"
     *  type="text"
     */
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	
	
	
}
