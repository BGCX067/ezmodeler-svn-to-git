package org.i4change.app.presenter.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import org.i4change.app.presenter.draw.helper.DrawGraphicStyle;
import org.i4change.app.presenter.draw.helper.DrawGraphicStylesList;
import org.i4change.app.presenter.draw.helper.DrawTextStyle;
import org.i4change.app.presenter.draw.helper.DrawTextStyleList;
import org.i4change.app.remote.Application;

/**
 * 
 * @author swagner
 *
 */
public class BaseShapesOOSVG {

	
	//Rect
	private static final String rectStylePart1 = "<draw:rect draw:style-name=\"";//+gr1
	private static final String rectStylePart2 = "\" draw:text-style-name=\"P1\" draw:layer=\"layout\" ";
			//"svg:width=\"4cm\" svg:height=\"3cm\" svg:x=\"1.5cm\" svg:y=\"2cm\">";
	private static final String rectStylePart3 = "<text:p/></draw:rect>";	
	
	
	//Text
	private static final String textPart1 = "<draw:frame draw:style-name=\"";//+gr2
	private static final String textPart2 = "\" draw:text-style-name=\"";//+P2
	private static final String textPart3 = "\" draw:layer=\"layout\" ";
			//"svg:width=\"4cm\" svg:height=\"3cm\" svg:x=\"1.5cm\" svg:y=\"2cm\">
	private static final String textPart4 = "<draw:text-box><text:p text:style-name=\"";//+P4
	private static final String textPart5 = "\"><text:span text:style-name=\"";//+T1
	private static final String textPart6 = "\">";//+Hasdad Asdasdasd asdasdasd
	private static final String textPart7 = "</text:span></text:p></draw:text-box></draw:frame>";
	
	//Polygon
	private static final String polygonPart1 = "<draw:polygon draw:style-name=\"";//+gr3
	private static final String polygonPart2 = "\" draw:text-style-name=\"";//+P3
	private static final String polygonPart3 = "\" draw:layer=\"layout\" ";
			//"svg:width=\"9.499cm\" svg:height=\"5.999cm\" svg:x=\"15cm\" svg:y=\"1.5cm\" " +
			//"svg:viewBox=\"0 0 9500 6000\" " +
			//"draw:points=\"0,3000 3000,0 6500,0 9500,3000 6500,6000 3000,6000\">
	private static final String polygonPart4 = "<text:p/></draw:polygon>";	
	
	//Line
	private static final String linePart1 = "<draw:line draw:style-name=\"";//+gr3
	private static final String linePart2 = "\" draw:text-style-name=\"";//+P3
	private static final String linePart3 = "\" draw:layer=\"layout\" ";
			//"svg:x1=\"6.5cm\" svg:y1=\"8cm\" svg:x2=\"10.5cm\" svg:y2=\"8cm\">
	private static final String linePart4 = "<text:p/></draw:line>";
	
	//Formel: Groesse in cm * 72 dpi / 2,54 = Mass in Pixel; 
	//=> Groesse in cm = (Mass in Pixel * 2,54) / 72 dpi
	
	protected String paintDiagramText(int x, int y, int width, int height,
			String textforfield, DrawGraphicStyle drawGraphicStyle, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText, 
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double xInCm = (x * 2.54) / 72;
        double yInCm = (y * 2.54) / 72;
        double widthInCm = (width * 2.54) / 72;
        double heightInCm = (height * 2.54) / 72;
        
        String obj = "";
        
        //Paint the Shadow
        obj += this.paintShadow(x, y, width, height, drawGraphicShadowStyle);
        
        //Paint the Rect
        obj += this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicStyle);
        
        if (includeText) {
	        //Get the Text Identifier Style Name
//	        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//	        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
	        
	        //Add the Text on Top of it
	        obj += this.drawText(xInCm, yInCm, widthInCm, heightInCm, textforfield, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        
        return obj;
		
	}
	
	protected String paintConnectorText(int x, int y, int width, int height,
			String textforfield, DrawGraphicStyle drawGraphicStyle, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText) throws Exception {

        double xInCm = (x * 2.54) / 72;
        double yInCm = (y * 2.54) / 72;
        double widthInCm = (width * 2.54) / 72;
        double heightInCm = (height * 2.54) / 72;
        
        String obj = "";
        
        //Paint the Shadow
        //obj += this.paintShadow(x, y, width, height, drawGraphicShadowStyle);
        
        //Paint the Rect
        obj += this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicStyle);
        
        if (includeText) {
	        //Get the Text Identifier Style Name
//	        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//	        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
	        
	        //Add the Text on Top of it
	        obj += this.drawText(xInCm, yInCm, widthInCm, heightInCm, textforfield, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        
        return obj;
		
	}
	
	private String paintShadow(int x, int y, int width, int height,
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double xInCm = ((x+2) * 2.54) / 72;
        double yInCm = ((y+2) * 2.54) / 72;
        double widthInCm = ((width) * 2.54) / 72;
        double heightInCm = ((height) * 2.54) / 72;
        
        return this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicShadowStyle);
	}

	protected String paintDiagramIssueFlow(int x1, int y1, int x2, 
			int y2, DrawGraphicStyle drawGraphicStyle, String text,
			String assigneeUserLogin, double factorScale,
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText,
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

		double x1InCm = (x1 * 2.54) / 72;
        double y1InCm = (y1 * 2.54) / 72;
        double x2InCm = (x2 * 2.54) / 72;
        double y2InCm = (y2 * 2.54) / 72;
        
        //Get the Text Identifier Style Name
//        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
        
        String obj = "";  
        
        //Paint the Shadow
        obj += this.paintIssueFlowShadow(x1, y1, x2, y2, drawGraphicShadowStyle, drawTextStyle);
        
        obj += this.paintIssueFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        			drawGraphicStyle, drawTextStyle);
        
        if (includeText) {
	        double assigneeHeight = 20 * factorScale;
	        
	        double widthInCm = x2InCm-x1InCm;
	        double heightInCm = (((y2-y1)-assigneeHeight) * 2.54) / 72;
	        
	        //Add the Text on Top of it
	        obj += this.drawText(x1InCm+(widthInCm/6), y1InCm, (widthInCm/6)*4, heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
	        
			//Assignee
			double height2InCm = (assigneeHeight * 2.54) / 72;
			obj += this.drawText(x1InCm+(widthInCm/6), y2InCm-height2InCm, (widthInCm/6)*4, height2InCm, assigneeUserLogin, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        return obj;
		
	}

	private String paintIssueFlowShadow(int x1, int y1, int x2, int y2,
			DrawGraphicStyle drawGraphicShadowStyle, DrawTextStyle drawTextStyle) {
		double x1InCm = ((x1+2) * 2.54) / 72;
        double y1InCm = ((y1+2) * 2.54) / 72;
        double x2InCm = ((x2+2) * 2.54) / 72;
        double y2InCm = ((y2+2) * 2.54) / 72;
        
        return this.paintIssueFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        		drawGraphicShadowStyle, drawTextStyle);
	}

	protected String paintDiagramInputFlow(int x1, int y1, int x2, 
			int y2, DrawGraphicStyle drawGraphicStyle, String text, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText,
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double x1InCm = (x1 * 2.54) / 72;
        double y1InCm = (y1 * 2.54) / 72;
        double x2InCm = (x2 * 2.54) / 72;
        double y2InCm = (y2 * 2.54) / 72;
        
        //Get the Text Identifier Style Name
//        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
        
        String obj = "";
        obj += this.paintInputFlowShadow(x1, y1, x2, y2, drawGraphicShadowStyle, drawTextStyle);
        
        obj += this.paintInputFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        			drawGraphicStyle, drawTextStyle);
        
        if (includeText) {
	        double widthInCm = x2InCm-x1InCm;
	        double heightInCm = y2InCm-y1InCm;
	        
	        //Add the Text on Top of it
	        obj += this.drawText(x1InCm, y1InCm, (widthInCm*2/3), heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        
        return obj;
		
	}
	
	private String paintInputFlowShadow(int x1, int y1, int x2, int y2,
			DrawGraphicStyle drawGraphicShadowStyle, DrawTextStyle drawTextStyle) throws Exception {
		
        double x1InCm = ((x1+2) * 2.54) / 72;
        double y1InCm = ((y1+2) * 2.54) / 72;
        double x2InCm = ((x2+2) * 2.54) / 72;
        double y2InCm = ((y2+2) * 2.54) / 72;
        
        return this.paintInputFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        					drawGraphicShadowStyle, drawTextStyle);
	}
	
	protected String paintDiagramOutputFlow(int x1, int y1, int x2, 
			int y2, DrawGraphicStyle drawGraphicStyle, String text, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText, 
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double x1InCm = (x1 * 2.54) / 72;
        double y1InCm = (y1 * 2.54) / 72;
        double x2InCm = (x2 * 2.54) / 72;
        double y2InCm = (y2 * 2.54) / 72;
        
        //Get the Text Identifier Style Name
//        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
        
        String obj = "";
        
        obj += this.paintOutputFlowShadow(x1, y1, x2, y2, 
        		drawGraphicShadowStyle, drawTextStyle);
        
        obj += this.paintOutputFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        			drawGraphicStyle, drawTextStyle);
        
        if (includeText) {
	        double widthInCm = x2InCm-x1InCm;
	        double heightInCm = y2InCm-y1InCm;
	        
	        //Add the Text on Top of it
	        obj += this.drawText(x1InCm+(widthInCm/3), y1InCm, (widthInCm*2/3), heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        
        return obj;
		
	}

	private String paintOutputFlowShadow(int x1, int y1, int x2, int y2,
			DrawGraphicStyle drawGraphicShadowStyle, DrawTextStyle drawTextStyle) throws Exception {
        double x1InCm = ((x1+2) * 2.54) / 72;
        double y1InCm = ((y1+2) * 2.54) / 72;
        double x2InCm = ((x2+2) * 2.54) / 72;
        double y2InCm = ((y2+2) * 2.54) / 72;
        
        return this.paintOutputFlow(x1InCm, y1InCm, x2InCm, y2InCm, 
        							drawGraphicShadowStyle, drawTextStyle);
	}

	protected String paintDiagramRect(int x, int y, int width,
			int height, DrawGraphicStyle drawGraphicStyle, String text, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText,
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double xInCm = (x * 2.54) / 72;
        double yInCm = (y * 2.54) / 72;
        double widthInCm = (width * 2.54) / 72;
        double heightInCm = (height * 2.54) / 72;
        
        String obj = "";
        
        obj += this.paintShadow(x, y, width, height, drawGraphicShadowStyle);
        
        obj += this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicStyle);
        
        if (includeText) {
	        //Get the Text Identifier Style Name
//	        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyle();
//	        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
	        
	        //Add the Text on Top of it
	        obj += this.drawText(xInCm, yInCm, widthInCm, heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        return obj;
        
	}

	protected String paintDiagramRectBold(int x, int y, int width,
			int height, DrawGraphicStyle drawGraphicStyle, String text, 
			DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText,
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double xInCm = (x * 2.54) / 72;
        double yInCm = (y * 2.54) / 72;
        double widthInCm = (width * 2.54) / 72;
        double heightInCm = (height * 2.54) / 72;
        
        String obj = "";
        obj += this.paintShadow(x, y, width, height, drawGraphicShadowStyle);
        
        obj += this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicStyle);
        
        if (includeText) {
	        //Get the Text Identifier Style Name
//	        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyleRole();
//	        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
	        
	        //Add the Text on Top of it
	        obj += this.drawText(xInCm, yInCm, widthInCm, heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        return obj;
		
	}

	protected String paintDiagramRectBoldPending(int x, int y,
			int width, int height, DrawGraphicStyle drawGraphicStyle,
			String text, DrawTextStyle drawTextStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox, Boolean includeText, 
			DrawGraphicStyle drawGraphicShadowStyle) throws Exception {

        double xInCm = (x * 2.54) / 72;
        double yInCm = (y * 2.54) / 72;
        double widthInCm = (width * 2.54) / 72;
        double heightInCm = (height * 2.54) / 72;
        
        String obj = "";
        obj += this.paintShadow(x, y, width, height, drawGraphicShadowStyle);
        
        obj += this.paintRect(xInCm, yInCm, widthInCm, heightInCm, drawGraphicStyle);
        
        if (includeText) {
	        //Get the Text Identifier Style Name
//	        DrawTextStyle drawTextStyle = drawTextStyleList.getDrawTextStyleRole();
//	        DrawTextStyle drawTextSpanStyle = drawTextStyleList.getDrawTextSpanStyle();
	        
	        //Add the Text on Top of it
	        obj += this.drawText(xInCm, yInCm, widthInCm, heightInCm, text, 
	        		drawTextStyle, drawTextStyle, drawGraphicStyleTextBox);
        }
        
        return obj;
		
	}
	
	protected String drawBasicLine(int px1, int py1, int px2, int py2,
			Color strokeColor, boolean hasNext, 
			DrawGraphicStylesList drawGraphicStylesList, 
			DrawTextStyleList drawTextStyleList) {
		
		double px1InCm = (px1 * 2.54) / 72;
        double py1InCm = (py1 * 2.54) / 72;
        double px2InCm = (px2 * 2.54) / 72;
        double py2InCm = (py2 * 2.54) / 72;
        
		DrawGraphicStyle drawGraphicStyle = drawGraphicStylesList.getLineStyle();
		if (!hasNext){
			drawGraphicStyle = drawGraphicStylesList.getLineStyleArrow();
		}
		
		String obj = linePart1 + drawGraphicStyle.getName() + 
				linePart2 + drawTextStyleList.getLineTextStyle().getName();
		
		
		obj += linePart3 + "svg:x1=\""+px1InCm+"cm\" svg:y1=\""+py1InCm+"cm\" " +
				"svg:x2=\""+px2InCm+"cm\" svg:y2=\""+py2InCm+"cm\">" + linePart4;
			
			
		return obj;
	}
	
	public String paintRect(double x, double y, double width, double height, 
			DrawGraphicStyle drawGraphicStyle) throws Exception {
          
		String obj = rectStylePart1 + drawGraphicStyle.getName();
        
        obj += rectStylePart2 + 
        	"svg:width=\""+width+"cm\" " +
			"svg:height=\""+height+"cm\" " +
			"svg:x=\""+x+"cm\" svg:y=\""+y+"cm\">";
        
        obj +=rectStylePart3;
        
        return obj;
    }	

	
	public String drawText(double x, double y, double width, double height, String text, 
			DrawTextStyle drawTextStyle, DrawTextStyle drawTextSpanStyle, 
			DrawGraphicStyle drawGraphicStyleTextBox) throws Exception {
		
		String obj = textPart1 + drawGraphicStyleTextBox.getName() + 
				textPart2 + drawTextStyle.getName();
        
        obj += textPart3 + 
        	"svg:width=\""+width+"cm\" " +
			"svg:height=\""+height+"cm\" " +
			"svg:x=\""+x+"cm\" svg:y=\""+y+"cm\">";
        
        text=text.replace("&", "&amp;");
        text=text.replace("<", "&lt;");
        text=text.replace(">", "&gt;"); 
        
        obj += textPart4 + drawTextSpanStyle.getName() + 
        		textPart5 + drawTextSpanStyle.getName() + 
        		textPart6 + text + textPart7;
        
        return obj;
	}
	
	public String paintInputFlow(double x1, double y1, double x2, double y2, 
			DrawGraphicStyle drawGraphicStyle, DrawTextStyle drawTextStyle) throws Exception {
		
		double mx1 = x1+((Math.abs(x1-x2)/3)*2);
		double my1 = y1+(Math.abs(y1-y2)/2);
		
		double[] xPoints = {x1,mx1,x2,mx1,x1};
		double[] yPoints = {y1,y1,my1,y2,y2};
		
		String obj = polygonPart1 + drawGraphicStyle.getName() + 
				polygonPart2 + drawTextStyle.getName() + polygonPart3;
		
		double width = Math.abs(x2-x1);
		double height = Math.abs(y2-y1);
		
		obj += "svg:width=\""+width+"cm\" svg:height=\""+height+"cm\" " +
				"svg:x=\""+x1+"cm\" svg:y=\""+y1+"cm\" ";
		
		obj += "svg:viewBox=\"0 0 "+Math.round(width*1000)+" "+Math.round(height*1000)+"\" ";
		
		
		//draw:points="2822,7761 6115,7761 7761,10172 6115,10583 2822,10583"
		
		obj += "draw:points=\""+Math.round(xPoints[0]*1000)+","+Math.round(yPoints[0]*1000)+
					" "+Math.round(xPoints[1]*1000)+","+Math.round(yPoints[1]*1000)+
					" "+Math.round(xPoints[2]*1000)+","+Math.round(yPoints[2]*1000)+
					" "+Math.round(xPoints[3]*1000)+","+Math.round(yPoints[3]*1000)+
					" "+Math.round(xPoints[4]*1000)+","+Math.round(yPoints[4]*1000)+"\">";
		
		obj += polygonPart4;
		
		return obj;
	}
	
	private String paintOutputFlow(double x1, double y1, double x2,
			double y2, DrawGraphicStyle drawGraphicStyle,
			DrawTextStyle drawTextStyle) throws Exception {
		
		double mx1 = x1+((Math.abs(x1-x2)/3));
		double my1 = y1+(Math.abs(y1-y2)/2);
		
		double[] xPoints = {x2,mx1,x1,mx1,x2};
		double[] yPoints = {y1,y1,my1,y2,y2};
		
		String obj = polygonPart1 + drawGraphicStyle.getName() + 
		polygonPart2 + drawTextStyle.getName() + polygonPart3;

		double width = Math.abs(x2-x1);
		double height = Math.abs(y2-y1);
		
		obj += "svg:width=\""+width+"cm\" svg:height=\""+height+"cm\" " +
				"svg:x=\""+x1+"cm\" svg:y=\""+y1+"cm\" ";
		
		obj += "svg:viewBox=\"0 0 "+Math.round(width*1000)+" "+Math.round(height*1000)+"\" ";
		
		obj += "draw:points=\""+Math.round(xPoints[0]*1000)+","+Math.round(yPoints[0]*1000)+
					" "+Math.round(xPoints[1]*1000)+","+Math.round(yPoints[1]*1000)+
					" "+Math.round(xPoints[2]*1000)+","+Math.round(yPoints[2]*1000)+
					" "+Math.round(xPoints[3]*1000)+","+Math.round(yPoints[3]*1000)+
					" "+Math.round(xPoints[4]*1000)+","+Math.round(yPoints[4]*1000)+"\">";
		
		obj += polygonPart4;
		
		return obj;
		
	}

	private String paintIssueFlow(double x1, double y1, double x2,
			double y2, DrawGraphicStyle drawGraphicStyle,
			DrawTextStyle drawTextStyle) {

		double mx1 = ((Math.abs(x1-x2)/6));
		double my1 = y1+(Math.abs(y1-y2)/2);
		
		double[] xPoints = {x2-mx1,x1+mx1,x1,x1+mx1,x2-mx1,x2};
		double[] yPoints = {y1,y1,my1,y2,y2,my1};
		
		String obj = polygonPart1 + drawGraphicStyle.getName() + 
		polygonPart2 + drawTextStyle.getName() + polygonPart3;

		double width = Math.abs(x2-x1);
		double height = Math.abs(y2-y1);
		
		obj += "svg:width=\""+width+"cm\" svg:height=\""+height+"cm\" " +
				"svg:x=\""+x1+"cm\" svg:y=\""+y1+"cm\" ";
		
		obj += "svg:viewBox=\"0 0 "+Math.round(width*1000)+" "+Math.round(height*1000)+"\" ";
		
		obj += "draw:points=\""+Math.round(xPoints[0]*1000)+","+Math.round(yPoints[0]*1000)+
					" "+Math.round(xPoints[1]*1000)+","+Math.round(yPoints[1]*1000)+
					" "+Math.round(xPoints[2]*1000)+","+Math.round(yPoints[2]*1000)+
					" "+Math.round(xPoints[3]*1000)+","+Math.round(yPoints[3]*1000)+
					" "+Math.round(xPoints[4]*1000)+","+Math.round(yPoints[4]*1000)+
					" "+Math.round(xPoints[5]*1000)+","+Math.round(yPoints[5]*1000)+"\">";
		
		obj += polygonPart4;
		
		return obj;
		
	}
	
}
