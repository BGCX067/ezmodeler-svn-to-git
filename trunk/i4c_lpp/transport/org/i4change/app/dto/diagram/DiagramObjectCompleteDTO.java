package org.i4change.app.dto.diagram;

import java.util.List;
import java.util.Set;

import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramDocument;

public class DiagramObjectCompleteDTO extends DiagramObjectListDTO {
	
	private String comment;
	private List<DiagramDocument> diagramDocuments;
	private List<DataCarrierDiagramObjectDTO> dataCarrierDiagramObject;

	//Properties are loaded separated, so no need to include them here
	
	public List<DataCarrierDiagramObjectDTO> getDataCarrierDiagramObject() {
		return dataCarrierDiagramObject;
	}
	public void setDataCarrierDiagramObject(
			List<DataCarrierDiagramObjectDTO> dataCarrierDiagramObject) {
		this.dataCarrierDiagramObject = dataCarrierDiagramObject;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<DiagramDocument> getDiagramDocuments() {
		return diagramDocuments;
	}
	public void setDiagramDocuments(List<DiagramDocument> diagramDocuments) {
		this.diagramDocuments = diagramDocuments;
	}
	
}
