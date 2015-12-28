package org.i4change.app.hibernate.beans.basic;

import java.util.Date;

import org.i4change.app.hibernate.beans.lang.Fieldvalues;

/**
 * 
 * @hibernate.class table="errortypes"
 *
 */
public class ErrorType {
	
	private Long errortype_id;
	private Long label_number;
	private Fieldvalues fieldvalues;
	private Date starttime;
	private Date updatetime;
	private String deleted;
	
    /**
     * 
     * @hibernate.id
     *  column="errortype_id"
     *  generator-class="increment"
     */ 
	public Long getErrortype_id() {
		return errortype_id;
	}
	public void setErrortype_id(Long errortype_id) {
		this.errortype_id = errortype_id;
	}
	
	/**
     * @hibernate.property
     *  column="starttime"
     *  type="java.util.Date"
     */  	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
    
    /**
     * @hibernate.property
     *  column="updatetime"
     *  type="java.util.Date"
     */  	
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
     *  column="label_number"
     *  type="long"
     */  
	public Long getLabel_number() {
		return label_number;
	}
	public void setLabel_number(Long label_number) {
		this.label_number = label_number;
	}


}
