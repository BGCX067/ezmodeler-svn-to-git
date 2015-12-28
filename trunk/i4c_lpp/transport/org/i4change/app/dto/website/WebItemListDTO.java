package org.i4change.app.dto.website;

import java.util.List;

public class WebItemListDTO {
	
	private long webItemId;
	private WebItemTypeDTO webItemType;
	private String webItemName;
	private Boolean isHelpItem;
	private Boolean hasChildItems;
	
	private List<WebItemListDTO> childItems;
	
	public long getWebItemId() {
		return webItemId;
	}
	public void setWebItemId(long webItemId) {
		this.webItemId = webItemId;
	}
	public WebItemTypeDTO getWebItemType() {
		return webItemType;
	}
	public void setWebItemType(WebItemTypeDTO webItemType) {
		this.webItemType = webItemType;
	}
	public String getWebItemName() {
		return webItemName;
	}
	public void setWebItemName(String webItemName) {
		this.webItemName = webItemName;
	}
	public Boolean getIsHelpItem() {
		return isHelpItem;
	}
	public void setIsHelpItem(Boolean isHelpItem) {
		this.isHelpItem = isHelpItem;
	}
	public Boolean getHasChildItems() {
		return hasChildItems;
	}
	public void setHasChildItems(Boolean hasChildItems) {
		this.hasChildItems = hasChildItems;
	}
	public List<WebItemListDTO> getChildItems() {
		return childItems;
	}
	public void setChildItems(List<WebItemListDTO> childItems) {
		this.childItems = childItems;
	}

}
