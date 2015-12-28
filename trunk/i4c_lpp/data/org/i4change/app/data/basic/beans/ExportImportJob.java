package org.i4change.app.data.basic.beans;

import java.util.Date;

public class ExportImportJob {
	
	private Long exportJobId;
	private Date inserted;
	private Object printItemList;
	private String diagramName;
	private Long diagramId;
	private Long diagramType;
	private Long pTemplateId;
	
	private Long language_id;
	
	private Boolean includeText;
	private Boolean printIdeas;
	private Boolean includeDetails;
	
	public ExportImportJob(Long exportJobId, Date inserted,
			Object printItemList, String diagramName, Long diagramId,
			Long diagramType, Long templateId, Boolean includeText,
			Boolean printIdeas, Boolean includeDetails, 
			Long language_id) {
		super();
		this.exportJobId = exportJobId;
		this.inserted = inserted;
		this.printItemList = printItemList;
		this.diagramName = diagramName;
		this.diagramId = diagramId;
		this.diagramType = diagramType;
		this.pTemplateId = templateId;
		this.includeText = includeText;
		this.printIdeas = printIdeas;
		this.includeDetails = includeDetails;
		this.language_id = language_id;
	}
	public Boolean getIncludeDetails() {
		return includeDetails;
	}
	public void setIncludeDetails(Boolean includeDetails) {
		this.includeDetails = includeDetails;
	}
	public ExportImportJob(Long exportJobId2) {
		this.exportJobId = exportJobId2;
	}
	public Long getExportJobId() {
		return exportJobId;
	}
	public void setExportJobId(Long exportJobId) {
		this.exportJobId = exportJobId;
	}
	public Date getInserted() {
		return inserted;
	}
	public void setInserted(Date inserted) {
		this.inserted = inserted;
	}
	public Object getPrintItemList() {
		return printItemList;
	}
	public void setPrintItemList(Object printItemList) {
		this.printItemList = printItemList;
	}
	public String getDiagramName() {
		return diagramName;
	}
	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}
	public Long getDiagramId() {
		return diagramId;
	}
	public void setDiagramId(Long diagramId) {
		this.diagramId = diagramId;
	}
	public Long getDiagramType() {
		return diagramType;
	}
	public void setDiagramType(Long diagramType) {
		this.diagramType = diagramType;
	}
	public Long getPTemplateId() {
		return pTemplateId;
	}
	public void setPTemplateId(Long templateId) {
		pTemplateId = templateId;
	}
	public Boolean getIncludeText() {
		return includeText;
	}
	public void setIncludeText(Boolean includeText) {
		this.includeText = includeText;
	}
	public Boolean getPrintIdeas() {
		return printIdeas;
	}
	public void setPrintIdeas(Boolean printIdeas) {
		this.printIdeas = printIdeas;
	}
	public Long getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Long language_id) {
		this.language_id = language_id;
	}

}
