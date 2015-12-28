package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="diagramobjectorganisation"
 * lazy="false"
 *
 */
public class DiagramObjectOrganisation {
	
	private long diagramobjectorganisationId;
	public DiagramInstanceObject diagramInstanceObject;
	private DiagramObject diagramObject;
	private Date inserted;
	private Long insertedby;
	
	/**
     * 
     * @hibernate.id
     *  column="diagramobjectorganisation_id"
     *  generator-class="increment"
     */
	public long getDiagramobjectorganisationId() {
		return diagramobjectorganisationId;
	}
	public void setDiagramobjectorganisationId(long diagramobjectorganisationId) {
		this.diagramobjectorganisationId = diagramobjectorganisationId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagraminstanceobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public DiagramInstanceObject getDiagramInstanceObject() {
		return diagramInstanceObject;
	}
	public void setDiagramInstanceObject(DiagramInstanceObject diagramInstanceObject) {
		this.diagramInstanceObject = diagramInstanceObject;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="true"
	 * update="true"
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

}
