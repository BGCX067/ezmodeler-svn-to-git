<%@ include file='constants.jsp' %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - ExpressCheckout API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head>
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=3 color=black face=Verdana><b>SetExpress Checkout</b>
</font>
<br><br>
	<font size = 2>
		<b>You must be logged into <a href="https://<%=devCentral%>.paypal.com" target="_blank">Developer Central</a></b>
	</font>
<br>
<form method="POST" action="SetExpressCheckout.jsp">
<input type=hidden name=paymentType value=<%= request.getParameter("paymentType") %>>
<table width=700>
	
	<tr>
		<td align=right><b>Amount:</b> </td>
		<td align=left>
			
				<input type="text" name=paymentAmount value=1.00>
				<select name=currencyCodeType>
					<option value=USD>USD</option>
					<option value=GBP>GBP</option>
					<option value=EUR>EUR</option>
					<option value=JPY>JPY</option>
					<option value=CAD>CAD</option>
					<option value=AUD>AUD</option>
				</select>
				<b>(Required)</b>
			
		</td>
	</tr>
	<tr>
		<td align=right><br><input type="image" name="submit" src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" border="0"></td>
		<td><br>
			<p>
				Save time. Checkout Securely.Pay without sharing your financial information.
			</p>
		</td>
</table>
</form>
</center>
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>

