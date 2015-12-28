package org.i4change.paypal.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class TestJob implements Job {
	
	private static final Log log = LogFactory.getLog(TestJob.class);
 
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(context.getJobDetail().getName());
        log.debug("context.getJobDetail().getName(): "+context.getJobDetail().getName());
    }
 
}