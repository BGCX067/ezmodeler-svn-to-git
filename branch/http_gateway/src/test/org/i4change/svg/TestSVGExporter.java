package org.i4change.svg;

import java.awt.Color;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.servlets.SVGExport;
import org.i4change.diagram.AddDefaultDiagramTypes;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestSVGExporter extends TestCase {
	
	private static final Log log = LogFactory.getLog(AddDefaultDiagramTypes.class);	
	
	public void testGetDiagramList() {
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
	        
	        SVGExport test = new SVGExport();

	        // Create an instance of the SVG Generator.
	        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

	        // Ask the test to render into the SVG Graphics2D implementation.
	        SVGGraphics2D svgGenerator2 = new SVGGraphics2D(svgGenerator);
	        
	        test.paintDiagramRect(svgGenerator2, 100, 100, 100, 60, new Color(255,204,0), new Color(255,255,0), "Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");
	        
	        SVGGraphics2D svgGenerator3 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        test.paintDiagramRect(svgGenerator3, 100, 300, 100, 60, new Color(255,204,0), new Color(255,255,0), "Process 2 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd");

	        // Finally, stream out SVG to the standard output using
	        // UTF-8 encoding.
	        boolean useCSS = true; // we want to use CSS style attributes
	        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        
	        String requestedFile = "diagram_xyz_"+new Date().getTime()+".svg";
	        
	        //OutputStream out = httpServletResponse.getOutputStream();
			//httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			//httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
	        Writer out = new OutputStreamWriter(System.out, "UTF-8");

	        
	        svgGenerator.stream(out, useCSS);

			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

}
