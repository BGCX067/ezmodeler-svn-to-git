package org.i4change.app.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.documents.GenerateDocument;
import org.i4change.app.documents.GeneratePDF;
import org.i4change.app.payment.GenerateInvoice;
import org.i4change.app.remote.Application;
import org.i4change.app.templates.InvoiceTemplate;
import org.i4change.app.utils.math.CalendarPatterns;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @deprecated
 */
public class CreatePaper extends HttpServlet {
	
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
			log.debug("sid: " + sid);
			
			String language = httpServletRequest.getParameter("language");
			if (language == null) {
				language = "0";
			}
			Long language_id = Long.valueOf(language).longValue();
			log.debug("language_id: " + language_id);

			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			
			Sessionmanagement sessionmanagement = (Sessionmanagement) context.getBean("i4change.Sessionmanagement");
			UserDaoImpl userDaoImpl = (UserDaoImpl) context.getBean("i4change.UserDaoImpl");
			
			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			users_id = 1L;
			
			log.debug("users_id: "+users_id);
			log.debug("user_level: "+user_level);
			
			String requestedFile = "myInvoice_"+CalendarPatterns.getTimeForStreamId(new Date());
			
			GenerateInvoice generateInvoice = (GenerateInvoice) context.getBean("i4change.GenerateInvoice");
			generateInvoice.generateInvoiceByTransaction(null);
//			
//			String fileAsString = InvoiceTemplate.getInstance().createInvoiceTemplate();
//			//PrintWriter pwriter = new PrintWriter(fileAsString);
			
//			String destinationFolder = Application.webAppPath + File.separatorChar 
//										+ "upload" + File.separatorChar 
//										+ "user_"+users_id + File.separatorChar;
//			
//			File userDir = new File(destinationFolder);
//			if (!userDir.exists()) {
//				userDir.mkdir();
//			}
//			String fileFullPath = destinationFolder + requestedFile + ".html";
//			String outputfile = requestedFile;
//			
//			log.debug("##### WRITE FILE TO: "+fileFullPath);
//			
//			//File f = new File(fileFullPath);
//
//			FileOutputStream fos = new FileOutputStream(fileFullPath);
//			fos.write(fileAsString.getBytes());
//			fos.close();

			//GeneratePDF.getInstance().doConvertExec(Application.webAppPath + File.separatorChar, fileFullPath, destinationFolder, outputfile);
			
//			String fileFullPath = Application.webAppPath + File.separatorChar 
//								+ "WEB-INF" + File.separatorChar;
//			
//			String fileFullPath_template = fileFullPath + "order-template.odt";
//			String fileFullPath_mergeXML = fileFullPath + "order-data.xml";
//			String fileFullPath_output = fileFullPath + "order-template.pdf";
//			
//			GenerateDocument.getInstance().doConvertExec(Application.webAppPath + File.separatorChar, 
//						fileFullPath_template, fileFullPath_output, fileFullPath_mergeXML);
//			
//			//Get file and handle download
//			RandomAccessFile rf = new RandomAccessFile(fileFullPath_output, "r");
//			
//			httpServletResponse.reset();
//			httpServletResponse.resetBuffer();
//			OutputStream out = httpServletResponse.getOutputStream();
//			httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
//			httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + ".pdf\"");
//			httpServletResponse.setHeader("Content-Length", ""+ rf.length());
//
//			byte[] buffer = new byte[1024];
//			int readed = -1;
//
//			while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
//				out.write(buffer, 0, readed);
//			}
//
//			rf.close();
//
//			out.flush();
//			out.close();

			//out.flush();
			//out.close();
			
		} catch (Exception er) {
			log.error("ERROR ", er);
			er.printStackTrace();
		}
	}

		

}
