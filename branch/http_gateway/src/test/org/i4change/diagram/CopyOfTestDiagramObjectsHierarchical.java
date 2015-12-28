package org.i4change.diagram;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;

public class CopyOfTestDiagramObjectsHierarchical extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestDiagramObjects.class);	
	
	public void testgetDiagramObjectList(){
		try {
			Long organization_id = new Long(1);
			int start = 0;
			int max = 100;
			
//			List<DiagramObject> listResult = DiagramObjectServiceDaoImpl.getInstance().
//												getDiagramObjectsHierarchicalByMax(organization_id, start, max);
//		
//			SearchResult sresult = new SearchResult();
//			sresult.setObjectName(DiagramObject.class.getName());
//			sresult.setRecords(DiagramObjectServiceDaoImpl.getInstance().selectMaxDiagramObjectsHierarchical(organization_id));
//			sresult.setResult(listResult);
//			
//			log.debug("sresult ITEMS: "+sresult.getResult().size());
//			log.debug("sresult RECORDS: "+sresult.getRecords());
		} catch (Exception err) {
			log.error("testgetDiagramObjectList: ",err);
		}
		
	}
	
}
