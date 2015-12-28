package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.remote.Application;
 
public class QuartzMailPendingOrganizations implements IQuartzMailPendingOrganizations {

	private static final Log log = LogFactory.getLog(QuartzMailPendingOrganizations.class);
	
	//Spring loaded beans
	private Usermanagement usermanagement = null;
	
	
	/**
	 * @return the usermanagement
	 */
	public Usermanagement getUsermanagement() {
		return usermanagement;
	}
	/**
	 * @param usermanagement the usermanagement to set
	 */
	public void setUsermanagement(Usermanagement usermanagement) {
		this.usermanagement = usermanagement;
	}

	private int timeout;
	  
	  /**
	   * Setter called after the ExampleJob is instantiated
	   * with the value from the JobDetailBean (5)
	   */ 
	public void setTimeout(int timeout) {
		//log.debug("QuartzSessionClear setTimeout "+timeout);
		this.timeout = timeout;
	}

	
	public QuartzMailPendingOrganizations() {
		
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.quartz.scheduler.IQuartzMailPendingOrganizations#doIt()
	 */
	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			
			//log.debug("Send OrganisationDaoImpl.getInstance().sendMailsToPendingOrgs()");
			
			if (Application.isInitialPendingMailQuartz) {
				Application.isInitialPendingMailQuartz = false;
			} else {
				//FIXME: Send Mails to Pending Users
				//OrganisationDaoImpl.getInstance().sendMailsToPendingOrgs();
				this.usermanagement.sendMailsToPendingUser();
				
			}
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
