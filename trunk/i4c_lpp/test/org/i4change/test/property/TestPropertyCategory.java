package org.i4change.test.property;

import java.util.List;

import junit.framework.TestCase;

import org.i4change.app.data.diagram.PropertyCategoryDaoImpl;
import org.i4change.app.hibernate.beans.diagram.PropertyCategory;

public class TestPropertyCategory extends TestCase {

	public TestPropertyCategory() {
		super();
	}

	public TestPropertyCategory(String name) {
		super(name);
	}
	
	public void testAddPresentationToTable() {
		try {
			
//			List<PropertyCategory> listR = PropertyCategoryDaoImpl.getInstance().getPropertyCategoriesList(0, 100, "c.propertyCategoryId", true, "");
//			
//			System.out.println("listR "+listR.size());
			
		} catch (Exception err) {
			err.printStackTrace();
		}
	}


}
