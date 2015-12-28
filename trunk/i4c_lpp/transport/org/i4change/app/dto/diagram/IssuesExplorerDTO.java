package org.i4change.app.dto.diagram;

import java.util.Date;

import org.i4change.app.dto.user.UserMinimalDTO;

public class IssuesExplorerDTO {
	
	private long diagramObjectId;
	private String name;
	private String objectTypeName;
	private DiagramMinimalDTO diagram;
	private Date inserted;
	private UserMinimalDTO insertedby;
	private UserMinimalDTO assignee;
	
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
	public DiagramMinimalDTO getDiagram() {
		return diagram;
	}
	public void setDiagram(DiagramMinimalDTO diagram) {
		this.diagram = diagram;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public UserMinimalDTO getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(UserMinimalDTO insertedby) {
		this.insertedby = insertedby;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public UserMinimalDTO getAssignee() {
		return assignee;
	}
	public void setAssignee(UserMinimalDTO assignee) {
		this.assignee = assignee;
	}
	
}
