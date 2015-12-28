<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.DoReauthorizationRequestType" %>
<%@ page import="com.paypal.soap.api.BasicAmountType" %>
<%@ page import="com.paypal.soap.api.DoReauthorizationResponseType" %>
<%@ page import="com.paypal.soap.api.CurrencyCodeType" %>
<%@ page import="com.paypal.soap.api.ReceiverInfoCodeType" %>
<%@ page import="com.paypal.soap.api.AckCodeType" %>

<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - DoReAuthorization API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<center>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");	

	DoReauthorizationRequestType pp_request = new DoReauthorizationRequestType();

	pp_request.setAuthorizationID(request.getParameter("authorizationID")); 
	
	BasicAmountType amount = new BasicAmountType();
	
	amount.set_value(request.getParameter("amount"));
	
	amount.setCurrencyID(CurrencyCodeType.fromString(request.getParameter("currency")));
	
	pp_request.setAmount(amount);
		
	
	
	
	try {
		DoReauthorizationResponseType ppresponse = (DoReauthorizationResponseType) caller.call("DoReauthorization", pp_request);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
		<b>ReAuthorization Succeeded!</b><br><br>
		<TABLE class="api" id="Table1">
			<td class="field">Authorization ID:</td>
				<td><%= ppresponse.getAuthorizationID() %></td>
			</TR>

		</TABLE>
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