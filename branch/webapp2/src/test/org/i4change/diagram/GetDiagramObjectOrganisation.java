package org.i4change.diagram;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramObjectOrganisationDaoImpl;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;

public class GetDiagramObjectOrganisation extends TestCase {
	
	private static final Log log = LogFactory.getLog(GetDiagramObjectOrganisation.class);	
	
	public void testGetDiagramList(){
		
		log.debug("start Test");
		try {
			List<DiagramObjectOrganisation> returnList = DiagramObjectOrganisationDaoImpl.getInstance().
					getOrganisationsObjectsByDiagramObject(
					new Long(208), new Long(1), "c.name", true);
			
			for (DiagramObjectOrganisation diaObject : returnList) {
				log.debug("diaObject: "+diaObject.getDiagramObject().getName());
			}
		} catch (Exception err) {
			log.error("testGetDiagramList: ",err);
		}
		
	}

}
