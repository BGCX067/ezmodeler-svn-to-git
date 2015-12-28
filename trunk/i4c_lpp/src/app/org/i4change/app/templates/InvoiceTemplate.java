package org.i4change.app.templates;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class InvoiceTemplate extends VelocityLoader{
	
	private static final String tamplateName = "i4Change_Invoice_Template.html";

	private static final Log log = LogFactory.getLog(InvoiceTemplate.class);

	private InvoiceTemplate() {
		super();
	}

	private static InvoiceTemplate instance = null;

	public static synchronized InvoiceTemplate getInstance() {
		if (instance == null) {
			instance = new InvoiceTemplate();
		}
		return instance;
	}

	public String createInvoiceTemplate(){
        try {
        	
        	this.initVelocity();
        	
	        /* lets make a Context and put data into it */
	        VelocityContext context = new VelocityContext();
	        context.put("BEDRIJF", "text1");
	        context.put("ADRES", "text2");
	        context.put("PLAATS", "text3");
	        context.put("POSTCODE", "text4");
	        context.put("LAND", "text5");
	
	        /* lets render a template */
	        StringWriter w = new StringWriter();
            Velocity.mergeTemplate(tamplateName, "UTF-8", context, w );
            
            return w.toString();         
            
        } catch (Exception e ) {
        	log.error("Problem merging template : " , e );
            //System.out.println("Problem merging template : " + e );
        }
        return null;
	}
	
}
