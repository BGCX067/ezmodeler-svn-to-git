package org.i4change.paypal.payment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.servlet.VelocityViewServlet;
import org.i4change.paypal.beans.Transaction;
import org.i4change.paypal.daos.CountryDaoImpl;
import org.i4change.paypal.daos.FieldlanguagesvaluesDaoImpl;
import org.i4change.paypal.daos.TransactionDaoImpl;
import org.i4change.paypal.jobs.SearchTransaction;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.CallerServices;
import com.paypal.soap.api.*;


public class DoDirectPayment extends VelocityViewServlet {
	
	private static final Log log = LogFactory.getLog(DoDirectPayment.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse httpServletResponse, Context ctx) throws ServletException,
			IOException {
		try {
			
			String lang = request.getParameter("lang");
			
			log.debug("########## lang: "+lang);
			
			if (lang == null) {
				return null;
			}
			Long language_id = Long.valueOf(lang).longValue();
			
			
			String command = request.getParameter("command");
			
			log.debug("########### command: "+command);
			
			if (command.equals("doBuy")) {
				
				if(request.getParameter("transactionId") == null) {
					ctx.put("ERROR", "No Transaction ID Given");
					return getVelocityEngine().getTemplate("error.vm");
				}
				
				Long i4change_transactionId = Long.valueOf(request.getParameter("transactionId")).longValue();
				
				log.debug("#### i4change_transactionId: "+i4change_transactionId);
				
				Transaction transaction = TransactionDaoImpl.getInstance().getTransactionTransactionById(i4change_transactionId);
				
				DoDirectPaymentResponseType ppresponse = ProcessPayment.getInstance().
					processPaymentByParams(transaction.getAmountstarttransaction(), 
							request.getParameter("firstName"), 
							request.getParameter("lastName"), 
							request.getParameter("address1"), 
							request.getParameter("address2"), 
							request.getParameter("city"), 
							request.getParameter("state"), 
							request.getParameter("zip"), 
							request.getParameter("countryCode"), 
							request.getParameter("creditCardType"), 
							request.getParameter("creditCardNumber"), 
							request.getParameter("expDateMonth"), 
							request.getParameter("expDateYear"), 
							request.getParameter("cvv2Number"), 
							request.getRemoteAddr());
			
				
				log.debug("####### ppresponse getAck "+ppresponse.getAck());
				
				if (!ppresponse.getAck().equals(AckCodeType.Success) && 
					!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
					//FIXME: ERROR
					log.error("ERROROEROEROEROR"+ppresponse);
					
					ctx.put("ACK", ppresponse.getAck());
					ctx.put("CorrelationID", ppresponse.getCorrelationID());
					ctx.put("Version", ppresponse.getVersion());
					
					if (ppresponse.getErrors().length != 0) {
						ctx.put("ErrorCode", ppresponse.getErrors(0).getErrorCode());
						ctx.put("ShortMessage", ppresponse.getErrors(0).getShortMessage());
						ctx.put("LongMessage", ppresponse.getErrors(0).getLongMessage());
					} else {
						ctx.put("ErrorCode", "");
						ctx.put("ShortMessage", "");
						ctx.put("LongMessage", "");
					}
					
					return getVelocityEngine().getTemplate("apierror.vm");
					
				} else {
					//Success in Payment
					String transactionId = ppresponse.getTransactionID();
					String amount = ppresponse.getAmount().get_value();
					Date transferDate = ppresponse.getTimestamp().getTime();
					log.debug("Success: "+transactionId);
					log.debug("Success transferDate1: "+transferDate);
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					log.debug("Success transferDate2: "+df.format(transferDate));
					
					//Add Transaction to List
					//TransactionDaoImpl.getInstance().addTransaction(transactionId, amount, transferDate, "NEW", userId);
					
					//Add Transaction to List of payed / in checking queue
					TransactionDaoImpl.getInstance().addAndUpdateTransaction(i4change_transactionId, transactionId, 
							"NEW", amount, transferDate,request.getParameter("firstName"), 
							request.getParameter("lastName"), 
							request.getParameter("address1"), 
							request.getParameter("address2"), 
							request.getParameter("city"), 
							request.getParameter("state"), 
							request.getParameter("zip"), 
							request.getParameter("countryCode"),
							request.getParameter("firstName2"), 
							request.getParameter("lastName2"));
					
					ctx.put("ERROR", FieldlanguagesvaluesDaoImpl.getInstance().
							getLabelStringByNumberAndLang(1194L, language_id));
					return getVelocityEngine().getTemplate("error.vm");
				}
			} else {
				String templateName = "doPayment.vm";
				
				if(request.getParameter("t") == null) {
					ctx.put("ERROR", "No Transaction Given");
					return getVelocityEngine().getTemplate("error.vm");
				
				}
				
				Long transactionId = Long.valueOf(request.getParameter("t")).longValue();
				
				Transaction transaction = TransactionDaoImpl.getInstance().getTransactionTransactionById(transactionId);
				
				log.debug("amount: "+transaction.getAmountstarttransaction());
				log.debug("templateName: "+templateName);
				
				ctx.put("Payment_Details_LabelId_1174",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1174L, language_id));
				ctx.put("First_Name_labelId_1175",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1175L, language_id));
				ctx.put("Last_Name_labelId_1176",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1176L, language_id));
				ctx.put("Card_Type_labelId_1177",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1177L, language_id));
				ctx.put("Visa_LabelId_1178",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1178L, language_id));
				ctx.put("MasterCard_labelId_1179",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1179L, language_id));
				ctx.put("Discover_labelId_1180",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1180L, language_id));
				ctx.put("American_Express_labelId_1181",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1181L, language_id));
				ctx.put("Card_Number_labelId_1182",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1182L, language_id));
				ctx.put("Expiration_Date_labelId_1183",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1183L, language_id));
				ctx.put("Card_Verification_Number_labelId_1184",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1184L, language_id));
				ctx.put("Billing_Address_labelId_1185",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1185L, language_id));
				ctx.put("Address_1_labelId_1186",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1186L, language_id));
				ctx.put("Address_2_labelId_1187",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1187L, language_id));
				ctx.put("City_labelId_1188",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1188L, language_id));
				ctx.put("Country_labelId_1189",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1189L, language_id));
				ctx.put("State_labelId_1190",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1190L, language_id));
				ctx.put("ZIP_Code_labelId_1191",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1191L, language_id));
				ctx.put("Amount_labelId_1192",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1192L, language_id));
				ctx.put("Submit_Payment_labelId_1193",FieldlanguagesvaluesDaoImpl.getInstance().
						getLabelStringByNumberAndLang(1193L, language_id));
				
				ctx.put("First_Name", transaction.getFirstName());
				ctx.put("Last_Name", transaction.getLastName());
				
				ctx.put("City", transaction.getCity());
				ctx.put("Address_1", transaction.getAddress_1());
				//transaction.getAddress_2()
				ctx.put("Address_2", "");
				
				ctx.put("Country", transaction.getCountryCode());
				
				ctx.put("countryList", CountryDaoImpl.getInstance().getCountryList());
				ctx.put("ZIP_Code", transaction.getZip());
				
				ctx.put("i4CHANGE_TID", transactionId);
				ctx.put("i4CHANGE_AMOUNT", transaction.getAmountstarttransaction()+" USD");
				ctx.put("i4CHANGE_SID", "");
				
				ctx.put("lang", language_id);
				
				return getVelocityEngine().getTemplate(templateName);
				
				//
			}
			
		} catch (Exception e) {
			log.error("DoDirectPayment",e);
			e.printStackTrace();
			return null;
		}
		
		//return null;
	}
	
	

}
