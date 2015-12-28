package org.i4change.app.dto.user;


public class UserSidebarPropertyDTO {
	
	private long userSidebarPropertyId;
	private Long diagramNo;
	
	private String currentOpenitem;
	private Double currentMaxWidth;
	private Double diagramWidth;
	private Double diagramY;
	private Boolean isOpen;
	private Double diagramX;
	private Integer currentZoom;
	private Double diagramHeight;
	private Boolean saveItemStatus;
	private Boolean showPreNextDiagramsOnFlows;
	
	public long getUserSidebarPropertyId() {
		return userSidebarPropertyId;
	}
	public void setUserSidebarPropertyId(long userSidebarPropertyId) {
		this.userSidebarPropertyId = userSidebarPropertyId;
	}
	public Long getDiagramNo() {
		return diagramNo;
	}
	public void setDiagramNo(Long diagramNo) {
		this.diagramNo = diagramNo;
	}
	public String getCurrentOpenitem() {
		return currentOpenitem;
	}
	public void setCurrentOpenitem(String currentOpenitem) {
		this.currentOpenitem = currentOpenitem;
	}
	public Double getCurrentMaxWidth() {
		return currentMaxWidth;
	}
	public void setCurrentMaxWidth(Double currentMaxWidth) {
		this.currentMaxWidth = currentMaxWidth;
	}
	public Double getDiagramWidth() {
		return diagramWidth;
	}
	public void setDiagramWidth(Double diagramWidth) {
		this.diagramWidth = diagramWidth;
	}
	public Double getDiagramY() {
		return diagramY;
	}
	public void setDiagramY(Double diagramY) {
		this.diagramY = diagramY;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public Double getDiagramX() {
		return diagramX;
	}
	public void setDiagramX(Double diagramX) {
		this.diagramX = diagramX;
	}
	public Integer getCurrentZoom() {
		return currentZoom;
	}
	public void setCurrentZoom(Integer currentZoom) {
		this.currentZoom = currentZoom;
	}
	public Double getDiagramHeight() {
		return diagramHeight;
	}
	public void setDiagramHeight(Double diagramHeight) {
		this.diagramHeight = diagramHeight;
	}
	public Boolean getSaveItemStatus() {
		return saveItemStatus;
	}
	public void setSaveItemStatus(Boolean saveItemStatus) {
		this.saveItemStatus = saveItemStatus;
	}
	public Boolean getShowPreNextDiagramsOnFlows() {
		return showPreNextDiagramsOnFlows;
	}
	public void setShowPreNextDiagramsOnFlows(Boolean showPreNextDiagramsOnFlows) {
		this.showPreNextDiagramsOnFlows = showPreNextDiagramsOnFlows;
	}
	
}
