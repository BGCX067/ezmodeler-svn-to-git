package org.i4change.app.dto.property;

import java.util.Date;

public class PropertyValidationTypeDTO {
	
	private long propertyValidationTypeId;
	private Long labelId;
	private String validationValue;
	
	private Date inserted;
	private Long insertedBy;
	
	private Integer height;

	public long getPropertyValidationTypeId() {
		return propertyValidationTypeId;
	}

	public void setPropertyValidationTypeId(long propertyValidationTypeId) {
		this.propertyValidationTypeId = propertyValidationTypeId;
	}

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getValidationValue() {
		return validationValue;
	}

	public void setValidationValue(String validationValue) {
		this.validationValue = validationValue;
	}

	public Date getInserted() {
		return inserted;
	}

	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}

	public Long getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(Long insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
}
