package org.i4change.app.remote;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.i4change.app.quartz.scheduler.QuartzMailClear;
import org.i4change.app.quartz.scheduler.QuartzMailPendingOrganizations;
import org.i4change.app.quartz.scheduler.QuartzSessionClear;
import org.i4change.app.quartz.scheduler.QuartzTransactionCheck;
import org.i4change.app.utils.crypt.ManageCryptStyle;
import org.i4change.app.session.beans.RoomClient;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.ObjectIdentifierDaoImpl;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.user.Users;


/**
 * 
 * @author swagner
 * 
 */

public class Application {
	
	private static Long clientId = new Long(0);

	private static final Log log = LogFactory.getLog(Application.class);

	private static HashMap<String,RoomClient> ClientList = new HashMap<String,RoomClient>();
	
	private static Long exportImportJobId = new Long(1);
	private static HashMap<Long,ExportImportJob> exportImportJobs = new HashMap<Long,ExportImportJob>();
	
	/* 
	 * EMoticons FileList
	 */
	public static LinkedList<LinkedList<String>> emotfilesList = new LinkedList<LinkedList<String>>();
		
	//The Global WebApp Path
	public static String webAppPath = "./i4c_lps";
	public static String configDirName = "conf";
	
	private static long broadCastCounter = 0;
	
	private static Application instance = null;

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
	
	public static synchronized Application getInstance(){
		return instance;
	}

	public boolean appStart() {
		try {
//			webAppPath = scope.getResource("/").getFile().getAbsolutePath();			
//			scope.getResource("public/").getFile().getParentFile().getAbsolutePath();
//			String filePath = scope.getResource("public/").getFile().getAbsolutePath();
//			instance = this;
//			// init your handler here
//			//System.out.println("################## appStart    ");
//			QuartzSessionClear bwHelp = new QuartzSessionClear();
//			QuartzMailClear bwMail = new QuartzMailClear();
//			QuartzMailPendingOrganizations bwPendingOrgs = new QuartzMailPendingOrganizations();
//			QuartzTransactionCheck quartzTransactionCheck = new QuartzTransactionCheck();
//			//String jobName = addScheduledJob(300000,bwHelp);
//			
//			
//			addScheduledJob(5000,quartzTransactionCheck);
//			
//			Integer sessionClearCheckTime = Integer.valueOf(Configurationmanagement.getInstance().
//					getConfKey(3, "sessionClearCheckTime").getConf_value()).intValue();
//			
//			addScheduledJob(sessionClearCheckTime,bwHelp);
//			//addScheduledJob(100000,bwHelp);
//			
//			Integer mailSpoolCheckTime = Integer.valueOf(Configurationmanagement.getInstance().
//					getConfKey(3, "mailSpoolCheckTime").getConf_value()).intValue();
//			
//			addScheduledJob(mailSpoolCheckTime,bwMail);
//			
//			Integer reminderMailsPendingOrgs = Integer.valueOf(Configurationmanagement.getInstance().
//					getConfKey(3, "reminderMailsPendingOrgs").getConf_value()).intValue();
//			
//			//do that one time a days
//			//addScheduledJob(86400000,bwPendingOrgs);
//			//reminderMailsPendingOrgs = 10000;
//			//log.debug("addScheduledJob bwPendingOrgs at time in milliseconds: "+reminderMailsPendingOrgs);
//			addScheduledJob(reminderMailsPendingOrgs,bwPendingOrgs);
//			
			//Every 10 seconds, just for testing
			//String jobName = addScheduledJob(10000,bwHelp);
			//log.debug("jobName: "+jobName);
		} catch (Exception err) {
			log.error("[appStart]",err);
		}
		return true;
	}
	
	
	/**
	 * this function is invoked directly after initial connecting
	 * @return
	 */
	public String getPublicSID() {
//		IConnection current = Red5.getConnectionLocal();
//		RoomClient currentClient = ClientList.get(current.getClient().getId());	
//		return currentClient.getPublicSID();
		return "";
	}
	
	/**
	 * this function is invoked after a reconnect
	 * @param newPublicSID
	 */
	public void overwritePublicSID(String newPublicSID) {
//		IConnection current = Red5.getConnectionLocal();
//		RoomClient currentClient = ClientList.get(current.getClient().getId());	
//		currentClient.setPublicSID(newPublicSID);
//		ClientList.put(current.getClient().getId(), currentClient);
	}
	
	public Long generateExportJob(String SID, Object printObjectList, String diagramName, Long diagramId, Long diagramType) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return addExportJob(printObjectList, diagramName, diagramId, diagramType);
	        	
	        }
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
		return new Long(-1);
	}
	
	public Long getNewExportJobId(String SID){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
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
	
	public static synchronized Long addExportJob(Object printObjectList, String diagramName, Long diagramId, Long diagramType) {
		try {
			exportImportJobId++;
			exportImportJobs.put(exportImportJobId, new ExportImportJob(exportImportJobId, new Date(), printObjectList, diagramName, diagramId, diagramType));
			return exportImportJobId;
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
		return null;
	}
	
	public static synchronized void addExportJobWithId(Long exportJobId, Object printObjectList, String diagramName, Long diagramId, Long diagramType) {
		try {
			exportImportJobs.put(exportJobId, new ExportImportJob(exportJobId, new Date(), printObjectList, diagramName, diagramId, diagramType));
		} catch (Exception err) {
			log.error("[addExportJob]",err);
		}
	}
	
	public static synchronized ExportImportJob getExportJob(Long exportJobId) {
		try {
			
			ExportImportJob exportJob = exportImportJobs.get(exportJobId);
			
			if (exportJob != null) {
				//exportJob.setInserted(new Date());
				//exportJobs.remove(exportJobId);
			}
			
			return exportJob;			
			
		} catch (Exception err) {
			log.error("[getExportJob]",err);
		}
		return null;
	}
	
	public Long getObjectIdentifier(long organization_id){
		return ObjectIdentifierDaoImpl.getInstance().getCurrentObjectIdentifier(organization_id);
	}
	
	public static synchronized Long getClientId(){
		clientId++;
		return clientId;
	}

	/**
	 * @return the clientList
	 */
	public static HashMap<String, RoomClient> getClientList() {
		return ClientList;
	}
	
	public void disconnectUser(RoomClient rcl){
		try {
			ClientList.remove(rcl.getStreamid());
		} catch (Exception err) {
			log.error("[disconnectUser]",err);
		}
	}
	
}