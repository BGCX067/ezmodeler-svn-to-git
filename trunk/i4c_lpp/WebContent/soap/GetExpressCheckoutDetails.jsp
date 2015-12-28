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
<!--font size=2 color=black face=Verdana><b>GetExpressCheckoutDetails</b></font-->

<%
	CallerServices caller = (CallerServices) session.getValue("caller");		
  
	GetExpressCheckoutDetailsRequestType pprequest = new GetExpressCheckoutDetailsRequestType();
	pprequest.setToken(request.getParameter("token"));

	try {
		GetExpressCheckoutDetailsResponseType ppresponse = (GetExpressCheckoutDetailsResponseType)caller.call("GetExpressCheckoutDetails", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		} 
		
		GetExpressCheckoutDetailsResponseDetailsType details = ppresponse.getGetExpressCheckoutDetailsResponseDetails();
%>
<!--b>Review and Place Order</b-->
<table width=400>
	<tr>
		<td ><b>Order Total:<b></td>
		<td align=left><%=  request.getParameter("currencyCodeType") + " " +  request.getParameter("paymentAmount") %></td>
	</tr>
		<tr>
		<td ><b>Shipping Address:<b></td>
		<td/>
	</tr>
	<tr>
		<td >Street 1:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getStreet1() %></td>
	</tr>
	<tr>
		<td >Street 2:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getStreet2() %></td>
	</tr>
	<tr>
		<td >City:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getCityName() %></td>
	</tr>
	<tr>
		<td >State:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getStateOrProvince() %></td>
	</tr>
	<tr>
		<td >ZIP Code:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getPostalCode() %></td>
	</tr>
	<tr>
		<td >Country:</td>
		<td align=left><%= details.getPayerInfo().getAddress().getCountryName() %></td>
	</tr>
</table>
<form method=POST action="DoExpressCheckoutPayment.jsp?token=<%= details.getToken() %>&PayerID=<%= details.getPayerInfo().getPayerID()%>&currencyCodeType=<%= request.getParameter("currencyCodeType") %>&paymentAmount=<%= request.getParameter("paymentAmount") %>">
	<input type=Submit value=Pay>
</form>
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