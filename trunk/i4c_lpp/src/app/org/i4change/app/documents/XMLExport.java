package org.i4change.app.documents;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.remote.Application;
import org.i4change.app.utils.math.CalendarPatterns;
import org.i4change.app.xml.export.ExportDiagramContainer;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.AttributesImpl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @author sebastianwagner
 *
 */
public class XMLExport {
	
	//Spring loaded beans
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;

	private static final Log log = LogFactory.getLog(XMLExport.class);

	public DiagramObjectDaoImpl getDiagramObjectDaoImpl() {
		return diagramObjectDaoImpl;
	}
	public void setDiagramObjectDaoImpl(DiagramObjectDaoImpl diagramObjectDaoImpl) {
		this.diagramObjectDaoImpl = diagramObjectDaoImpl;
	}
	
	public DiagramDaoImpl getDiagramDaoImpl() {
		return diagramDaoImpl;
	}
	public void setDiagramDaoImpl(DiagramDaoImpl diagramDaoImpl) {
		this.diagramDaoImpl = diagramDaoImpl;
	}
	
	public DiagramObjectPropertyDaoImpl getDiagramObjectPropertyDaoImpl() {
		return diagramObjectPropertyDaoImpl;
	}
	public void setDiagramObjectPropertyDaoImpl(
			DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl) {
		this.diagramObjectPropertyDaoImpl = diagramObjectPropertyDaoImpl;
	}

	public File doExport(ExportImportJob exportJob) {
		try {
			
			ExportDiagramContainer exportDiagramContainer = new ExportDiagramContainer();
			
			Diagram diagram = this.diagramDaoImpl.getDiagramById(exportJob.getDiagramId());
			
			log.debug("PATH: "+Application.webAppPath); 
			String fileName = Application.webAppPath + "export" 
					+ File.separatorChar + "export" 
					+ CalendarPatterns.getTimeForStreamId(new Date());
			
			FileOutputStream fos = new FileOutputStream(fileName);
			
			OutputFormat of = new OutputFormat("XML","UTF-8",true);
			
			XMLSerializer serializer = new XMLSerializer(fos,of);
		
			// SAX2.0 ContentHandler.
			ContentHandler hd = serializer.asContentHandler();
			hd.startDocument();
			
			/*############################################
			 * Add the Diagram
			 */
			
			
			
			// USERS tag.
			
//			// USER tags.
//			String[] id = {"PWD122","MX787","A4Q45"};
//			String[] type = {"customer","manager","employee"};
//			String[] desc = {"Tim@Home","Jack&Moud","John D'oé"};
//			for (int i=0;i<id.length;i++)
//			{
//			  atts.clear();
//			  atts.addAttribute("","","ID","CDATA",id[i]);
//			  atts.addAttribute("","","TYPE","CDATA",type[i]);
//			  hd.startElement("","","USER",atts);
//			  hd.characters(desc[i].toCharArray(),0,desc[i].length());
//			  hd.endElement("","","USER");
//			}
			
			AttributesImpl atts = new AttributesImpl();
			
			//add Attributes
			atts.clear();
			atts.addAttribute("","","NAME","CDATA",diagram.getName());
			atts.addAttribute("","","shortName","CDATA",diagram.getShortName());
			
			hd.startElement("","","DIAGRAM",atts);
			//Write Text
			hd.characters(diagram.getDescription().toCharArray(),0,diagram.getDescription().length());
			
			/*
			 * Add Properties to the Diagram
			 * 
			 */
			atts.clear();
			hd.startElement("","","PROPERTIES",atts);
			
			List<DiagramObjectProperty> propertyValues = this.diagramObjectPropertyDaoImpl.getDiagramObjectPropertyByDiagramNo(diagram.getDiagramNo());
			
			for (DiagramObjectProperty diagramObjectProperty : propertyValues)
			{
			  atts.clear();
//			  atts.addAttribute("","","NAME","CDATA",String.valueOf(diagramObjectProperty.getProperty().getLabelid()).toString());
//			  atts.addAttribute("","","LABELID","CDATA",String.valueOf(diagramObjectProperty.getProperty().getLabelid()).toString());
//			  atts.addAttribute("","","TYPE","CDATA",type[i]);
//			  hd.startElement("","","PROPERTY",atts);
//			  hd.characters(desc[i].toCharArray(),0,desc[i].length());
//			  hd.endElement("","","PROPERTY");
			}
			
			hd.endElement("","","PROPERTIES");
			
			hd.endElement("","","DIAGRAM");
			
			
			
			
			
			
			
			
			
			
			
			
			hd.endDocument();
			fos.close();
			
			return new File(fileName);
			
		} catch (Exception err) {
			log.error("[doExport]",err);
		}
		return null;
	}
	
	

	public String _doExport(ExportImportJob exportJob) {
		try {
			
			//Diagram Instances - Graphs
			Vector diagramMap = (Vector) exportJob.getPrintItemList();
			
			//Diagram Objects
			Map<Long,DiagramObject> diagramObjects = new HashMap<Long,DiagramObject>();
			
			for (Iterator iterMap = diagramMap.iterator();iterMap.hasNext();) {
				Vector whiteboardObj = (Vector) iterMap.next();
				Long diagramObjectId = Long.valueOf(whiteboardObj.get(whiteboardObj.size()-7).toString()).longValue();
				
				boolean foundItem = false;
				for (Iterator<Long> iter = diagramObjects.keySet().iterator();iter.hasNext();) {
					Long diaObjInList = iter.next();
					if (diaObjInList.equals(diagramObjectId)) {
						foundItem = true;
						break;
					}
				}
				log.debug("foundItem: "+foundItem);
				if (!foundItem) {
					log.debug("Add: "+diagramObjectId);
					DiagramObject diaObject = this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
					//diaObject.
//					diaObject.setAssignee(null);
//					diaObject.setInsertedby(null);
//					diaObject.setIssueassignee(null);
					
					diagramObjects.put(diagramObjectId, diaObject);
				}
			}
			
			//Data Carriers
			
			
			XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			//String xmlString2 = xStream.toXML(diagramObjects);
			
			//log.debug("xmlString2: "+xmlString2);
			
			//Diagram Instances - Graphs
			Map diagram = new HashMap();
			diagram.put("diagramMap", diagramMap);
			diagram.put("diagramObjects", diagramObjects);
			diagram.put("diagramName", exportJob.getDiagramName());
			diagram.put("diagramId", exportJob.getDiagramId());
			diagram.put("diagramType", exportJob.getDiagramType());
			
			String xmlString = xStream.toXML(diagram);
			
			return xmlString;
			
		} catch (Exception err) {
			log.error("[doExport]",err);
		}
		return null;
	}
	
}
