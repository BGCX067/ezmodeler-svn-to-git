package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
 
public class QuartzSessionClear implements Job {

	private static final Log log = LogFactory.getLog(QuartzSessionClear.class);

	public void execute(JobExecutionContext service) {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			Sessionmanagement.getInstance().clearSessionTable();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
