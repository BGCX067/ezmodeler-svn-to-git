package org.i4change.app.servlets.services;

import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.documents.GenerateImage;
import org.i4change.app.documents.GenerateThumbs;
import org.i4change.app.hibernate.beans.user.Users;
import org.i4change.app.servlets.UploadHandler;
import org.i4change.app.utils.crypt.MD5;
import org.i4change.app.utils.stringhandlers.StringComparer;

public class DocumentUploadHandlerService implements IDocumentUploadHandlerService {

	
	private static final Log log = LogFactory.getLog(UploadHandler.class);
	
	private String filesString[] = null;
	protected HashMap<String, String> fileExtensions = new HashMap<String, String>();
	protected HashMap<String, String> pdfExtensions = new HashMap<String, String>();
	protected HashMap<String, String> imageExtensions = new HashMap<String, String>();
	protected HashMap<String, String> jpgExtensions = new HashMap<String, String>();
	
	//Spring loaded Beans
	private UserDaoImpl userDaoImpl = null;
	private Sessionmanagement sessionmanagement = null;
	
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



	public DocumentUploadHandlerService() {
		fileExtensions.put("ext1", ".ppt");
		fileExtensions.put("ext2", ".odp");
		fileExtensions.put("ext3", ".odt");
		fileExtensions.put("ext4", ".sxw");
		fileExtensions.put("ext5", ".wpd");
		fileExtensions.put("ext6", ".doc");
		fileExtensions.put("ext7", ".rtf");
		fileExtensions.put("ext8", ".txt");
		fileExtensions.put("ext9", ".ods");
		fileExtensions.put("ext10", ".sxc");
		fileExtensions.put("ext11", ".xls");
		fileExtensions.put("ext12", ".sxi");

		pdfExtensions.put("ext1", ".pdf");
		pdfExtensions.put("ext2", ".ps"); //PostScript

		jpgExtensions.put("ext1", ".jpg");


		imageExtensions.put("ext1", ".png");
		imageExtensions.put("ext2", ".gif");
		imageExtensions.put("ext3", ".svg");
		imageExtensions.put("ext4", ".dpx"); //DPX
		imageExtensions.put("ext5", ".exr"); //EXR
		imageExtensions.put("ext6", ".pcd"); //PhotoCD
		imageExtensions.put("ext7", ".pcds"); //PhotoCD
		imageExtensions.put("ext8", ".psd"); //Adobe Photoshop bitmap file
		imageExtensions.put("ext9", ".tiff"); //Tagged Image File Format
		imageExtensions.put("ext10", ".ttf"); //TrueType font file
		imageExtensions.put("ext11", ".xcf"); //GIMP imag
		imageExtensions.put("ext12", ".wpg"); //Word Perfect Graphics File
		imageExtensions.put("ext13", ".txt"); //Raw text file
		imageExtensions.put("ext14", ".bmp");
		imageExtensions.put("ext15", ".ico"); //Microsoft Icon File
		imageExtensions.put("ext16", ".tga"); //Truevision Targa image
		imageExtensions.put("ext17", ".jpeg");
	}

	
	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IUploadHandlerService#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	/* (non-Javadoc)
	 * @see org.i4change.app.servlets.services.IDocumentUploadHandlerService#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	public void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, ServletContext servletContext) throws ServletException,
			IOException {
		boolean userProfilePic = false;
		try {

			if (httpServletRequest.getContentLength() > 0) {
				
				
				log.debug("uploading........ ");
				
				HashMap<String,HashMap> returnError = new HashMap<String,HashMap>();

				String sid = httpServletRequest.getParameter("sid");
				if (sid == null) {
					sid = "default";
				}

				log.debug("uploading........ sid: "+sid);
				
				Long users_id = sessionmanagement.checkSession(sid);
				Long user_level = userDaoImpl.getUserLevelByID(users_id);

				if (user_level > 0) {
					
					log.debug("uploading........ user_level: "+user_level);
					
					
					String fileHash = httpServletRequest.getParameter("fileHash");
					if (fileHash == null) {
						fileHash = "default";
					}
					
					//Get the current User-Directory

					String current_dir = servletContext.getRealPath("/");
					
					String working_dir = current_dir + "upload"
											+ File.separatorChar + "files";
					
					//System.out.println("IS USER PROFILE");
					File f2 = new File(working_dir);
					if (!f2.exists()) {
						f2.mkdir();
					}	

					log.debug("#### UploadHandler working_dir: "+ working_dir);

					//Check variable to see if this file is a presentation

					ServletMultipartRequest upload = new ServletMultipartRequest(httpServletRequest, 104857600); // max 100 mb

					InputStream is = upload.getFileContents("Filedata");

					//Date d = new Date();
					
					//String hashName = MD5.do_checksum("data"+d.getTime());
					
					String completeName = working_dir + File.separatorChar + fileHash;
					
					log.debug("##### WRITE FILE TO: "+completeName);

					FileOutputStream fos = new FileOutputStream(completeName);
					byte[] buffer = new byte[1024];
					int len = 0;

					while (len != (-1)) {
						len = is.read(buffer, 0, 1024);
						if (len != (-1))
							fos.write(buffer, 0, len);
					}

					fos.close();
					is.close();

					String ok = "200 OK";
					
					httpServletResponse.getOutputStream().write(ok.getBytes());
						
				}
			}
		} catch (Exception e) {
			System.out.println("ee " + e);
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		
		//Do Return something
		return;

	}
	
}
