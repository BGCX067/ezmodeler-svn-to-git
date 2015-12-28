package org.i4change.app.hibernate.beans.user;

import java.util.Date;
import java.util.Map;

import org.i4change.app.hibernate.beans.diagram.Diagram;

/**
 * 
 * @hibernate.class table="usersidebarproperty"
 * lazy="false"
 *
 */
public class UserSidebarProperty {
	
	private long userSidebarPropertyId;
	private Users users;
	private Long diagramNo;
	
	//private String propKey;

	private Date inserted;
	private Date updated;
	
	private String currentOpenitem;
	private Double currentMaxWidth;
	private Double diagramWidth;
	private Double diagramY;
	private Boolean isOpen;
	private Double diagramX;
	private Integer currentZoom;
	private Double diagramHeight;
	
	private Boolean saveItemStatus;
	private Boolean showPreNextDiagramsOnFlows;
	
	//Not Mapped cause it is stored as serialized XML in propKey
	//private Map propMap;
	
	/**
     * 
     * @hibernate.id
     *  column="usersidebarproperty_id"
     *  generator-class="increment"
     */
	public long getUserSidebarPropertyId() {
		return userSidebarPropertyId;
	}
	public void setUserSidebarPropertyId(long userSidebarPropertyId) {
		this.userSidebarPropertyId = userSidebarPropertyId;
	}
	
	/**
	 * @hibernate.many-to-one
	 * column = "user_id"
	 * class = "org.i4change.app.hibernate.beans.user.Users"
	 * insert="true"
	 * update="true"
	 * outer-join="true"
	 * lazy="false"
     */
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
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
 
//	public String getPropKey() {
//		return propKey;
//	}
//	public void setPropKey(String propKey) {
//		this.propKey = propKey;
//	}
	
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
     * @hibernate.property
     *  column="currentOpenitem"
     *  type="string"
     */ 
	public String getCurrentOpenitem() {
		return currentOpenitem;
	}
	public void setCurrentOpenitem(String currentOpenitem) {
		this.currentOpenitem = currentOpenitem;
	}
	
	/**
     * @hibernate.property
     *  column="currentMaxWidth"
     *  type="double"
     */ 
	public Double getCurrentMaxWidth() {
		return currentMaxWidth;
	}
	public void setCurrentMaxWidth(Double currentMaxWidth) {
		this.currentMaxWidth = currentMaxWidth;
	}
	
	/**
     * @hibernate.property
     *  column="diagramWidth"
     *  type="double"
     */
	public Double getDiagramWidth() {
		return diagramWidth;
	}
	public void setDiagramWidth(Double diagramWidth) {
		this.diagramWidth = diagramWidth;
	}
	
	/**
     * @hibernate.property
     *  column="diagramY"
     *  type="double"
     */
	public Double getDiagramY() {
		return diagramY;
	}
	public void setDiagramY(Double diagramY) {
		this.diagramY = diagramY;
	}
	
	/**
     * @hibernate.property
     *  column="isOpen"
     *  type="boolean"
     */
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	/**
     * @hibernate.property
     *  column="diagramX"
     *  type="double"
     */
	public Double getDiagramX() {
		return diagramX;
	}
	public void setDiagramX(Double diagramX) {
		this.diagramX = diagramX;
	}
	
	/**
     * @hibernate.property
     *  column="currentZoom"
     *  type="int"
     */
	public Integer getCurrentZoom() {
		return currentZoom;
	}
	public void setCurrentZoom(Integer currentZoom) {
		this.currentZoom = currentZoom;
	}
	
	/**
     * @hibernate.property
     *  column="diagramHeight"
     *  type="double"
     */
	public Double getDiagramHeight() {
		return diagramHeight;
	}
	public void setDiagramHeight(Double diagramHeight) {
		this.diagramHeight = diagramHeight;
	}
	
	/**
     * @hibernate.property
     *  column="saveitemstatus"
     *  type="boolean"
     */
	public Boolean getSaveItemStatus() {
		return saveItemStatus;
	}
	public void setSaveItemStatus(Boolean saveItemStatus) {
		this.saveItemStatus = saveItemStatus;
	}
	
	/**
     * @hibernate.property
     *  column="showprenextdiagramsonflows"
     *  type="boolean"
     */
	public Boolean getShowPreNextDiagramsOnFlows() {
		return showPreNextDiagramsOnFlows;
	}
	public void setShowPreNextDiagramsOnFlows(Boolean showPreNextDiagramsOnFlows) {
		this.showPreNextDiagramsOnFlows = showPreNextDiagramsOnFlows;
	}
	
	
//	public Map getPropMap() {
//		return propMap;
//	}
//	public void setPropMap(Map propMap) {
//		this.propMap = propMap;
//	}
	
}
