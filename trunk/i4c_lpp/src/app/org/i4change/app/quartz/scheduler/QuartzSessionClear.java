package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.i4change.app.data.basic.Sessionmanagement;

public class QuartzSessionClear implements IQuartzSessionClear {
	
	//Spring loaded beans
	private Sessionmanagement sessionmanagement = null;

	private static final Log log = LogFactory.getLog(QuartzSessionClear.class);
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.quartz.scheduler.IQuartzSessionClear#doIt()
	 */
	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			//log.debug("QuartzSessionClear "+(new java.util.Date()));
			
			this.sessionmanagement.clearSessionTable();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	

}
