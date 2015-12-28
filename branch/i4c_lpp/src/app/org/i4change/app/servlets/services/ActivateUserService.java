package org.i4change.app.servlets.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.Context;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.mail.MailItemServiceDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.servlets.HelpExport;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ActivateUserService implements IActivateUserService {
	
	private static final Log log = LogFactory.getLog(DownloadHashHandlerService.class);
	
	//Spring Loaded Beans
	private Fieldmanagment fieldmanagment = null;
	private UserDaoImpl userDaoImpl = null;
	private Configurationmanagement configurationmanagement = null;
	private MailItemServiceDaoImpl mailItemServiceDaoImpl = null;
	
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public MailItemServiceDaoImpl getMailItemServiceDaoImpl() {
		return mailItemServiceDaoImpl;
	}
	public void setMailItemServiceDaoImpl(
			MailItemServiceDaoImpl mailItemServiceDaoImpl) {
		this.mailItemServiceDaoImpl = mailItemServiceDaoImpl;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IActivateUserService#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.velocity.context.Context)
	 */
	public void handleRequest (HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) {
		try {
			
			String hash = httpServletRequest.getParameter("u");
			
			if (hash == null) {
				//No hash
				Long default_lang_id = Long.valueOf(configurationmanagement.
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				Fieldlanguagesvalues labelid843 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(843), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid843.getValue());
				ctx.put("link", "<a href='/i4c_lpp/i4change/'>"+ labelid846.getValue() + "</a>");
			}
			//
			Users user = userDaoImpl.getUserByActivationHash(hash);
			
			if (user == null) {
				//No User Found with this Hash
				Long default_lang_id = Long.valueOf(configurationmanagement.
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid843 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(843), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid843.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				
			} else if (user.getStatus() == 1) {
				//already activated
				Long default_lang_id = Long.valueOf(configurationmanagement.
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid844 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(844), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid844.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				
			} else if (user.getStatus() == 0) {
				//activate
				user.setStatus(1);
				user.setUpdatetime(new Date());

				userDaoImpl.updateUser(user);
				
				String content = "A User has Actived his account up:<br/> " +
								" "+user.getFirstname()+" "+user.getLastname()+" ["+user.getLogin()+"]<br/>";

				Configuration confEmailFromLogin = configurationmanagement.getConfKey(3L, "emailFromLogin");
				
				Configuration confEmailMonitor = configurationmanagement.getConfKey(3L, "emailMonitor");
				
				
				mailItemServiceDaoImpl.addMailItem(1L, 
						confEmailFromLogin.getConf_value(), //From
						confEmailMonitor.getConf_value(), //receipent
						"User " +user.getFirstname()+" "+user.getLastname()+" ["+user.getLogin()+"] has Activated his account at "+confEmailFromLogin.getConf_value(), 
						content, 
						null);
				
				Long default_lang_id = Long.valueOf(configurationmanagement.
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid845 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(845), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid845.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				
			} else {
				//unkown Status
				Long default_lang_id = Long.valueOf(configurationmanagement.
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", "Unkown Status");
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				
			}
			
		} catch (Exception err) {
			log.error("[ActivateUser]",err);
			err.printStackTrace();
		}
	}

}
