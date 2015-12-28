package org.i4change.app.presenter.draw.helper;

public class DiagramDimension {

	private int deltaX = 0;
	private int deltaY = 0;
	
	private int overallWidth = 0;
	private int overallHeight = 0;
	
	private int templateDiagramWidth;
	private int templateDiagramHeight;
	
	private float scaleFactor;
	
	private double templateXIndentInCm;
	private double templateYIndentInCm;
	
	private double templateXIndentInPx;
	private double templateYIndentInPx;

	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

	public int getOverallWidth() {
		return overallWidth;
	}

	public void setOverallWidth(int overallWidth) {
		this.overallWidth = overallWidth;
	}

	public int getOverallHeight() {
		return overallHeight;
	}

	public void setOverallHeight(int overallHeight) {
		this.overallHeight = overallHeight;
	}

	public int getTemplateDiagramWidth() {
		return templateDiagramWidth;
	}

	public void setTemplateDiagramWidth(int templateDiagramWidth) {
		this.templateDiagramWidth = templateDiagramWidth;
	}

	public int getTemplateDiagramHeight() {
		return templateDiagramHeight;
	}

	public void setTemplateDiagramHeight(int templateDiagramHeight) {
		this.templateDiagramHeight = templateDiagramHeight;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public double getTemplateXIndentInCm() {
		return templateXIndentInCm;
	}

	public void setTemplateXIndentInCm(double templateXIndentInCm) {
		this.templateXIndentInCm = templateXIndentInCm;
	}

	public double getTemplateYIndentInCm() {
		return templateYIndentInCm;
	}

	public void setTemplateYIndentInCm(double templateYIndentInCm) {
		this.templateYIndentInCm = templateYIndentInCm;
	}

	public double getTemplateXIndentInPx() {
		return templateXIndentInPx;
	}

	public void setTemplateXIndentInPx(double templateXIndentInPx) {
		this.templateXIndentInPx = templateXIndentInPx;
	}

	public double getTemplateYIndentInPx() {
		return templateYIndentInPx;
	}

	public void setTemplateYIndentInPx(double templateYIndentInPx) {
		this.templateYIndentInPx = templateYIndentInPx;
	}
	
}
