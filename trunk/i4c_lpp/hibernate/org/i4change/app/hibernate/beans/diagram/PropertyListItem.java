package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

/**
 * 
 * @hibernate.class table="propertylistitem"
 * lazy="false"
 *
 */
public class PropertyListItem {
	
	private long propertyListItemId;
	private Date inserted;
	private Date updated;
	private Long insertedby;
	private String key;
	private String value;
	private String deleted;
	private Long propertyId;
	
	/**
     * 
     * @hibernate.id
     *  column="propertylistitem_id"
     *  generator-class="increment"
     */
	public long getPropertyListItemId() {
		return propertyListItemId;
	}
	public void setPropertyListItemId(long propertyListItemId) {
		this.propertyListItemId = propertyListItemId;
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
	public Long getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(Long insertedby) {
		this.insertedby = insertedby;
	}
	
	/**
     * @hibernate.property
     *  column="key_value"
     *  type="string"
     */
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
     * @hibernate.property
     *  column="value_value"
     *  type="string"
     */
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
     * @hibernate.property
     *  column="property_id"
     *  type="long"
     */
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
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
