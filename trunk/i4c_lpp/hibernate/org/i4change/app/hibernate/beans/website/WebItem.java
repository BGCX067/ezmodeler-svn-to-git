package org.i4change.app.hibernate.beans.website;

import java.util.Date;
import java.util.List;

/**
 * 
 * @hibernate.class table="webitem"
 * lazy="false"
 *
 */
public class WebItem {
	 
	private long webItemId;
	private WebItemType webItemType;
	private String webItemName;
	private String webItemImagepath;
	private String webItemText;
	
	private String deleted;
	private Long insertedBy;
	private Date inserted;
	private Long updatedBy;
	private Date updated;
	
	private Integer position;
	private String videoURL;
	private String userDefiniedType;
	private Boolean isRoot;
	private Boolean changeOnlyNeeded;
	
	private Integer orderInt;
	
	List<WebItemRelation> parentItems;
	List<WebItemRelation> childItems;
	
	private Boolean isMasterRoot;
	private String webItemLayout;
	
	private Boolean isHelpItem;
	
	List<WebItem> childTreeItems;
	
	/**
     * 
     * @hibernate.id
     *  column="webitem_id"
     *  generator-class="increment"
     */
	public long getWebItemId() {
		return webItemId;
	}
	public void setWebItemId(long webItemId) {
		this.webItemId = webItemId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "webitemtype_id"
	 * class = "org.i4change.app.hibernate.beans.website.WebItemType"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */	
	public WebItemType getWebItemType() {
		return webItemType;
	}
	public void setWebItemType(WebItemType webItemType) {
		this.webItemType = webItemType;
	}
	
	/**
     * @hibernate.property
     *  column="webitemname"
     *  type="string"
     */	
	public String getWebItemName() {
		return webItemName;
	}
	public void setWebItemName(String webItemName) {
		this.webItemName = webItemName;
	}
	
	/**
     * @hibernate.property
     *  column="webitemimagepath"
     *  type="string"
     */		
	public String getWebItemImagepath() {
		return webItemImagepath;
	}
	public void setWebItemImagepath(String webItemImagepath) {
		this.webItemImagepath = webItemImagepath;
	}
	
	/**
     * @hibernate.property
     *  column="webitemtext"
     *  type="text"
     */		
	public String getWebItemText() {
		return webItemText;
	}
	public void setWebItemText(String webItemText) {
		this.webItemText = webItemText;
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

	/**
     * @hibernate.property
     *  column="position"
     *  type="int"
     */	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	/**
     * @hibernate.property
     *  column="isroot"
     *  type="boolean"
     */	
	public Boolean getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}

	/**
     * @hibernate.property
     *  column="videourl"
     *  type="string"
     */	
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	/**
     * @hibernate.property
     *  column="userdefiniedtype"
     *  type="string"
     */	
	public String getUserDefiniedType() {
		return userDefiniedType;
	}
	public void setUserDefiniedType(String userDefiniedType) {
		this.userDefiniedType = userDefiniedType;
	}
	
	/**
     * @hibernate.property
     *  column="changeonlyneeded"
     *  type="boolean"
     */
	public Boolean getChangeOnlyNeeded() {
		return changeOnlyNeeded;
	}
	public void setChangeOnlyNeeded(Boolean changeOnlyNeeded) {
		this.changeOnlyNeeded = changeOnlyNeeded;
	}
	
	/**
     * @hibernate.property
     *  column="orderint"
     *  type="int"
     */
	public Integer getOrderInt() {
		return orderInt;
	}
	public void setOrderInt(Integer orderInt) {
		this.orderInt = orderInt;
	}
	
	public List<WebItemRelation> getParentItems() {
		return parentItems;
	}
	public void setParentItems(List<WebItemRelation> parentItems) {
		this.parentItems = parentItems;
	}
	
	public List<WebItemRelation> getChildItems() {
		return childItems;
	}
	public void setChildItems(List<WebItemRelation> childItems) {
		this.childItems = childItems;
	}
	
	/**
     * @hibernate.property
     *  column="ismasterroot"
     *  type="boolean"
     */
	public Boolean getIsMasterRoot() {
		return isMasterRoot;
	}
	public void setIsMasterRoot(Boolean isMasterRoot) {
		this.isMasterRoot = isMasterRoot;
	}
	
	/**
     * @hibernate.property
     *  column="webitemlayout"
     *  type="string"
     */
	public String getWebItemLayout() {
		return webItemLayout;
	}
	public void setWebItemLayout(String webItemLayout) {
		this.webItemLayout = webItemLayout;
	}
	
	/**
     * @hibernate.property
     *  column="ishelpitem"
     *  type="boolean"
     */
	public Boolean getIsHelpItem() {
		return isHelpItem;
	}
	public void setIsHelpItem(Boolean isHelpItem) {
		this.isHelpItem = isHelpItem;
	}
	
	public List<WebItem> getChildTreeItems() {
		return childTreeItems;
	}
	public void setChildTreeItems(List<WebItem> childTreeItems) {
		this.childTreeItems = childTreeItems;
	}
	
	
}
