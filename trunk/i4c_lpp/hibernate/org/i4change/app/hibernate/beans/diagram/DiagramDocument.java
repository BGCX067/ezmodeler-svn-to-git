package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="diagram_document"
 * lazy="false"
 *
 */
public class DiagramDocument {
	
	private long diagramDocumentId;
	private String url;
	private Boolean isInternal;
	private String fileName;
	private String fileIconName;
	private String fileHash;
	private Long diagramObjectId;
	
	private String deleted;
	private Date inserted;
	private Long insertedBy;
	private Date updated;
	private Long updatedBy;
	
	/**
     * 
     * @hibernate.id
     *  column="diagram_document_id"
     *  generator-class="increment"
     */ 
	public long getDiagramDocumentId() {
		return diagramDocumentId;
	}
	public void setDiagramDocumentId(long diagramDocumentId) {
		this.diagramDocumentId = diagramDocumentId;
	}
	
    /**
     * @hibernate.property
     *  column="url"
     *  type="string"
     */	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
    /**
     * @hibernate.property
     *  column="is_internal"
     *  type="boolean"
     */	
	public Boolean getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}
	
    /**
     * @hibernate.property
     *  column="file_name"
     *  type="string"
     */	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
    /**
     * @hibernate.property
     *  column="file_icon_name"
     *  type="string"
     */		
	public String getFileIconName() {
		return fileIconName;
	}
	public void setFileIconName(String fileIconName) {
		this.fileIconName = fileIconName;
	}
	
    /**
     * @hibernate.property
     *  column="filehash"
     *  type="string"
     */	
	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	
    /**
     * @hibernate.property
     *  column="diagram_object_id"
     *  type="long"
     */	
	public Long getDiagramObjectId() {
		return diagramObjectId;
	}
	public void setDiagramObjectId(Long diagramObjectId) {
		this.diagramObjectId = diagramObjectId;
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
     *  column="insertedby"
     *  type="long"
     */	
	public Long getInsertedBy() {
		return insertedBy;
	}
	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
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
     * @hibernate.property
     *  column="updatedby"
     *  type="long"
     */
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
