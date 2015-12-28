package org.i4change.app.presenter.draw.helper;

import java.awt.Color;

/**
 * 
 * @author swagner
 *
 */
public class DrawGraphicStyle {
	
	private String objName;
	private Color fill;
	private Color stroke;
	private String name;
	
	public DrawGraphicStyle(String objName, Color fill, Color stroke,
			String name) {
		super();
		this.objName = objName;
		this.fill = fill;
		this.stroke = stroke;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public Color getFill() {
		return fill;
	}
	public void setFill(Color fill) {
		this.fill = fill;
	}
	public Color getStroke() {
		return stroke;
	}
	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}
	
}
