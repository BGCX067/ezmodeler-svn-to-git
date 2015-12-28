package org.i4change.app.hibernate.beans.lang;

import java.util.Date;

/**
 * 
 * @hibernate.class table="fieldlanguagesvalues"
 *
 */
public class Fieldlanguagesvalues {

	private Long fieldlanguagesvalues_id;
	private Long label_number;
	private Long language_id;
	private Date starttime;
	private Date updatetime;
	private String deleted;
	private String value;
	
	public Fieldlanguagesvalues() {
		super();
	}
	
    /**
     * 
     * @hibernate.id
     *  column="fieldlanguagesvalues_id"
     *  generator-class="increment"
     */ 
	public Long getFieldlanguagesvalues_id() {
		return fieldlanguagesvalues_id;
	}
	public void setFieldlanguagesvalues_id(Long fieldlanguagesvalues_id) {
		this.fieldlanguagesvalues_id = fieldlanguagesvalues_id;
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
     *  not-null="true"
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
     *  not-null="true"
     *  column="language_id"
     *  type="long"
     */
	public Long getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
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
	
}
