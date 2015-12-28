package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="diagramobject_propertyhistory"
 * lazy="false"
 *
 */
public class DiagramObjectPropertyHistory {
	
	/**
	 * This class stores ALL Properties of a Diagram at a certain Save Point, so that a user 
	 * can go back in the history and reload that status
	 */
	private long diagramObjectPropertyHistoryId;
	private Long propertyId;
	private Long diagramId;
	private Long diagramObjectId;
	private String value;
	
	private Date inserted;
	private Long insertedBy;

	/**
     * 
     * @hibernate.id
     *  column="diagramobject_propertyhistory_id"
     *  generator-class="increment"
     */ 
	public long getDiagramObjectPropertyHistoryId() {
		return diagramObjectPropertyHistoryId;
	}
	public void setDiagramObjectPropertyHistoryId(
			long diagramObjectPropertyHistoryId) {
		this.diagramObjectPropertyHistoryId = diagramObjectPropertyHistoryId;
	}
	
	/**
     * @hibernate.property
     *  column="diagram_id"
     *  type="long"
     */
	public Long getDiagramId() {
		return diagramId;
	}
	public void setDiagramId(Long diagramId) {
		this.diagramId = diagramId;
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
	
}
