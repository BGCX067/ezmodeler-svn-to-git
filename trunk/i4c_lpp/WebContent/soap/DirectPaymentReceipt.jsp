<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - DoDirectPayment API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>Do Direct Payment</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
	
	DoDirectPaymentRequestType pprequest = new DoDirectPaymentRequestType();
	DoDirectPaymentRequestDetailsType details = new DoDirectPaymentRequestDetailsType();
	PaymentDetailsType paymentDetails = new PaymentDetailsType();
	paymentDetails.setButtonSource("Java_SDK_JSP");

	BasicAmountType amount = new BasicAmountType();
	amount.set_value(request.getParameter("amount"));
	amount.setCurrencyID(CurrencyCodeType.USD);
	paymentDetails.setOrderTotal(amount);	
			
	AddressType shipTo = new AddressType();
	shipTo.setName(request.getParameter("firstName") + " " + request.getParameter("lastName"));
	shipTo.setStreet1(request.getParameter("address1"));
	shipTo.setStreet2(request.getParameter("address2"));
	shipTo.setCityName(request.getParameter("city"));
	shipTo.setStateOrProvince(request.getParameter("state"));
	shipTo.setCountry(CountryCodeType.US);
	shipTo.setPostalCode(request.getParameter("zip"));
	paymentDetails.setShipToAddress(shipTo);
	
	details.setPaymentDetails(paymentDetails);
       
	CreditCardDetailsType cardDetails = new CreditCardDetailsType();
	cardDetails.setCreditCardType(CreditCardTypeType.fromString(request.getParameter("creditCardType")));
	cardDetails.setCreditCardNumber(request.getParameter("creditCardNumber"));
	cardDetails.setExpMonth(new Integer(Integer.parseInt(request.getParameter("expDateMonth"))));
	cardDetails.setExpYear(new Integer(Integer.parseInt(request.getParameter("expDateYear"))));
	cardDetails.setCVV2(request.getParameter("cvv2Number"));
	
	PayerInfoType payer = new PayerInfoType();
	PersonNameType name = new PersonNameType();
	name.setFirstName(request.getParameter("firstName"));
	name.setLastName(request.getParameter("lastName"));
	payer.setPayerName(name);
	payer.setPayerCountry(CountryCodeType.US);
    payer.setAddress(shipTo);
   
    cardDetails.setCardOwner(payer);
                
	details.setCreditCard(cardDetails);
	
	details.setIPAddress(request.getRemoteAddr());
	details.setPaymentAction(PaymentActionCodeType.fromString(request.getParameter("paymentType")));
	
    pprequest.setDoDirectPaymentRequestDetails(details);
	
	try {
		DoDirectPaymentResponseType ppresponse = (DoDirectPaymentResponseType)caller.call("DoDirectPayment", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
<b>Thank you for your payment!</b><br><br>
<table width=400>
	<tr>
		<td>Transaction ID:</td>
		<td><%= ppresponse.getTransactionID() %></td>
	</tr>
	<tr>
		<td>Amount:</td>
		<td><%= ppresponse.getAmount().getCurrencyID() + " " + ppresponse.getAmount().get_value() %></td>
	</tr>
	<tr>
		<td>AVS:</td>
		<td><%= ppresponse.getAVSCode() %></td>
	</tr>
	<tr>
		<td>CVV2:</td>
		<td><%= ppresponse.getCVV2Code() %></td>
	</tr>
</table>
<%
	} catch (Exception e) {
		out.println(e);
		session.setAttribute("exception", e);
		response.sendRedirect("Error.jsp");
		return;
	}
%>
</center>
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>