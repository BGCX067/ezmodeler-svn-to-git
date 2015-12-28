package org.i4change.test.diagram;

import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;

import junit.framework.TestCase;

public class TestSearchDiagramObject extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestSearchDiagramObject.class);	

	public TestSearchDiagramObject() {
		super();
	}

	public TestSearchDiagramObject(String name) {
		super(name);
	}
	
	public void testAddPresentationToTable() {
		try {
			
//			Vector objectTypeNames = new  Vector();
//			objectTypeNames.add("datacarrier");
//			
//			SearchResult sResult = DiagramObjectDaoImpl.getInstance().selectSearchDiagramObjectsQueryBuilder(1L, 500, 0, objectTypeNames, "name", true, new Vector(), null, 0);
//			
//			List<Object[]> listResult = sResult.getResult();
//			
//			log.debug("listResult"+listResult);
//			
//			for (Object[] item : listResult) {
//				log.debug(item);
//				for (Object itemObj : item) {
//					log.debug(itemObj);
//				}
//			}
			
			
			
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
