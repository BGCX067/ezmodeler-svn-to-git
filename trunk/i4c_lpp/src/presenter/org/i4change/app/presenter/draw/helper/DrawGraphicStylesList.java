package org.i4change.app.presenter.draw.helper;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author swagner
 *
 */
public class DrawGraphicStylesList {
	
	private LinkedList<DrawGraphicStyle> drawGraphicStyles = new LinkedList<DrawGraphicStyle>();
	private DrawGraphicStyle drawGraphicStyleTextBox = null;
	
	private DrawGraphicStyle lineStyle = null;
	private DrawGraphicStyle lineStyleArrow = null;
	private DrawGraphicStyle shadowStyle = null;
	private DrawGraphicStyle connectorStyle = null;
	private int startNumberIndent = 1;
	
	private static final Log log = LogFactory.getLog(DrawGraphicStylesList.class);

	public DrawGraphicStylesList(int startNumberIndent) {
		
		this.startNumberIndent  = startNumberIndent;
		String baseGraphicsName = "gr";
		
		this.drawGraphicStyles.add(new DrawGraphicStyle("outputflow", 
					new Color (0xFF3300), new Color (0xCC0000), 
					baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("inputflow", 
				new Color (0x00FF33), new Color (0x009900), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("processgroup", 
				new Color (0xFF9900), new Color (0xFF6600), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("process", 
				new Color (0xFFFF00), new Color (0xFFCC00), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("activity", 
				new Color (0xFEFEFE), new Color (0xFF6600), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("unit", 
				new Color (0xB4C4FE), new Color (0x3F65FC), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("departement", 
				new Color (0x89A2FE), new Color (0x2D56FD), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("company", 
				new Color (0x5073FC), new Color (0x032FDA), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("letter", 
				new Color (0xDDDDDD), new Color (0x000000), 
				baseGraphicsName+this.startNumberIndent));
		this.startNumberIndent++;
		this.drawGraphicStyles.add(new DrawGraphicStyle("issue", 
				new Color (0x9D97DF), new Color (0x000000), 
				baseGraphicsName+this.startNumberIndent));
		
		this.startNumberIndent++;
		this.connectorStyle = new DrawGraphicStyle("connectorStyle", 
				new Color (0xFFFFFF), new Color (0xFFFFFF), 
				baseGraphicsName+this.startNumberIndent);
		this.drawGraphicStyles.add(this.connectorStyle);
		
		this.startNumberIndent++;
		drawGraphicStyleTextBox = new DrawGraphicStyle("textBox", 
				new Color (0x9D97DF), new Color (0x000000), 
				baseGraphicsName+this.startNumberIndent);
		
		this.startNumberIndent++;
		this.lineStyle = new DrawGraphicStyle("lineStyle", 
				new Color (0x000000), new Color (0x000000), 
				baseGraphicsName+this.startNumberIndent);
		
		this.startNumberIndent++;
		this.lineStyleArrow = new DrawGraphicStyle("lineStyleArrow", 
				new Color (0x000000), new Color (0x000000), 
				baseGraphicsName+this.startNumberIndent);
		
		this.startNumberIndent++;
		this.shadowStyle = new DrawGraphicStyle("shadowStyle", 
				new Color (0x666666), new Color (0x666666), 
				baseGraphicsName+this.startNumberIndent);
				
		this.startNumberIndent++;
	}
	
	public LinkedList<DrawGraphicStyle> getDrawGraphicStyles() {
		return drawGraphicStyles;
	}
	
	public DrawGraphicStyle getDrawGraphicStyleTextBox() {
		return drawGraphicStyleTextBox;
	}
	
	public DrawGraphicStyle getLineStyle() {
		return lineStyle;
	}

	public DrawGraphicStyle getLineStyleArrow() {
		return lineStyleArrow;
	}
	
	public DrawGraphicStyle getShadowStyle() {
		return shadowStyle;
	}
	
	public DrawGraphicStyle getConnectorStyle() {
		return connectorStyle;
	}

	public DrawGraphicStyle getDrawGraphicStyleByName(String objName) {
		try {
			
			for (DrawGraphicStyle drawGraphicStyle : drawGraphicStyles) {
				if (drawGraphicStyle.getObjName().equals(objName)) {
					return drawGraphicStyle;
				}
			}
			
		} catch (Exception err) {
			log.error("[getDrawGraphicStyleByName]",err);
		}
		return null;
	}

}
