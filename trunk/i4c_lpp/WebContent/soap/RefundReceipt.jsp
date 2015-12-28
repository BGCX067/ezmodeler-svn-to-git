<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal SDK - RefundTransaction API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>Refund Transaction</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
   	RefundTransactionRequestType pprequest = new RefundTransactionRequestType();

	if ((request.getParameter("amount").length() > 0) &&
	//	(request.getParameter("refundType").equals(RefundType.Partial)))	 {
		(request.getParameter("refundType").equals("Partial")))	 {
		BasicAmountType amount = new BasicAmountType();
    	amount.set_value(request.getParameter("amount"));
    	amount.setCurrencyID(CurrencyCodeType.fromString(request.getParameter("currency")));
   		pprequest.setAmount(amount);
	}
		
	pprequest.setTransactionID(request.getParameter("transactionID"));
	pprequest.setMemo(request.getParameter("memo"));
	pprequest.setRefundType(RefundType.fromString(request.getParameter("refundType")));
	
    
	try {
		RefundTransactionResponseType ppresponse = (RefundTransactionResponseType) caller.call("RefundTransaction", pprequest);
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
<b>Transaction refunded!</b><br><br>
<table width=400>
	<tr>
		<td>Transaction ID:</td>
		<td><%= ppresponse.getRefundTransactionID() %></td>
	</tr>
	<tr>
		<td>Gross Refund Amount:</td>
		<td><%= ppresponse.getGrossRefundAmount().getCurrencyID() + " " + ppresponse.getGrossRefundAmount().get_value() %></td>
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