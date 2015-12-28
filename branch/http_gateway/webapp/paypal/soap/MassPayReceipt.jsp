<%@ page import="com.paypal.sdk.services.CallerServices" %>
<%@ page import="com.paypal.soap.api.MassPayRequestType" %>
<%@ page import="com.paypal.soap.api.MassPayRequestItemType" %>
<%@ page import="com.paypal.soap.api.BasicAmountType" %>
<%@ page import="com.paypal.soap.api.MassPayResponseType" %>
<%@ page import="com.paypal.soap.api.CurrencyCodeType" %>
<%@ page import="com.paypal.soap.api.ReceiverInfoCodeType" %>
<%@ page import="com.paypal.soap.api.AckCodeType" %>


<%@ page language="java" %>

<html>
<head>
<title>PayPal JSP SDK - MassPay API</title>
<link href="sdk.css" rel="stylesheet" type="text/css"/>
</head> 
<body alink=#0000FF vlink=#0000FF>
<center>
<font size=2 color=black face=Verdana><b>Mass Pay</b></font>
<%
	CallerServices caller = (CallerServices) session.getValue("caller");	

	MassPayRequestType pp_request = new MassPayRequestType();
		String[] receiverEmailItems=request.getParameterValues("receiveremail");
		String[] amountItems=request.getParameterValues("amount");
		String[] uniqueIDItems=request.getParameterValues("uniqueID");
		String[] noteItems=request.getParameterValues("note");
		String   currencyCode=request.getParameter("currency");
		int numberOfNotNullItems=0;
		int totalItems=receiverEmailItems.length;
		String recreceiverEmailNotNull;
				 
		while(totalItems>0)
		{     recreceiverEmailNotNull=receiverEmailItems[totalItems-1];
				totalItems--;
		     if(recreceiverEmailNotNull != null && recreceiverEmailNotNull.length()!= 0)
		           numberOfNotNullItems++;
		}
				
   MassPayRequestItemType[] massPayItem = new MassPayRequestItemType[numberOfNotNullItems];
		
		for(int i=0,j=0;i<receiverEmailItems.length; i++)
		{
			String recreceiverEmail=receiverEmailItems[i];
			if(recreceiverEmail != null && recreceiverEmail.length()!= 0)
			  {
				MassPayRequestItemType massItemReq = new MassPayRequestItemType();
				
				massItemReq.setReceiverEmail(receiverEmailItems[i]);
				
				BasicAmountType amount = new BasicAmountType();
				
				amount.set_value(amountItems[i]);
				
				amount.setCurrencyID(CurrencyCodeType.fromString(currencyCode));
				
				massItemReq.setUniqueId(uniqueIDItems[i]);
				
				massItemReq.setNote(noteItems[i]);
				
				massItemReq.setAmount(amount);
				
				massPayItem[j]=massItemReq;
				
				j++;
			
			  }								
		}
	
	pp_request.setEmailSubject(request.getParameter("EmailSubject"));;
	
	pp_request.setReceiverType(ReceiverInfoCodeType.fromString("EmailAddress"));
	
	pp_request.setMassPayItem(massPayItem);

	
	try {
		MassPayResponseType ppresponse = (MassPayResponseType) caller.call("MassPay", pp_request);
		
		if (!ppresponse.getAck().equals(AckCodeType.Success) && 
			!ppresponse.getAck().equals(AckCodeType.SuccessWithWarning)) {
			session.setAttribute("response", ppresponse);
			response.sendRedirect("APIError.jsp");
			return;
		}
%>
		<TABLE class="api" id="Table1">
			<TR>
				<TD class="header" colSpan="2"><STRONG>MassPay has been completed successfully!</STRONG></TD>
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