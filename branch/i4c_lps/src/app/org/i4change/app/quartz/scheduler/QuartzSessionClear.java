package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.i4change.app.data.basic.Sessionmanagement;

public class QuartzSessionClear {

	private static final Log log = LogFactory.getLog(QuartzSessionClear.class);
	
	public QuartzSessionClear() {
		
	}

	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			//log.debug("QuartzSessionClear "+(new java.util.Date()));
			
			// TODO Generate report
			Sessionmanagement.getInstance().clearSessionTable();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
