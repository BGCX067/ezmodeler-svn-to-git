package org.i4change.app.templates;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

public class ResetPasswordTemplate extends VelocityLoader{
	
	private static final String tamplateName = "resetPass.vm";

	private static final Log log = LogFactory.getLog(ResetPasswordTemplate.class);

	//Spring loaded beans
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
//	private ResetPasswordTemplate() {
//		super();
//	}


	public String getResetPasswordTemplate(String reset_link, Long default_lang_id){
        try {
        	
        	this.initVelocity();
        	
        	Fieldlanguagesvalues labelid513 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(513), default_lang_id);
        	Fieldlanguagesvalues labelid514 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(514), default_lang_id);
        	Fieldlanguagesvalues labelid515 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(515), default_lang_id);
        	Fieldlanguagesvalues labelid516 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(516), default_lang_id);
        	
	        /* lets make a Context and put data into it */
	        VelocityContext context = new VelocityContext();
	        context.put("reset_link", reset_link);
	        context.put("reset_link2", reset_link);
	        context.put("labelid513", labelid513.getValue());
	        context.put("labelid514", labelid514.getValue());
	        context.put("labelid515", labelid515.getValue());
	        context.put("labelid516", labelid516.getValue());
	
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
