package org.i4change.app.servlets;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.remote.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ImportDiagram extends HttpServlet {
	

	private static final Log log = LogFactory.getLog(ImportDiagram.class);

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
			
			// Get Spring context
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			
			UserDaoImpl userDaoImpl = (UserDaoImpl) context.getBean("i4change.UserDaoImpl");
			Sessionmanagement sessionmanagement = (Sessionmanagement) context.getBean("i4change.Sessionmanagement");
			
			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			if (user_level != null && user_level > 0) {
				
				String orgId = httpServletRequest.getParameter("orgId");
				
				Long organisation_id = null;
				if (orgId != null) {
					organisation_id = Long.valueOf(orgId).longValue();
				} else {
					return;
				}
				
				String exportJobString = httpServletRequest.getParameter("jobId");
				Long exportJobId = null;
				if (orgId != null) {
					exportJobId = Long.valueOf(exportJobString).longValue();
				} else {
					return;
				}
					
				ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max100 mb
				InputStream is = upload.getFileContents("Filedata");
				
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				Map diagramXML = (Map) xStream.fromXML(is);
				
				String diagramName = diagramXML.get("diagramName").toString();
				Map diagramMapObj = (Map) diagramXML.get("diagramMap");
				Long diagramId = Long.valueOf(diagramXML.get("diagramId").toString()).longValue();
				Long diagramType = Long.valueOf(diagramXML.get("diagramType").toString()).longValue();
				Long pTemplateId = 1L;
				
//				ExportImportJob exportImportJob = new ExportImportJob(exportJobId, new Date(), diagramMapObj, diagramName, 
//						diagramId, diagramType);
				
				Application.addExportJobWithId(exportJobId,diagramMapObj, diagramName, diagramId, diagramType, pTemplateId, null, null, null);
    			
    			httpServletResponse.reset();
				httpServletResponse.resetBuffer();
				OutputStream out = httpServletResponse.getOutputStream();
				out.flush();
				out.close();
			}
			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error Importing: " + er);
			er.printStackTrace();
		}

	}

	
	private DiagramInstanceObject getWhiteBoardItem(String unique_key, Map diagramMap) throws Exception {

		// Get Spring context
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		
		DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl = (DiagramInstanceObjectDaoImpl) context.getBean("i4change.DiagramInstanceObjectDaoImpl");
		
		log.debug("getWhiteBoardItem"+unique_key+" "+diagramMap);
		log.debug("diagramMap.SIZE "+diagramMap.size());
		
		for (Iterator keyiter = diagramMap.keySet().iterator();keyiter.hasNext();) {
			Object key = keyiter.next();
			log.debug("key: "+key);
			Map whiteBoardItem = (Map) diagramMap.get(key);
			log.debug("whiteBoardItem"+whiteBoardItem);
			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
			
			log.debug("objInternalUID"+objInternalUID);
			
			if (objInternalUID.equals(unique_key)) {
				
				log.debug("Found whiteBoardItem"+whiteBoardItem);
				Long diagramInstanceObjectId = (Long) whiteBoardItem.get(whiteBoardItem.size()-2);
				log.debug("Found diagramInstanceObjectId"+diagramInstanceObjectId);
				return diagramInstanceObjectDaoImpl.getDiagramInstanceObjectById(diagramInstanceObjectId);
			}
			
		}
		
		return null;
	}
	
}
