package org.i4change.test.javarpc;

import java.util.Hashtable;
import java.util.Vector;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TestJavaRPC {
	
	private TestService testService = null;
	
	public TestService getTestService() {
		return testService;
	}
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public String passInteger(int i) {
		try { 
		
//		ServletContext servletContext = this.servletRequest.getSession().getServletContext();
//		
//		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//		TestService serviceClass = (TestService) context.getBean("testService");
//		
//		
//		System.out.println("serviceClass: "+serviceClass);
//		
//		return "got integer parameter: " + serviceClass.doTest();
		} catch (Exception err) {
			err.printStackTrace();
		}
		return "-1";
	}

	public String passDouble(double d) {
		try {
			
			
			return "got double parameter: " + this.testService.doTest();
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return "-1";
		
	}

	public String passBoolean(boolean b) {
		return "got boolean parameter: " + b;
	}

	public String passClientArray(Vector v) {
		return "got vector parameter: " + v;
	}

	public String passClientObject(Hashtable t) {
		return "got hashtable parameter: " + t;
	}

}
