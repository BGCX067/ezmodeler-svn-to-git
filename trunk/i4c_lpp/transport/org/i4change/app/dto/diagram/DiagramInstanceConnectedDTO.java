package org.i4change.app.dto.diagram;

import java.util.Date;

public class DiagramInstanceConnectedDTO {
	
	private Long diagramInstanceObjectId;
	private DiagramMinimalDTO diagram;
	private DiagramObjectListDTO diagramObject;
	private String flowType;
	private Date inserted;
	private Long insertedby;
	
	public Long getDiagramInstanceObjectId() {
		return diagramInstanceObjectId;
	}
	public void setDiagramInstanceObjectId(Long diagramInstanceObjectId) {
		this.diagramInstanceObjectId = diagramInstanceObjectId;
	}
	public DiagramMinimalDTO getDiagram() {
		return diagram;
	}
	public void setDiagram(DiagramMinimalDTO diagram) {
		this.diagram = diagram;
	}
	public DiagramObjectListDTO getDiagramObject() {
		return diagramObject;
	}
	public void setDiagramObject(DiagramObjectListDTO diagramObject) {
		this.diagramObject = diagramObject;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public Long getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
	}

}
