package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.remote.Application;
 
public class QuartzMailPendingOrganizations implements IScheduledJob {

	private static final Log log = LogFactory.getLog(QuartzMailPendingOrganizations.class);

	public void execute(ISchedulingService service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			
			log.debug("Send OrganisationDaoImpl.getInstance().sendMailsToPendingOrgs()");
			
			if (Application.isInitialPendingMailQuartz) {
				Application.isInitialPendingMailQuartz = false;
			} else {
				//FIXME: Send Mails to Pending Users
				//OrganisationDaoImpl.getInstance().sendMailsToPendingOrgs();
				Usermanagement.getInstance().sendMailsToPendingUser();
				
			}
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
