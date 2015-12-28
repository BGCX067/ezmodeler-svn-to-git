package org.i4change.app.dto.diagram;

import java.util.Date;

public class DiagramObjectListDTO {
	
	private long diagramObjectId;
	private String name;
	private String objectTypeName;
	private Date inserted;
	
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
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

}
