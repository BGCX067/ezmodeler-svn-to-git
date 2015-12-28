package org.i4change.app.presenter.draw;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.presenter.draw.helper.DrawTextStyle;
import org.i4change.app.presenter.draw.helper.DrawTextStyleList;
import org.i4change.app.remote.Application;

/**
 * 
 * @author swagner
 *
 */
public class DrawTextSchemes {

	private static final Log log = LogFactory.getLog(DrawTextSchemes.class);

	private static DrawTextSchemes instance = null;

	public static synchronized DrawTextSchemes getInstance() {
		if (instance == null) {
			instance = new DrawTextSchemes();
		}
		return instance;
	}
	
	private static final String textBoxTextStyle1 = "<style:style style:name=\"";//+P2
	private static final String textBoxTextStyle2 = "\" style:family=\"paragraph\">" +
				"<style:text-properties fo:font-family=\"Arial\" fo:font-size=\"";//+11pt
	private static final String textBoxTextStyle3 = "pt\" style:font-size-asian=\"";//+11pt
	private static final String textBoxTextStyle4 = "pt\" style:font-size-complex=\"";//+11pt
	private static final String textBoxTextStyle5 = "pt\"/></style:style>";
	
	
	private static final String textBoxTextBoldStyle1 = "<style:style style:name=\"";//+P4
	private static final String textBoxTextBoldStyle2 = "\" style:family=\"paragraph\"><style:text-properties fo:font-size=\"";//+20
	private static final String textBoxTextBoldStyle3 = "pt\" fo:font-weight=\"bold\" fo:font-family=\"Arial\" style:font-size-asian=\"";//+20
	private static final String textBoxTextBoldStyle4 = "pt\" style:font-weight-asian=\"bold\" style:font-size-complex=\"";//+20
	private static final String textBoxTextBoldStyle5 = "pt\" style:font-weight-complex=\"bold\"/></style:style>";
	
	private static final String textBoxTextIalicStyle1 = "<style:style style:name=\"";//T7
	private static final String textBoxTextIalicStyle2 = "\" style:family=\"text\">";
	private static final String textBoxTextIalicStyle3 = "<style:text-properties fo:color=\"#000080\" fo:font-family=\"Arial\" " +
							"style:font-family-generic=\"swiss\" style:font-pitch=\"variable\" fo:font-size=\"";//10
	private static final String textBoxTextIalicStyle4 = "pt\" fo:font-style=\"italic\" style:font-size-asian=\"";//10
	private static final String textBoxTextIalicStyle5 = "pt\" style:font-style-asian=\"italic\" style:font-size-complex=\"";//10
	private static final String textBoxTextIalicStyle6 = "pt\" style:font-style-complex=\"italic\"/></style:style>";
	
	public String getTextStyles(DrawTextStyleList drawTextStyleList, int scaledFontSize, int scaledRoleFontSize, int scaledItalicFontSize) {
		try {
			
			String textStylesOOXML = "";
			
	        drawTextStyleList.setDrawTextStyle(scaledFontSize, false);
	        drawTextStyleList.setDrawTextSpanStyle(scaledFontSize, true);
	        
	        drawTextStyleList.setDrawTextStyleRole(scaledRoleFontSize,true);
	        
	        drawTextStyleList.setLineTextStyle(scaledFontSize, false);
	        
	        drawTextStyleList.setDrawTextStyleFooter(Application.defaultFontSizeFooter, false);
	        
	        drawTextStyleList.setDrawTextStyleUnScaled(Application.defaultFontSize, false);
	        drawTextStyleList.setDrawTextStyleRoleUnScaled(Application.defaultFontRoleSize, true);
	        
	        drawTextStyleList.setDrawTextStyleItalic(scaledItalicFontSize);
	        
	        List<DrawTextStyle> dTextStyles = drawTextStyleList.getDrawTextStyles();
	        
			for (DrawTextStyle drawTextStyle : dTextStyles) {
				
				if (drawTextStyle.getIsBold()) {
					textStylesOOXML += textBoxTextBoldStyle1 + drawTextStyle.getName() + 
							textBoxTextBoldStyle2 + drawTextStyle.getHeight() + 
							textBoxTextBoldStyle3 + drawTextStyle.getHeight() + 
							textBoxTextBoldStyle4 + drawTextStyle.getHeight() + 
							textBoxTextBoldStyle5;
				} else if (drawTextStyle.getIsItalic()) {
					textStylesOOXML += textBoxTextIalicStyle1 + drawTextStyle.getName() + textBoxTextIalicStyle2 +
							textBoxTextIalicStyle3 + drawTextStyle.getHeight() + 
							textBoxTextIalicStyle4 + drawTextStyle.getHeight() + 
							textBoxTextIalicStyle5 + drawTextStyle.getHeight() + 
							textBoxTextIalicStyle6;
				} else {
					textStylesOOXML += textBoxTextStyle1 + drawTextStyle.getName() + 
							textBoxTextStyle2 + drawTextStyle.getHeight() + 
							textBoxTextStyle3 + drawTextStyle.getHeight() + 
							textBoxTextStyle4 + drawTextStyle.getHeight() + 
							textBoxTextStyle5;
				}
				 
			}
			
			return textStylesOOXML;
			
		} catch (Exception err) {
			log.error("[getStyles]",err);
		}
		
		return null;
	}
	
//	public String getTextSpanStyles(int startIndentSpanNumber, DrawTextStyleList drawTextStyleList) {
//		try {
//			
//			String textStylesOOXML = "";
//			
//			List<DrawTextStyle> dTextStylesSpans = drawTextStyleList.getDrawTextStylesSpans();
//			
//			for (DrawTextStyle drawTextStyleSpane : dTextStylesSpans) {
//				//log.debug("draw Span Name: "+drawTextStyleSpane.getName());
//				if (drawTextStyleSpane.getIsBold()) {
//					textStylesOOXML += textBoxTextBoldStyle1 + drawTextStyleSpane.getName() + 
//							textBoxTextBoldStyle2 + drawTextStyleSpane.getHeight() + 
//							textBoxTextBoldStyle3 + drawTextStyleSpane.getHeight() + 
//							textBoxTextBoldStyle4 + drawTextStyleSpane.getHeight() + 
//							textBoxTextBoldStyle5;
//				} else {
//					textStylesOOXML += textBoxTextStyle1 + drawTextStyleSpane.getName() + 
//							textBoxTextStyle2 + drawTextStyleSpane.getHeight() + 
//							textBoxTextStyle3 + drawTextStyleSpane.getHeight() + 
//							textBoxTextStyle4 + drawTextStyleSpane.getHeight() + 
//							textBoxTextStyle5;
//				}
//				 
//			}
//			
//			return textStylesOOXML;
//			
//		} catch (Exception err) {
//			log.error("[getStyles]",err);
//		}
//		
//		return null;
//	}
	
	
}
