package org.i4change.app.templates;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class RegisterUserTemplate extends VelocityLoader {
	
	private static final String tamplateName = "register_mail.vm";

	//Spring loaded beans
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	private static final Log log = LogFactory.getLog(RegisterUserTemplate.class);

	public String getRegisterUserTemplate(String username, String userpass, String email, Long default_lang_id, String activation_link){
        try {
        	
        	this.initVelocity();
        	
        	Fieldlanguagesvalues labelid506 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(506), default_lang_id);
        	Fieldlanguagesvalues labelid507 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(507), default_lang_id);
        	Fieldlanguagesvalues labelid508 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(508), default_lang_id);
        	Fieldlanguagesvalues labelid509 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(509), default_lang_id);
        	Fieldlanguagesvalues labelid510 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(510), default_lang_id);
        	Fieldlanguagesvalues labelid511 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(511), default_lang_id);
        	Fieldlanguagesvalues labelid839 = this.fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(839), default_lang_id);
        	
        	
	        /* lets make a Context and put data into it */
	        VelocityContext context = new VelocityContext();
	
	        context.put("username", username);
	        context.put("userpass", userpass);
	        context.put("mail", email);
	        context.put("activation_link", activation_link);
	        context.put("labelid506", labelid506.getValue());
	        context.put("labelid507", labelid507.getValue());
	        context.put("labelid508", labelid508.getValue());
	        context.put("labelid509", labelid509.getValue());
	        context.put("labelid510", labelid510.getValue());
	        context.put("labelid511", labelid511.getValue());
	        context.put("labelid839", labelid839.getValue());
	
	        /* lets render a template */
	
	        StringWriter w = new StringWriter();
	        
	        
	        
            Velocity.mergeTemplate(tamplateName, "UTF-8", context, w );
            
//            System.out.println(" template : " + w );
            
            return w.toString();         
            
        } catch (Exception e ) {
        	log.error("Problem merging template : " , e );
//            System.out.println("Problem merging template : " + e );
        }
        return null;
	}
}
