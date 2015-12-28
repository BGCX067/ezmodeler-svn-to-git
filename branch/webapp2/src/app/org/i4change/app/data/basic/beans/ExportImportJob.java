package org.i4change.app.data.basic.beans;

import java.util.Date;

public class ExportImportJob {
	
	private Long exportJobId;
	private Date inserted;
	private Object printItemList;
	private String diagramName;
	private Long diagramId;
	private Long diagramType;
	
	public ExportImportJob(Long exportJobId, Date inserted, Object printItemList, String diagramName, 
				Long diagramId, Long diagramType) {
		super();
		this.exportJobId = exportJobId;
		this.inserted = inserted;
		this.printItemList = printItemList;
		this.diagramName = diagramName;
		this.diagramId = diagramId;
		this.diagramType = diagramType;
	}
	public ExportImportJob(Long exportJobId2) {
		// TODO Auto-generated constructor stub
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

}
