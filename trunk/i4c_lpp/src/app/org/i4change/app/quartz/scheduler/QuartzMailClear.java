package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.i4change.app.data.basic.Mailmanagement;
 
public class QuartzMailClear implements IQuartzMailClear {

	private static final Log log = LogFactory.getLog(QuartzMailClear.class);
	
	//Spring loaded Beans
	private Mailmanagement mailmanagement;
		
	public Mailmanagement getMailmanagement() {
		return mailmanagement;
	}
	public void setMailmanagement(Mailmanagement mailmanagement) {
		this.mailmanagement = mailmanagement;
	}

	public QuartzMailClear() {
		 
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.quartz.scheduler.IQuartzMailClear#doIt()
	 */
	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			// TODO Generate report
			//log.debug("Send Mailmanagement.getInstance().sendMails()");
			this.mailmanagement.sendMails();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
