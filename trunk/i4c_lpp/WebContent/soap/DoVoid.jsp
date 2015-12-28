<%@ page language="java" %>

<html>
<head>
<title>PayPal PHP SDK - DoVoid API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head>
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>DoVoid</b></font>
<br><br>
<%
	String authorization_id = request.getParameter("authorization_id");
	if (authorization_id == null) authorization_id = "";
%>
<form method="POST" action="VoidReceipt.jsp">
<table width=500>
	<tr>
		<td align=right>Authorization ID:</td>
		<td align=left><input type="text" name="authorization_id" value=<%= authorization_id %>></td>
		<td><b>(Required)</b></td>
	</tr>
	<tr>
		<td align=right>Note:</td>
		<td align=left><textarea name="note" cols=30 rows=4></textarea></td>
	</tr>
	<tr>
		<td align=right></td>
		<td align=left><br><input type="Submit" value="Submit"></form></td>
	</tr>
</table>
</form>
</center>
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>