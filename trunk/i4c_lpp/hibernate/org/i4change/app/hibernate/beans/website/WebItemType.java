package org.i4change.app.hibernate.beans.website;

import java.util.Date;

/**
 * 
 * @hibernate.class table="webitemtype"
 * lazy="false"
 *
 */
public class WebItemType {
	 
	private long webItemTypeId;
	private String typeName;
	private Long label_number;
	private String deleted;
	private Long insertedBy;
	private Date inserted;
	private Long updatedBy;
	private Date updated;
	
	/**
     * 
     * @hibernate.id
     *  column="webitemtype_id"
     *  generator-class="increment"
     */
	public long getWebItemTypeId() {
		return webItemTypeId;
	}
	public void setWebItemTypeId(long webItemTypeId) {
		this.webItemTypeId = webItemTypeId;
	}
	
	/**
     * @hibernate.property
     *  column="typename"
     *  type="string"
     */		
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
     * @hibernate.property
     *  column="label_number"
     *  type="long"
     */	
	public Long getLabel_number() {
		return label_number;
	}
	public void setLabel_number(Long label_number) {
		this.label_number = label_number;
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
     *  column="updatedBy"
     *  type="java.util.Date"
     */		
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	
}
