package org.i4change.app.presenter.helper;

import java.io.File;

public class DiagramExportBean {

	private String diagramName;
	private String rawName;
	private File diagram;
	private int width;
	private int height;
	
	public String getDiagramName() {
		return diagramName;
	}
	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}
	public File getDiagram() {
		return diagram;
	}
	public void setDiagram(File diagram) {
		this.diagram = diagram;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getRawName() {
		return rawName;
	}
	public void setRawName(String rawName) {
		this.rawName = rawName;
	}
	
}
