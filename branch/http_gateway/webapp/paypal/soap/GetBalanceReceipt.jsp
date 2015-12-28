<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - GetBalance API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>GetBalance</b></font>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");
		
	GetBalanceRequestType pprequest = new GetBalanceRequestType();

	try {
		GetBalanceResponseType ppresponse = (GetBalanceResponseType)caller.call("GetBalance", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
		BasicAmountType amtType = ppresponse.getBalance(); 
%>
<table>
	<tr>
		<td><%= amtType.get_value() %></td>
		<td><%= amtType.getCurrencyID() %></td>
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