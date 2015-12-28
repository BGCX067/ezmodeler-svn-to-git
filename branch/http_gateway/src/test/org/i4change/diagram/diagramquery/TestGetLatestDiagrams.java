package org.i4change.diagram.diagramquery;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.diagram.TestDiagramObjects;

public class TestGetLatestDiagrams extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetLatestDiagrams.class);	
	
	public void testgetDiagramObjectList(){
		try {
			
			log.debug("TestGetLatestDiagrams: ");
			Long organization_id = new Long(1);
			String orderBy = "c.name";
			orderBy = "c.diagramNo";
			Boolean orderAsc = true;
			
			int start = 0;
			int max = 100;
			
			String search = "%";
			
			List<Diagram> listResult = DiagramDaoImpl.getInstance().getDiagramByNoMaxAndOrder(organization_id, orderBy, orderAsc, start, max, search);
		
			SearchResult sresult = new SearchResult();
			sresult.setObjectName(Diagram.class.getName());
			sresult.setRecords(DiagramDaoImpl.getInstance().getMaxDiagram(organization_id, search));
			sresult.setResult(listResult);
			
			log.debug("sresult ITEMS: "+sresult.getResult().size());
			log.debug("sresult RECORDS: "+sresult.getRecords());
			
			for (Diagram dia : listResult) {
				log.debug("dia: "+dia.getName()+" "+dia.getDiagramId()+" "+dia.getDiagramNo()+" "+dia.getDiagramrevision().getDiagramrevisionId()+ " " + dia.getInserted());
			}
			
			for (Diagram dia : listResult) {
				log.debug("dia: "+dia.getDiagramNo());
			}
			
		} catch (Exception err) {
			log.error("testgetDiagramObjectList: ",err);
		}
		
	}

}
