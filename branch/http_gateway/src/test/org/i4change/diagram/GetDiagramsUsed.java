package org.i4change.diagram;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramType;

public class GetDiagramsUsed  extends TestCase {
	
	private static final Log log = LogFactory.getLog(GetDiagramObjectList.class);	
	
	public void testGetDiagramList(){
		try {
			Long diagramObjectId = new Long(47);
			Long organisation_id = new Long(1);
			Long language_id = new Long(1);
	        	
        	List<DiagramInstanceObject> listDiagramObjects = DiagramInstanceObjectDaoImpl.getInstance().
        					getDiagramInstanceObjectListByDiagramObjectId(diagramObjectId);
        	
        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
        	for (DiagramInstanceObject dia : listDiagramObjects) {
        		
        		Diagram returnDia = DiagramDaoImpl.getInstance().getDiagramByNo(dia.getDiagram().getDiagramNo(), organisation_id);
        		
        		//Can be NULL if the returnDia has been deleted
        		if (returnDia != null) {
        			DiagramType dType = returnDia.getDiagramType();
            		dType.setLabel(Fieldmanagment.getInstance().
            				getFieldByLabelNumberAndLanguage(dia.getDiagram().getDiagramType().getFieldId(), language_id));
            		returnDia.setDiagramType(dType);
            		
            		diagramReturnList.add(returnDia);
        		}
        	}

        	
        	log.debug("diagramReturnList SIZE: "+diagramReturnList.size());
        	for (Diagram returnDia : diagramReturnList) {
        		log.debug("diagramReturnList: "+returnDia.getName()+" "+returnDia.getUpdated());
        	}
		} catch (Exception err) {
			log.error("[getListOfDiagramsByDiagramObjectId]",err);
		}
	}

}
