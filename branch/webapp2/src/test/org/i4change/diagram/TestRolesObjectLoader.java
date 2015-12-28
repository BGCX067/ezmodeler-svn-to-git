package org.i4change.diagram;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;

public class TestRolesObjectLoader extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestRolesObjectLoader.class);
	
	public void testTest(){
		log.debug("start Test");
		
		Long organization_id = new Long(1);
		int start = 0;
		int max = 25;
//		
//		List<DiagramObject> listResult = DiagramObjectServiceDaoImpl.getInstance().getDiagramObjectsRolesByMax(organization_id, start, max);
//    	
//    	SearchResult sresult = new SearchResult();
//		sresult.setObjectName(DiagramObject.class.getName());
//		sresult.setRecords(DiagramObjectServiceDaoImpl.getInstance().selectMaxFromRoleObject(organization_id));
//		sresult.setResult(listResult);
//		
		
//		log.debug("sresult Count: "+sresult.getRecords());
//		log.debug("sresult listResult SIZE: "+listResult.size());
		
	}

}
