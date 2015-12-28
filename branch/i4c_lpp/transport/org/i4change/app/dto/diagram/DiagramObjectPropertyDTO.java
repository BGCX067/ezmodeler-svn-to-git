package org.i4change.app.dto.diagram;

import java.util.Date;

public class DiagramObjectPropertyDTO {

	private long diagramObjectPropertyId;
	private long propertyId;
	private String validationValue;
	private Integer textBoxHeight;
	private long labelid; //This is the Name of the Property as Labelid
	private long toolTip; //This is the Tooltip of the Property as Labelid
	private String value;
	private Date updated;
	private Long updatedBy;
	
	public long getDiagramObjectPropertyId() {
		return diagramObjectPropertyId;
	}
	public void setDiagramObjectPropertyId(long diagramObjectPropertyId) {
		this.diagramObjectPropertyId = diagramObjectPropertyId;
	}
	public String getValidationValue() {
		return validationValue;
	}
	public void setValidationValue(String validationValue) {
		this.validationValue = validationValue;
	}
	public long getLabelid() {
		return labelid;
	}
	public void setLabelid(long labelid) {
		this.labelid = labelid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getTextBoxHeight() {
		return textBoxHeight;
	}
	public void setTextBoxHeight(Integer textBoxHeight) {
		this.textBoxHeight = textBoxHeight;
	}
	public long getToolTip() {
		return toolTip;
	}
	public void setToolTip(long toolTip) {
		this.toolTip = toolTip;
	}
	public long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}
	
}
