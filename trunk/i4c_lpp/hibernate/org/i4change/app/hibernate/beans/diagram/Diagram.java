package org.i4change.app.hibernate.beans.diagram;

// Generated 06.05.2008 11:46:39 by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.i4change.app.hibernate.beans.domain.Organisation;
import org.i4change.app.hibernate.beans.user.Users;

/**
 * 
 * @hibernate.class table="diagram"
 * lazy="false"
 *
 */
public class Diagram implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806721846545680932L;
	private long diagramId;
	private Diagramrevision diagramrevision;
	private String name;
	private String shortName;
	private String description;
	private Users updatedby;
	private Users insertedby;
	private Date updated;
	private Date inserted;
	private Long revisionNumber;
	private String deleted;
	
	//This attribute is not used for the moment, the parent is always a DiagramObject, NOT
	//Another Diagram
	private Long parentDiagramId;
	private Long diagramNo;
	private DiagramType diagramType;
	private Organisation organisation;
	private Boolean canRead;
	private Boolean canWrite;
	private Boolean onlyIssues;
	
	private DiagramObject parentDiagramObject;
	
	private String svgFile;
	private String pngFile;
	
	public Diagram() {
	}

	public Diagram(long diagramId) {
		this.diagramId = diagramId;
	}

	/**
     * 
     * @hibernate.id
     *  column="diagram_id"
     *  generator-class="increment"
     */
	public long getDiagramId() {
		return this.diagramId;
	}

	public void setDiagramId(long diagramId) {
		this.diagramId = diagramId;
	}
	
    /**
	 * @hibernate.many-to-one
	 * column = "diagramrevision_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.Diagramrevision"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public Diagramrevision getDiagramrevision() {
		return this.diagramrevision;
	}

	public void setDiagramrevision(Diagramrevision diagramrevision) {
		this.diagramrevision = diagramrevision;
	}

    /**
     * @hibernate.property
     *  column="name"
     *  type="string"
     */
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    /**
     * @hibernate.property
     *  column="description"
     *  type="string"
     */
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    /**
	 * @hibernate.many-to-one
	 * column = "updatedby"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */		
	public Users getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(Users updatedby) {
		this.updatedby = updatedby;
	}
	
    /**
	 * @hibernate.many-to-one
	 * column = "insertedby"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */			
	public Users getInsertedby() {
		return this.insertedby;
	}

	public void setInsertedby(Users insertedby) {
		this.insertedby = insertedby;
	}
	
    /**
     * @hibernate.property
     *  column="updated"
     *  type="java.util.Date"
     */
	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
    /**
     * @hibernate.property
     *  column="inserted"
     *  type="java.util.Date"
     */
	public Date getInserted() {
		return this.inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	
    /**
     * @hibernate.property
     *  column="revisionNumber"
     *  type="long"
     */
	public Long getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(Long revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	
    /**
     * @hibernate.property
     *  column="deleted"
     *  type="string"
     */	
	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
    /**
     * @hibernate.property
     *  column="parentdiagram_id"
     *  type="long"
     */
	public Long getParentDiagramId() {
		return this.parentDiagramId;
	}

	public void setParentDiagramId(Long parentDiagramId) {
		this.parentDiagramId = parentDiagramId;
	}
	
    /**
     * @hibernate.property
     *  column="diagram_no"
     *  type="long"
     */
	public Long getDiagramNo() {
		return this.diagramNo;
	}

	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}

	/**
	 * @hibernate.many-to-one
	 * column = "diagramtype_id"
	 * class = "org.i4change.app.hibernate.beans.diagram.DiagramType"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public DiagramType getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(DiagramType diagramType) {
		this.diagramType = diagramType;
	}

    /**
	 * @hibernate.many-to-one
	 * column = "organisation_id"
	 * class = "org.i4change.app.hibernate.beans.domain.Organisation"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */		
	public Organisation getOrganisation() {
		return organisation;
	}
	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

	/**
     * @hibernate.property
     *  column="canRead"
     *  type="boolean"
     */
	public Boolean getCanRead() {
		return canRead;
	}
	public void setCanRead(Boolean canRead) {
		this.canRead = canRead;
	}

	/**
     * @hibernate.property
     *  column="canWrite"
     *  type="boolean"
     */
	public Boolean getCanWrite() {
		return canWrite;
	}
	public void setCanWrite(Boolean canWrite) {
		this.canWrite = canWrite;
	}

	/**
     * @hibernate.property
     *  column="onlyissues"
     *  type="boolean"
     */
	public Boolean getOnlyIssues() {
		return onlyIssues;
	}
	public void setOnlyIssues(Boolean onlyIssues) {
		this.onlyIssues = onlyIssues;
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
	public DiagramObject getParentDiagramObject() {
		return parentDiagramObject;
	}
	public void setParentDiagramObject(DiagramObject parentDiagramObject) {
		this.parentDiagramObject = parentDiagramObject;
	}

	/**
     * @hibernate.property
     *  column="svgfile"
     *  type="boolean"
     */
	public String getSvgFile() {
		return svgFile;
	}
	public void setSvgFile(String svgFile) {
		this.svgFile = svgFile;
	}

	/**
     * @hibernate.property
     *  column="pngfile"
     *  type="boolean"
     */
	public String getPngFile() {
		return pngFile;
	}
	public void setPngFile(String pngFile) {
		this.pngFile = pngFile;
	}

	/**
     * @hibernate.property
     *  column="shortname"
     *  type="string"
     */
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
