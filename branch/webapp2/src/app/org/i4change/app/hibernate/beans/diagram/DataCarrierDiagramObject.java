package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="datacarrier_diagramobject"
 * lazy="false"
 *
 */
public class DataCarrierDiagramObject implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6315074107056873011L;

	private long dataCarrierDiagramObjectId;
	
	private DiagramObject diagramObject;
	private DiagramObject dataCarrierObjectdiagramObject;
	
	private Long diagramObjectId;
	private Long dataCarrierObjectdiagramObjectId;
	
	private Long insertedBy;
	private Date inserted;
	
	/**
     * 
     * @hibernate.id
     *  column="datacarrier_diagramobject_id"
     *  generator-class="increment"
     */
	public long getDataCarrierDiagramObjectId() {
		return dataCarrierDiagramObjectId;
	}
	public void setDataCarrierDiagramObjectId(long dataCarrierDiagramObjectId) {
		this.dataCarrierDiagramObjectId = dataCarrierDiagramObjectId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public DiagramObject getDiagramObject() {
		return diagramObject;
	}
	public void setDiagramObject(DiagramObject diagramObject) {
		this.diagramObject = diagramObject;
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
	 * @hibernate.many-to-one
	 * column = "datacarrier_object_diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public DiagramObject getDataCarrierObjectdiagramObject() {
		return dataCarrierObjectdiagramObject;
	}
	public void setDataCarrierObjectdiagramObject(DiagramObject dataCarrier) {
		this.dataCarrierObjectdiagramObject = dataCarrier;
	}

	/**
     * @hibernate.property
     *  column="datacarrier_object_diagramobject_id"
     *  type="long"
     */
	public Long getDataCarrierObjectdiagramObjectId() {
		return dataCarrierObjectdiagramObjectId;
	}
	public void setDataCarrierObjectdiagramObjectId(Long dataCarrierObjectdiagramObjectId) {
		this.dataCarrierObjectdiagramObjectId = dataCarrierObjectdiagramObjectId;
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
     *  column="inserted"
     *  type="java.util.Date"
     */
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
}
