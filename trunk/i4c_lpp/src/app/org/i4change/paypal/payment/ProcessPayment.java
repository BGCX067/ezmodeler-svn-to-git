package org.i4change.paypal.payment;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.services.CallerServices;
import com.paypal.soap.api.AddressType;
import com.paypal.soap.api.BasicAmountType;
import com.paypal.soap.api.BillingPeriodDetailsType;
import com.paypal.soap.api.BillingPeriodType;
import com.paypal.soap.api.CountryCodeType;
import com.paypal.soap.api.CreateRecurringPaymentsProfileReq;
import com.paypal.soap.api.CreateRecurringPaymentsProfileRequestDetailsType;
import com.paypal.soap.api.CreateRecurringPaymentsProfileRequestType;
import com.paypal.soap.api.CreateRecurringPaymentsProfileResponseType;
import com.paypal.soap.api.CreditCardDetailsType;
import com.paypal.soap.api.CreditCardTypeType;
import com.paypal.soap.api.CurrencyCodeType;
import com.paypal.soap.api.DoDirectPaymentRequestDetailsType;
import com.paypal.soap.api.DoDirectPaymentRequestType;
import com.paypal.soap.api.DoDirectPaymentResponseType;
import com.paypal.soap.api.PayerInfoType;
import com.paypal.soap.api.PaymentActionCodeType;
import com.paypal.soap.api.PaymentDetailsType;
import com.paypal.soap.api.PersonNameType;
import com.paypal.soap.api.RecurringPaymentsProfileDetailsType;
import com.paypal.soap.api.ScheduleDetailsType;

public class ProcessPayment {

	private static final Log log = LogFactory.getLog(ProcessPayment.class);

//	private static ProcessPayment instance = null;
//
//	private ProcessPayment() {
//	}
//
//	public static synchronized ProcessPayment getInstance() {
//		if (instance == null) {
//			instance = new ProcessPayment();
//		}
//		return instance;
//	}
	
	/**
	 * In case of success the return value should contain the Unique Transaction ID
	 * 
	 * @param amountAsString
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param state
	 * @param zip
	 * @param countryCode
	 * @param creditCardType
	 * @param creditCardNumber
	 * @param expDateMonth
	 * @param expDateYear
	 * @param cvv2Number
	 * @param remoteAddr
	 * @return
	 * @throws Exception
	 */
	public DoDirectPaymentResponseType processPaymentByParams(String amountAsString, String firstName, String lastName,
			String address1, String address2, String city, String state, String zip, String countryCode,
			String creditCardType, String creditCardNumber, String expDateMonth, String expDateYear, 
			String cvv2Number, String remoteAddr) 
		throws Exception {
		
//		log.debug("amountAsString:"+amountAsString);
//		log.debug("firstName:"+firstName);
//		log.debug("lastName:"+lastName);
//		log.debug("address1:"+address1);
//		log.debug("address2:"+address2);
//		log.debug("city:"+city);
//		log.debug("state:"+state);
//		log.debug("zip:"+zip);
//		log.debug("countryCode:"+countryCode);
//		log.debug("creditCardType:"+creditCardType);
//		log.debug("creditCardNumber:"+creditCardNumber);
//		log.debug("expDateMonth:"+expDateMonth);
//		log.debug("expDateYear:"+expDateYear);
//		log.debug("cvv2Number:"+cvv2Number);
//		log.debug("remoteAddr:"+remoteAddr);
		
		APIProfile profile = APIProfileDaoImpl.getInstance().getAPIProfile();
		
		log.debug("profile getAPIUsername: "+profile.getAPIUsername());
		log.debug("profile getAPIPassword: "+profile.getAPIPassword());
		log.debug("profile getSignature: "+profile.getSignature());
		log.debug("profile getEnvironment: "+profile.getEnvironment());
		
		CallerServices caller = new CallerServices();
		caller.setAPIProfile(profile);
		
		DoDirectPaymentRequestType pprequest = new DoDirectPaymentRequestType();
		DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();
		PaymentDetailsType paymentDetails = new PaymentDetailsType();
		paymentDetails.setButtonSource("Java_SDK_JSP");

		BasicAmountType amount = new BasicAmountType();
		amount.set_value(amountAsString);
		amount.setCurrencyID(CurrencyCodeType.USD);
		paymentDetails.setOrderTotal(amount);	
				
		AddressType shipTo = new AddressType();
		shipTo.setName(firstName + " " + lastName);
		shipTo.setStreet1(address1);
		shipTo.setStreet2(address2);
		shipTo.setCityName(city);
		shipTo.setStateOrProvince(state);
		
		//FIXME: Shipping Address has to be NON US too!!
		CountryCodeType countryToken = CountryCodeManager.getCountryCodeTypeByCountryCode(countryCode);
		
		if (countryToken == null) {
			log.error("INVALID COUNTRY CODE "+countryCode);
		}
		
		shipTo.setCountry(countryToken);
		shipTo.setPostalCode(zip);
		paymentDetails.setShipToAddress(shipTo);
		
		details.setPaymentDetails(paymentDetails);
		
		log.debug("creditCardType: "+creditCardType);
	       
		CreditCardDetailsType cardDetails = new CreditCardDetailsType();
		cardDetails.setCreditCardType(CreditCardTypeType.fromString(creditCardType));
		cardDetails.setCreditCardNumber(creditCardNumber);
		cardDetails.setExpMonth(new Integer(Integer.parseInt(expDateMonth)));
		cardDetails.setExpYear(new Integer(Integer.parseInt(expDateYear)));
		cardDetails.setCVV2(cvv2Number);
		
		PayerInfoType payer = new PayerInfoType();
		PersonNameType name = new PersonNameType();
		name.setFirstName(firstName);
		name.setLastName(lastName);
		payer.setPayerName(name);
		payer.setPayerCountry(CountryCodeType.US);
	    payer.setAddress(shipTo);
	   
	    cardDetails.setCardOwner(payer);
	                
		details.setCreditCard(cardDetails);
		
		details.setIPAddress(remoteAddr);
		details.setPaymentAction(PaymentActionCodeType.fromString("Sale"));
		
	    pprequest.setDoDirectPaymentRequestDetails(details);
	
		DoDirectPaymentResponseType ppresponse = (DoDirectPaymentResponseType)caller.call("DoDirectPayment", pprequest);
		
		return ppresponse;
		
		
	}
	
	public CreateRecurringPaymentsProfileResponseType processSubscriptionByParams(String amountAsString, String firstName, String lastName,
			String address1, String address2, String city, String state, String zip, String countryCode,
			String creditCardType, String creditCardNumber, String expDateMonth, String expDateYear, 
			String cvv2Number, String remoteAddr) 
		throws Exception {

		APIProfile profile = APIProfileDaoImpl.getInstance().getAPIProfile();
		
		log.debug("profile getAPIUsername: "+profile.getAPIUsername());
		log.debug("profile getAPIPassword: "+profile.getAPIPassword());
		log.debug("profile getSignature: "+profile.getSignature());
		log.debug("profile getEnvironment: "+profile.getEnvironment());
		
		CallerServices caller = new CallerServices();
		caller.setAPIProfile(profile);
		
		CreateRecurringPaymentsProfileRequestType request=new CreateRecurringPaymentsProfileRequestType();

    	request.setCreateRecurringPaymentsProfileRequestDetails(new CreateRecurringPaymentsProfileRequestDetailsType());
    	
    	AddressType shipTo = new AddressType();
		shipTo.setName(firstName + " " + lastName);
		shipTo.setStreet1(address1);
		shipTo.setStreet2(address2);
		shipTo.setCityName(city);
		shipTo.setStateOrProvince(state);
		
		//FIXME: Shipping Address has to be NON US too!!
		CountryCodeType countryToken = CountryCodeManager.getCountryCodeTypeByCountryCode(countryCode);
		
		if (countryToken == null) {
			log.error("INVALID COUNTRY CODE "+countryCode);
		}
		
		shipTo.setCountry(countryToken);
		shipTo.setPostalCode(zip);

    	CreditCardDetailsType cardDetails = new CreditCardDetailsType();
		cardDetails.setCreditCardType(CreditCardTypeType.fromString(creditCardType));
		cardDetails.setCreditCardNumber(creditCardNumber);
		cardDetails.setExpMonth(new Integer(Integer.parseInt(expDateMonth)));
		cardDetails.setExpYear(new Integer(Integer.parseInt(expDateYear)));
		cardDetails.setCVV2(cvv2Number);
    	
		PayerInfoType payer = new PayerInfoType();
		PersonNameType name = new PersonNameType();
		name.setFirstName(firstName);
		name.setLastName(lastName);
		payer.setPayerName(name);
		payer.setPayerCountry(CountryCodeType.US);
	    payer.setAddress(shipTo);
	   
	    cardDetails.setCardOwner(payer);
	    
    	request.getCreateRecurringPaymentsProfileRequestDetails().setCreditCard(cardDetails);
    	
    	request.getCreateRecurringPaymentsProfileRequestDetails().setRecurringPaymentsProfileDetails(new RecurringPaymentsProfileDetailsType());

    	Calendar start_date = Calendar.getInstance();
    	//start_date.set(2008,5,30);
    	
    	request.getCreateRecurringPaymentsProfileRequestDetails().getRecurringPaymentsProfileDetails().setBillingStartDate(start_date);
		request.getCreateRecurringPaymentsProfileRequestDetails().setScheduleDetails(new ScheduleDetailsType());
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().setPaymentPeriod(new BillingPeriodDetailsType());
		
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().setDescription("RP-Test- Java SOAP SDK");
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().getPaymentPeriod().setAmount(new BasicAmountType());
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().getPaymentPeriod().getAmount().set_value(amountAsString) ;
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().getPaymentPeriod().getAmount().setCurrencyID(CurrencyCodeType.USD);
		
		//every Day as an example
		int BF=1;
        BillingPeriodType BP = BillingPeriodType.Day;

		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().getPaymentPeriod().setBillingFrequency(BF);
		request.getCreateRecurringPaymentsProfileRequestDetails().getScheduleDetails().getPaymentPeriod().setBillingPeriod(BP);

    	
		CreateRecurringPaymentsProfileResponseType response = (CreateRecurringPaymentsProfileResponseType) caller.call("CreateRecurringPaymentsProfile", request);
		
		
		return response;
	}
	

}
