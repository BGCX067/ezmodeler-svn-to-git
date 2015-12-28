package org.i4change.app.servlets.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.daos.DownloadHashDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.basic.DownloadHash;
import org.i4change.app.servlets.DownloadHashHandler;

public class DownloadHashHandlerService implements IDownloadHashHandlerService {

	private static final Log log = LogFactory.getLog(DownloadHashHandlerService.class);
	
	//Spring loaded Beans
	private UserDaoImpl userDaoImpl = null;
	private Sessionmanagement sessionmanagement = null;
	private DownloadHashDaoImpl downloadHashDaoImpl = null;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	public DownloadHashDaoImpl getDownloadHashDaoImpl() {
		return downloadHashDaoImpl;
	}
	public void setDownloadHashDaoImpl(DownloadHashDaoImpl downloadHashDaoImpl) {
		this.downloadHashDaoImpl = downloadHashDaoImpl;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IDownloadHashHandlerService#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			String sid = httpServletRequest.getParameter("sid");
			if (sid == null) {
				return;
			}
			
			String hash = httpServletRequest.getParameter("hash");
			if (hash == null) {
				return;
			}
			
			log.debug("sid: " + sid+" hash: "+hash);

			Long users_id = sessionmanagement.checkSession(sid);
			Long user_level = userDaoImpl.getUserLevelByID(users_id);

			if (user_level!=null && user_level > 0) {
				
				DownloadHash downloadHash = downloadHashDaoImpl.getDownloadHashByHash(hash);
				
				if (downloadHash == null) {
					return;
				}
				if (downloadHash.getAbsoluteFilePath() == null || downloadHash.getAbsoluteFilePath().length() == 0) {
					log.error("No File generated or Path empty Hash: "+downloadHash.getHash());
				}
				
				File f = new File(downloadHash.getAbsoluteFilePath());
				
				if (!f.exists()) {
					log.error("File does not exist: "+downloadHash.getHash());
				}
				
				String ip = httpServletRequest.getRemoteAddr();
				
				//Get file and handle download
				RandomAccessFile rf = new RandomAccessFile(f.getAbsolutePath(), "r");

				httpServletResponse.reset();
				httpServletResponse.resetBuffer();
				OutputStream out = httpServletResponse.getOutputStream();
				httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
				httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + f.getName() + "\"");
				httpServletResponse.setHeader("Content-Length", ""+ rf.length());

				byte[] buffer = new byte[1024];
				int readed = -1;

				while ((readed = rf.read(buffer, 0, buffer.length)) > -1) {
					out.write(buffer, 0, readed);
				}

				rf.close();

				out.flush();
				out.close();
				
				downloadHash.setIp(ip);
				downloadHash.setDownloaded(new Date());
				downloadHash.setUsed(true);
				
				downloadHashDaoImpl.updateDownloadHash(downloadHash);

			} else {
				System.out.println("ERROR DownloadHandler: not authorized FileDownload "+(new Date()));
			}

		} catch (Exception er) {
			log.error("Error downloading: " , er);
			
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			Date now = new Date();
			
			er.printStackTrace();
			
			StackTraceElement[] str = er.getStackTrace();
			
			String stack = "";
			for (int i=0;i<str.length;i++) {
				stack += "<m>"+str[i].getFileName()
						+" Line: "+str[i].getLineNumber()
						+" Method: "+str[i].getMethodName()
						+" Class: "+str[i].getClassName()
						+"</m>";
			}
			
			
			String xmlString = "<error>" +
					"<date>"+now.toString()+"</date>" +
					"<message>"+er.getMessage()+"</message>" +
					"<caused>"+er.getCause()+"</caused>" +
					"<stack>"+str.toString()+"</stack>" +
					"<stack2>"+stack+"</stack2>" +
					"</error>";
			
			httpServletResponse.reset();
			httpServletResponse.resetBuffer();
			OutputStream out = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			httpServletResponse.setHeader("Content-Disposition",
					"attachment; filename=\"" +"error" + ".xml\"");
			
			out.write(xmlString.getBytes());
			
			out.close();
		}
	}
	
	private String getChatUserName(String userprofile_folder) throws Exception{
		
		File f = new File(userprofile_folder);
		if (f.exists() && f.isDirectory()) {
			String filesString[] = f.list();
			for (int i=0;i<filesString.length;i++) {
				String fileName = filesString[i];
				if (fileName.startsWith("_chat_")) return fileName;
			}
		}
		return "_no.jpg";
	}
	
	private String getBigProfileUserName(String userprofile_folder) throws Exception{
		
		File f = new File(userprofile_folder);
		if (f.exists() && f.isDirectory()) {
			String filesString[] = f.list();
			for (int i=0;i<filesString.length;i++) {
				String fileName = filesString[i];
				if (fileName.startsWith("_big_")) return fileName;
			}
		}
		return "_no.jpg";
	}	
	
}
