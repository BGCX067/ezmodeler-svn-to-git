package org.i4change.diagram;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramTypeDaoImpl;
import org.i4change.app.data.diagram.PropertyValidationTypeDaoImpl;

public class AddDefaultPropertyValidationTypes extends TestCase {
	
	private static final Log log = LogFactory.getLog(AddDefaultPropertyValidationTypes.class);	
	
	public void testGetDiagramList() {
		
		//Add default Property Validation Types
		PropertyValidationTypeDaoImpl.getInstance().addDefaultPropertyValidationTypes();
		
	}

}
