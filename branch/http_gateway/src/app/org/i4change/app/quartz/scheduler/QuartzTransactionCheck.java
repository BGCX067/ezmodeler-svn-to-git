package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.Usermanagement;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class QuartzTransactionCheck implements Job {

	private static final Log log = LogFactory.getLog(QuartzSessionClear.class);

	public void execute(JobExecutionContext service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			Usermanagement.getInstance().checkTransactionStatus();
			
			
		} catch (Exception err){
			log.error("execute",err);
		}
	}

}
