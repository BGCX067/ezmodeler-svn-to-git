package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.i4change.app.data.basic.Mailmanagement;
 
public class QuartzMailClear {

	private static final Log log = LogFactory.getLog(QuartzMailClear.class);
	
	
	public QuartzMailClear() {
		 
	}

	public void doIt() {
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
