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

public class DownloadService {

	private static final Log log = LogFactory.getLog(DownloadService.class);
	
	//Spring beans
	private DownloadHashDaoImpl downloadHashDaoImpl;
	private Application application;
	private InvoiceDaoImpl invoiceDaoImpl;
	
	public DownloadHashDaoImpl getDownloadHashDaoImpl() {
		return downloadHashDaoImpl;
	}
	public void setDownloadHashDaoImpl(DownloadHashDaoImpl downloadHashDaoImpl) {
		this.downloadHashDaoImpl = downloadHashDaoImpl;
	}

	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public InvoiceDaoImpl getInvoiceDaoImpl() {
		return invoiceDaoImpl;
	}
	public void setInvoiceDaoImpl(InvoiceDaoImpl invoiceDaoImpl) {
		this.invoiceDaoImpl = invoiceDaoImpl;
	}
	
	private DownloadService() {}

	private static DownloadService instance = null;

	public static synchronized DownloadService getInstance() {
		if (instance == null) {
			instance = new DownloadService();
		}
		return instance;
	}
	
	public String generateDownLoadLinkInvoiceSelf(String SID, Long invoiceId) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	Invoice invoice = this.invoiceDaoImpl.getInvoiceById(invoiceId);
	        	
	        	if (invoice != null) {
	        		
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
