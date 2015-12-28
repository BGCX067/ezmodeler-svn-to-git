package org.i4change.app.servlets.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.Context;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.user.Users;

public class UnregisterService implements IUnregisterService {

	private static final Log log = LogFactory.getLog(UnregisterService.class);
	
	//Spring loaded beans
	private Fieldmanagment fieldmanagment = null;
	private UserDaoImpl userDaoImpl = null;
	private Configurationmanagement configurationmanagement = null;
	
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


	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IUnregisterService#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.velocity.context.Context)
	 */
	public void handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		try {
			
			Long default_lang_id = Long.valueOf(configurationmanagement.
	        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			
			String uid = httpServletRequest.getParameter("uid");
			if (uid == null) {
				
				Fieldlanguagesvalues labelid942 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(942), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid942.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
			}
			
			
			Users us = userDaoImpl.getUserByUserHash(uid);
			
			
			
			if (us == null) {
				Fieldlanguagesvalues labelid942 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(942), default_lang_id);
				Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid942.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
			} else {
				
				if (us.getReceivePendingReminder() == null || us.getReceivePendingReminder()) {
					us.setReceivePendingReminder(false);
					userDaoImpl.updateUser(us);
					
					
					Fieldlanguagesvalues labelid944 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(944), default_lang_id);
					Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
					
					ctx.put("message", labelid944.getValue());
					ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
					
				} else {
				
					Fieldlanguagesvalues labelid943 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(943), default_lang_id);
					Fieldlanguagesvalues labelid846 = fieldmanagment.getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
					
					ctx.put("message", labelid943.getValue());
					ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
					
				}
			}
			
		} catch (Exception err) {
			log.error("[Unregister]",err);
		}
	}
	
}
