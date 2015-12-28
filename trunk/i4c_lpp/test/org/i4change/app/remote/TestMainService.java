package org.i4change.app.remote;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;

public class TestMainService extends TestCase {
	
	public TestMainService() {
		super();
	}

	public TestMainService(String name) {
		super(name);
	}
	
	public void testMainServiceRegister() {
		try {
			
			Map regObject = new HashMap();
			
//			regObject.put("Username","hans");
//			regObject.put("Userpass","asdasd"), 
//			regObject.put("lastname",""), regObject.put("firstname").toString(), regObject.put("email").toString(), 
//			null, regObject.put("street").toString(), regObject.put("additionalname").toString(), 
//			regObject.put("fax").toString(), regObject.put("zip").toString(), 
//			Long.valueOf(regObject.put("states_id").toString()).longValue(), regObject.put("town").toString(), 
//			Long.valueOf(regObject.put("language_id").toString()).longValue(),
//			regObject.put("domain").toString(),
//			Integer.valueOf(regObject.put("port").toString()).intValue(),
//			regObject.put("webapp").toString()
			
			//ApplicationContext context = new ClassPathXmlApplicationContext("WebContent/WEB-INF/applicationContext.xml");
			ApplicationContext context = new FileSystemXmlApplicationContext("WebContent/WEB-INF/applicationContext.xml");
			
			IMainService mainService = (IMainService) context.getBean("mainService.service");
//			try {
//				mainService.registerUserByObjectAdvanced(regObject);
//				fail("Es wurde keine Exception geworfen");
//			} catch (Exception err) {
//				
//			}
			
			assertFalse( mainService.registerUserByObjectAdvanced(regObject) < 0 );
			
			
			
		} catch (Exception err) {
			err.printStackTrace();
			
		}
	}

}
