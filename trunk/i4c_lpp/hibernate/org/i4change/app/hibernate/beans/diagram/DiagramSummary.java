package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="diagramsummary"
 * lazy="false"
 *
 */
public class DiagramSummary {
	
	private long diagramSummaryId;
	private Diagram diagram;
	private long diagramNo;
	private Date inserted;
	private Date updated;
	private String deleted;
	
	/**
     * 
     * @hibernate.id
     *  column="diagramsummary_id"
     *  generator-class="increment"
     */
	public long getDiagramSummaryId() {
		return diagramSummaryId;
	}
	public void setDiagramSummaryId(long diagramSummaryId) {
		this.diagramSummaryId = diagramSummaryId;
	}
	
    /**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */	
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
    /**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */	
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "diagram_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Diagram"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Diagram getDiagram() {
		return diagram;
	}
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
	
	/**
     * @hibernate.property
     *  column="deleted"
     *  type="string"
     */	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	/**
     * @hibernate.property
     *  column="diagramNo"
     *  type="long"
     */	
	public long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(long diagramNo) {
		this.diagramNo = diagramNo;
	}
	
}
