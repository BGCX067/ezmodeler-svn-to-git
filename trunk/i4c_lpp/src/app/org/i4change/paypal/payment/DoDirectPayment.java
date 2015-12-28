package org.i4change.paypal.payment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.user.CountryDaoImpl;
import org.i4change.app.data.user.daos.TransactionPaypalDaoImpl;
import org.i4change.app.hibernate.beans.user.TransactionPaypal;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.paypal.soap.api.*;

/**
 * @deprecated
 */
public class DoDirectPayment extends VelocityViewServlet {
	
	private static final Log log = LogFactory.getLog(DoDirectPayment.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	/**
	 * @deprecated
	 */
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
//		try {
//			// Get Spring context
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//			
//			TransactionPaypalDaoImpl transactionPaypalDaoImpl = (TransactionPaypalDaoImpl) context.getBean("i4change.TransactionPaypalDaoImpl");
//			
//			
//			String lang = request.getParameter("lang");
//			
//			log.debug("########## lang: "+lang);
//			
//			if (lang == null) {
//				return null;
//			}
//			Long language_id = Long.valueOf(lang).longValue();
//			
//			
//			String command = request.getParameter("command");
//			
//			log.debug("########### command: "+command);
//			
//			if (command.equals("doBuy")) {
//				
//				if(request.getParameter("transactionId") == null) {
//					ctx.put("ERROR", "No Transaction ID Given");
//					return getVelocityEngine().getTemplate("error.vm");
//				}
//				
//				Long i4change_transactionId = Long.valueOf(request.getParameter("transactionId")).longValue();
//				
//				log.debug("#### i4change_transactionId: "+i4change_transactionId);
//				
//				TransactionPaypal transaction = transactionPaypalDaoImpl.getTransactionTransactionById(i4change_transactionId);
//				
//				//FIXME: TODO: Check or remove method
//				ProcessPayment processPayment = null;
//				
//				DoDirectPaymentResponseType ppresponse = processPayment.
//					processPaymentByParams(transaction.getAmountStartTransaction(), 
//							request.getParameter("firstName"), 
//							request.getParameter("lastName"), 
//							request.getParameter("address1"), 
//							request.getParameter("address2"), 
//							request.getParameter("city"), 
//							request.getParameter("state"), 
//							request.getParameter("zip"), 
//							request.getParameter("countryCode"), 
//							request.getParameter("creditCardType"), 
//							request.getParameter("creditCardNumber"), 
//							request.getParameter("expDateMonth"), 
//							request.getParameter("expDateYear"), 
//							request.getParameter("cvv2Number"), 
//							request.getRemoteAddr());
//			
//				
//				log.debug("####### ppresponse getAck "+ppresponse.getAck());
//				
//				if (!ppresponse.getAck().equals(AckCodeType.Success) && 
//					!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
//					//FIXME: ERROR
//					log.error("ERROROEROEROEROR"+ppresponse);
//					
//					ctx.put("ACK", ppresponse.getAck());
//					ctx.put("CorrelationID", ppresponse.getCorrelationID());
//					ctx.put("Version", ppresponse.getVersion());
//					
//					if (ppresponse.getErrors().length != 0) {
//						ctx.put("ErrorCode", ppresponse.getErrors(0).getErrorCode());
//						ctx.put("ShortMessage", ppresponse.getErrors(0).getShortMessage());
//						ctx.put("LongMessage", ppresponse.getErrors(0).getLongMessage());
//					} else {
//						ctx.put("ErrorCode", "");
//						ctx.put("ShortMessage", "");
//						ctx.put("LongMessage", "");
//					}
//					
//					return getVelocityEngine().getTemplate("apierror.vm");
//					
//				} else {
//					//Success in Payment
//					String transactionId = ppresponse.getTransactionID();
//					String amount = ppresponse.getAmount().get_value();
//					Date transferDate = ppresponse.getTimestamp().getTime();
//					log.debug("Success: "+transactionId);
//					log.debug("Success transferDate1: "+transferDate);
//					DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
//					log.debug("Success transferDate2: "+df.format(transferDate));
//					
//					//Add Transaction to List
//					//TransactionDaoImpl.getInstance().addTransaction(transactionId, amount, transferDate, "NEW", userId);
//					
//					//Add Transaction to List of payed / in checking queue
//					transactionPaypalDaoImpl.addAndUpdateTransaction(i4change_transactionId, transactionId, 
//							"NEW", amount, transferDate,request.getParameter("firstName"), 
//							request.getParameter("lastName"), 
//							request.getParameter("address1"), 
//							request.getParameter("address2"), 
//							request.getParameter("city"), 
//							request.getParameter("state"), 
//							request.getParameter("zip"), 
//							request.getParameter("countryCode"),
//							request.getParameter("firstName2"), 
//							request.getParameter("lastName2"),null);
//					
//					ctx.put("ERROR", Fieldmanagment.getInstance().
//							getFieldByLabelNumberAndLanguage(1194L, language_id).getValue());
//					return getVelocityEngine().getTemplate("error.vm");
//				}
//			} else {
//				String templateName = "doPayment.vm";
//				
//				if(request.getParameter("t") == null) {
//					ctx.put("ERROR", "No Transaction Given");
//					return getVelocityEngine().getTemplate("error.vm");
//				
//				}
//				
//				Long transactionId = Long.valueOf(request.getParameter("t")).longValue();
//				
//				TransactionPaypal transaction = transactionPaypalDaoImpl.getTransactionTransactionById(transactionId);
//				
//				log.debug("amount: "+transaction.getAmountStartTransaction());
//				log.debug("templateName: "+templateName);
//				
//				ctx.put("Payment_Details_LabelId_1174",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1174L, language_id).getValue());
//				ctx.put("First_Name_labelId_1175",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1175L, language_id).getValue());
//				ctx.put("Last_Name_labelId_1176",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1176L, language_id).getValue());
//				ctx.put("Card_Type_labelId_1177",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1177L, language_id).getValue());
//				ctx.put("Visa_LabelId_1178",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1178L, language_id).getValue());
//				ctx.put("MasterCard_labelId_1179",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1179L, language_id).getValue());
//				ctx.put("Discover_labelId_1180",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1180L, language_id).getValue());
//				ctx.put("American_Express_labelId_1181",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1181L, language_id).getValue());
//				ctx.put("Card_Number_labelId_1182",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1182L, language_id).getValue());
//				ctx.put("Expiration_Date_labelId_1183",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1183L, language_id).getValue());
//				ctx.put("Card_Verification_Number_labelId_1184",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1184L, language_id).getValue());
//				ctx.put("Billing_Address_labelId_1185",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1185L, language_id).getValue());
//				ctx.put("Address_1_labelId_1186",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1186L, language_id).getValue());
//				ctx.put("Address_2_labelId_1187",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1187L, language_id).getValue());
//				ctx.put("City_labelId_1188",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1188L, language_id).getValue());
//				ctx.put("Country_labelId_1189",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1189L, language_id).getValue());
//				ctx.put("State_labelId_1190",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1190L, language_id).getValue());
//				ctx.put("ZIP_Code_labelId_1191",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1191L, language_id).getValue());
//				ctx.put("Amount_labelId_1192",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1192L, language_id).getValue());
//				ctx.put("Submit_Payment_labelId_1193",Fieldmanagment.getInstance().
//						getFieldByLabelNumberAndLanguage(1193L, language_id).getValue());
//				
//				ctx.put("First_Name", transaction.getFirstName());
//				ctx.put("Last_Name", transaction.getLastName());
//				
//				ctx.put("City", transaction.getCity());
//				ctx.put("Address_1", transaction.getAddress_1());
//				//transaction.getAddress_2()
//				ctx.put("Address_2", "");
//				
//				ctx.put("Country", transaction.getCountryCode());
//				
//				//ctx.put("countryList", CountryDaoImpl.getInstance().getCountry());
//				ctx.put("ZIP_Code", transaction.getZip());
//				
//				ctx.put("i4CHANGE_TID", transactionId);
//				ctx.put("i4CHANGE_AMOUNT", transaction.getAmountStartTransaction()+" USD");
//				ctx.put("i4CHANGE_SID", "");
//				
//				ctx.put("lang", language_id);
//				
//				return getVelocityEngine().getTemplate(templateName);
//				
//				//
//			}
//			
//		} catch (Exception e) {
//			log.error("DoDirectPayment",e);
//			e.printStackTrace();
//			return null;
//		}
		
		return null;
		//return null;
	}
	
	

}
