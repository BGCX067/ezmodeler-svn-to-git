<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.*" %>
<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - DoVoid API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<br>
<center>
<font size=2 color=black face=Verdana><b>Do Void</b></font>
<br><br>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");	

	DoVoidRequestType pprequest = new DoVoidRequestType();
	pprequest.setAuthorizationID(request.getParameter("authorization_id"));
	pprequest.setNote(request.getParameter("Note"));
	
	try {
		DoVoidResponseType ppresponse = (DoVoidResponseType)caller.call("DoVoid", pprequest);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
<b>Authorization voided!</b><br><br>
<table width=400>
	<tr>
		<td>Authorization ID:</td>
		<td><%= ppresponse.getAuthorizationID() %></td>
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
<a id="CallsLink" href="Calls.html">Home</a>
</body>
</html>