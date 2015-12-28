package org.i4change.paypal.payment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.services.CallerServices;
import com.paypal.soap.api.AddressType;
import com.paypal.soap.api.BasicAmountType;
import com.paypal.soap.api.CountryCodeType;
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

public class ProcessPayment {

	private static final Log log = LogFactory.getLog(ProcessPayment.class);

	private static ProcessPayment instance = null;

	private ProcessPayment() {
	}

	public static synchronized ProcessPayment getInstance() {
		if (instance == null) {
			instance = new ProcessPayment();
		}
		return instance;
	}
	
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
		CountryCodeType countryToken = CountryCodeManager.getInstance().getCountryCodeTypeByCountryCode(countryCode);
		
		if (countryToken == null) {
			log.error("INVALID COUNTRY CODE "+countryCode);
		}
		
		shipTo.setCountry(countryToken);
		shipTo.setPostalCode(zip);
		paymentDetails.setShipToAddress(shipTo);
		
		details.setPaymentDetails(paymentDetails);
	       
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

}
