package org.i4change.app.hibernate.beans.project;

import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;

/**
 * 
 * @hibernate.class table="project_relation"
 *
 */
public class ProjectRelation {

	private long projectRelationId;
	private Long projectId;
	private DiagramObject diagramObject;
	private Long diagramNo;
	
	/**
     * 
     * @hibernate.id
     *  column="project_relation_id"
     *  generator-class="increment"
     */ 
	public long getProjectRelationId() {
		return projectRelationId;
	}
	public void setProjectRelationId(long projectRelationId) {
		this.projectRelationId = projectRelationId;
	}

	/**
     * @hibernate.property
     *  column="project_id"
     *  type="long"
     */
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @hibernate.many-to-one
	 * column = "diagramobject_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramObject"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public DiagramObject getDiagramObject() {
		return diagramObject;
	}
	public void setDiagramObject(DiagramObject diagramObject) {
		this.diagramObject = diagramObject;
	}
	
	/**
     * @hibernate.property
     *  column="diagramNo"
     *  type="long"
     */
	public Long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}
	
}
