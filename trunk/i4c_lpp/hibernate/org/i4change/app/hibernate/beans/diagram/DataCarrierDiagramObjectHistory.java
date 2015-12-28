package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="datacarrier_diagramobject_history"
 * lazy="false"
 *
 */
public class DataCarrierDiagramObjectHistory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7502078276783011376L;

	private long dataCarrierDiagramObjectHistoryId;
	
	//private DiagramObject diagramObject;
	//this is the actuall Data-Carrier (or any type of Object's) diagramObject-Id
	//that gets linked
	private DiagramObject dataCarrierObjectdiagramObject;
	
	private Long diagramId;
	//this is the diagramObject-Id of the Shape
	private Long diagramObjectId;
	private Long dataCarrierObjectdiagramObjectId;
	
	private Long insertedBy;
	private Date inserted;
	
	
	/**
     * 
     * @hibernate.id
     *  column="datacarrier_diagramobject_history_id"
     *  generator-class="increment"
     */
	public long getDataCarrierDiagramObjectHistoryId() {
		return dataCarrierDiagramObjectHistoryId;
	}
	public void setDataCarrierDiagramObjectHistoryId(
			long dataCarrierDiagramObjectHistoryId) {
		this.dataCarrierDiagramObjectHistoryId = dataCarrierDiagramObjectHistoryId;
	}
	
//	/**
//	 * @hibernate.many-to-one
//	 * column = "diagramobject_id"
//	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
//	 * insert="false"
//	 * update="false"
//	 * outer-join="true"
//	 * lazy="false"
//     */
//	public DiagramObject getDiagramObject() {
//		return diagramObject;
//	}
//	public void setDiagramObject(DiagramObject diagramObject) {
//		this.diagramObject = diagramObject;
//	}

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
	
	/**
     * @hibernate.property
     *  column="diagramId"
     *  type="long"
     */
	public Long getDiagramId() {
		return diagramId;
	}
	public void setDiagramId(Long diagramId) {
		this.diagramId = diagramId;
	}
	
	
}
