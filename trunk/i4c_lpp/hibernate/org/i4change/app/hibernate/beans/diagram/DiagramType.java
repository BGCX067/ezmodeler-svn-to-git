package org.i4change.app.hibernate.beans.diagram;

import java.util.Date;

import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

/**
 * 
 * @hibernate.class table="diagramtype"
 * lazy="false"
 *
 */
public class DiagramType {
	
	private Long diagramTypeId;
	private Date inserted;
	private Long fieldId;
	private String internalName;
	
	private Fieldlanguagesvalues label;
	
	/**
     * 
     * @hibernate.id
     *  column="diagramtype_id"
     *  generator-class="increment"
     */
	public Long getDiagramTypeId() {
		return diagramTypeId;
	}
	public void setDiagramTypeId(Long diagramTypeId) {
		this.diagramTypeId = diagramTypeId;
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
     *  column="field_id"
     *  type="long"
     */
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	
	public Fieldlanguagesvalues getLabel() {
		return label;
	}
	public void setLabel(Fieldlanguagesvalues label) {
		this.label = label;
	}
	
	/**
     * @hibernate.property
     *  column="internalname"
     *  type="string"
     */
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	
	

}
