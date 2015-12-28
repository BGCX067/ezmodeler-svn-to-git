package org.i4change.app.dto.diagram;

import java.util.List;

public class FlowConnectedDiagramDTO {
	
	private long diagramObjectId;
	private List<DiagramSearchDTO> diagrams;
	
	public long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
	}
	public List<DiagramSearchDTO> getDiagrams() {
		return diagrams;
	}
	public void setDiagrams(List<DiagramSearchDTO> diagrams) {
		this.diagrams = diagrams;
	}

}
