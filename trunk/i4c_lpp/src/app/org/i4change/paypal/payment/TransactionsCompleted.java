package org.i4change.paypal.payment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.paypal.payment.APIProfileDaoImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.services.CallerServices;
import com.paypal.soap.api.*;

public class TransactionsCompleted {
	
	private static final Log log = LogFactory.getLog(TransactionsCompleted.class);

	private TransactionPaypalDaoImpl transactionPaypalDaoImpl;
	
//	private static TransactionsCompleted instance = null;
//
//	private TransactionsCompleted() {
//	}
//
//	public static synchronized TransactionsCompleted getInstance() {
//		if (instance == null) {
//			instance = new TransactionsCompleted();
//		}
//		return instance;
//	}
	
	public TransactionPaypalDaoImpl getTransactionPaypalDaoImpl() {
		return transactionPaypalDaoImpl;
	}
	public void setTransactionPaypalDaoImpl(
			TransactionPaypalDaoImpl transactionPaypalDaoImpl) {
		this.transactionPaypalDaoImpl = transactionPaypalDaoImpl;
	}

	public void checkCompletedTransactions() {
		try {
			
			APIProfile profile = APIProfileDaoImpl.getInstance().getAPIProfile();
			
			CallerServices caller = new CallerServices();
			caller.setAPIProfile(profile);
			
			
			//3600000 * 24 == 1 day in milliseconds
			
			long numberOfDays = 185;
			Date yesterdate = new Date();
//			log.debug("yesterdate.getTime(): "+yesterdate.getTime());
			Long timeYesterday = yesterdate.getTime() - (3600000 * ((24*numberOfDays)));
			Date yesterday = new Date();
//			log.debug("timeYesterday: "+timeYesterday);
			yesterday.setTime(timeYesterday);
			
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(yesterday);
			
			Calendar endDate = Calendar.getInstance();
			Date date = new Date();
			//This is to make sure that we will also get Transaction which are within the last seconds
			date.setTime(date.getTime() + 3600000);
			endDate.setTime(date);  
			
			TransactionSearchRequestType pprequest = new TransactionSearchRequestType();
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			
//			log.debug("startDate: "+df.format(startDate.getTime()));
//			log.debug("endDate: "+df.format(endDate.getTime()));
			
			pprequest.setStartDate(startDate);
			pprequest.setEndDate(endDate);
			pprequest.setTransactionID("");
			
//			log.debug("pprequest: "+pprequest);
			
			TransactionSearchResponseType ppresponse = (TransactionSearchResponseType) caller.call("TransactionSearch", pprequest);
			
			//log.debug("pprequest 2: "+pprequest);
			//System.out.println("pprequest 2: "+pprequest);
			
			if (!ppresponse.getAck().equals(AckCodeType.Success) && 
				!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
//				session.setAttribute("response", ppresponse);
//				response.sendRedirect("APIError.jsp");
				log.error("ERROR IN CALLING API: "+ppresponse);
				log.debug("ERROR IN CALLING API: "+ppresponse);
				System.out.println("ERROR IN CALLING API: "+ppresponse);
				return;
			}

			PaymentTransactionSearchResultType[] ts = ppresponse.getPaymentTransactions();
			
			//log.debug("Do List Transactions");
			
			if (ts != null) {
				
				//log.debug("Do List Transactions ts.length "+ts.length);
				
				for (int i = 0; i < ts.length; i++) {
					
		            String transactionId = ts[i].getTransactionID(); //String
		            Date timePaid = new Date((ts[i].getTimestamp()).getTimeInMillis()); //Date
		            String status = ts[i].getStatus(); //String
		            ts[i].getGrossAmount().getCurrencyID(); //
		            //log.debug("ts[i].getGrossAmount().getCurrencyID(): "+ts[i].getGrossAmount().getCurrencyID());
		            String value = ts[i].getGrossAmount().get_value();
		            String displayName = ts[i].getPayerDisplayName();
					
		            //System.out.println("transactionId: "+transactionId);
		            
//		            log.debug("transactionId: "+transactionId);
//		            log.debug("timePaid: "+df.format(timePaid));
//		            log.debug("status: "+status);
		            //log.debug("value: "+value);
		            //log.debug("displayName: "+displayName);
		            
		            this.transactionPaypalDaoImpl.updateTransactionStatus(transactionId, status);
		            
				}
				
				if (ts.length == 0) {
					log.debug("No Transactions in Given Runtime ts.length IS 0");
				}
			} else {
				log.debug("No Transactions in Given Runtime ts is NULL ");
			}
			
		} catch (Exception ex2) {
			log.error("[getTransactions]" ,ex2);
		}
		
	}


}
