<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - DoCapture API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head>
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>DoCapture</b></font>
<br><br>
<%
	String authorization_id = request.getParameter("authorization_id");
	if (authorization_id == null) authorization_id = "";
	String amount = request.getParameter("amount");
	if (amount == null) amount = "0.00";
	String currency = request.getParameter("currency");
	if (currency == null) currency = "USD";
%>
<form method="POST" action="CaptureReceipt.jsp">
<table width=500>
	<tr>
		<td align=right><br>Authorization ID:</td>
		<td align=left><br><input type="text" name="authorization_id" value=<%= authorization_id %>></td>
		<td><b>(Required)</b></td>
	</tr>
	<tr>
		<td align=right>Complete Code Type:</td>
		<td align=left>
			<select name=CompleteCodeType>
				<option value="Complete">Complete</option>
				<option value="NotComplete">NotComplete</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align=right>Amount:</td>
		<td align=left>
			<input type="text" name="amount" value=<%=amount%>>
			<select name=currency>
<%
			String[] currencies = {"USD", "GBP", "EUR", "JPY", "CAD", "AUD"};
			for(int s=0; s < currencies.length; s++) {
%>
				<option <%=currency.equals(currencies[s]) ? "selected" : ""%>><%=currencies[s]%></option>
<%
			}
%>
			</select>
		</td>
		<td><b>(Required)</b></td>
	</tr>
	<tr>
		<td align=right>Invoice ID:</td>
		<td align=left><input type="text" name="invoice_id"></td>
	</tr>
	<tr>
		<td align=right>Note:</td>
		<td align=left><textarea name="note" cols=30 rows=4></textarea></td>
	</tr>
	<tr>
		<td/>
		<td align=left><br><input type="Submit" value="Submit"></td>
	</tr>
</table>
</form>
</center>
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>