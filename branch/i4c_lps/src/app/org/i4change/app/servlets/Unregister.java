package org.i4change.app.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.beans.user.Users;

public class Unregister extends VelocityViewServlet {

	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(Unregister.class);

	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		
		try {
			
			Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
	        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
			
			
			String uid = httpServletRequest.getParameter("uid");
			if (uid == null) {
				
				Fieldlanguagesvalues labelid942 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(942), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid942.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
			}
			
			
			Users us = UserDaoImpl.getInstance().getUserByUserHash(uid);
			
			
			
			if (us == null) {
				Fieldlanguagesvalues labelid942 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(942), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid942.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
			} else {
				
				if (us.getReceivePendingReminder() == null || us.getReceivePendingReminder()) {
					us.setReceivePendingReminder(false);
					UserDaoImpl.getInstance().updateUser(us);
					
					
					Fieldlanguagesvalues labelid944 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(944), default_lang_id);
					Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
					
					ctx.put("message", labelid944.getValue());
					ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
					return getVelocityEngine().getTemplate("activation_template.vm");
					
				} else {
				
					Fieldlanguagesvalues labelid943 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(943), default_lang_id);
					Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
					
					ctx.put("message", labelid943.getValue());
					ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
					return getVelocityEngine().getTemplate("activation_template.vm");
					
				}
			}
			
		} catch (Exception err) {
			log.error("[Unregister]",err);
		}
		return null;
	}

}
