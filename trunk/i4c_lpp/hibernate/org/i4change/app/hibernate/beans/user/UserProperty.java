package org.i4change.app.hibernate.beans.user;

import java.util.Date;

/**
 * 
 * @hibernate.class table="userproperty"
 * lazy="false"
 *
 */
public class UserProperty {

	private long userPropertyId;
	private Long user_id;
	private String keyItem;
	private String value;
	private Date inserted;
	
    /**
     * 
     * @hibernate.id
     *  column="userproperty_id"
     *  generator-class="increment"
     */ 
	public long getUserPropertyId() {
		return userPropertyId;
	}
	public void setUserPropertyId(long userPropertyId) {
		this.userPropertyId = userPropertyId;
	}
	
	/**
     * @hibernate.property
     *  column="user_id"
     *  type="long"
     */
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	/**
     * @hibernate.property
     *  column="keyItem"
     *  type="string"
     */
	public String getKeyItem() {
		return keyItem;
	}
	public void setKeyItem(String key) {
		this.keyItem = key;
	}
	
	/**
     * @hibernate.property
     *  column="value"
     *  type="text"
     */ 
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	
}
