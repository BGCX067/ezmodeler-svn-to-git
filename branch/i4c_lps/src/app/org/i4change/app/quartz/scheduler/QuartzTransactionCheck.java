package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.Usermanagement;

public class QuartzTransactionCheck {

	private static final Log log = LogFactory.getLog(QuartzTransactionCheck.class);
	
	public QuartzTransactionCheck() {
		
	}

	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			//log.debug("QuartzTransactionCheck : "+(new java.util.Date()));
			// TODO Generate report
			Usermanagement.getInstance().checkTransactionStatus();
			
			
		} catch (Exception err){
			log.error("execute",err);
		}
	}

}
