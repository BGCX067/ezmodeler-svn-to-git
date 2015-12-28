<%@ include file='constants.jsp' %>
<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - ExpressCheckout API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>SetExpressCheckout</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");		
    
    SetExpressCheckoutRequestType pprequest = new SetExpressCheckoutRequestType();
    SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();

	StringBuffer url = new StringBuffer();
	url.append("http://");
	url.append(request.getServerName());
	url.append(":");
	url.append(request.getServerPort());
	url.append(request.getContextPath());
	
	String returnURL = url.toString() + "/soap/GetExpressCheckoutDetails.jsp";
	String cancelURL = url.toString() + "/soap/ExpressCheckout.jsp";
	
	details.setReturnURL(returnURL + "?paymentAmount=" + request.getParameter("paymentAmount") + "&currencyCodeType=" + request.getParameter("currencyCodeType"));
	details.setCancelURL(returnURL);
	
	BasicAmountType orderTotal = new BasicAmountType();
	orderTotal.set_value(request.getParameter("paymentAmount"));
	orderTotal.setCurrencyID(CurrencyCodeType.fromString(request.getParameter("currencyCodeType")));
	details.setOrderTotal(orderTotal);
		
	details.setPaymentAction(PaymentActionCodeType.fromString(request.getParameter("paymentType")));
	session.setAttribute("paymentType", request.getParameter("paymentType"));
	
	pprequest.setSetExpressCheckoutRequestDetails(details);

	try {
		SetExpressCheckoutResponseType ppresponse = (SetExpressCheckoutResponseType)caller.call("SetExpressCheckout", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		} else {
			response.sendRedirect("https://www."+testEnv+".paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+ppresponse.getToken());
		}
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