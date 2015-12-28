package org.i4change.app.remote;

 
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.ObjectIdentifierDaoImpl;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.user.daos.UserDaoImpl;


/**
 * 
 * @author swagner
 * 
 */

public class Application implements IApplication {
	
	private static final Log log = LogFactory.getLog(Application.class);

	private static Long exportImportJobId = new Long(1);
	private static HashMap<Long,ExportImportJob> exportImportJobs = new HashMap<Long,ExportImportJob>();
	
	/* 
	 * EMoticons FileList
	 */
	public static LinkedList<LinkedList<String>> emotfilesList = new LinkedList<LinkedList<String>>();
		 
	//The Global WebApp Path
	public static String webAppPath = "./i4c_lpp";
	public static String configDirName = "conf";
	
	public static Integer minBorderExport = 60; 
	
	public static Integer defaultFontRoleSize = 17; 
	
	public static Integer defaultFontSize = 11; 
	
	public static Integer defaultItalicFontSize = 10; 
	
	public static Integer defaultFontSizeBold = 14; 
	
	public static Integer defaultFontSizeFooter = 11; 
	
	//private static long broadCastCounter = 0;
	
	private static IApplication instance = null;

	//See StartUpServlet
	public static String batchFileFir = "webapps"+File.separatorChar
											+"i4change"+File.separatorChar
											+"jod" + File.separatorChar;
	public static String mergeFileFir = "webapps"+File.separatorChar
											+"i4change"+File.separatorChar
											+"joo" + File.separatorChar;
	public static String tempFileFir = "webapps"+File.separatorChar
											+"i4change"+File.separatorChar
											+"temp" + File.separatorChar;

	public static String lineSeperator = System.getProperty("line.separator");
	
	public static boolean isInitialPendingMailQuartz = true;
	
	//Spring loaded Beans
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private ObjectIdentifierDaoImpl objectIdentifierDaoImpl;
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public ObjectIdentifierDaoImpl getObjectIdentifierDaoImpl() {
		return objectIdentifierDaoImpl;
	}
	public void setObjectIdentifierDaoImpl(
			ObjectIdentifierDaoImpl objectIdentifierDaoImpl) {
		this.objectIdentifierDaoImpl = objectIdentifierDaoImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IApplication#generateExportJob(java.lang.String, java.lang.Object, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.Boolean, java.lang.Boolean)
	 */
	public Long generateExportJob(String SID, Object printObjectList, String diagramName, Long diagramId, 
			Long diagramType, Long pTemplateId, Boolean includeText,
			Boolean printIdeas, Boolean includeDetails) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return addExportJob(printObjectList, diagramName, diagramId, diagramType, pTemplateId, 
	        			includeText, printIdeas, includeDetails);
	        	
	        }
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IApplication#getNewExportJobId(java.lang.String)
	 */
	public Long getNewExportJobId(String SID){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return getExportJobId();
	        	
	        }
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
		return new Long(-1);
	}
	
	private static synchronized Long getExportJobId(){
		exportImportJobId++;
		return exportImportJobId;
	}
	
	public static synchronized Long addExportJob(Object printObjectList, String diagramName, 
			Long diagramId, Long diagramType, Long pTemplateId, Boolean includeText,
			Boolean printIdeas, Boolean includeDetails) {
		try {
			exportImportJobId++;
			exportImportJobs.put(exportImportJobId, new ExportImportJob(exportImportJobId, new Date(), printObjectList, 
					diagramName, diagramId, diagramType, pTemplateId, includeText, printIdeas, includeDetails, 1L));
			return exportImportJobId;
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
		return null;
	}
	
	public static synchronized void addExportJobWithId(Long exportJobId, Object printObjectList, 
			String diagramName, Long diagramId, Long diagramType, Long pTemplateId, Boolean includeText,
			Boolean printIdeas, Boolean includeDetails) {
		try {
			exportImportJobs.put(exportJobId, new ExportImportJob(exportJobId, new Date(), printObjectList, 
					diagramName, diagramId, diagramType, pTemplateId, includeText, printIdeas, includeDetails, 1L));
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
	}
	
	public static synchronized ExportImportJob getExportJob(Long exportJobId) {
		try {
			
			ExportImportJob exportJob = exportImportJobs.get(exportJobId);
			
			if (exportJob != null) {
				exportJob.setInserted(new Date());
				exportImportJobs.remove(exportJobId);
			}
			
			return exportJob;			
			
		} catch (Exception err) {
			log.error("[getExportJob]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IApplication#getObjectIdentifier(long)
	 */
	public Long getObjectIdentifier(long organization_id){
		return this.objectIdentifierDaoImpl.getCurrentObjectIdentifier(organization_id);
	}
	
	
}