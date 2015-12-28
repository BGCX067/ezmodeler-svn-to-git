package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.red5.server.api.scheduling.IScheduledJob;
import org.red5.server.api.scheduling.ISchedulingService;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
 
public class QuartzMailClear implements IScheduledJob {

	private static final Log log = LogFactory.getLog(QuartzMailClear.class);

	public void execute(ISchedulingService service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			//log.debug("Send Mailmanagement.getInstance().sendMails()");
			Mailmanagement.getInstance().sendMails();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
