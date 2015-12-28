<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - DoCapture API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>Do Capture</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
		
	DoCaptureRequestType pprequest = new DoCaptureRequestType();

	pprequest.setAuthorizationID(request.getParameter("authorization_id"));
    pprequest.setCompleteType(CompleteCodeType.fromString(request.getParameter("CompleteCodeType")));
	pprequest.setInvoiceID(request.getParameter("invoice_id"));
	pprequest.setNote(request.getParameter("Note"));
	
	BasicAmountType amount = new BasicAmountType();
	amount.set_value(request.getParameter("amount"));
	amount.setCurrencyID(CurrencyCodeType.fromString(request.getParameter("currency")));
	pprequest.setAmount(amount);
	
	try {
		DoCaptureResponseType ppresponse = (DoCaptureResponseType)caller.call("DoCapture", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
		
		DoCaptureResponseDetailsType details = ppresponse.getDoCaptureResponseDetails();
%>
<font face=Verdana> <b>Authorization captured!</b> </font> <br><br> 
<table width=400>
	<tr>
		<td>Authorization ID:</td>
		<td><%= details.getAuthorizationID() %></td>
	</tr>
	<tr>
		<td>Transaction ID:</td>
		<td><%= details.getPaymentInfo().getTransactionID() %></td>
	</tr>
	<tr>
		<td>Payment Status:</td>
		<td><%= details.getPaymentInfo().getPaymentStatus() %></td>
	</tr>
	<tr>
		<td>Gross Amount:</td>
		<td><%= details.getPaymentInfo().getGrossAmount().getCurrencyID() + " " + details.getPaymentInfo().getGrossAmount().get_value() %></td>
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
<a href="Calls.html">Home</a>
</body>
</html>