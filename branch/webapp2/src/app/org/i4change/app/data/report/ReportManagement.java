package org.i4change.app.data.report;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.report.daos.ReportDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.hibernate.beans.report.Report;

import com.sun.tools.jdi.LinkedHashMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ReportManagement {
	
	private static final Log log = LogFactory.getLog(ReportManagement.class);

	private static ReportManagement instance = null;

	private ReportManagement() {
	}

	public static synchronized ReportManagement getInstance() {
		if (instance == null) {
			instance = new ReportManagement();
		}
		return instance;
	}
	
	public void getReportsToGenerate() {
		try {
			
			List<Report> reports = ReportDaoImpl.getInstance().getReportsToConvertByType(1L);
			
			
			
		} catch (Exception err) {
			log.error("[getReportsToGenerate]",err);
		}
	}

	
	public void generateReportsForOverview(List<Report> reports) {
		try {
		
			for (Report report : reports ) {
				
				List<ProjectRelation> relationList = ProjectRelationDaoImpl.getInstance().getProjectRelationsByProjectById(report.getProject().getProjectId());
				
				for (ProjectRelation projectRelation : relationList ) {
					
					Long diagramNo = projectRelation.getDiagramNo();
					
					Diagram diagram = DiagramDaoImpl.getInstance().getDiagramByNo(diagramNo, report.getProject().getOrganisation().getOrganisation_id());
					
					log.debug("diaggetDiagramObjectsByNoram: "+diagram);
					
					List<DiagramInstanceObject> digramInstanceList = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
					
					Map diagramMap = new HashMap();
					int i = 0;
					for (DiagramInstanceObject diagramInstance : digramInstanceList) {
						
						log.debug("diagramInstance1 : "+diagramInstance);
						
						XStream xStream = new XStream(new XppDriver());
						xStream.setMode(XStream.NO_REFERENCES);
						
						diagramInstance.setGraphAsObject((LinkedHashMap) xStream.fromXML(diagramInstance.getGraphObject()));
						
						diagramMap.put(i, diagramInstance.getGraphAsObject());
						i++;
						log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
						
					}
					
					//Generate SVG and Stream to temp-file Dir
					Map<String,File> files = GeneratePreview.getInstance().generatePreview(diagramMap, diagram.getDiagramType().getDiagramTypeId());
					
					File svg = files.get("svg");
					File png = files.get("png");
					File thumb = files.get("thumb");
					
				}
				
				report.setEndProcessing(new Date());
				ReportDaoImpl.getInstance().updateReport(report);
				
			}
			
		} catch (Exception err) {
			log.error("[getReportsToGenerate]",err);
		}
	}
}
