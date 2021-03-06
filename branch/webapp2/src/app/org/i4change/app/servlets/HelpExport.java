package org.i4change.app.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;

/**
 * 
 * @author sebastianwagner
 *
 */
public class HelpExport extends HttpServlet {

	private static final Log log = LogFactory.getLog(HelpExport.class);

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
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				sid = "default";
			}
			System.out.println("sid: " + sid);
			
			String language = httpServletRequest.getParameter("language");
			if (language == null) {
				language = "0";
			}
			Long language_id = Long.valueOf(language).longValue();
			System.out.println("language_id: " + language_id);

			Long users_id = Sessionmanagement.getInstance().checkSession(sid);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);

			System.out.println("users_id: "+users_id);
			System.out.println("user_level: "+user_level);
			
			if (user_level!=null && user_level > 0) {
				List<HelpTopic> helpTopics = HelpTopicServiceDaoImpl.getInstance().getHelpTopics(new LinkedHashMap());

				
				if (helpTopics!=null) {
					Document doc = this.createDocument(helpTopics);
					
					String requestedFile = "helpTopics.xml";
					
					httpServletResponse.reset();
					httpServletResponse.resetBuffer();
					OutputStream out = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
					httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
					//httpServletResponse.setHeader("Content-Length", ""+ rf.length());
					
					this.serializetoXML(out, "UTF-8", doc);
					
					out.flush();
					out.close();
				}
			} else {
				System.out.println("ERROR LangExport: not authorized FileDownload "+(new Date()));
			}
	
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}

	public Document createDocument(List<HelpTopic> helpTopics) throws Exception {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		document.addComment(
				"###############################################\n" +
				"This File is auto-generated by the Application \n" +
				"to add new Help-Items or modify/customize it use the Application \n" +
				"###############################################");
		
		Element root = document.addElement("help");

		for (Iterator<HelpTopic> it = helpTopics.iterator();it.hasNext();) {
			HelpTopic helpTopic = it.next();
			
			String isAgentHelp = "false";
			if (helpTopic.getIsAgentHelp()) {
				isAgentHelp = "true";
			}
			
			Element eTemp = root.addElement("item")
					.addAttribute("priority", helpTopic.getPriority().toString())
					.addAttribute("labelid", helpTopic.getLabelId().toString())
					.addAttribute("isAgentHelp", isAgentHelp)
					.addAttribute("topiclabelid", helpTopic.getTopicLabelId().toString())
					.addAttribute("helpid", helpTopic.getHelpId().toString())
					.addAttribute("helpname", helpTopic.getHelpName());
			
		}

		return document;
	}	
	public void serializetoXML(OutputStream out, String aEncodingScheme, Document doc)
			throws Exception {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding(aEncodingScheme);
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}


}
