package org.i4change.app.quartz.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.paypal.payment.TransactionsCompleted;

public class QuartzTransactionCheck {

	private static final Log log = LogFactory.getLog(QuartzTransactionCheck.class);
	
	//Spring managed Beans
	private TransactionsCompleted transactionsCompleted;
	private Usermanagement usermanagement;
	
	public TransactionsCompleted getTransactionsCompleted() {
		return transactionsCompleted;
	}
	public void setTransactionsCompleted(TransactionsCompleted transactionsCompleted) {
		this.transactionsCompleted = transactionsCompleted;
	}

	public Usermanagement getUsermanagement() {
		return usermanagement;
	}
	public void setUsermanagement(Usermanagement usermanagement) {
		this.usermanagement = usermanagement;
	}

	public QuartzTransactionCheck() {
		
	}

	public void doIt() {
		try {
			
			//cntxt.getScheduler().rescheduleJob("Income Session", "SessionClear Generation", cntxt.getTrigger());
			
			//log.debug("QuartzTransactionCheck : "+(new java.util.Date()));
			
			this.transactionsCompleted.checkCompletedTransactions();
			
			// TODO Generate report
			this.usermanagement.checkTransactionStatus();
			
			
		} catch (Exception err){
			log.error("execute",err);
		}
	}

}
