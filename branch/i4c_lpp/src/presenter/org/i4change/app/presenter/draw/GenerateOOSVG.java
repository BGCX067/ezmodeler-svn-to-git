package org.i4change.app.presenter.draw;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.presenter.draw.helper.DiagramDimension;
import org.i4change.app.presenter.draw.helper.DrawGraphicStyle;
import org.i4change.app.presenter.draw.helper.DrawGraphicStylesList;
import org.i4change.app.presenter.draw.helper.DrawTextStyleList;
import org.i4change.app.remote.Application;

/**
 * 
 * @author swagner
 *
 */
public class GenerateOOSVG extends BaseShapesOOSVG {
	
	private static final Log log = LogFactory.getLog(GenerateOOSVG.class);

	private static GenerateOOSVG instance = null;
	
	public static synchronized GenerateOOSVG getInstance() {
		if (instance == null) {
			instance = new GenerateOOSVG();
		}
		return instance;
	}
	
	public DiagramDimension calcFactor(Vector diagramMap, PresentationTemplate pTemplate) {
		
		//Calc the Images Position relative to the Border
        int minBorder = Application.minBorderExport;
    	
        int minx = 240000000;
        int maxx = -240000000;
        
        int miny = 160000000;
        int maxy = -160000000;
        
        for (Iterator iter = diagramMap.iterator(); iter.hasNext();) {
        	
        	Object key = iter.next();
        	Vector whiteBoardItem = (Vector) key;
        	
        	int x = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 5).toString()).floatValue());
			int y = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 4).toString()).floatValue());

			if (x < minx) {
				minx = x;
			}
			if (y < miny) {
				miny = y;
			}
			
			int width = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 3).toString()).floatValue());
			int height = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 2).toString()).floatValue());
        	
			if ((x +  width ) > maxx) {
				maxx = (x +  width ) ;
			}
			
			if ((y +  height ) > maxy) {
				maxy = (y +  height ) ;
			}
			
        }
        
        minx -= minBorder;
        miny -= minBorder;
        if (minx < 0) {
        	minx = 0;
        }
        if (miny < 0) {
        	miny = 0;
        }
        maxx += minBorder;
        maxy += minBorder;
        
        log.debug("minx,maxx,miny,maxy: "+minx+","+maxx+","+miny+","+maxy);
        
        int deltaX = minx;
        int deltaY = miny;
          
        int overallWidth = maxx - minx;
        int overallHeight = maxy - miny;

		log.debug("overallWidth|overallHeight: "+overallWidth+"|"+overallHeight);
		
		
		DiagramDimension diagramDimension = new DiagramDimension();
		
		diagramDimension.setDeltaX(deltaX);
		diagramDimension.setDeltaY(deltaY);
		
		diagramDimension.setOverallHeight(overallHeight);
		diagramDimension.setOverallWidth(overallWidth);
		
		//Calculate Factor based on Template  
		float factor = 1;
		//pTemplate
		int widthInPx = Long.valueOf((Math.round(pTemplate.getImageWidth() * 72 / 2.54))).intValue();
		//int heightInPx = (Double.valueOf(pTemplate.getImageHeight() * 72 / 2.54).intValue());
		
		if (widthInPx < overallWidth) {
			log.debug("Shrink Size To Width");
			factor = Float.valueOf(widthInPx).floatValue() / Float.valueOf(overallWidth).floatValue();
		}
		log.debug("#### factor 4: "+(Float.valueOf(widthInPx).floatValue() / Float.valueOf(overallWidth).floatValue()));
		log.debug("#### factor 3: "+widthInPx);
		log.debug("#### factor 2: "+overallWidth);
		log.debug("#### factor 1: "+factor);
		
		log.debug("#### Math.round(overallHeight * factor): "+Math.round(overallHeight * factor));
		
		int heightInPx = Long.valueOf(Math.round(overallHeight * factor)).intValue();
		
		int maxHeight = Long.valueOf((Math.round(pTemplate.getImageHeight() * 72 / 2.54))).intValue();
		
		log.debug("#### heightInPx 1: "+heightInPx);
		
		log.debug("#### maxHeight 1: "+maxHeight);
		
		if (heightInPx > maxHeight) {
			log.debug("STILL more maxHeight: "+maxHeight);
			factor = Float.valueOf(maxHeight).floatValue() / Float.valueOf(overallHeight).floatValue();
			
			
		}

		log.debug("#### factor 2: "+factor);
		
		log.debug("widthInPx|heightInPx: "+widthInPx+"|"+heightInPx);
		
		log.debug("factor: "+factor);
		
		diagramDimension.setScaleFactor(factor);
		
		return diagramDimension;
		
	}
	
	public String generatePreview(Vector diagramMap, Long diagramType,
			DrawGraphicStylesList drawGraphicStylesList, 
			DrawTextStyleList drawTextStyleList, DiagramDimension diagramDimension,
			Boolean includeText, Boolean printIdeas) {
		try {
			
			String diagram = "";
			
			int deltaX = diagramDimension.getDeltaX();
	        int deltaY = diagramDimension.getDeltaY();
	        
	        double factor = diagramDimension.getScaleFactor();
	        
	        int templateXIndent = Long.valueOf(Math.round(diagramDimension.getTemplateXIndentInPx())).intValue();
	        int templateYIndent = Long.valueOf(Math.round(diagramDimension.getTemplateYIndentInPx())).intValue();
			
			//First draw all Objects, but no connectors
			for (Iterator iter = diagramMap.iterator(); iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: " + key);

				Vector whiteBoardItem = (Vector) key;
				log.debug("whiteBoardItem: " + whiteBoardItem);
				String typeOfObject = whiteBoardItem.get(0).toString();
				log.debug("typeOfObject: " + typeOfObject);

				if (!typeOfObject.equals("connector")) {

					int x = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 5).toString()).floatValue());
					int y = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 4).toString()).floatValue());

					//Trim to the content
					x -= deltaX;
					y -= deltaY;
					
					//Trim to the Scaling
					x = Long.valueOf(Math.round(x * factor)).intValue();
					y = Long.valueOf(Math.round(y * factor)).intValue();
					
					//Set to Position
					x += templateXIndent;
					y += templateYIndent;
					
					int width = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 3).toString()).floatValue());
					int height = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 2).toString()).floatValue());
 
					log.debug("x,y,width,height: " + x + "|" + y + "|" + width + "|" + height);
					
					//Trim to the Scaling
					width = Long.valueOf(Math.round(width * factor)).intValue();
					height = Long.valueOf(Math.round(height * factor)).intValue();

					Color strokeColor = null;
					Color fillColor = null;
					
					//Get the DrawGraphicStyle for that object
					DrawGraphicStyle drawGraphicStyle = this.getDrawGraphicStyleForObjectType(drawGraphicStylesList, typeOfObject);
					DrawGraphicStyle drawGraphicStyleTextBox = drawGraphicStylesList.getDrawGraphicStyleTextBox();
					DrawGraphicStyle drawGraphicShadowStyle = drawGraphicStylesList.getShadowStyle();
					DrawGraphicStyle connectorStyle = drawGraphicStylesList.getConnectorStyle();
					
					if (typeOfObject.equals("process")
							|| typeOfObject.equals("processFixed")
							|| typeOfObject.equals("processtree")
							|| typeOfObject.equals("activity")
							|| typeOfObject.equals("company")
							|| typeOfObject.equals("companyFixed")
							|| typeOfObject.equals("departement")
							|| typeOfObject.equals("departementFixed")
							|| typeOfObject.equals("unit")
							|| typeOfObject.equals("unitFixed")
							|| typeOfObject.equals("processgroup")) {
						
						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(
								whiteBoardItem.size() - 8).toString();

						log.debug("strokeColor: " + strokeColor);
						log.debug("fillColor: " + fillColor);
						log.debug("text: " + text);
						
						if (typeOfObject.equals("unit") || typeOfObject.equals("company") || typeOfObject.equals("departement")  
								|| ((diagramType==2 || diagramType==4) && typeOfObject.equals("unitFixed"))
								|| ((diagramType==2 || diagramType==4) && typeOfObject.equals("departementFixed"))
								|| ((diagramType==2 || diagramType==4) && typeOfObject.equals("companyFixed"))) {
							Boolean pending = Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
					        if (pending) {
					        	diagram += this.paintDiagramRectBoldPending(x, y, width, height, drawGraphicStyle, text, 
					        			drawTextStyleList.getDrawTextStyleRole(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle);
					        } else {
					        	diagram += this.paintDiagramRectBold(x, y, width, height, drawGraphicStyle, text, 
					        			drawTextStyleList.getDrawTextStyleRole(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle);
					        }
						} else {
							diagram += this.paintDiagramRect(x, y, width, height, drawGraphicStyle, text, 
									drawTextStyleList.getDrawTextStyle(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle);
						}

					} else if (typeOfObject.equals("inputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						diagram += this.paintDiagramInputFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, drawTextStyleList.getDrawTextStyle(), drawGraphicStyleTextBox, 
				        		includeText, drawGraphicShadowStyle);
						
					} else if (typeOfObject.equals("outputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						diagram += this.paintDiagramOutputFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, drawTextStyleList.getDrawTextStyle(), drawGraphicStyleTextBox, 
				        		includeText, drawGraphicShadowStyle);
						
					} else if (typeOfObject.equals("issueflow") && printIdeas) {
 
						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						String assigneeUserLogin = whiteBoardItem.get(7).toString();
						
						diagram += this.paintDiagramIssueFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, assigneeUserLogin, factor, 
				        		drawTextStyleList.getDrawTextStyle(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle);
						
					} else if (typeOfObject.equals("letter") && printIdeas) {

						Color fgcolor = new Color(Integer.valueOf(
								whiteBoardItem.get(2).toString()).intValue());

						String textforfield = whiteBoardItem.get(1).toString();
						
						diagram += this.paintDiagramText(x, y, width, height, textforfield, 
								drawGraphicStyle, drawTextStyleList.getDrawTextStyle(), drawGraphicStyleTextBox, 
								includeText, drawGraphicShadowStyle);
						
					} else if (typeOfObject.equals("connectorText")) {

						Color fgcolor = new Color(Integer.valueOf(
								whiteBoardItem.get(2).toString()).intValue());

						String textforfield = whiteBoardItem.get(1).toString();
						
						//String fontstyle = whiteBoardItem.get(4).toString();
						
						//int fontcolor = Integer.valueOf(whiteBoardItem.get(2).toString()).intValue();
						
						//int fontsize = Integer.valueOf(whiteBoardItem.get(3).toString()).intValue();
						
						log.debug("connectorStyle "+connectorStyle);
						
						diagram += this.paintConnectorText(x, y, width, height, textforfield, 
								drawGraphicStyle, drawTextStyleList.getDrawTextStyleItalic(), 
								connectorStyle, includeText);
						
					} else {
						log.error("Unkown Object Type: "+typeOfObject);
					}

				}
				
				
			}
			
			//Then Draw the connectors
			for (Iterator iter = diagramMap.iterator(); iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: " + key);

				Vector whiteBoardItem = (Vector) key;
				log.debug("whiteBoardItem: " + whiteBoardItem);
				String typeOfObject = whiteBoardItem.get(0).toString();
				log.debug("typeOfObject: " + typeOfObject);

				if (typeOfObject.equals("connector")) {

					int x = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 5).toString()).floatValue());
					int y = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 4).toString()).floatValue());

					//Trim to the content
					x -= deltaX;
					y -= deltaY;
					
//					int width = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 3).toString()).floatValue());
//					int height = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 2).toString()).floatValue());
//
//					//Trim to the Scaling
//					width = Long.valueOf(Math.round(width * factor)).intValue();
//					height = Long.valueOf(Math.round(height * factor)).intValue();
					
					//log.debug("x,y,width,height: " + x + "|" + y + "|" + width + "|" + height);

					String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
					//log.debug("text: " + text);
					Vector pointsMap = (Vector) whiteBoardItem.get(5);
					
					Color strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(6).toString()).intValue());
					
					//log.debug("Map: "+pointsMap);
					
					int px2 = 0;
					int py2 = 0;
					
					for (Iterator pointIter = pointsMap.iterator();pointIter.hasNext();) {
						
						Object pointKey = pointIter.next();
						//log.debug("pointKey: "+pointKey); 
						Vector pointObjectMap = (Vector) pointKey;
						//log.debug("pointObjectMap: "+pointObjectMap);
						
						int px1 = x+Math.round(Float.valueOf(pointObjectMap.get(0).toString()).floatValue());
						int py1 = y+Math.round(Float.valueOf(pointObjectMap.get(1).toString()).floatValue());
						px2 = x+Math.round(Float.valueOf(pointObjectMap.get(2).toString()).floatValue());
						py2 = y+Math.round(Float.valueOf(pointObjectMap.get(3).toString()).floatValue());
						
						//log.debug("px1,py1,px2,py2: "+px1+"|"+py1+"|"+px2+"|"+py2);
						
						//Trim to the Scaling
						px1 = Long.valueOf(Math.round(px1 * factor)).intValue() + templateXIndent;
						py1 = Long.valueOf(Math.round(py1 * factor)).intValue() + templateYIndent;
						px2 = Long.valueOf(Math.round(px2 * factor)).intValue() + templateXIndent;
						py2 = Long.valueOf(Math.round(py2 * factor)).intValue() + templateYIndent;
						
						diagram += this.drawBasicLine(px1, py1, px2, py2, strokeColor, 
								pointIter.hasNext(), drawGraphicStylesList, drawTextStyleList);
						
					} 

				}
				
			}
			
			
			return diagram;
			
		} catch (Exception err) {
			log.error("[generatePreview]",err);
		}
		return "";
	}
	
	private DrawGraphicStyle getDrawGraphicStyleForObjectType(DrawGraphicStylesList drawGraphicStylesList, String typeOfObject) {
		try {
			if (typeOfObject.equals("process") || typeOfObject.equals("processFixed") 
					|| typeOfObject.equals("processtree")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("process");
			}
			if (typeOfObject.equals("processgroup")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("processgroup");
			}
			if (typeOfObject.equals("activity")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("activity");
			}
			if (typeOfObject.equals("company") || typeOfObject.equals("companyFixed")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("company");
			}
			if (typeOfObject.equals("departement") || typeOfObject.equals("departementFixed")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("departement");
			}
			if (typeOfObject.equals("unit") || typeOfObject.equals("unitFixed")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("unit");
			}
			if (typeOfObject.equals("unit") || typeOfObject.equals("unitFixed")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("unit");
			}
			if (typeOfObject.equals("inputflow")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("inputflow");
			}
			if (typeOfObject.equals("outputflow")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("outputflow");
			}
			if (typeOfObject.equals("issueflow")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("issue");
			}
			if (typeOfObject.equals("letter")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("letter");
			}
			if (typeOfObject.equals("connectorText")) {
				return drawGraphicStylesList.getDrawGraphicStyleByName("connectorStyle");
			}
		} catch (Exception err) {
			log.error("[getDrawGraphicStyleForObjectType]",err);
		}
		return null;
	}
	

}
