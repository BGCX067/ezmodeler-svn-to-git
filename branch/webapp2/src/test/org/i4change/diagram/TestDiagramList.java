package org.i4change.diagram;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;

import java.util.LinkedHashMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;


public class TestDiagramList extends TestCase {
	
	private static final Log log = LogFactory.getLog(TestDiagramList.class);	
	
	public void testGetDiagramList(){
		log.debug("start Test");
		try {
			
			Long diagramNo = new Long(1);
			Long organisation_id = new Long(1);
			
			Diagram diagram = DiagramDaoImpl.getInstance().getDiagramByNo(diagramNo, organisation_id);
			
			log.debug("diaggetDiagramObjectsByNoram: "+diagram);
			
			List<DiagramInstanceObject> digramInstanceList = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
			
			for (DiagramInstanceObject diagramInstance : digramInstanceList) {
				
				log.debug("diagramInstance1 : "+diagramInstance);
				
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.NO_REFERENCES);
				
				diagramInstance.setGraphAsObject((LinkedHashMap) xStream.fromXML(diagramInstance.getGraphObject()));
				
				log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
			}
			
		} catch (Exception err) {
			log.error("[testGetDiagramList]",err);
		}
	}

}
