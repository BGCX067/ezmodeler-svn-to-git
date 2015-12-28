package org.i4change.paypal.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerListener;

public class TransactionCheckJob implements Job {

	private static final Log log = LogFactory.getLog(TransactionCheckJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			//Do Update active Transactions
			log.debug("Transaction Search Start");
			SearchTransaction.getInstance().getTransactions();
		} catch (Exception err){
			log.error("execute",err);
		}
	}
	
}
