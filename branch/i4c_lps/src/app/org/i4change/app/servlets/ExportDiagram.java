package org.i4change.app.servlets;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.report.GeneratePreview;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.documents.GenerateImage;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ExportDiagram extends AbstractBatikServlet {

	private static final Log log = LogFactory.getLog(ExportDiagram.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			
			Image arrow_line_right = null;
			Image arrow_line_left = null;
			Image arrow_line_down = null;
			Image arrow_line_up = null;
			
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			log.debug("sid: " + sid);

			Long users_id = Sessionmanagement.getInstance().checkSession(sid);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);

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
					
					Map diagramMap = (Map) exportJob.getPrintItemList();
					
					// Create an instance of the SVG Generator and Fill it with SVG-Data
					Map<String,Object> returnMap = GeneratePreview.getInstance().generateImage(diagramMap, exportJob.getDiagramType());
			        SVGGraphics2D svgGenerator = (SVGGraphics2D) returnMap.get("svgGenerator");
					
					int width = (Integer) returnMap.get("width");
					int height = (Integer) returnMap.get("height");
					
					if (exportType.equals("svg")) {
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
					} else {

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
						if (f_in.exists()) {
							GenerateImage.getInstance().convertSingleImageFromSvg(f_in.getAbsolutePath(),f_out.getAbsolutePath(),width,height);
							
							//Get file and handle download
							RandomAccessFile rf = new RandomAccessFile(f_out.getAbsolutePath(), "r");

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
							
							f_in.delete();
							f_out.delete();
						}
						
					}
				} else {
					log.debug("Export Type XML");
					
					
					Map diagramMap = (Map) exportJob.getPrintItemList();
					
					Map diagram = new HashMap();
					diagram.put("diagramMap", diagramMap);
					diagram.put("diagramName", exportJob.getDiagramName());
					diagram.put("diagramId", exportJob.getDiagramId());
					diagram.put("diagramType", exportJob.getDiagramType());
					
					XStream xStream = new XStream(new XppDriver());
	    			xStream.setMode(XStream.NO_REFERENCES);
	    			String xmlString = xStream.toXML(diagram);
	    			
	    			httpServletResponse.reset();
					httpServletResponse.resetBuffer();
					OutputStream out = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
					httpServletResponse.setHeader("Content-Disposition",
							"attachment; filename=\"" + exportJob.getDiagramName() + ".xml\"");
					
					out.write(xmlString.getBytes());

					out.flush();
					out.close();
				}
				
			}

		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}

	}

}
