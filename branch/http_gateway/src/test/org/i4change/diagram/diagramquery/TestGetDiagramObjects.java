package org.i4change.diagram.diagramquery;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;

public class TestGetDiagramObjects extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetDiagramObjects.class);	
	
	public void testgetDiagramObjectList(){
		try {
			
			log.debug("TestGetLatestDiagrams: ");
			Long organization_id = new Long(2);
			int start = 0;
			int max = 100;
			
			String orderBy = "c.name";
			boolean asc = true;
			String search = "%%";
	        
			Map<Integer,String> objectTypeNames = new HashMap<Integer,String>();
			objectTypeNames.put(0,"unitFixed");
			
			List<DiagramObject> listResult = DiagramObjectDaoImpl.getInstance().
													selectDiagramObjects(organization_id, start, max, objectTypeNames, 
															orderBy, asc, search, null);
	
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(DiagramObject.class.getName());
			sresult.setRecords(DiagramObjectDaoImpl.getInstance().
						selectMaxDiagramObjects(organization_id, objectTypeNames, search, null));
			sresult.setResult(listResult);
							
			log.debug("sresult ITEMS: "+sresult.getResult().size());
			log.debug("sresult RECORDS: "+sresult.getRecords());
		
			for (DiagramObject diaObject : listResult) {
				log.debug("diaObject: "+diaObject.getName()+" "+diaObject.getInserted());
				log.debug("diaObject getDataCarrierDiagramObject: "+diaObject.getDataCarrierDiagramObject().size());
			}
			
		} catch (Exception err) {
			log.error("testgetDiagramObjectList: ",err);
		}
		
	}

}
