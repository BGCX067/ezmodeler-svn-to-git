<%@ include file='constants.jsp' %>  
<%@ page import="com.paypal.sdk.profiles.APIProfile" %>
<%@ page import="com.paypal.sdk.profiles.ProfileFactory" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" %>

<jsp:useBean id="caller" scope="session" class="com.paypal.sdk.services.CallerServices" />

<%	
	try {
		APIProfile profile = null;
				
		Hashtable pars = new Hashtable();
		if (FileUpload.isMultipartContent(request)) {
			DiskFileUpload upload = new DiskFileUpload();

			// Parse the request
			List items = upload.parseRequest(request);
	
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					pars.put(item.getFieldName(), item.getString());
				} else {
					if ((item.getName() != null) && (item.getName().length() > 0)) {
						File cfile = new File(item.getName()); 
						File tosave = new File(getServletContext().getRealPath("/cert/"),cfile.getName());
						item.write(tosave);
						pars.put("certificateFile", tosave.getAbsolutePath());
					}
				}
			}
		}
		String environment = (String) pars.get("environment");
		if (pars.containsKey("DefaultButton")) {
			/*
			 WARNING: Do not embed plaintext credentials in your application code.
 			 Doing so is insecure and against best practices.
			 Your API credentials must be handled securely. Please consider 
			 encrypting them for use in any production environment, and ensure
			 that only authorized individuals may view or modify them.
			 */
			profile = ProfileFactory.createSSLAPIProfile();
			profile.setAPIUsername("sdk-seller_api1.sdk.com");
			profile.setAPIPassword("12345678");
			profile.setCertificateFile(getServletContext().getRealPath("WEB-INF/cert/sdk-seller.p12"));
			profile.setPrivateKeyPassword("password");
			profile.setEnvironment(environment);
		} else if (pars.containsKey("certificateFile")) {
			profile = ProfileFactory.createSSLAPIProfile();
			profile.setAPIUsername((String) pars.get("apiUsername"));
			profile.setAPIPassword((String) pars.get("apiPassword"));
			profile.setCertificateFile((String) pars.get("certificateFile"));
			profile.setPrivateKeyPassword((String) pars.get("privateKeyPassword"));
			profile.setEnvironment(environment);
		} else {
			profile = ProfileFactory.createSignatureAPIProfile();
			profile.setAPIUsername((String) pars.get("apiUsername"));
			profile.setAPIPassword((String) pars.get("apiPassword"));
			profile.setSignature((String) pars.get("signature"));
			profile.setEnvironment(environment);
		}
	    caller.setAPIProfile(profile);
    	session.setAttribute("caller", caller);
    	response.sendRedirect("Calls.html");
	} catch (Exception e) {
		session.setAttribute("exception", e);
		response.sendRedirect("Error.jsp");
	}
%>
