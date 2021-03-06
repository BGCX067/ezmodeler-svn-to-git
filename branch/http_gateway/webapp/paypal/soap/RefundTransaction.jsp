<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - RefundTransaction API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head>
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>RefundTransaction</b></font>
<br><br>
<%
	String transaction_id = request.getParameter("transactionID");
	if (transaction_id == null) transaction_id = "";
	String currency = request.getParameter("currency");
	String amount = request.getParameter("amount");
	if (amount == null) amount = "0.00";
%>
<form method="POST" action="RefundReceipt.jsp">
<table width=500>
	<tr>
		<td align=right>Transaction ID:</td>
		<td align=left><input type="text" name="transactionID" value=<%= transaction_id %>></td>
		<td><b>(Required)</b></td>
	</tr>
	<tr>
		<td align=right>Refund Type:</td>
		<td align=left>
			<select name=refundType>
				<option value="Full">Full</option>
				<option value="Partial">Partial</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align=right>Amount:</td>
		<td align=left>
			<p>
				<input type="text" name="amount" value=<%= amount %>>
<%
	if ((currency != null) && (currency.length() > 0)) {
%>
				<%= currency %>
				<input type="hidden" name="currency" value=<%= currency %>>			
<%
	} else {
%>
				<select name="currency">
					<option value=USD>USD</option>
					<option value=GBP>GBP</option>
					<option value=JPY>JPY</option>
					<option value=EUR>EUR</option>
					<option value=CAD>CAD</option>
					<option value=AUD>AUD</option>
				</select>
<%
	}
%>
			</p>
		</td>
	</tr>
	<tr>
		<td/>
		<td><b>(Required if Partial Refund)</b></td>
	</tr>
	<tr>
		<td align=right>Memo:</td>
		<td align=left><textarea name="memo" cols=30 rows=4></textarea></td>
	</tr>
	<tr>
		<td align=right></td>
		<td align=left><br><input type="Submit" value="Submit"></td>
	</tr>
</table>
</form>
</center>
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>