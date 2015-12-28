package org.i4change.app.hibernate.beans.basic;

import java.util.Date;

/**
 * 
 * @hibernate.class table="objectidentifier"
 *
 */
public class ObjectIdentifier {
	
	private Long objectidentifier_id;
	private Long organization_id;
	private Long currentid;
	private Date updated;
	
	/**
     * @hibernate.id
     *  column="objectidentifier_id"
     *  generator-class="increment"
     */ 
    public Long getObjectidentifier_id() {
		return objectidentifier_id;
	}
	public void setObjectidentifier_id(Long objectidentifier_id) {
		this.objectidentifier_id = objectidentifier_id;
	}

	/**
     * @hibernate.property
     *  column="organization_id"
     *  type="long"
     */ 	
	public Long getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	/**
     * @hibernate.property
     *  column="currentid"
     *  type="long"
     */ 	
	public Long getCurrentid() {
		return currentid;
	}
	public void setCurrentid(Long currentid) {
		this.currentid = currentid;
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
