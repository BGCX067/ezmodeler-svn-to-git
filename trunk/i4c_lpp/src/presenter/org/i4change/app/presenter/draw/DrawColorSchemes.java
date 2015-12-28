package org.i4change.app.presenter.draw;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.presenter.draw.helper.DrawGraphicStyle;
import org.i4change.app.presenter.draw.helper.DrawGraphicStylesList;

/**
 * 
 * @author swagner
 *
 */
public class DrawColorSchemes {
	
	private static final Log log = LogFactory.getLog(DrawColorSchemes.class);

	private static DrawColorSchemes instance = null;

	public static synchronized DrawColorSchemes getInstance() {
		if (instance == null) {
			instance = new DrawColorSchemes();
		}
		return instance;
	}

	//Usual Obbject Style
	private static final String graphicStylePart1 = "<style:style style:name=\"";//+gr1
	private static final String graphicStylePart2 = "\" style:family=\"graphic\" style:parent-style-name=\"standard\">"+
				"<style:graphic-properties svg:stroke-width=\"";//+0.1cm
	private static final String graphicStylePart3 = "\" svg:stroke-color=\"";//+#000080
	private static final String graphicStylePart4 = "\" draw:marker-start-width=\"0.35cm\" draw:marker-end-width=\"0.35cm\" " +
				"draw:fill=\"solid\" draw:fill-color=\"";//+#00ae00
	private static final String graphicStylePart5 = "\" draw:textarea-horizontal-align=\"center\" draw:textarea-vertical-align=\"middle\" " +
				"fo:padding-top=\"0.0cm\" fo:padding-bottom=\"0.175cm\" " +
				"fo:padding-left=\"0.0cm\" fo:padding-right=\"0.3cm\" " +
				"draw:shadow=\"hidden\"/></style:style>";
	
	//Text Box Style
	private static final String graphicStyleTextBoxPart1 = "<style:style style:name=\"";//+gr2
	private static final String graphicStyleTextBoxPart2 = "\" style:family=\"graphic\" style:parent-style-name=\"standard\">";
	private static final String graphicStyleTextBoxPart3 = "<style:graphic-properties draw:stroke=\"none\" svg:stroke-color=\"#000000\" " +
									"draw:fill=\"none\" draw:fill-color=\"#ffffff\" " +
									"draw:auto-grow-height=\"true\" draw:auto-grow-width=\"false\" " +
									"fo:max-height=\"0cm\" fo:min-height=\"2.75cm\" fo:padding-top=\"0.0cm\" fo:padding-left=\"0.0cm\" /></style:style>";

	//Line Style
	private static final String lineStylePart1 = "<style:style style:name=\"";//+gr5
	private static final String lineStylePart2 = "\" style:family=\"graphic\" style:parent-style-name=\"standard\">";
	private static final String lineStylePart3 = "<style:graphic-properties ";
							//draw:marker-end=\"Arrow\" draw:marker-end-width=\"0.3cm\" 
	private static final String lineStylePart4 = "draw:textarea-horizontal-align=\"center\" draw:textarea-vertical-align=\"middle\"/></style:style>";
	
	
//	<style:style style:name="gr5" style:family="graphic" style:parent-style-name="standard">
//	<style:graphic-properties draw:marker-end="Arrow" draw:marker-end-width="0.3cm" draw:textarea-horizontal-align="center" draw:textarea-vertical-align="middle"/>
//</style:style>
	
//	<style:style style:name="gr4" style:family="graphic" style:parent-style-name="standard">
//	<style:graphic-properties draw:textarea-horizontal-align="center" draw:textarea-vertical-align="middle"/>
//</style:style>
	
	public String getStyles(DrawGraphicStylesList drawGraphicStylesList, double scaleFactor) {
		try {
			
			String graphicStylesOOXML = "";
			
			List<DrawGraphicStyle> drawGraphicStyles = drawGraphicStylesList.getDrawGraphicStyles();
			
			for (DrawGraphicStyle drawGraphicStyle : drawGraphicStyles) {
				graphicStylesOOXML += graphicStylePart1 + drawGraphicStyle.getName() + 
						graphicStylePart2 + "0.1";
				
				String strokeRGB = Integer.toHexString(drawGraphicStyle.getStroke().getRGB());
				strokeRGB = strokeRGB.substring(2, strokeRGB.length());

				graphicStylesOOXML += graphicStylePart3 + "#"+strokeRGB;
						
				String fillRGB = Integer.toHexString(drawGraphicStyle.getFill().getRGB());
				fillRGB = fillRGB.substring(2, fillRGB.length());
				
				graphicStylesOOXML += graphicStylePart4 + "#"+fillRGB + graphicStylePart5;
				
			}
			
			//Add Style for the Graphical Textbox
			DrawGraphicStyle textBoxStyle = drawGraphicStylesList.getDrawGraphicStyleTextBox();
			
			graphicStylesOOXML += graphicStyleTextBoxPart1 + textBoxStyle.getName() + 
							graphicStyleTextBoxPart2 + graphicStyleTextBoxPart3;
			
			//Add Style for Connector Component
			graphicStylesOOXML += lineStylePart1 + drawGraphicStylesList.getLineStyle().getName() +
						lineStylePart2 + lineStylePart3 +lineStylePart4;
			
			//Add Style for Shadow
			graphicStylesOOXML += graphicStylePart1 + drawGraphicStylesList.getShadowStyle().getName() + 
										graphicStylePart2 + "0.1";
	
			String strokeRGB = Integer.toHexString(drawGraphicStylesList.getShadowStyle().getStroke().getRGB());
			strokeRGB = strokeRGB.substring(2, strokeRGB.length());
		
			graphicStylesOOXML += graphicStylePart3 + "#"+strokeRGB;
					
			String fillRGB = Integer.toHexString(drawGraphicStylesList.getShadowStyle().getFill().getRGB());
			fillRGB = fillRGB.substring(2, fillRGB.length());
			
			graphicStylesOOXML += graphicStylePart4 + "#"+fillRGB + graphicStylePart5;
			
			//Add Arrow Style for connector line end
			double scaleFactorArrowEnd = 0.3 * scaleFactor;
			
			log.debug("*** scaleFactorArrowEnd1 "+scaleFactorArrowEnd);
			log.debug("*** scaleFactorArrowEnd2 "+scaleFactor);
			
			graphicStylesOOXML += lineStylePart1 + drawGraphicStylesList.getLineStyleArrow().getName() +
						lineStylePart2 + lineStylePart3 + 
						"draw:marker-end=\"Arrow\" draw:marker-end-width=\""+scaleFactorArrowEnd+"cm\" " +
						lineStylePart4;
			
			
			return graphicStylesOOXML;
			
		} catch (Exception err) {
			log.error("[getStyles]",err);
		}
		return null;
	}
	
	
}
