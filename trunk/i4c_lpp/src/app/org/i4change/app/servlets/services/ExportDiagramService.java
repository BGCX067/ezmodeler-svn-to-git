package org.i4change.app.servlets.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.report.GeneratePreview;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.documents.GenerateImage;
import org.i4change.app.documents.XMLExport;
import org.i4change.app.presenter.ImpressDraw;
import org.i4change.app.presenter.PresenterService;
import org.i4change.app.presenter.helper.DiagramExportBean;
import org.i4change.app.remote.Application;
import org.i4change.app.svg.TranscoderAccess;
import org.i4change.app.utils.math.CalendarPatterns;

public class ExportDiagramService implements IExportDiagramService {
	
	private static final Log log = LogFactory.getLog(ExportDiagramService.class);

	//Spring loaded Beans
	private UserDaoImpl userDaoImpl = null;
	private Sessionmanagement sessionmanagement = null;
	private GeneratePreview generatePreview = null;
	private GenerateImage generateImage = null;
	private ImpressDraw impressDraw = null;
	private PresenterService presenterService = null;
	private XMLExport xmlExport = null;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	public GeneratePreview getGeneratePreview() {
		return generatePreview;
	}
	public void setGeneratePreview(GeneratePreview generatePreview) {
		this.generatePreview = generatePreview;
	}

	public GenerateImage getGenerateImage() {
		return generateImage;
	}
	public void setGenerateImage(GenerateImage generateImage) {
		this.generateImage = generateImage;
	}
	
	public ImpressDraw getImpressDraw() {
		return impressDraw;
	}
	public void setImpressDraw(ImpressDraw impressDraw) {
		this.impressDraw = impressDraw;
	}
	
	public PresenterService getPresenterService() {
		return presenterService;
	}
	public void setPresenterService(PresenterService presenterService) {
		this.presenterService = presenterService;
	}
	
	public XMLExport getXmlExport() {
		return xmlExport;
	}
	public void setXmlExport(XMLExport xmlExport) {
		this.xmlExport = xmlExport;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IExportDiagramService#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			log.debug("sid: " + sid);

			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			if (user_level != null && user_level > 0) {
				
				String exportParamId = httpServletRequest.getParameter("exportId");
				if (exportParamId == null) {
					exportParamId = "0";
				}
				log.debug("exportParamId: " + exportParamId);
				
				String exportType = httpServletRequest.getParameter("exportType");
				if (exportType == null) {
					exportType = "svg";
				}
				log.debug("exportParamId: " + exportParamId);

				Long exportId = Long.valueOf(exportParamId).longValue();
				ExportImportJob exportJob = Application.getExportJob(exportId);

				if (!exportType.equals("xml") && exportJob != null) {
				 	
					Vector diagramMap = (Vector) exportJob.getPrintItemList();
					
					if (exportType.equals("svg")) {
						
						// Create an instance of the SVG Generator and Fill it with SVG-Data
						Map<String,Object> returnMap = generatePreview.generateImage(diagramMap, exportJob.getDiagramType());
				        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
						
						int width = (Integer) returnMap.get("width");
						int height = (Integer) returnMap.get("height");
						
						// Finally, stream out SVG to the standard output using
				        // UTF-8 encoding.
				        boolean useCSS = true; // we want to use CSS style attributes
				        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
				        
				        String requestedFile = exportJob.getDiagramName()+".svg";
				        
				        //OutputStream out = httpServletResponse.getOutputStream();
						httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
						httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
				        Writer out = httpServletResponse.getWriter();
				        
				        svgGenerator.stream(out, useCSS);					
					} else if (exportType.equals("ppt")) {
						
						String downLoadFileName = exportJob.getDiagramName()+"."+exportType;
							
						File presentation = impressDraw.generatePresentation(diagramMap, exportJob.getPTemplateId(), 
								exportJob.getDiagramType(), exportJob);
						
						this.writeFileToOutput(presentation.getAbsolutePath(), httpServletResponse, downLoadFileName);
						
						presentation.delete();
						
						//FIXME @deprecated remove this Else-Clause
					} else if (exportType.equals("ppto")) {
						
						// Create an instance of the SVG Generator and Fill it with SVG-Data
						Map<String,Object> returnMap = generatePreview.generateImage(diagramMap, exportJob.getDiagramType());
				        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
						
						int width = (Integer) returnMap.get("width");
						int height = (Integer) returnMap.get("height");
						
						
						// Finally, stream out SVG to the standard output using
				        // UTF-8 encoding.
				        boolean useCSS = true; // we want to use CSS style attributes
				        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
				        
				        String downLoadFileName = exportJob.getDiagramName()+"."+exportType;
				        
				        String fileName = "diagram_"+CalendarPatterns.getTimeForStreamId(new Date());
						String requestedFile = fileName+".svg";
						String outputFileName = fileName+".png";
						
						FileWriter outputFile = new FileWriter (Application.tempFileFir + requestedFile);
						
						svgGenerator.stream(outputFile, useCSS);	
						
						File f_in = new File(Application.tempFileFir + requestedFile);
						
						log.debug("Write To File: "+f_in.getAbsolutePath());
						
						File f_out = new File(Application.tempFileFir + outputFileName);
						DiagramExportBean dExportBean = new DiagramExportBean();
						dExportBean.setDiagramName(exportJob.getDiagramName());
						dExportBean.setWidth(width);
						dExportBean.setRawName(Application.tempFileFir + fileName+"_scale.png");
						dExportBean.setHeight(height);
						dExportBean.setDiagram(f_out);
						generateImage.convertSingleImageFromSvg(f_in.getAbsolutePath(),f_out.getAbsolutePath(),width,height);
						
						if (f_out.exists()) {
							
							LinkedList<DiagramExportBean> diagramImages = new LinkedList<DiagramExportBean> ();
							diagramImages.add(dExportBean);
							
							File presentation = presenterService.generatePresentation(diagramImages, exportJob.getPTemplateId());
							
							this.writeFileToOutput(presentation.getAbsolutePath(), httpServletResponse, downLoadFileName);
							
							presentation.delete();
							f_out.delete();
						}
					} else if (exportType.equals("jpg")) {
						
						// Create an instance of the SVG Generator and Fill it with SVG-Data
						Map<String,Object> returnMap = generatePreview.generateImage(diagramMap, exportJob.getDiagramType());
				        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
						
						String downLoadFileName = exportJob.getDiagramName()+"."+exportType;

				        File f_out = TranscoderAccess.getInstance().JPEGExport(svgGenerator, exportJob, exportType);
				        
						if (f_out.exists()) {
							
							this.writeFileToOutput(f_out.getAbsolutePath(), httpServletResponse, downLoadFileName);
							
							f_out.delete();
						}
						
					} else if (exportType.equals("png")) {
						
						// Create an instance of the SVG Generator and Fill it with SVG-Data
						Map<String,Object> returnMap = generatePreview.generateImage(diagramMap, exportJob.getDiagramType());
				        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
						
						String downLoadFileName = exportJob.getDiagramName()+"."+exportType;

				        File f_out = TranscoderAccess.getInstance().PNGExport(svgGenerator, exportJob, exportType);
				        
						if (f_out.exists()) {
							
							this.writeFileToOutput(f_out.getAbsolutePath(), httpServletResponse, downLoadFileName);
							
							f_out.delete();
						}
						
					} else {
						
						// Create an instance of the SVG Generator and Fill it with SVG-Data
						Map<String,Object> returnMap = generatePreview.generateImage(diagramMap, exportJob.getDiagramType());
				        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
						
						int width = (Integer) returnMap.get("width");
						int height = (Integer) returnMap.get("height");

						// Finally, stream out SVG to the standard output using
				        // UTF-8 encoding.
				        boolean useCSS = true; // we want to use CSS style attributes
				        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
				        
				        String downLoadFileName = exportJob.getDiagramName()+"."+exportType;
				        
				        String fileName = "diagram_"+CalendarPatterns.getTimeForStreamId(new Date());
						String requestedFile = fileName+".svg";
						String outputFileName = fileName+"."+exportType;
						
						FileWriter outputFile = new FileWriter (Application.tempFileFir + requestedFile);
						
						svgGenerator.stream(outputFile, useCSS);	
						
						File f_in = new File(Application.tempFileFir + requestedFile);
						
						log.debug("Write To File: "+f_in.getAbsolutePath());
						
						File f_out = new File(Application.tempFileFir + outputFileName);
						generateImage.convertSingleImageFromSvg(f_in.getAbsolutePath(),f_out.getAbsolutePath(),width,height);
						
						if (f_out.exists()) {
							
							this.writeFileToOutput(f_out.getAbsolutePath(), httpServletResponse, downLoadFileName);
							
							f_in.delete();
							f_out.delete();
						}
						
					} 
				} else {
					log.debug("Export Type XML");
					
					File xmlFile = xmlExport.doExport(exportJob);
					
					
//					Map diagram = new HashMap();
//					diagram.put("diagramMap", diagramMap);
//					diagram.put("diagramName", exportJob.getDiagramName());
//					diagram.put("diagramId", exportJob.getDiagramId());
//					diagram.put("diagramType", exportJob.getDiagramType());
//					
//					XStream xStream = new XStream(new XppDriver());
//	    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
//	    			String xmlString = xStream.toXML(diagram);
	    			
//	    			httpServletResponse.reset();
//					httpServletResponse.resetBuffer();
//					OutputStream out = httpServletResponse.getOutputStream();
//					httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
//					httpServletResponse.setHeader("Content-Disposition",
//							"attachment; filename=\"" + exportJob.getDiagramName() + ".xml\"");
//					
//					out.write(xmlString.getBytes());
//
//					out.flush();
//					out.close();
					
					
					//Get file and handle download
					RandomAccessFile rf = new RandomAccessFile(xmlFile.getAbsolutePath(), "r");

					httpServletResponse.reset();
					httpServletResponse.resetBuffer();
					OutputStream out = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
					httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + exportJob.getDiagramName() + "\"");
					httpServletResponse.setHeader("Content-Length", ""+ rf.length());

					byte[] buffer = new byte[1024];
					int readed = -1;

					while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
						out.write(buffer, 0, readed);
					}

					rf.close();

					out.flush();
					out.close();
				}
				
			}

		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			Date now = new Date();
			
			er.printStackTrace();
			
			StackTraceElement[] str = er.getStackTrace();
			
			String stack = "";
			for (int i=0;i<str.length;i++) {
				stack += "<m>"+str[i].getFileName()
						+" Line: "+str[i].getLineNumber()
						+" Method: "+str[i].getMethodName()
						+" Class: "+str[i].getClassName()
						+"</m>";
			}
			
			
			String xmlString = "<error>" +
					"<date>"+now.toString()+"</date>" +
					"<message>"+er.getMessage()+"</message>" +
					"<caused>"+er.getCause()+"</caused>" +
					"<stack>"+str.toString()+"</stack>" +
					"<stack2>"+stack+"</stack2>" +
					"</error>";
			
			httpServletResponse.reset();
			httpServletResponse.resetBuffer();
			OutputStream out = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			httpServletResponse.setHeader("Content-Disposition",
					"attachment; filename=\"" +"error" + ".xml\"");
			
			out.write(xmlString.getBytes());
			
			out.close();
		}

	}
	
	private void writeFileToOutput(String path, HttpServletResponse httpServletResponse, String downLoadFileName) throws Exception {
		
		//Get file and handle download
		RandomAccessFile rf = new RandomAccessFile(path, "r");

		httpServletResponse.reset();
		httpServletResponse.resetBuffer();
		OutputStream out = httpServletResponse.getOutputStream();
		httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
		httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + downLoadFileName + "\"");
		httpServletResponse.setHeader("Content-Length", ""+ rf.length());

		byte[] buffer = new byte[1024];
		int readed = -1;

		while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
			out.write(buffer, 0, readed);
		}

		rf.close();

		out.flush();
		out.close();
		
	}
}
