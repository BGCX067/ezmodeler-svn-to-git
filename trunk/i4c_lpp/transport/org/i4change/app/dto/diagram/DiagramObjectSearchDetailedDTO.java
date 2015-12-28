package org.i4change.app.dto.diagram;

import java.util.List;

import org.i4change.app.dto.user.UserMinimalDTO;

public class DiagramObjectSearchDetailedDTO {
	
	private long diagramObjectId;
	private String name;
	private String comment;
	private String objectTypeName;
	private String inserted;
	private String updated;
	private Boolean ispending;
	private UserMinimalDTO insertedby;
	private UserMinimalDTO updatedby;
	private UserMinimalDTO assignee;
	
	private List<DiagramMinimalDTO> diagram;
	
	private List<DiagramObjectListDTO> connectedObjects;
	private List<DiagramObjectListDTO> connectedOrgObjects;
	
	public long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getInserted() {
		return inserted;
	}
	public void setInserted(String inserted) {
		this.inserted = inserted;
	}
	public Boolean getIspending() {
		return ispending;
	}
	public void setIspending(Boolean ispending) {
		this.ispending = ispending;
	}
	public UserMinimalDTO getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(UserMinimalDTO insertedby) {
		this.insertedby = insertedby;
	}
	public UserMinimalDTO getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(UserMinimalDTO updatedby) {
		this.updatedby = updatedby;
	}
	public UserMinimalDTO getAssignee() {
		return assignee;
	}
	public void setAssignee(UserMinimalDTO assignee) {
		this.assignee = assignee;
	}
	public List<DiagramMinimalDTO> getDiagram() {
		return diagram;
	}
	public void setDiagram(List<DiagramMinimalDTO> diagram) {
		this.diagram = diagram;
	}
	public List<DiagramObjectListDTO> getConnectedObjects() {
		return connectedObjects;
	}
	public void setConnectedObjects(List<DiagramObjectListDTO> connectedObjects) {
		this.connectedObjects = connectedObjects;
	}
	public List<DiagramObjectListDTO> getConnectedOrgObjects() {
		return connectedOrgObjects;
	}
	public void setConnectedOrgObjects(
			List<DiagramObjectListDTO> connectedOrgObjects) {
		this.connectedOrgObjects = connectedOrgObjects;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
}
