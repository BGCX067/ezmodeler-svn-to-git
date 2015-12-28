package org.i4change.app.dto.diagram;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.i4change.app.hibernate.beans.diagram.DiagramDocument;

public class DiagramInstanceObjectDTO {

	private Long diagramInstanceObjectId;
	private Vector graphAsObject;
	private Map addInfos;
	private List<DiagramDocument> diagramDocuments;
	
	public Long getDiagramInstanceObjectId() {
		return diagramInstanceObjectId;
	}
	public void setDiagramInstanceObjectId(Long diagramInstanceObjectId) {
		this.diagramInstanceObjectId = diagramInstanceObjectId;
	}
	public Vector getGraphAsObject() {
		return graphAsObject;
	}
	public void setGraphAsObject(Vector graphAsObject) {
		this.graphAsObject = graphAsObject;
	}
	
	public Map getAddInfos() {
		return addInfos;
	}
	public void setAddInfos(Map addInfos) {
		this.addInfos = addInfos;
	}
	
	public List<DiagramDocument> getDiagramDocuments() {
		return diagramDocuments;
	}
	public void setDiagramDocuments(List<DiagramDocument> diagramDocuments) {
		this.diagramDocuments = diagramDocuments;
	}
	
}
