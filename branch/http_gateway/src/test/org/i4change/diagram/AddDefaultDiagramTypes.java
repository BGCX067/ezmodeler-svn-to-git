package org.i4change.diagram;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramTypeDaoImpl;

public class AddDefaultDiagramTypes extends TestCase {
	
	private static final Log log = LogFactory.getLog(AddDefaultDiagramTypes.class);	
	
	public void testGetDiagramList() {
		
		DiagramTypeDaoImpl.getInstance().addDiagramType(new Long(593),"1");
		DiagramTypeDaoImpl.getInstance().addDiagramType(new Long(594),"2");
		DiagramTypeDaoImpl.getInstance().addDiagramType(new Long(595),"3");
		
	}

}
