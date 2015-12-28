package org.i4change.app.dto.diagram;

import java.util.Date;

import org.i4change.app.dto.project.ProjectMinimalDTO;
import org.i4change.app.dto.user.UserMinimalDTO;

public class DiagramSearchDTO {

	private long diagramId;
	private String name;
	private Date inserted;
	private UserMinimalDTO insertedby;
	private DiagramrevisionDTO diagramrevision;
	private DiagramTypeDTO diagramType;
	private ProjectMinimalDTO project;
	private Long diagramNo;
	private DiagramObjectSearchDTO parentDiagramObject;
	private String shortName;
	
	private String comment;
	
	public long getDiagramId() {
		return diagramId;
	}
	public void setDiagramId(long diagramId) {
		this.diagramId = diagramId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public DiagramrevisionDTO getDiagramrevision() {
		return diagramrevision;
	}
	public void setDiagramrevision(DiagramrevisionDTO diagramrevision) {
		this.diagramrevision = diagramrevision;
	}
	public DiagramTypeDTO getDiagramType() {
		return diagramType;
	}
	public void setDiagramType(DiagramTypeDTO diagramType) {
		this.diagramType = diagramType;
	}
	public ProjectMinimalDTO getProject() {
		return project;
	}
	public void setProject(ProjectMinimalDTO project) {
		this.project = project;
	}
	public Long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}
	public DiagramObjectSearchDTO getParentDiagramObject() {
		return parentDiagramObject;
	}
	public void setParentDiagramObject(DiagramObjectSearchDTO parentDiagramObject) {
		this.parentDiagramObject = parentDiagramObject;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
