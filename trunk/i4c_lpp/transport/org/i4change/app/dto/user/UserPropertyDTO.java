package org.i4change.app.dto.user;

public class UserPropertyDTO {
	
	private Long userPropertyId;
	private String keyItem;
	private Object value;
	
	public Long getUserPropertyId() {
		return userPropertyId;
	}
	public void setUserPropertyId(Long userPropertyId) {
		this.userPropertyId = userPropertyId;
	}
	public String getKeyItem() {
		return keyItem;
	}
	public void setKeyItem(String keyItem) {
		this.keyItem = keyItem;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
