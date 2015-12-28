package org.i4change.app.remote;

import junit.framework.TestCase;

import org.i4change.app.installation.IImportInitvalues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestInstallService extends TestCase {

	public TestInstallService() {
		super();
	}

	public TestInstallService(String name) {
		super(name);
	}

	public void testMainServiceRegister() {
		try {

			// ApplicationContext context = new
			// ClassPathXmlApplicationContext("WebContent/WEB-INF/applicationContext.xml");
			ApplicationContext context = new FileSystemXmlApplicationContext(
					"WebContent/WEB-INF/applicationContext.xml");

			IImportInitvalues importInitvalues = (IImportInitvalues) context
					.getBean("i4change.txImportInitvalues");

			importInitvalues.loadMainMenu();

			assertFalse(true);

		} catch (Exception err) {
			err.printStackTrace();

		}
	}

}
