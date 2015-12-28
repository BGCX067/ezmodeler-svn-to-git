package org.i4change.app.presenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.i4change.app.data.export.dao.PresentationTemplateDaoImpl;
import org.i4change.app.documents.GenerateImage;
import org.i4change.app.documents.GeneratePDF;
import org.i4change.app.file.FileUtil;
import org.i4change.app.file.ZipUtil;
import org.i4change.app.hibernate.beans.export.PresentationTemplate;
import org.i4change.app.presenter.helper.DiagramExportBean;
import org.i4change.app.remote.Application;

/**
 * 
 * @author swagner
 *
 */
public class PresenterService {

	private static final Log log = LogFactory.getLog(PresenterService.class);

	//Spring managed beans
	private PresentationTemplateDaoImpl presentationTemplateDaoImpl = null;
	private GenerateImage generateImage;
	private GeneratePDF generatePDF;
	
	public GeneratePDF getGeneratePDF() {
		return generatePDF;
	}
	public void setGeneratePDF(GeneratePDF generatePDF) {
		this.generatePDF = generatePDF;
	}
	
	public GenerateImage getGenerateImage() {
		return generateImage;
	}
	public void setGenerateImage(GenerateImage generateImage) {
		this.generateImage = generateImage;
	}
	
	public PresentationTemplateDaoImpl getPresentationTemplateDaoImpl() {
		return presentationTemplateDaoImpl;
	}
	public void setPresentationTemplateDaoImpl(
			PresentationTemplateDaoImpl presentationTemplateDaoImpl) {
		this.presentationTemplateDaoImpl = presentationTemplateDaoImpl;
	}

	public File generatePresentation(LinkedList<DiagramExportBean> diagramImagesRaw, Long presentationTemplateId) {
		try {
			
			PresentationTemplate pTemplate = this.presentationTemplateDaoImpl.getPresentationTemplateById(presentationTemplateId);
			
			LinkedList<DiagramExportBean> diagramImages = new LinkedList<DiagramExportBean>();
			
			//Resize Images to actuall needed ones
			//GenerateImage.getInstance().resizeSingleImage(inputFile, outputfile, width, height)
			for (Iterator<DiagramExportBean> iter = diagramImagesRaw.iterator();iter.hasNext();) {
				DiagramExportBean diagramImageRaw = iter.next();
				
				double widthInCM = pTemplate.getImageWidth();
				//Formel: Groesse in cm * 72 dpi / 2,54 = Mass in Pixel; 
				int widthInPx = (Double.valueOf(widthInCM * 72 / 2.54).intValue());
				log.debug("widthInPx: "+widthInPx);
				
				float factor = Float.valueOf(diagramImageRaw.getWidth()) / Float.valueOf(diagramImageRaw.getHeight());
				
				int heightInPx =  Math.round(widthInPx / factor);
				
				log.debug("heightInPx: "+heightInPx);
				
				this.generateImage.resizeSingleImage(diagramImageRaw.getDiagram().getAbsolutePath(), 
						diagramImageRaw.getRawName(), widthInPx, heightInPx);
			
				DiagramExportBean diagramImage = new DiagramExportBean();
				diagramImage.setWidth(widthInPx);
				diagramImage.setHeight(heightInPx);
				diagramImage.setDiagram(new File(diagramImageRaw.getRawName()));
				diagramImage.setDiagramName(diagramImageRaw.getDiagramName());
				
				diagramImages.add(diagramImage);
			}
			
			
			
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
			
			//Copy Images to Presentation Structure
            for (Iterator<DiagramExportBean> iter = diagramImages.iterator();iter.hasNext();) {
            	DiagramExportBean diagramImage = iter.next();
				String slideFile = "Pictures" + File.separatorChar + diagramImage.getDiagram().getName();
				File destination = new File(outputRootPath + File.separatorChar + slideFile);
				FileUtil.copy(diagramImage.getDiagram(), destination);
				imagePathRootEntry += "<manifest:file-entry manifest:media-type=\"image/png\" manifest:full-path=\""+slideFile+"\"/>";
            }
			
			context_mani.put("PICTURES", imagePathRootEntry);
			
			StringWriter w_mani = new StringWriter();
			Velocity.mergeTemplate(manifestXML, "UTF-8", context_mani, w_mani );
			
			FileOutputStream fos_mani = new FileOutputStream(outputRootPath + File.separatorChar 
					+ "META-INF" + File.separatorChar + "manifest.xml");
			fos_mani.write(w_mani.toString().getBytes());
			fos_mani.close();
			
			
			//Generate Slides
			String slideContent = "";
			
			log.debug("Number of Slides: "+diagramImages.size());
			
			for (Iterator<DiagramExportBean> iter = diagramImages.iterator();iter.hasNext();) {
				DiagramExportBean diagramImage = iter.next();
				
				String slide = pTemplate.getHeadSlide();
				slide += diagramImage.getDiagramName();
				slide += pTemplate.getMidSlide();
				
				//Add image of Diagram in scaled propertions
				String image = pTemplate.getImagePre();
				
				log.debug(diagramImage.getWidth());
				log.debug(diagramImage.getHeight());
				 
				float factor = Float.valueOf(diagramImage.getWidth()) / Float.valueOf(diagramImage.getHeight());
				
				log.debug("float factor: "+factor);
				
				double widthInCM = pTemplate.getImageWidth();
				double heightInCM =  Math.round(widthInCM / factor);
				
				log.debug("int heightInCM: "+heightInCM);
				log.debug("int pTemplate.getImageHeight(): "+pTemplate.getImageHeight());
				
				if (heightInCM > pTemplate.getImageHeight()) {
					heightInCM = pTemplate.getImageHeight();
					widthInCM =  Math.round(heightInCM * factor);
				}
				if (heightInCM <= 0) {
					heightInCM = pTemplate.getImageHeight();
				}
				
				log.debug("Actual W|H: "+widthInCM+"|"+heightInCM);
				
				String slideFile = "Pictures" + File.separatorChar + diagramImage.getDiagram().getName();
				
				image += " svg:width=\""+widthInCM+"cm\"" +
						" svg:height=\""+heightInCM+"cm\"" +
						" svg:x=\""+pTemplate.getImageX()+"cm\"" +
						" svg:y=\""+pTemplate.getImageY()+"cm\">";
				image += "<draw:image xlink:href=\""+slideFile+"\" xlink:type=\"simple\" xlink:show=\"embed\" xlink:actuate=\"onLoad\">"
						+ "<text:p/></draw:image></draw:frame>";
				
				slide += image;
				
				slide += pTemplate.getFootSlide();
				
				log.debug("ADD SLIDE"+slide);
				
				slideContent += slide;
			}
			
			
			//Write Content File
			String contentXML = templateName + "_content.xml";
			VelocityContext context = new VelocityContext();
			context.put("SLIDES", slideContent);
			
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
			log.error("[generatePresenation]",err);
		}
		return null;
	}
	
}
