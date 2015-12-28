package org.i4change.app.presenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.export.dao.PresentationTemplateDaoImpl;
import org.i4change.app.documents.GeneratePDF;
import org.i4change.app.file.FileUtil;
import org.i4change.app.file.ZipUtil;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.presenter.draw.DrawColorSchemes;
import org.i4change.app.presenter.draw.DrawTextSchemes;
import org.i4change.app.presenter.draw.GenerateOOSVG;
import org.i4change.app.presenter.draw.GenerateOOSVGDetails;
import org.i4change.app.presenter.draw.GenerateOOSVGFooter;
import org.i4change.app.remote.Application;
import org.i4change.app.presenter.draw.helper.DiagramDimension;
import org.i4change.app.presenter.draw.helper.DrawGraphicStyle;
import org.i4change.app.presenter.draw.helper.DrawGraphicStylesList;
import org.i4change.app.presenter.draw.helper.DrawTextStyleList;

/**
 * 
 * @author swagner
 *
 */
public class ImpressDraw {
	
	private static final Log log = LogFactory.getLog(ImpressDraw.class);

	private static ImpressDraw instance = null;

	private ImpressDraw() {
	}

	public static synchronized ImpressDraw getInstance() {
		if (instance == null) {
			instance = new ImpressDraw();
		}
		return instance;
	}

	//Spring managed beans
	private PresentationTemplateDaoImpl presentationTemplateDaoImpl = null;
	private GenerateOOSVG generateOOSVG;
	private DrawTextSchemes drawTextSchemes;
	private DrawColorSchemes drawColorSchemes;
	private GenerateOOSVGFooter generateOOSVGFooter;
	private GenerateOOSVGDetails generateOOSVGDetails;
	private GeneratePDF generatePDF;
	
	public GeneratePDF getGeneratePDF() {
		return generatePDF;
	}
	public void setGeneratePDF(GeneratePDF generatePDF) {
		this.generatePDF = generatePDF;
	}

	public PresentationTemplateDaoImpl getPresentationTemplateDaoImpl() {
		return presentationTemplateDaoImpl;
	}
	public void setPresentationTemplateDaoImpl(
			PresentationTemplateDaoImpl presentationTemplateDaoImpl) {
		this.presentationTemplateDaoImpl = presentationTemplateDaoImpl;
	}
	public GenerateOOSVG getGenerateOOSVG() {
		return generateOOSVG;
	}
	public void setGenerateOOSVG(GenerateOOSVG generateOOSVG) {
		this.generateOOSVG = generateOOSVG;
	}
	
	public GenerateOOSVGFooter getGenerateOOSVGFooter() {
		return generateOOSVGFooter;
	}

	public void setGenerateOOSVGFooter(GenerateOOSVGFooter generateOOSVGFooter) {
		this.generateOOSVGFooter = generateOOSVGFooter;
	}

	public DrawTextSchemes getDrawTextSchemes() {
		return drawTextSchemes;
	}
	public void setDrawTextSchemes(DrawTextSchemes drawTextSchemes) {
		this.drawTextSchemes = drawTextSchemes;
	} 

	public DrawColorSchemes getDrawColorSchemes() {
		return drawColorSchemes;
	}
	public void setDrawColorSchemes(DrawColorSchemes drawColorSchemes) {
		this.drawColorSchemes = drawColorSchemes;
	}
	
	public GenerateOOSVGDetails getGenerateOOSVGDetails() {
		return generateOOSVGDetails;
	}
	public void setGenerateOOSVGDetails(GenerateOOSVGDetails generateOOSVGDetails) {
		this.generateOOSVGDetails = generateOOSVGDetails;
	}

	public File generatePresentation(Vector diagramMap, Long presentationTemplateId, 
			Long diagramType, ExportImportJob exportJob) {
		try {
			
			PresentationTemplate pTemplate = this.presentationTemplateDaoImpl.getPresentationTemplateById(presentationTemplateId);
			
			String templateName = pTemplate.getTemplateKey();
			
			String rootPath = Application.webAppPath + "ppt_generation" + File.separatorChar 
									+ "raw" + File.separatorChar;
			String templateRootPath = rootPath + "templates" + File.separatorChar + templateName + File.separatorChar;
			
			String outputFile = "presentation_"+(new Date()).getTime();
			String outputRootPath = rootPath + "output" + File.separatorChar + outputFile;
			String outputPathODP = rootPath + "output" + File.separatorChar + outputFile + ".odp";
			
			//Copy Template Basic Files to OutPut Dir
			FileUtil.copy(new File(templateRootPath), new File(outputRootPath));
			
			//Init Velocity
			String current_dir = Application.webAppPath+"WEB-INF"+File.separatorChar+"classes"+File.separatorChar+"velocity.properties";	
            Velocity.init(current_dir);
            
            //Write Manifext File
			String manifestXML = templateName + "_manifest.xml";
			VelocityContext context_mani = new VelocityContext();
			String imagePathRootEntry = "<manifest:file-entry manifest:media-type=\"\" manifest:full-path=\"Pictures/\"/>";
			
			//imagePathRootEntry += "<manifest:file-entry manifest:media-type="image/png" manifest:full-path="Pictures/1000000000000310000000CB25E9A87F.png"/>"
			
			context_mani.put("PICTURES", imagePathRootEntry);
			
			StringWriter w_mani = new StringWriter();
			Velocity.mergeTemplate(manifestXML, "UTF-8", context_mani, w_mani );
			
			FileOutputStream fos_mani = new FileOutputStream(outputRootPath + File.separatorChar 
					+ "META-INF" + File.separatorChar + "manifest.xml");
			fos_mani.write(w_mani.toString().getBytes());
			fos_mani.close();
			
			//##############################################################
			//	TODO: Calculate Scaling Factor
			//##############################################################
			
			DiagramDimension diagramDimension = this.generateOOSVG.calcFactor(diagramMap, pTemplate);
			
			diagramDimension.setTemplateXIndentInCm(pTemplate.getImageX());
			diagramDimension.setTemplateYIndentInCm(pTemplate.getImageY());
			
			//Formel: Groesse in cm * 72 dpi / 2,54 = Mass in Pixel; 
			//=> Groesse in cm = (Mass in Pixel * 2,54) / 72 dpi
			
			diagramDimension.setTemplateXIndentInPx((pTemplate.getImageX() * 72) / 2.54);
			diagramDimension.setTemplateYIndentInPx((pTemplate.getImageY() * 72) / 2.54);
			
			double factorScaling = diagramDimension.getScaleFactor(); 
			
			//Calculate Text Height
	        Integer scaledFontSize = Long.valueOf(Math.round(Application.defaultFontSize * factorScaling)).intValue();
	        
	        Integer scaledRoleFontSize = Long.valueOf(Math.round(Application.defaultFontRoleSize * factorScaling)).intValue();
	        
	        Integer scaledItalicFontSize = Long.valueOf(Math.round(Application.defaultItalicFontSize * factorScaling)).intValue();
			
	        if (scaledFontSize <= 5) {
	        	scaledFontSize = 6;
	        }
	        if (scaledRoleFontSize <=5) {
	        	scaledRoleFontSize = 5;
	        }
			//##############################################################
			//	Create Styles for that PPT
			//##############################################################
			DrawGraphicStylesList drawGraphicStylesList = new DrawGraphicStylesList(pTemplate.getGraphicStyleIndent());
			
			DrawTextStyleList drawTextStyleList = new DrawTextStyleList(pTemplate.getTextStyleIndent(),pTemplate.getTextSpanStyleIndent());
			
			String graphicStylesOOXML = this.drawColorSchemes.getStyles(drawGraphicStylesList, diagramDimension.getScaleFactor());
			
			String testStylesOOXML = this.drawTextSchemes.getTextStyles(drawTextStyleList, scaledFontSize, 
					scaledRoleFontSize, scaledItalicFontSize);
			
			//String testSpanStylesOOXML = this.drawTextSchemes.getTextSpanStyles(3, drawTextStyleList);
			String testSpanStylesOOXML = "";
			
			//Generate Slides
			String slideContent = "";
			
			//##############################################################
			//	Create Overview Slide with Diagram
			//##############################################################
			log.debug("Number of Slides: 1");
				
			String slide = pTemplate.getHeadSlide();
			slide += exportJob.getDiagramName();
			slide += pTemplate.getMidSlide();
			
			//Generate the Slide Content with scaling factor
			String diagram = this.generateOOSVG.generatePreview(diagramMap, diagramType, 
					drawGraphicStylesList, drawTextStyleList, diagramDimension, 
					exportJob.getIncludeText(), exportJob.getPrintIdeas());
			
			//Generate Info with Slide Version
			log.debug("ADD Footer");
			diagram += this.generateOOSVGFooter.generateFooter(drawTextStyleList,exportJob,drawGraphicStylesList, pTemplate);
			
			slide += diagram;
			
			slide += pTemplate.getFootSlide();
			
			log.debug("ADD SLIDE"+slide);
			
			slideContent += slide;
			
			//##############################################################
			//	add additional Slides with Object Details
			//##############################################################
			if (exportJob.getIncludeDetails()) {
				
				List<String> diagramObjects = this.generateOOSVGDetails.generate(diagramMap, diagramType, drawGraphicStylesList, 
												drawTextStyleList, diagramDimension, 
												exportJob.getIncludeText(), exportJob.getPrintIdeas());
				
				for (int i=0;i<diagramObjects.size();i++) {
					
					String slideDetail = pTemplate.getHeadSlide();
					slideDetail += exportJob.getDiagramName();
					slideDetail += pTemplate.getMidSlide();
					
					slideDetail += diagramObjects.get(i);
					
					//18.394
					slideDetail += this.generateOOSVGFooter.generateFooter(drawTextStyleList,exportJob,drawGraphicStylesList, pTemplate);
					
					slideDetail += pTemplate.getFootSlide();
					
					slideContent += slideDetail;
				}
				
			}
			
			//##############################################################
			//	merge Content and write to File
			//##############################################################
			String contentXML = templateName + "_content.xml";
			VelocityContext context = new VelocityContext();
			context.put("SLIDES", slideContent);
			context.put("GRAPHICSTYLES", graphicStylesOOXML);
			context.put("TEXTSTYLES", testStylesOOXML);
			context.put("SPANSTYLES", testSpanStylesOOXML);
			
			StringWriter w = new StringWriter();
			Velocity.mergeTemplate(contentXML, "UTF-8", context, w );
			
			FileOutputStream fos = new FileOutputStream(outputRootPath + File.separatorChar + "content.xml");
			fos.write(w.toString().getBytes());
			fos.close();
			
			
			//Zip Package to ODP File again
			ZipUtil.createODPFile(outputRootPath, outputPathODP);
			
			
			//Generate Powerpoint Presentation
			this.generatePDF.doConvertExecToFormat(Application.webAppPath, 
					outputPathODP, rootPath + "output" + File.separatorChar, outputFile, "ppt");
			
			
			return new File(rootPath + "output" + File.separatorChar + outputFile + ".ppt");
			
		} catch (Exception err) {
			log.error("[generatePresentation]",err);
		}
		return null;
	}

}