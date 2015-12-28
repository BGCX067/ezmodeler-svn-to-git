package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import org.i4change.app.data.basic.Mailmanagement;
 
public class QuartzMailClear implements Job {

	private static final Log log = LogFactory.getLog(QuartzMailClear.class);

	public void execute(JobExecutionContext service) {
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
