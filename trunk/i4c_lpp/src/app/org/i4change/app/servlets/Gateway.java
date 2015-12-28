package org.i4change.app.servlets;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.hibernate.beans.user.Invoice;
import org.i4change.app.http.PassArguments;
import org.i4change.app.http.beans.MessageBean;
import org.i4change.app.remote.IMainService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class Gateway extends HttpServlet {

	private static final Log log = LogFactory.getLog(Export.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		try {
			
			String service = httpServletRequest.getParameter("service");
			if (service == null) {
				service = "web.handler";
			}
			log.debug("service: " + service);
			
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			Object serviceClass = context.getBean(service);
			
			log.debug("serviceClass: " + serviceClass);
			
			String method = httpServletRequest.getParameter("method");
			if (method == null) {
				method = "default";
			}
			log.debug("method: " + method);
			
			String args = httpServletRequest.getParameter("args");
			if (args == null) {
				args = "";
			}
			log.debug("args: " + args);
			
			Map paramObj = PassArguments.getInstance().processRequest(args);
			
			
			if (paramObj == null ||paramObj.size() == 0) {
				//number of Params is zero
				//serviceClass
				
				//serviceClass
				
			}
			
			
			Object obj = null;
			if (service.equals("xmlcrm.service")) {
				IMainService mService = (IMainService) serviceClass;
				
				if (method.equals("getsessiondata")) {
					obj = mService.getsessiondata();
				}
				
				
				
			}
			
			//serviceClass
			
			MessageBean mBean = new MessageBean();
			
			if (obj == null) {
				//Error during Message Processing => return Standard Error
				
				mBean.setObj(null);
				mBean.setType("501");
				
			} else {
				
				mBean.setObj(obj);
				mBean.setType("200");
				
			}
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			xStream.alias("message", MessageBean.class);
			String xmlString = xStream.toXML(mBean);
			
			httpServletResponse.getWriter().write(xmlString);
			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

}
