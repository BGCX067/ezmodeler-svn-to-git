<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK: Transaction Search Results</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=3 color=black face=Verdana><b>Transaction Search Results</b></font>
<br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
			
	DateFormat dfRead = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss Z");
	
	Calendar startDate = Calendar.getInstance();
	startDate.setTime(dfRead.parse(request.getParameter("startDateStr") + " 00:00:00 +0000"));
	
	Calendar endDate = Calendar.getInstance();
	if ((request.getParameter("endDateStr") != null) 
		&& (request.getParameter("endDateStr").length() > 0)) {
		endDate.setTime(dfRead.parse(request.getParameter("endDateStr") + " 23:59:59 +0000"));  
	}
	
	TransactionSearchRequestType pprequest = new TransactionSearchRequestType();
	pprequest.setStartDate(startDate);
	pprequest.setEndDate(endDate);
	pprequest.setTransactionID(request.getParameter("transactionID"));
	
	try {
		TransactionSearchResponseType ppresponse = (TransactionSearchResponseType) caller.call("TransactionSearch", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}

		PaymentTransactionSearchResultType[] ts = ppresponse.getPaymentTransactions();
		if (ts != null) {
%>
		<br>
		<table width=600>
			<tr > 
			<td colspan="6" align= right> Results 1 - <%= ts.length %> </td> 
			</tr>
			<tr>
				<td/>
				<td><b>ID</b></td>
				<td><b>Time</b></td>
				<td><b>Status</b></td>
				<td><b>PayerName</b></td>
				<td><b>GrossAmount</b></td>
			</tr>
<%
			DateFormat df = new SimpleDateFormat();
            df.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
			for (int i = 0; i < ts.length; i++) {
%>			
			<tr>
				<td><%= i+1 %></td>
				<td>
					<a id="TransactionDetailsLink0" href=TransactionDetails.jsp?transactionID=<%= ts[i].getTransactionID() %>>
						<%= ts[i].getTransactionID() %>
					</a>
				</td>
				<td><%= df.format((ts[i].getTimestamp()).getTime()) %></td>
				<td><%= ts[i].getStatus() %></td>
				<td><%= ts[i].getPayerDisplayName() %></td>
				<td><%= ts[i].getGrossAmount().getCurrencyID() + " " + ts[i].getGrossAmount().get_value() %></td>
			</tr>
<%
			}
%>
		</table>
<%
		} else {
%>
		<br>Found 0 transactions
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