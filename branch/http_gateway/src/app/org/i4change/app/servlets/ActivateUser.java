package org.i4change.app.servlets;

import java.io.IOException;
import java.util.Date;

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

public class ActivateUser extends VelocityViewServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8892729047921796170L;
	private static final Log log = LogFactory.getLog(HelpExport.class);

	@Override
	public Template handleRequest(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		
		try {
			String hash = httpServletRequest.getParameter("u");
			
			if (hash == null) {
				//No hash
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				Fieldlanguagesvalues labelid843 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(843), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid843.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
			}
			//
			Users user = UserDaoImpl.getInstance().getUserByActivationHash(hash);
			
			if (user == null) {
				//No User Found with this Hash
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid843 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(843), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid843.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
				
			} else if (user.getStatus() == 1) {
				//already activated
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid844 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(844), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid844.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
				
			} else if (user.getStatus() == 0) {
				//activate
				user.setStatus(1);
				user.setUpdatetime(new Date());

				UserDaoImpl.getInstance().updateUser(user);
				
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid845 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(845), default_lang_id);
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", labelid845.getValue());
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
				
			} else {
				//unkown Status
				Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().
		        		getConfKey(3,"default_lang_id").getConf_value()).longValue();
				
				Fieldlanguagesvalues labelid846 = Fieldmanagment.getInstance().getFieldByLabelNumberAndLanguage(new Long(846), default_lang_id);
				
				ctx.put("message", "Unkown Status");
				ctx.put("link", "<a href='/i4change/'>"+ labelid846.getValue() + "</a>");
				return getVelocityEngine().getTemplate("activation_template.vm");
				
			}
			
		} catch (Exception err) {
			log.error("[ActivateUser]",err);
			err.printStackTrace();
		}
		return null;
	}

}
