package org.i4change.diagram;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.IssueAssigneeDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;

public class TestGetAllIssuesAssigned extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestGetAllIssuesAssigned.class);

	public void testAllIssues(){
		try {
			Long organization_id = new Long(4);
			Long users_id = new Long(4);
			
			log.debug("organization_id: "+organization_id);
	    	
	    	//First get all Diagrams available, for THIS moment
	    	List<Diagram> diagramList = DiagramDaoImpl.getInstance().getDiagramList(organization_id);
	    	
	    	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
	    	for (Diagram dia : diagramList) {
	    		Diagram returnDia = DiagramDaoImpl.getInstance().getDiagramByNo(dia.getDiagramNo(), organization_id);
	    		//.. for THIS moment
	    		diagramReturnList.add(returnDia);
	    	}
	    	
	    	//Return List
	    	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();

	    	List<DiagramObject> diagramObjectList = DiagramObjectDaoImpl.getInstance().
	    												getDiagramObjectsIssuesAssignedToMe(organization_id, users_id);

	    	//Check if this Issue is part of the latest Diagram Revision and still up to date
	    	for (DiagramObject diaObject : diagramObjectList) {
	    		DiagramInstanceObject diagramInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().
								getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
				
				//Only add if this Item is found on the latest/current Diagrams available at this moment
				if (diagramInstanceObjectList != null) {	
	    			for (Diagram dia : diagramReturnList) {
	    				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
	    					//Add the Diagram Object to the return List to open it
	    					diaObject.setDiagram(dia);
	    					returnDiagramObjectList.add(diaObject);
	    				}
	    			}
				}
				
				
	    	}
	    	
	    	//Get all Assigned Objects (Pending Role at the moment only)
	    	List<DiagramObject> diagramObjectListPending = DiagramObjectDaoImpl.getInstance().
	    							getDiagramObjectsPendingObjectsAssignedToMe(organization_id, users_id);
	    	//Check if this Issue is part of the latest Diagram Revision and still up to date
	    	for (DiagramObject diaObject : diagramObjectListPending) {
	    		DiagramInstanceObject diagramInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().
								getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
				
				//Always add this Item
				if (diagramInstanceObjectList != null) {
	    			for (Diagram dia : diagramReturnList) {
	    				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
	    					//Add the Diagram to the Diagram Object of the return List to open it
	    					diaObject.setDiagram(dia);
	    				}
	    			}
				}
				
				returnDiagramObjectList.add(diaObject);
	    	}
	    	
	    	for (DiagramObject diaObject : returnDiagramObjectList) {
	    		log.debug("DiagramObject: "+diaObject.getName());
	    	}
	    	
		} catch (Exception err ){
			log.error("[TestAllIssues] ",err);
		}
	}
}
