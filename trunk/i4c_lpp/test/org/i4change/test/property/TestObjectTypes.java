package org.i4change.test.property;

import java.util.List;

import junit.framework.TestCase;

import org.i4change.app.data.diagram.ObjectTypeDaoImpl;

public class TestObjectTypes extends TestCase {

	public TestObjectTypes() {
		super();
	}

	public TestObjectTypes(String name) {
		super(name);
	}
	
	public void testAddPresentationToTable() {
		try {
			
//			ObjectTypeDaoImpl.getInstance().addDefaultObjectTypes();
			
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
