package org.i4change.app.svg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.Polygon;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JTextArea;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.remote.Application;

/**
 * Includes a set of Functions to generate Graphic Basic Types
 * @author swagner
 *
 */
public class AbstractBatik {
	
	
	private static final Log log = LogFactory.getLog(AbstractBatik.class);
	

	public void paintDiagramRect(Graphics2D g2d, int x, int y, int width, int height, 
			Color strokeCol, Color fillCol, String text) throws Exception {
		
		//Shadow
		this.paintRect(g2d, x+3, y+3, width, height, new Color(0,0,0,80));
		
		//Border
		this.paintRect(g2d, x, y, width, height, strokeCol);
		
		//Fill
		this.paintRect(g2d, x+2, y+2, width-4, height-4, fillCol);
		
		//Text
		this.drawText(g2d, x+2, y+2, width-4, height-4, text);
		
	}
	
	public void paintDiagramRectBold(Graphics2D g2d, int x, int y, int width, int height, 
			Color strokeCol, Color fillCol, String text) throws Exception {
		
		//Shadow
		this.paintRect(g2d, x+3, y+3, width, height, new Color(0,0,0,80));
		
		//Border
		this.paintRect(g2d, x, y, width, height, strokeCol);
		
		//Fill
		this.paintRect(g2d, x+2, y+2, width-4, height-4, fillCol);
		
		//Text
		this.drawTextBold(g2d, x+2, y+2, width-4, height-4, text);
		
	}
	
	public void paintDiagramRectBoldPending(Graphics2D g2d, int x, int y, int width, int height, 
			Color strokeCol, Color fillCol, String text) throws Exception {
		
		//Shadow
		this.paintRect(g2d, x+3, y+3, width, height, new Color(0,0,0,80));
		
		//Border Line Dotted
		this.drawDottedLine(g2d, x, y, x+width, y, strokeCol);
		this.drawDottedLine(g2d, x+width, y, x+width, y+height, strokeCol);
		this.drawDottedLine(g2d, x+width, y+height, x, y+height, strokeCol);
		this.drawDottedLine(g2d, x, y+height, x, y, strokeCol);
		
		//Fill
		this.paintRect(g2d, x+2, y+2, width-4, height-4, fillCol);
		
		//Text
		this.drawTextBold(g2d, x+2, y+2, width-4, height-4, text);
		
	}

	public void paintDiagramInputFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, 
			Color strokeCol, Color fillCol, String text) throws Exception {
		
		//Shadow
		this.paintInputFlow(g2d, x1+3, y1+3, x2+3, y2+2, new Color(0,0,0,80));
		
		//Border
		this.paintInputFlow(g2d, x1, y1, x2, y2, strokeCol);
		
		//Fill
		this.paintInputFlow(g2d, x1+2, y1+2, x2-2, y2-2, fillCol);
		
		//Text
		int width = x2-x1;
		int height = y2-y1;
		this.drawText(g2d, x1+2, y1+2, (width*2/3)-2, height-2, text);
		
	}

	public void paintDiagramOutputFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, 
			Color strokeCol, Color fillCol, String text) throws Exception {
		
		//Shadow
		this.paintOutputFlow(g2d, x1+2, y1+3, x2+3, y2+3, new Color(0,0,0,80));
		
		//Border
		this.paintOutputFlow(g2d, x1, y1, x2, y2, strokeCol);
		
		//Fill
		this.paintOutputFlow(g2d, x1+2, y1+2, x2-2, y2-2, fillCol);
		
		//Text
		int width = x2-x1;
		int height = y2-y1;
		this.drawText(g2d, x1+(width/3)+2, y1+2, (width*2/3)-2, height-2, text);
		
	}

	public void paintDiagramIssueFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, 
			Color strokeCol, Color fillCol, String text, String assignee) throws Exception {
		
		//Shadow
		this.paintIssueFlow(g2d, x1+3, y1+3, x2+3, y2+2, new Color(0,0,0,80));
		
		//Border
		this.paintIssueFlow(g2d, x1, y1, x2, y2, strokeCol);
		
		//Fill
		this.paintIssueFlow(g2d, x1+2, y1+2, x2-4, y2-4, fillCol);
		
		//Text
		int width = x2-x1;
		int height = y2-y1;
		this.drawText(g2d, x1+(width/6)+2, y1+2, (width/6)*4-4, height-20, text);
		
		//Assignee
		int height2 = 20;
		this.drawText(g2d, x1+(width/6)+2, y1+height-20, (width/6)*4-4, height2, assignee);
	}
	
	public void paintDiagramText(Graphics2D g2d, int x1, int y1, int x2, int y2, String text) throws Exception {

		int width = x2-x1;
		int height = y2-y1;
		
		//Border
		this.paintRect(g2d, x1, y1, width, height, new Color(0,0,0));
		
		//Fill
		this.paintRect(g2d, x1+2, y1+2, width-4, height-4, new Color(221,221,221));
		
		//Text
		this.drawText(g2d, x1+2, y1+2, width-4, height-4, text);

	}
	
	public void paintConnectorText(Graphics2D g2d, int x1, int y1, int x2, int y2, 
			String text, String fontStyle, int fontSize, int fontColor) throws Exception {

		int width = x2-x1;
		int height = y2-y1;
		
		//Border
		this.paintRect(g2d, x1, y1, width, height, new Color(255,255,255));
		
		//Fill
		//this.paintRect(g2d, x1+2, y1+2, width-4, height-4, new Color(221,221,221));
		
		//Text
		this.drawTextByCustom(g2d, x1+1, y1+1, width-2, height-2, text, fontStyle, fontSize, fontColor);

	}
	
	public void paintRect(Graphics2D g2d, int x, int y, int width, int height, Color col) throws Exception {
        g2d.setPaint(col);
        		//int x, int y, int width, int height
        g2d.fill(new Rectangle(x,y,width,height));
        
    }
	
	public void drawDottedLine(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		
		Stroke drawingStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		
		g2d.setColor(col);
		g2d.setStroke(drawingStroke);
		g2d.draw(line);
		
	}
	
	public void drawBasicLine(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		
		Stroke drawingStroke = new BasicStroke(2);
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		
		g2d.setColor(col);
		g2d.setStroke(drawingStroke);
		g2d.draw(line);
		
	}
	
	public void paintLine(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		g2d.setPaint(col);
		g2d.drawLine(x1, y1, x2, y2);
	}

	public void paintInputFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		
		g2d.setPaint(col);
		
		int mx1 = x1+((Math.abs(x1-x2)/3)*2);
		int my1 = y1+(Math.abs(y1-y2)/2)+1;
		
		int[] xPoints = {x1,mx1,x2,mx1,x1,x1};
		int[] yPoints = {y1,y1,my1,y2,y2,y1};
		
		g2d.fill(new Polygon(xPoints, yPoints, 6));
		
	}

	public void paintOutputFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		
		g2d.setPaint(col);
		
		int mx1 = x1+((Math.abs(x1-x2)/3));
		int my1 = y1+(Math.abs(y1-y2)/2)+1;
		
		int[] xPoints = {x2,mx1,x1,mx1,x2,x2};
		int[] yPoints = {y1,y1,my1,y2,y2,y1};
		
		g2d.fill(new Polygon(xPoints, yPoints, 6));
		
	}
	
	public void paintIssueFlow(Graphics2D g2d, int x1, int y1, int x2, int y2, Color col) throws Exception {
		
		g2d.setPaint(col);
		
		int mx1 = ((Math.abs(x1-x2)/6));
		int my1 = y1+(Math.abs(y1-y2)/2);
		
		int[] xPoints = {x2-mx1,x1+mx1,x1,x1+mx1,x2-mx1,x2,x2-mx1};
		int[] yPoints = {y1,y1,my1,y2,y2,my1,y1};
		
		g2d.fill(new Polygon(xPoints, yPoints, 6));
		
	}
	
	public void drawTextByCustom(Graphics2D g2d, int x, int y, int width, int height, String text, 
					String fontStyle, int fontSize, int fontColor) throws Exception {
		
		//		g2d.setClip(x, y, width, height);
		//		g2d.setColor(Color.black);
		//		g2d.drawString(text, x, y+20);
		
			int fontStyleAsInt = Font.PLAIN;
			if (fontStyle.equals("bold")) {
				fontStyleAsInt = Font.BOLD;
			} else if (fontStyle.equals("italic")) {
				fontStyleAsInt = Font.ITALIC;
			}
				
			//Font font = new Font("Verdana", Font.PLAIN, 11);
			Font font = new Font("Arial", fontStyleAsInt, fontSize);

			Color fillColor = new Color(fontColor);
			
			String[] stringsText = text.split("\r");
			log.debug("TEXT: "+stringsText);
			log.debug("TEXT: "+stringsText.length);
			
			String newText = "";
			
			for (int i=0;i<stringsText.length;i++) {
				newText += stringsText[i];
				if (i+1<stringsText.length) {
					newText += "\n";
				}
			}
			
			JTextArea n = new JTextArea( newText );
			n.setFont(font);
			n.setForeground(fillColor);
			n.setWrapStyleWord( true );
			n.setLineWrap( true );
			
			
			log.debug("Text at: "+x+" "+y);
				//int x, int y, int width, int height
			n.setBounds( x, y , width, height );
			n.setOpaque( false );
			
			SVGGraphics2D svgGenerator2 = (SVGGraphics2D) g2d.create(x, y, width, height);
			

			//svgGenerator2.create(x, y, width, height);
			//svgGenerator2.draw(.dra)
			n.paint( svgGenerator2 );
			
			//n.paintComponents(svgGenerator2);
			//n.setBounds( x, y , width, height );
			
		}
	
	public void drawText(Graphics2D g2d, int x, int y, int width, int height, String text) throws Exception {
		
	//		g2d.setClip(x, y, width, height);
	//		g2d.setColor(Color.black);
	//		g2d.drawString(text, x, y+20);
			
		//Font font = new Font("Verdana", Font.PLAIN, 11);
		Font font = new Font("Arial", Font.PLAIN, Application.defaultFontSize);

		String[] stringsText = text.split("\r");
		log.debug("TEXT: "+stringsText);
		log.debug("TEXT: "+stringsText.length);
		
		String newText = "";
		
		for (int i=0;i<stringsText.length;i++) {
			newText += stringsText[i];
			if (i+1<stringsText.length) {
				newText += "\n";
			}
		}
		
		JTextArea n = new JTextArea( newText );
		n.setFont(font);
		n.setWrapStyleWord( true );
		n.setLineWrap( true );
		
		
		log.debug("Text at: "+x+" "+y);
			//int x, int y, int width, int height
		n.setBounds( x, y , width, height );
		n.setOpaque( false );
		
		SVGGraphics2D svgGenerator2 = (SVGGraphics2D) g2d.create(x, y, width, height);
		

		//svgGenerator2.create(x, y, width, height);
		//svgGenerator2.draw(.dra)
		n.paint( svgGenerator2 );
		
		//n.paintComponents(svgGenerator2);
		//n.setBounds( x, y , width, height );
		
	}
	
	public void drawTextBold(Graphics2D g2d, int x, int y, int width, int height, String text) throws Exception {
		
//		g2d.setClip(x, y, width, height);
//		g2d.setColor(Color.black);
//		g2d.drawString(text, x, y+20);
		
		//Font font = new Font("Verdana", Font.PLAIN, 11);
		Font font = new Font("Arial", Font.BOLD, Application.defaultFontSizeBold);

		String[] stringsText = text.split("\r");
		log.debug("TEXT: "+stringsText);
		log.debug("TEXT: "+stringsText.length);
		
		String newText = "";
		
		for (int i=0;i<stringsText.length;i++) {
			newText += stringsText[i];
			if (i+1<stringsText.length) {
				newText += "\n";
			}
		}
		
		JTextArea n = new JTextArea( newText );
		n.setFont(font);
		n.setWrapStyleWord( true );
		n.setLineWrap( true );
		
		
		log.debug("Text at: "+x+" "+y);
			//int x, int y, int width, int height
		n.setBounds( x, y , width, height );
		n.setOpaque( false );
		
		SVGGraphics2D svgGenerator2 = (SVGGraphics2D) g2d.create(x, y, width, height);
		

		//svgGenerator2.create(x, y, width, height);
		//svgGenerator2.draw(.dra)
		n.paint( svgGenerator2 );
		
		//n.paintComponents(svgGenerator2);
		//n.setBounds( x, y , width, height );

		
	}
	
}
