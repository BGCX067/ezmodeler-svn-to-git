package org.i4change.app.dto.diagram;

public class DiagramMinimalDTO {
	
	private long diagramId;
	private String name;
	private Long diagramNo;
	private Long diagramType;//Labelid
	private Long revision;
	private String shortName;
	private String project;
	
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
	public Long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}
	public Long getDiagramType() {
		return diagramType;
	}
	public void setDiagramType(Long diagramType) {
		this.diagramType = diagramType;
	}
	public Long getRevision() {
		return revision;
	}
	public void setRevision(Long revision) {
		this.revision = revision;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}

}
