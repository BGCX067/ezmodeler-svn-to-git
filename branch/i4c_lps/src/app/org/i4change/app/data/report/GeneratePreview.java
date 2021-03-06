package org.i4change.app.data.report;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.report.daos.ReportDaoImpl;
import org.i4change.app.data.report.daos.ReportTypesDaoImpl;
import org.i4change.app.documents.GenerateImage;
import org.i4change.app.documents.GenerateThumbs;
import org.i4change.app.remote.Application;
import org.i4change.app.servlets.SVGExport;
import org.i4change.app.utils.math.CalendarPatterns;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GeneratePreview {

	private static final Log log = LogFactory.getLog(GeneratePreview.class);

	private static GeneratePreview instance = null;

	public static synchronized GeneratePreview getInstance() {
		if (instance == null) {
			instance = new GeneratePreview();
		}
		return instance;
	}
	
	public Map generatePreview(Map diagramMap, Long diagramType) {
		try {
			
			log.debug("generatePreview "+diagramMap.size()+" diagramType "+diagramType);
			Map<String,Object> returnMap = this.generateImage(diagramMap, diagramType);
			SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
			
			int width = (Integer) returnMap.get("width");
			int height = (Integer) returnMap.get("height");
			
			boolean useCSS = true; // we want to use CSS style attributes
			
			String fileName = "diagram_"+CalendarPatterns.getTimeForStreamId(new Date());
			String requestedFile = fileName+".svg";
			String outputFileName = fileName+".png";
			String outputFileNameThumb = "THUMB_"+fileName+".png";
			
			FileWriter outputFile = new FileWriter (Application.tempFileFir + requestedFile);
			
			svgGenerator.stream(outputFile, useCSS);
			
			File f_in = new File(Application.tempFileFir + requestedFile);
			File f_out = new File(Application.tempFileFir + outputFileName);
			File thumb_out = new File(Application.tempFileFir + outputFileNameThumb);
			
			GenerateImage.getInstance().convertSingleImageFromSvg(f_in.getAbsolutePath(),f_out.getAbsolutePath(),width,height);
			
			GenerateThumbs.getInstance().generateThumb(f_out, thumb_out, 200);
			
			Map<String,File> returnItem = new HashMap<String,File>();
			returnItem.put("svg", f_in);
			returnItem.put("png", f_out);
			returnItem.put("thumb", thumb_out);
			
			return returnItem;
			
		} catch (Exception err) {
			log.error("[generatePreview]",err);
		}
		return null;
	}
	
	public Map<String,Object> generateImage (Map diagramMap, Long diagramType) {
		try {
			
			Map<String,Object> returnMap = new HashMap<String,Object>();
			
			log.debug("generatePreview "+diagramMap.size()+" diagramType "+diagramType);
			
			Image arrow_line_right = null;
			Image arrow_line_left = null;
			Image arrow_line_down = null;
			Image arrow_line_up = null;
			
			//Capture Images - Arrows
			File f_right = new File(Application.tempFileFir + "arrow_line_right.png");
			File f_left = new File(Application.tempFileFir + "arrow_line_left.png");
			File f_down = new File(Application.tempFileFir + "arrow_line_down.png");
			File f_up = new File(Application.tempFileFir + "arrow_line_up.png");
			if (f_right.exists()){
				//ImageIO io = new ImageIO();
				arrow_line_right = ImageIO.read(f_right);
			}
			if (f_left.exists()){
				//ImageIO io = new ImageIO();
				arrow_line_left = ImageIO.read(f_left);
			}
			if (f_down.exists()){
				//ImageIO io = new ImageIO();
				arrow_line_down = ImageIO.read(f_down);
			}
			if (f_up.exists()){
				//ImageIO io = new ImageIO();
				arrow_line_up = ImageIO.read(f_up);
			}
			
			
			// Get a DOMImplementation.
			DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

			// Create an instance of org.w3c.dom.Document.
			//String svgNS = "http://www.w3.org/2000/svg";
			String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

			Document document = domImpl.createDocument(svgNS, "svg",null);

			// Get the root element (the 'svg' element).
			Element svgRoot = document.getDocumentElement();

			SVGExport test = new SVGExport();

			// Create an instance of the SVG Generator.
	        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
	        
	        int minBorder = 60;
	        	
	        int minx = 2400;
	        int maxx = 0;
	        
	        int miny = 1600;
	        int maxy = 0;
	        
	        for (Iterator iter = diagramMap.keySet().iterator(); iter.hasNext();) {
	        	
	        	Object key = iter.next();
	        	Map whiteBoardItem = (Map) diagramMap.get(key);
	        	
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

	        // Set the width and height attributes on the root 'svg' element.
			svgRoot.setAttributeNS(null, "width", ""+overallWidth);
			svgRoot.setAttributeNS(null, "height", ""+overallHeight);
			
			
			//First draw all Objects, but no connectors
			for (Iterator iter = diagramMap.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: " + key);

				Map whiteBoardItem = (Map) diagramMap.get(key);
				log.debug("whiteBoardItem: " + whiteBoardItem);
				String typeOfObject = whiteBoardItem.get(0).toString();
				log.debug("typeOfObject: " + typeOfObject);

				if (!typeOfObject.equals("connector")) {

					int x = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 5).toString()).floatValue());
					int y = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 4).toString()).floatValue());

					//Trim to the content
					x -= deltaX;
					y -= deltaY;
					
					int width = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 3).toString()).floatValue());
					int height = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 2).toString()).floatValue());

					log.debug("x,y,width,height: " + x + "|" + y + "|" + width + "|" + height);

					Color strokeColor = null;
					Color fillColor = null;

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
							SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
							Boolean pending = Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
					        if (pending) {
					        	test.paintDiagramRectBoldPending(svgGenerator_temp, x, y, width, height, strokeColor, fillColor, text);
					        } else {
					        	test.paintDiagramRectBold(svgGenerator_temp, x, y, width, height, strokeColor, fillColor, text);
					        }
						} else {
							SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
					        test.paintDiagramRect(svgGenerator_temp, x, y, width, height, strokeColor, fillColor, text);
						}

					} else if (typeOfObject.equals("inputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
				        test.paintDiagramInputFlow(svgGenerator_temp, x, y, x+width, y+height, 
				        		strokeColor, fillColor, text);
						
					} else if (typeOfObject.equals("outputflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
				        test.paintDiagramOutputFlow(svgGenerator_temp, x, y, x+width, y+height, 
				        		strokeColor, fillColor, text);
						
					} else if (typeOfObject.equals("issueflow")) {

						strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(1).toString()).intValue());
						fillColor = new Color(Integer.valueOf(whiteBoardItem.get(3).toString()).intValue());

						String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
						
						String assigneeUserLogin = whiteBoardItem.get(7).toString();
						
						SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
				        test.paintDiagramIssueFlow(svgGenerator_temp, x, y, x+width, y+height, 
				        		strokeColor, fillColor, text, assigneeUserLogin);
						
					} else if (typeOfObject.equals("letter")) {

						Color fgcolor = new Color(Integer.valueOf(
								whiteBoardItem.get(2).toString()).intValue());

						String textforfield = whiteBoardItem.get(1).toString();
						
						SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
				        test.paintDiagramText(svgGenerator_temp, x, y, x+width, y+height, textforfield);
						
					} else {
						log.error("Unkown Object Type: "+typeOfObject);
					}

				}
				
				
			}
			
			//Then Draw the connectors
			for (Iterator iter = diagramMap.keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				log.debug("key: " + key);

				Map whiteBoardItem = (Map) diagramMap.get(key);
				log.debug("whiteBoardItem: " + whiteBoardItem);
				String typeOfObject = whiteBoardItem.get(0).toString();
				log.debug("typeOfObject: " + typeOfObject);

				if (typeOfObject.equals("connector")) {

					int x = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 5).toString()).floatValue());
					int y = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 4).toString()).floatValue());

					//Trim to the content
					x -= deltaX;
					y -= deltaY;
					
					int width = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 3).toString()).floatValue());
					int height = Math.round(Float.valueOf(whiteBoardItem.get(whiteBoardItem.size() - 2).toString()).floatValue());

					//log.debug("x,y,width,height: " + x + "|" + y + "|" + width + "|" + height);

					String text = whiteBoardItem.get(whiteBoardItem.size() - 8).toString();
					//log.debug("text: " + text);
					Map pointsMap = (Map) whiteBoardItem.get(5);
					
					Color strokeColor = new Color(Integer.valueOf(whiteBoardItem.get(6).toString()).intValue());
					
					//log.debug("Map: "+pointsMap);
					
					int px2 = 0;
					int py2 = 0;;
					
					for (Iterator pointIter = pointsMap.keySet().iterator();pointIter.hasNext();) {
						
						Object pointKey = pointIter.next();
						//log.debug("pointKey: "+pointKey);
						Map pointObjectMap = (Map) pointsMap.get(pointKey);
						//log.debug("pointObjectMap: "+pointObjectMap);
						
						int px1 = x+Math.round(Float.valueOf(pointObjectMap.get(0).toString()).floatValue());
						int py1 = y+Math.round(Float.valueOf(pointObjectMap.get(1).toString()).floatValue());
						px2 = x+Math.round(Float.valueOf(pointObjectMap.get(2).toString()).floatValue());
						py2 = y+Math.round(Float.valueOf(pointObjectMap.get(3).toString()).floatValue());
						
						//log.debug("px1,py1,px2,py2: "+px1+"|"+py1+"|"+px2+"|"+py2);
						
						SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
						test.drawDottedLine(svgGenerator_temp, px1, py1, px2, py2, strokeColor);
						
					}
					
					
					//Add the Arrow to the Connector
					String arrowDir = whiteBoardItem.get(9).toString();
					
					//SVGGraphics2D svgGenerator_temp = new SVGGraphics2D(svgGenerator);
			        //test.paintDiagramRect(svgGenerator_temp, x, y, width, height, strokeColor, fillColor, text);
					log.debug("Generate Image To Connector");
					SVGGraphics2D svgGenerator_image = new SVGGraphics2D(svgGenerator);

					if (arrowDir.equals("left")){
						log.debug("img: "+arrow_line_left);
						log.debug("img Width: "+arrow_line_left.getWidth(null));
						log.debug("img Height: "+arrow_line_left.getHeight(null));
						//log.debug("img: "+img.get);
				        //Image img = null;
						log.debug("px2, py2: "+px2+ " " +py2);
						//svgGenerator_image.drawImage(img, px2, py2, null);
						svgGenerator_image.drawImage(arrow_line_left, px2, py2-6, 12, 12, null);
					} else if (arrowDir.equals("right")){
						log.debug("img: "+arrow_line_right);
						log.debug("img Width: "+arrow_line_right.getWidth(null));
						log.debug("img Height: "+arrow_line_right.getHeight(null));
						//log.debug("img: "+img.get);
				        //Image img = null;
						log.debug("px2, py2: "+px2+ " " +py2);
						//svgGenerator_image.drawImage(img, px2, py2, null);
						svgGenerator_image.drawImage(arrow_line_right, px2-6, py2-6, 12, 12, null);
					} else if (arrowDir.equals("down")){
						log.debug("img: "+arrow_line_down);
						log.debug("img Width: "+arrow_line_down.getWidth(null));
						log.debug("img Height: "+arrow_line_down.getHeight(null));
						//log.debug("img: "+img.get);
				        //Image img = null;
						log.debug("px2, py2: "+px2+ " " +py2);
						//svgGenerator_image.drawImage(img, px2, py2, null);
						svgGenerator_image.drawImage(arrow_line_down, px2-6, py2-6, 12, 12, null);
					} else if (arrowDir.equals("up")){
						log.debug("img: "+arrow_line_right);
						log.debug("img Width: "+arrow_line_up.getWidth(null));
						log.debug("img Height: "+arrow_line_up.getHeight(null));
						//log.debug("img: "+img.get);
				        //Image img = null;
						log.debug("px2, py2: "+px2+ " " +py2);
						//svgGenerator_image.drawImage(img, px2, py2, null);
						svgGenerator_image.drawImage(arrow_line_up, px2-6, py2, 12, 12, null);
					}
					//URLImageSource t = new URLImageSource(connection.getContent());



				}
				
			}
			
			returnMap.put("svgGenerator", svgGenerator);
			returnMap.put("width", overallWidth);
			returnMap.put("height", overallHeight);
			
			
			return returnMap;
			
		} catch (Exception err) {
			log.error("[generatePreview]",err);
		}
		return null;
	}





}
