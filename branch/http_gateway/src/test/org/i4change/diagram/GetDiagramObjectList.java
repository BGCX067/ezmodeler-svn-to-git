package org.i4change.diagram;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramType;

public class GetDiagramObjectList extends TestCase {
	
	private static final Log log = LogFactory.getLog(GetDiagramObjectList.class);	
	
	public void testGetDiagramList(){
		log.debug("start Test");
		
		Long organisation_id = new Long(1);
		Long language_id = new Long(1);
		
		List<Diagram> diagramList = DiagramDaoImpl.getInstance().getDiagramList(organisation_id);
    	
    	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
    	for (Diagram dia : diagramList) {
    		
    		Diagram returnDia = DiagramDaoImpl.getInstance().getDiagramByNo(dia.getDiagramNo(), organisation_id);
    		
    		DiagramType dType = returnDia.getDiagramType();
    		dType.setLabel(Fieldmanagment.getInstance().
    				getFieldByLabelNumberAndLanguage(dia.getDiagramType().getFieldId(), language_id));
    		returnDia.setDiagramType(dType);
    		
    		diagramReturnList.add(returnDia);
    	}
		
		
	}
	
	

}
