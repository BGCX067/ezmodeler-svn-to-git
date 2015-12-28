package org.i4change.app.remote;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.daos.DownloadHashDaoImpl;
import org.i4change.app.data.user.daos.InvoiceDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.user.Invoice;

public class DownloadService implements IDownloadservice {

	private static final Log log = LogFactory.getLog(DownloadService.class);
	
	//Spring beans
	private DownloadHashDaoImpl downloadHashDaoImpl;
	private IApplication application;
	private InvoiceDaoImpl invoiceDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	
	public DownloadHashDaoImpl getDownloadHashDaoImpl() {
		return downloadHashDaoImpl;
	}
	public void setDownloadHashDaoImpl(DownloadHashDaoImpl downloadHashDaoImpl) {
		this.downloadHashDaoImpl = downloadHashDaoImpl;
	}

	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
		this.application = application;
	}
	
	public InvoiceDaoImpl getInvoiceDaoImpl() {
		return invoiceDaoImpl;
	}
	public void setInvoiceDaoImpl(InvoiceDaoImpl invoiceDaoImpl) {
		this.invoiceDaoImpl = invoiceDaoImpl;
	}
	
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
	
	private DownloadService() {}

	private static IDownloadservice instance = null;

	public static synchronized IDownloadservice getInstance() {
		if (instance == null) {
			instance = new DownloadService();
		}
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDownloadservice#generateDownLoadLinkInvoiceSelf(java.lang.String, java.lang.Long)
	 */
	public String generateDownLoadLinkInvoiceSelf(String SID, Long invoiceId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	Invoice invoice = this.invoiceDaoImpl.getInvoiceById(invoiceId);
	        	
	        	if (invoice != null) {
	        		
	        		if (invoice.getInvoiceNumber() == null) {
	        			//Transaction no completed
	        			return "";
	        		}
	        		
	        		//check if the User is really the one who is owner of this invoice
	        		if (invoice.getTransactionPaypal().getUsers().getUser_id().equals(users_id)) {
	        			
	        			String destinationFolder = Application.webAppPath + File.separatorChar 
												+ "upload" + File.separatorChar 
												+ "user_"+invoice.getTransactionPaypal().getUsers().getUser_id() + File.separatorChar;
	        			
	        			String fileFullPath = destinationFolder + invoice.getInvoiceFileName();
	        			
	        			return this.downloadHashDaoImpl.addDownloadHash(fileFullPath);
	        			
	        		}
	        		
	        	}
	        	
	        }
		} catch (Exception err) {
			log.error("[generateDownLoadLinkInvoiceSelf]",err);
		}
		return null;
	}

}
