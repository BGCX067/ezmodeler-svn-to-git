package org.i4change.app.svg;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;

public class TranscoderAccess {

	private static final Log log = LogFactory.getLog(TranscoderAccess.class);

	private static TranscoderAccess instance = null;

	public static synchronized TranscoderAccess getInstance() {
		if (instance == null) {
			instance = new TranscoderAccess();
		}
		return instance;
	}

	
	public File JPEGExport(SVGGraphics2D svgGenerator, ExportImportJob exportJob, String exportType) {
		try {
			
			// Finally, stream out SVG to the standard output using
	        // UTF-8 encoding.
	        boolean useCSS = true; // we want to use CSS style attributes
	        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        
	        String fileName = "diagram_"+CalendarPatterns.getTimeForStreamId(new Date());
			String requestedFile = fileName+".svg";
			String outputFileName = fileName+"."+exportType;
			
			//FileWriter outputFile = new FileWriter (Application.tempFileFir + requestedFile);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Application.tempFileFir + requestedFile), "UTF8"));
			
			svgGenerator.stream(bw, useCSS);	
			
			File f_in = new File(Application.tempFileFir + requestedFile);
			
			log.debug("Write To File: "+f_in.getAbsolutePath());
			
			// Create a JPEG transcoder
	        JPEGTranscoder t = new JPEGTranscoder();
	        
	        //log.debug("width|height: "+width+height);
	        
//	        t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH,new Float(width));
//	        t.addTranscodingHint(JPEGTranscoder.KEY_HEIGHT,new Float(height));
//	        t.addTranscodingHint(JPEGTranscoder.KEY_PIXEL_TO_MM,new Float(0.3528f));
//
//	        t.addTranscodingHint(JPEGTranscoder.KEY_AOI, new Rectangle(0,0,width,height));

	        // Set the transcoding hints.
	        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(1));

	        // Create the transcoder input.
	        String svgURI = f_in.toURL().toString();
	        TranscoderInput input = new TranscoderInput(svgURI);
	        
	        // Create the transcoder output.
	        OutputStream ostream = new FileOutputStream(Application.tempFileFir + outputFileName);

	        TranscoderOutput output = new TranscoderOutput(ostream);

	        // Save the image.
	        t.transcode(input, output);

	        // Flush and close the stream.
	        ostream.flush();
	        ostream.close();
	        
	        f_in.delete();
	        
	        return new File(Application.tempFileFir + outputFileName);
	        
		} catch (Exception err) {
			log.error("[JPEGExport]",err);
		}
		return null;
	}
	
	public File PNGExport(SVGGraphics2D svgGenerator, ExportImportJob exportJob, String exportType) {
		try {
			
			// Finally, stream out SVG to the standard output using
	        // UTF-8 encoding.
	        boolean useCSS = true; // we want to use CSS style attributes
	        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        
	        String fileName = "diagram_"+CalendarPatterns.getTimeForStreamId(new Date());
			String requestedFile = fileName+".svg";
			String outputFileName = fileName+"."+exportType;
			
			//FileWriter outputFile = new FileWriter (Application.tempFileFir + requestedFile);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Application.tempFileFir + requestedFile), "UTF8"));
			
			svgGenerator.stream(bw, useCSS);	
			
			File f_in = new File(Application.tempFileFir + requestedFile);
			
			log.debug("Write To File: "+f_in.getAbsolutePath());
			
			// Create a JPEG transcoder
	        PNGTranscoder t = new PNGTranscoder();
	        
	        //log.debug("width|height: "+width+height);
	        
	        // Set the transcoding hints.
	        //t.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, Color.white);
	        //t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);

	        t.addTranscodingHint(PNGTranscoder.KEY_INDEXED,new Integer(5));

	        // Create the transcoder input.
	        String svgURI = f_in.toURL().toString();
	        TranscoderInput input = new TranscoderInput(svgURI);
	        
	        // Create the transcoder output.
	        OutputStream ostream = new FileOutputStream(Application.tempFileFir + outputFileName);

	        TranscoderOutput output = new TranscoderOutput(ostream);

	        // Save the image.
	        t.transcode(input, output);

	        // Flush and close the stream.
	        ostream.flush();
	        ostream.close();
	        
	        f_in.delete();
	        
	        return new File(Application.tempFileFir + outputFileName);
	        
		} catch (Exception err) {
			log.error("[JPEGExport]",err);
		}
		return null;
	}

}
