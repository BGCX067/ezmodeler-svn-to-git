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
	private String propKey;

	private Date inserted;
	private Date updated;
	
	//Not Mapped cause it is stored as serialized XML in propKey
	private Map propMap;
	
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
	
	/**
     * @hibernate.property
     *  column="propKey"
     *  type="text"
     */ 
	public String getPropKey() {
		return propKey;
	}
	public void setPropKey(String propKey) {
		this.propKey = propKey;
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
	
	public Map getPropMap() {
		return propMap;
	}
	public void setPropMap(Map propMap) {
		this.propMap = propMap;
	}
	
}
