package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;
import java.util.Vector;

/**
 * 
 * @hibernate.class table="diagraminstanceobject"
 * lazy="false"
 *
 */

public class DiagramInstanceObject implements java.io.Serializable {

	private static final long serialVersionUID = -2997008286625096896L;
	private Long diagramInstanceObjectId;
	private Diagram diagram;
	
	//If this is null then this Object is a Connector between others
	private DiagramObject diagramObject;
	private Date inserted;
	private Long insertedby;
	
	private DiagramObject startDiagramObject;
	private DiagramObject endDiagramObject;
	
	private Long startDiagramObjectId;
	private Long endDiagramObjectId;
	
	//An UID for identifing the UI-Object
	private String objInternalUID;
	
	private String flowType;
	
	//An xml String with the necessary Graph information Object
	private String graphObject;
	
	@SuppressWarnings("unchecked")
	private Vector graphAsObject;
	
	public DiagramInstanceObject() {
		super();
	}

	public DiagramInstanceObject(Long diagramInstanceObjectId) {
		super();
		this.diagramInstanceObjectId = diagramInstanceObjectId;
	}



	/**
     * 
     * @hibernate.id
     *  column="diagraminstanceobject_id"
     *  generator-class="increment"
     */
	public Long getDiagramInstanceObjectId() {
		return diagramInstanceObjectId;
	}
	public void setDiagramInstanceObjectId(Long diagramInstanceObjectId) {
		this.diagramInstanceObjectId = diagramInstanceObjectId;
	}	
	
	
    /**
	 * @hibernate.many-to-one
	 * column = "diagram_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Diagram"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Diagram getDiagram() {
		return this.diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
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

	/**
	 * @hibernate.many-to-one
	 * column = "startdiagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public DiagramObject getStartDiagramObject() {
		return startDiagramObject;
	}
	public void setStartDiagramObject(DiagramObject startDiagramObject) {
		this.startDiagramObject = startDiagramObject;
	}
	
	/**
     * @hibernate.property
     *  column="startdiagramobject_id"
     *  type="long"
     */
	public Long getStartDiagramObjectId() {
		return startDiagramObjectId;
	}
	public void setStartDiagramObjectId(Long startDiagramObjectId) {
		this.startDiagramObjectId = startDiagramObjectId;
	}

	/**
	 * @hibernate.many-to-one
	 * column = "enddiagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */
	public DiagramObject getEndDiagramObject() {
		return endDiagramObject;
	}
	public void setEndDiagramObject(DiagramObject endDiagramObject) {
		this.endDiagramObject = endDiagramObject;
	}
	
	/**
     * @hibernate.property
     *  column="enddiagramobject_id"
     *  type="long"
     */
	public Long getEndDiagramObjectId() {
		return endDiagramObjectId;
	}
	public void setEndDiagramObjectId(Long endDiagramObjectId) {
		this.endDiagramObjectId = endDiagramObjectId;
	}

	/**
     * @hibernate.property
     *  column="graphObject"
     *  type="text"
     */
	public String getGraphObject() {
		return graphObject;
	}
	public void setGraphObject(String graphObject) {
		this.graphObject = graphObject;
	}

	/**
     * @hibernate.property
     *  column="obj_internal_uid"
     *  type="text"
     */
	public String getObjInternalUID() {
		return objInternalUID;
	}
	public void setObjInternalUID(String objInternalUID) {
		this.objInternalUID = objInternalUID;
	}
	
	/**
     * @hibernate.property
     *  column="flowtype"
     *  type="string"
     */
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public Vector getGraphAsObject() {
		return graphAsObject;
	}
	public void setGraphAsObject(Vector graphAsObject) {
		this.graphAsObject = graphAsObject;
	}

}
