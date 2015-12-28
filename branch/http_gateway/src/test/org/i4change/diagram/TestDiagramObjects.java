package org.i4change.diagram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;


import junit.framework.TestCase;

public class TestDiagramObjects extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestDiagramObjects.class);	
	
	public void testInsertNewDiagram(){
		log.debug("start Test");
		
//		DiagramServiceDaoImpl.getInstance().addNewDiagramByName("new_name", new Long(1), "comment", null, null);
//		
//		//First you need to get the Base Diagram		
//		Diagram diagram = DiagramServiceDaoImpl.getInstance().getDiagramById(new Long(1));
//		
//		//Then you need to create the DiagramObject and its Instance if not present already
//		DiagramObjectServiceDaoImpl.getInstance().saveDiagramInstanceObject(null, "connector", new Long(1), null, 
//				diagram, "Name Object 1", new Long(1), new Long(1), "asd", "OID");
		
		Long organization_id = new Long(1);
		String search = "";
		int start = 0;
		int max = 25;
		Map<Integer,String> objectTypeNames = new HashMap<Integer,String>();
		objectTypeNames.put(0, "companyFixed");
		objectTypeNames.put(0, "departementFixed");
		//objectTypeNames.put(1, "processtree");
		
		String orderBy = "c.name";
		boolean asc = true;
		
		if (search == null || search.length() == 0) {
    		search = "%";
    	} else {
    		search = "%" + search + "%";
    	}
    	
//    	List<DiagramObject> listResult = DiagramObjectDaoImpl.getInstance().
//		selectParentDiagramObjects(organization_id, start, max, objectTypeNames, 
//				orderBy, asc, search);
//
//		SearchResult sresult = new SearchResult();
//		sresult.setObjectName(DiagramObject.class.getName());
//		sresult.setRecords(DiagramObjectDaoImpl.getInstance().
//		selectParentMaxDiagramObjects(organization_id, objectTypeNames, search));
//		sresult.setResult(listResult);
		
    	List<DiagramObject> listResult = DiagramObjectDaoImpl.getInstance().
		selectParentDiagramObjects(organization_id, start, max, objectTypeNames, 
				orderBy, asc, search);

		SearchResult sresult = new SearchResult();
		sresult.setObjectName(DiagramObject.class.getName());
		sresult.setRecords(DiagramObjectDaoImpl.getInstance().
				selectParentMaxDiagramObjects(organization_id, objectTypeNames, search));
		sresult.setResult(listResult);
    	
//		
    	
    	log.debug("sresult SIZE: "+sresult.getRecords());
    	log.debug("sresult ITEMS: "+sresult.getResult().size());
    	
    	for (Iterator<DiagramObject> iter = sresult.getResult().iterator();iter.hasNext();) {
    		
    		DiagramObject dia = iter.next();
    		
    		log.debug("dia Name: "+dia.getName());
    		
    	}
    	
//    	List<Diagram> listDias = DiagramObjectDaoImpl.getInstance().testSelectParentDiagramObjects(organization_id, new Long(14));
//    	
//    	for (Iterator<Diagram> iter = listDias.iterator();iter.hasNext();) {
//    		
//    		Diagram dia = iter.next();
//    		
//    		log.debug("dia Name: "+dia.getName());
//    		
//    	}
    	
	}
	
	

}
