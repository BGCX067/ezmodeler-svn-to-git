package org.i4change.app.presenter.draw;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
public class GenerateOOSVGDetails extends BaseShapesOOSVG {
	
	private static final Log log = LogFactory.getLog(GenerateOOSVGDetails.class);

	private static GenerateOOSVGDetails instance = null;
	
	public static synchronized GenerateOOSVGDetails getInstance() {
		if (instance == null) {
			instance = new GenerateOOSVGDetails();
		}
		return instance;
	}
	
	public List<String> generate(Vector diagramMap, Long diagramType,
			DrawGraphicStylesList drawGraphicStylesList, 
			DrawTextStyleList drawTextStyleList, DiagramDimension diagramDimension,
			Boolean includeText, Boolean printIdeas) {
		try {
			
			List<String> diagram = new LinkedList<String>();
			
			//First draw all Objects, but no connectors
			for (Iterator iter = diagramMap.iterator(); iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: " + key);

				Vector whiteBoardItem = (Vector) key;
				log.debug("whiteBoardItem: " + whiteBoardItem);
				String typeOfObject = whiteBoardItem.get(0).toString();
				log.debug("typeOfObject: " + typeOfObject);

				if (!typeOfObject.equals("connector")) {

					int x = 200;
					int y = 200;

					int width = 300;
					int height = 200;

					log.debug("x,y,width,height: " + x + "|" + y + "|" + width + "|" + height);
					

					Color strokeColor = null;
					Color fillColor = null;
					
					//Get the DrawGraphicStyle for that object
					DrawGraphicStyle drawGraphicStyle = this.getDrawGraphicStyleForObjectType(drawGraphicStylesList, typeOfObject);
					DrawGraphicStyle drawGraphicStyleTextBox = drawGraphicStylesList.getDrawGraphicStyleTextBox();
					DrawGraphicStyle drawGraphicShadowStyle = drawGraphicStylesList.getShadowStyle();
					
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
					        	diagram.add(this.paintDiagramRectBoldPending(x, y, width, height, drawGraphicStyle, text,  
					        			drawTextStyleList.getDrawTextStyleRoleUnScaled(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle));
					        } else {
					        	diagram.add(this.paintDiagramRectBold(x, y, width, height, drawGraphicStyle, text,  
					        			drawTextStyleList.getDrawTextStyleRoleUnScaled(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle));
					        }
						} else {
							diagram.add(this.paintDiagramRect(x, y, width, height, drawGraphicStyle, text,  
									drawTextStyleList.getDrawTextStyleUnScaled(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle));
						}

					} else if (typeOfObject.equals("inputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						diagram.add(this.paintDiagramInputFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, drawTextStyleList.getDrawTextStyleUnScaled(), drawGraphicStyleTextBox, 
				        		includeText, drawGraphicShadowStyle));
						
					} else if (typeOfObject.equals("outputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						diagram.add(this.paintDiagramOutputFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, drawTextStyleList.getDrawTextStyleUnScaled(), drawGraphicStyleTextBox, 
				        		includeText, drawGraphicShadowStyle));
						
					} else if (typeOfObject.equals("issueflow") && printIdeas) {
 
						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						String assigneeUserLogin = whiteBoardItem.get(7).toString();
						
						diagram.add(this.paintDiagramIssueFlow(x, y, x+width, y+height, 
				        		drawGraphicStyle, text, assigneeUserLogin, 1,  
				        		drawTextStyleList.getDrawTextStyleUnScaled(), drawGraphicStyleTextBox, includeText, drawGraphicShadowStyle));
						
					} else if (typeOfObject.equals("letter") && printIdeas) {

						Color fgcolor = new Color(Integer.valueOf(
								whiteBoardItem.get(2).toString()).intValue());

						String textforfield = whiteBoardItem.get(1).toString();
						
						diagram.add(this.paintDiagramText(x, y, width, height, textforfield, 
								drawGraphicStyle, drawTextStyleList.getDrawTextStyleUnScaled(), drawGraphicStyleTextBox, 
								includeText, drawGraphicShadowStyle));
						
					} else {
						log.error("Unkown Object Type: "+typeOfObject);
					}

				}
				
				
			}
			
			return diagram;
			
		} catch (Exception err) {
			log.error("[generatePreview]",err);
		}
		return null;
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
		} catch (Exception err) {
			log.error("[getDrawGraphicStyleForObjectType]",err);
		}
		return null;
	}
	

}
