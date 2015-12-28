package org.i4change.app.hibernate.beans.website;

import java.util.Date;

/**
 * 
 * @hibernate.class table="webitemrelation"
 * lazy="false"
 *
 */
public class WebItemRelation {

	private long webItemRelationId;
	private WebItem parentItem;
	private Long parent_webitem_id;
	private WebItem childItem;
	private Long child_webitem_id;
	private String deleted;
	private Long insertedBy;
	private Date inserted;
	private Long updatedBy;
	private Date updated;

	/**
     * 
     * @hibernate.id
     *  column="webitemrelation_id"
     *  generator-class="increment"
     */
	public long getWebItemRelationId() {
		return webItemRelationId;
	}
	public void setWebItemRelationId(long webItemRelationId) {
		this.webItemRelationId = webItemRelationId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "parent_webitem_id"
	 * class = "org.i4change.app.hibernate.beans.website.WebItem"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */	
	public WebItem getParentItem() {
		return parentItem;
	}
	public void setParentItem(WebItem parentItem) {
		this.parentItem = parentItem;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "child_webitem_id"
	 * class = "org.i4change.app.hibernate.beans.website.WebItem"
	 * insert="false"
	 * update="false"
	 * outer-join="true"
	 * lazy="false"
     */		
	public WebItem getChildItem() {
		return childItem;
	}
	public void setChildItem(WebItem childItem) {
		this.childItem = childItem;
	}
	
	/**
     * @hibernate.property
     *  column="parent_webitem_id"
     *  type="long"
     */	
	public Long getParent_webitem_id() {
		return parent_webitem_id;
	}
	public void setParent_webitem_id(Long parent_webitem_id) {
		this.parent_webitem_id = parent_webitem_id;
	}
	
	/**
     * @hibernate.property
     *  column="child_webitem_id"
     *  type="long"
     */	
	public Long getChild_webitem_id() {
		return child_webitem_id;
	}
	public void setChild_webitem_id(Long child_webitem_id) {
		this.child_webitem_id = child_webitem_id;
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
