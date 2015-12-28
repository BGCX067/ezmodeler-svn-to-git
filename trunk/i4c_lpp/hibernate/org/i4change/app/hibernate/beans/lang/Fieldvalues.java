package org.i4change.app.hibernate.beans.lang;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @hibernate.class table="fieldvalues"
 *
 */
public class Fieldvalues {

	private Long fieldvalues_id;
	private Long label_number;
	private String name;
	private Date starttime;
	private Date updatetime;
	private String deleted;
//	
//	private Set fieldlanguagesvalues;
	
	private Fieldlanguagesvalues fieldlanguagesvalue;
	
    public Fieldvalues() {
		super();
	}

    /**
     * 
     * @hibernate.id
     *  column="fieldvalues_id"
     *  generator-class="increment"
     */ 
	public Long getFieldvalues_id() {
		return fieldvalues_id;
	}
	public void setFieldvalues_id(Long fieldvalues_id) {
		this.fieldvalues_id = fieldvalues_id;
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
     *  column="name"
     *  type="string"
     */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public Fieldlanguagesvalues getFieldlanguagesvalue() {
		return fieldlanguagesvalue;
	}
	public void setFieldlanguagesvalue(Fieldlanguagesvalues fieldlanguagesvalue) {
		this.fieldlanguagesvalue = fieldlanguagesvalue;
	}

}
