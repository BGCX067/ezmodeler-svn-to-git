<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal SDK - DoCapture API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>Do Authorization</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
		
	DoAuthorizationRequestType pprequest = new DoAuthorizationRequestType();

	pprequest.setTransactionID(request.getParameter("order_id"));
    pprequest.setTransactionEntity(TransactionEntityType.Order);
		
	BasicAmountType amount = new BasicAmountType();
	amount.set_value(request.getParameter("amount"));
	amount.setCurrencyID(CurrencyCodeType.fromString(request.getParameter("currency")));
	pprequest.setAmount(amount);
	
	try {
		DoAuthorizationResponseType ppresponse = (DoAuthorizationResponseType)caller.call("DoAuthorization", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
<b>Authorization approved!</b><br><br>
<table width=400>
	<tr>
		<td>Authorization ID:</td>
		<td><%= ppresponse.getTransactionID() %></td>
	</tr>
	<tr>
		<td>Amount:</td>
		<td><%= ppresponse.getAmount().getCurrencyID() + " " + ppresponse.getAmount().get_value() %></td>
	</tr>
</table>

<%
	if (ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
%>
<br><b>Warning!</b><br>
<table>
<%
		for (int i = 0; i < ppresponse.getErrors().length; i++) {
%>
	<tr>
		<td>Error Number:</td>
		<td><%= ppresponse.getErrors(i).getErrorCode() %></td>
	</tr>
	<tr>
		<td>Short Message:</td>
		<td><%= ppresponse.getErrors(i).getShortMessage() %></td>
	</tr>
	<tr>
		<td>Long Message:</td>
		<td><%= ppresponse.getErrors(i).getLongMessage() %></td>
	</tr>
<%
		}
	}
%>
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