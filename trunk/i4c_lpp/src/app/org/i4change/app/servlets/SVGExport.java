package org.i4change.app.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.OutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.i4change.app.svg.AbstractBatik;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.DOMImplementation;

public class SVGExport extends HttpServlet {

	private static final Log log = LogFactory.getLog(SVGExport.class);

	
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
			
	        // Get a DOMImplementation.
	        DOMImplementation domImpl =
	            GenericDOMImplementation.getDOMImplementation();

	        // Create an instance of org.w3c.dom.Document.
	        //String svgNS = "http://www.w3.org/2000/svg";
	        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

	        Document document = domImpl.createDocument(svgNS, "svg", null);
	        
	        // Get the root element (the 'svg' element).
	        Element svgRoot = document.getDocumentElement();

	        
	        // Set the width and height attributes on the root 'svg' element.
	        svgRoot.setAttributeNS(null, "width", "2400");
	        svgRoot.setAttributeNS(null, "height", "1600");
	        
	        AbstractBatik test = new AbstractBatik();

	        // Create an instance of the SVG Generator.
	        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

	        // Ask the test to render into the SVG Graphics2D implementation.
	        SVGGraphics2D svgGenerator2 = new SVGGraphics2D(svgGenerator);
	        test.paintDiagramRect(svgGenerator2, 100, 100, 100, 60, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasd" +
	        		"	asdasdad a  das dasdasdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");
	        
	        SVGGraphics2D svgGenerator3 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramRect(svgGenerator3, 100, 300, 100, 60, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasd" +
	        		"	asdasdad a  das dasdasdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");

	        SVGGraphics2D svgGenerator4 = new SVGGraphics2D(svgGenerator);
	        test.drawDottedLine(svgGenerator4, 150, 160, 150, 300, new Color(0,0,0));
	        
	        
	        SVGGraphics2D svgGenerator5 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramInputFlow(svgGenerator5, 300, 100, 400, 160, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasdasdasdad a  das dasd" +
	        		"	asdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");
	        
	        SVGGraphics2D svgGenerator6 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramOutputFlow(svgGenerator6, 300, 300, 400, 360, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasdasdasdad a  das dasd" +
	        		"	asdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");
	        
	        SVGGraphics2D svgGenerator7 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramIssueFlow(svgGenerator7, 500, 100, 600, 160, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasdasdasdad a  das dasd" +
	        		"	asdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd","swagner");
	        
	        SVGGraphics2D svgGenerator8 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramText(svgGenerator8, 500, 300, 600, 360, "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasdasdasdad a  das dasd" +
	        		"	asdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");
	        
	        // Finally, stream out SVG to the standard output using
	        // UTF-8 encoding.
	        boolean useCSS = true; // we want to use CSS style attributes
	        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        
	        String requestedFile = "diagram_xyz_"+new Date().getTime()+".svg";
	        
	        //OutputStream out = httpServletResponse.getOutputStream();
			//httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			//httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
	        Writer out = httpServletResponse.getWriter();
	        
	        svgGenerator.stream(out, useCSS);

			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

}
