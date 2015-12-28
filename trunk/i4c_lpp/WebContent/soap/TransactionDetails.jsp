<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal SDK - GetTransactionDetails API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=3 color=black face=Verdana><b>Transaction Details</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");

    GetTransactionDetailsRequestType pprequest = new GetTransactionDetailsRequestType();
	pprequest.setTransactionID(request.getParameter("transactionID"));

	try {
		GetTransactionDetailsResponseType ppresponse = 
			(GetTransactionDetailsResponseType) caller.call("GetTransactionDetails", pprequest);
		
			if (!ppresponse.getAck().equals(AckCodeType.Success) && 
				!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
				session.setAttribute("response", ppresponse);
				response.sendRedirect("APIError.jsp");
				return;
			}
			
			PaymentTransactionType ts = ppresponse.getPaymentTransactionDetails();
%>
<table width=400>
	<tr>
		<td>Payer:</td>
		<td><%= ts.getPayerInfo().getPayer() %></td>
	</tr>
	<tr>
		<td>Payer ID:</td>
		<td><%= ts.getPayerInfo().getPayerID() %></td>
	</tr>
	<tr>
		<td>First Name:</td>
		<td><%= ts.getPayerInfo().getPayerName().getFirstName() %></td>
	</tr>
	<tr>
		<td>Last Name:</td>
		<td><%= ts.getPayerInfo().getPayerName().getLastName() %></td>
	</tr>
	<tr>
		<td>Transaction ID:</td>
		<td><%= ts.getPaymentInfo().getTransactionID() %></td>
	</tr>
	<tr>
		<td>Parent Transaction ID (if any):</td>
		<td><%= ts.getPaymentInfo().getParentTransactionID() %></td>
	</tr>
	<tr>
		<td>Gross Amount:</td>
		<td><%= ts.getPaymentInfo().getGrossAmount().getCurrencyID() + " " + ts.getPaymentInfo().getGrossAmount().get_value() %></td>
	</tr>
	<tr>
		<td>Payment Status:</td>
		<td><%= ts.getPaymentInfo().getPaymentStatus() %></td>
	</tr>
	<!--tr>
		<td>Pending Reason:</td>
		<td><%= ts.getPaymentInfo().getPendingReason() %></td>
	</tr-->
</table>
<%
		if (ts.getPaymentInfo().getPaymentStatus().toString() == "Pending" 
			&& ts.getPaymentInfo().getPendingReason() == PendingStatusCodeType.fromString("authorization")) {
%>
				<br><br><a id="DoCaptureLink" href=DoCapture.jsp?authorization_id=<%= ts.getPaymentInfo().getTransactionID() %>&currency=<%= ts.getPaymentInfo().getGrossAmount().getCurrencyID() %>&amount=<%= ts.getPaymentInfo().getGrossAmount().get_value() %>>Capture</a> 
				| <a id="DoVoidLink" href=DoVoid.jsp?authorization_id=<%= ts.getPaymentInfo().getTransactionID() %>>Void</a>
<%
		} else if (ts.getPaymentInfo().getPaymentStatus().toString() == "Pending" 
			&& ts.getPaymentInfo().getPendingReason() == PendingStatusCodeType.fromString("order")) {
%>
				<br><br><a id="DoAuthorizationLink" href=DoAuthorization.jsp?order_id=<%= ts.getPaymentInfo().getTransactionID() %>&currency=<%= ts.getPaymentInfo().getGrossAmount().getCurrencyID() %>&amount=<%= ts.getPaymentInfo().getGrossAmount().get_value() %>>Authorize</a>
				| <a id="DoVoidLink" href=DoVoid.jsp?authorization_id=<%= ts.getPaymentInfo().getTransactionID() %>>Void</a>
<%
		} else if (ts.getPaymentInfo().getPaymentStatus().toString() == "Completed") {
%>
				<br><br><a id="RefundTransactionLink" href=RefundTransaction.jsp?transactionID=<%= ts.getPaymentInfo().getTransactionID() %>&currency=<%= ts.getPaymentInfo().getGrossAmount().getCurrencyID() %>&amount=<%= ts.getPaymentInfo().getGrossAmount().get_value() %>>Refund</a>
<%
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