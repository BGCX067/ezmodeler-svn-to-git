package org.i4change.app.dto.website;

public class WebItemRelationDTO {
	
	private long webItemRelationId;
	private Long parent_webitem_id;
	private Long child_webitem_id;
	private WebItemMinimalDTO parentItem;
	private WebItemMinimalDTO childItem;
	
	public long getWebItemRelationId() {
		return webItemRelationId;
	}

	public void setWebItemRelationId(long webItemRelationId) {
		this.webItemRelationId = webItemRelationId;
	}
	public Long getParent_webitem_id() {
		return parent_webitem_id;
	}
	public void setParent_webitem_id(Long parent_webitem_id) {
		this.parent_webitem_id = parent_webitem_id;
	}
	public Long getChild_webitem_id() {
		return child_webitem_id;
	}
	public void setChild_webitem_id(Long child_webitem_id) {
		this.child_webitem_id = child_webitem_id;
	}

	public WebItemMinimalDTO getParentItem() {
		return parentItem;
	}

	public void setParentItem(WebItemMinimalDTO parentItem) {
		this.parentItem = parentItem;
	}

	public WebItemMinimalDTO getChildItem() {
		return childItem;
	}

	public void setChildItem(WebItemMinimalDTO childItem) {
		this.childItem = childItem;
	}
	
	
}
