package org.i4change.app.templates;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

public class PaymentReceivedTemplate extends VelocityLoader{
	
	private static final String tamplateName = "paymentReceived.vm";

	private static final Log log = LogFactory.getLog(PaymentReceivedTemplate.class);

	private PaymentReceivedTemplate() {
		super();
	}

	private static PaymentReceivedTemplate instance = null;

	public static synchronized PaymentReceivedTemplate getInstance() {
		if (instance == null) {
			instance = new PaymentReceivedTemplate();
		}
		return instance;
	}

	public String getResetPasswordTemplate(String textHeader, String textBody){
        try {
        	
        	this.initVelocity();
        	
	        /* lets make a Context and put data into it */
	        VelocityContext context = new VelocityContext();
	        context.put("labelid1136", textHeader);
	        context.put("labelid1137", textBody);
	
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
