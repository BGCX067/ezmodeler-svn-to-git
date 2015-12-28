package org.i4change.diagram;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.IssueAssigneeDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;

public class TestAssignedIssues extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestAssignedIssues.class);	
	
	public void testGetDiagramList(){
		try {
        	Long users_id = new Long(1);
			Long organization_id = new Long(1);
			
	        	log.debug("organization_id: "+organization_id);
	        	
	        	//First get all Diagrams available, for THIS moment
	        	List<Diagram> diagramList = DiagramDaoImpl.getInstance().getDiagramList(organization_id);
	        	
	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
	        	for (Diagram dia : diagramList) {
	        		Diagram returnDia = DiagramDaoImpl.getInstance().getDiagramByNo(dia.getDiagramNo(), organization_id);
	        		diagramReturnList.add(returnDia);
	        	}
	        	
	        	List<DiagramObject> diagramObjectList = DiagramObjectDaoImpl.getInstance().
	        			getDiagramObjectsIssues(organization_id);
	        	
	        	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();
	        	for (DiagramObject diaObject : diagramObjectList) {
	        		
	        		log.debug("DiagramObject NAME: "+diaObject.getName());
	        		IssueAssignee issueAssignee = IssueAssigneeDaoImpl.getInstance().getIssueAssignee(diaObject.getDiagramObjectId());
	        		
	        		if (issueAssignee != null) {
	        			log.debug("issueAssignee Login: "+issueAssignee.getAssignee().getLogin());
		        		diaObject.setIssueassignee(issueAssignee);
		        		if (diaObject.getIssueassignee().getAssignee().getUser_id().equals(users_id)) {
		        			
		        			DiagramInstanceObject diagramInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().
		        			getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
		        			
		        			//Only add if this Item is found on the latest/current Diagrams available at this moment
		        			if (diagramInstanceObjectList != null) {
	//		        			Diagram diagram = DiagramServiceDaoImpl.getInstance().getDiagramByNo(
	//		        						diagramInstanceObjectList.get(0).getDiagram().getDiagramNo(), organization_id);
			        			
			        			for (Diagram dia : diagramReturnList) {
			        				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
			        					//Add the Diagram Object to the return List to open it
			        					diaObject.setDiagram(dia);
			        					returnDiagramObjectList.add(diaObject);
			        				}
			        			}
		        			}
		        			
		        		}
	        		}
	        	}
	        	
	        	
	        	log.debug("FOUND ISSUES: "+returnDiagramObjectList.size());
	        	
	        	for (DiagramObject returnObject : returnDiagramObjectList) {
	        		log.debug("returnObject: "+returnObject.getName());
	        		log.debug("returnObject: "+returnObject.getIssueassignee());
	        		log.debug("returnObject: "+returnObject.getDiagram());
	        	}
	        
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
	}

}
