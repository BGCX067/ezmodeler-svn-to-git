package org.i4change.app.quartz.scheduler;

public interface IQuartzMailPendingOrganizations {

	public abstract void doIt();
	
	public void setTimeout(int timeout);

}