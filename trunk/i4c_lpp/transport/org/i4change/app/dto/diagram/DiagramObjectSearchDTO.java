package org.i4change.app.dto.diagram;

import java.util.Date;

public class DiagramObjectSearchDTO {
	
	private long diagramObjectId;
	private String objectTypeName;
	private String name;
	private Boolean pending;
	private Date inserted;
	private Long insertedBy;
	
	public long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getPending() {
		return pending;
	}
	public void setPending(Boolean pending) {
		this.pending = pending;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public Long getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
	}

}
