package org.i4change.app.dto.website;

import java.util.List;

public class WebItemDTO {
	
	private long webItemId;
	private WebItemTypeDTO webItemType;
	private String webItemName;
	private String webItemImagepath;
	private String webItemText;
	private Integer position;
	private String videoURL;
	private Boolean isRoot;
	private Boolean changeOnlyNeeded;
	private String userDefiniedType;
	
	List<WebItemRelationDTO> parentItems;
	List<WebItemRelationDTO> childItems;
	
	private List<WebItemDTO> childs;
	
	private Boolean isMasterRoot;
	private String webItemLayout;
	private Boolean isHelpItem;
	private Integer orderInt;
	
	public long getWebItemId() {
		return webItemId;
	}
	public void setWebItemId(long webItemId) {
		this.webItemId = webItemId;
	}
	public String getWebItemName() {
		return webItemName;
	}
	public void setWebItemName(String webItemName) {
		this.webItemName = webItemName;
	}
	public String getWebItemImagepath() {
		return webItemImagepath;
	}
	public void setWebItemImagepath(String webItemImagepath) {
		this.webItemImagepath = webItemImagepath;
	}
	public String getWebItemText() {
		return webItemText;
	}
	public void setWebItemText(String webItemText) {
		this.webItemText = webItemText;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	public String getUserDefiniedType() {
		return userDefiniedType;
	}
	public void setUserDefiniedType(String userDefiniedType) {
		this.userDefiniedType = userDefiniedType;
	}
	public Boolean getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(Boolean isRoot) {
		this.isRoot = isRoot;
	}
	public Boolean getChangeOnlyNeeded() {
		return changeOnlyNeeded;
	}
	public void setChangeOnlyNeeded(Boolean changeOnlyNeeded) {
		this.changeOnlyNeeded = changeOnlyNeeded;
	}
	public List<WebItemRelationDTO> getParentItems() {
		return parentItems;
	}
	public void setParentItems(List<WebItemRelationDTO> parentItems) {
		this.parentItems = parentItems;
	}
	public List<WebItemRelationDTO> getChildItems() {
		return childItems;
	}
	public void setChildItems(List<WebItemRelationDTO> childItems) {
		this.childItems = childItems;
	}
	public WebItemTypeDTO getWebItemType() {
		return webItemType;
	}
	public void setWebItemType(WebItemTypeDTO webItemType) {
		this.webItemType = webItemType;
	}
	public Boolean getIsMasterRoot() {
		return isMasterRoot;
	}
	public void setIsMasterRoot(Boolean isMasterRoot) {
		this.isMasterRoot = isMasterRoot;
	}
	public String getWebItemLayout() {
		return webItemLayout;
	}
	public void setWebItemLayout(String webItemLayout) {
		this.webItemLayout = webItemLayout;
	}
	public List<WebItemDTO> getChilds() {
		return childs;
	}
	public void setChilds(List<WebItemDTO> childs) {
		this.childs = childs;
	}
	public Boolean getIsHelpItem() {
		return isHelpItem;
	}
	public void setIsHelpItem(Boolean isHelpItem) {
		this.isHelpItem = isHelpItem;
	}
	public Integer getOrderInt() {
		return orderInt;
	}
	public void setOrderInt(Integer orderInt) {
		this.orderInt = orderInt;
	}
	
}
