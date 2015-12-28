<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" %>

<%
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	Calendar startDate = Calendar.getInstance();
	startDate.add(Calendar.DATE, -1);
	Calendar endDate = Calendar.getInstance();
%>

<html>
<head>
<title>PayPal JSP SDK - TransactionSearch API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head>
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=3 color=black face=Verdana><b>TransactionSearch</b></font>
<br><br>
<form method="POST" action="TransactionSearchResults.jsp">
	<table width=500>	
			<tr>
				<td align=right> <b>Start Date:</b> </td> 
				<td align=left ><input type="text" name="startDateStr" value=<%= df.format(startDate.getTime()) %> ></td>
				<td>(Required) </td>
			</tr>
			
			<tr>
				<td> </td>
             	<td> MM/DD/YYYY  </td>
            </tr>
            			
			<tr>
				<td align=right><b>End Date:</b></td>
				<td align=left><input type="text" name="endDateStr" value=<%= df.format(endDate.getTime()) %> >	</td>
			</tr>	
			
			<tr>
				<td> </td>
				<td>MM/DD/YYYY </td>
			</tr>
		
			
		
		<tr>
			<td align=right> <b>Transaction ID: </b></td>
			<td align=left>
				<input type="text" name="transactionID">
			</td>
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