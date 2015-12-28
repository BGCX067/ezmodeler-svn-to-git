package org.i4change.app.data.report;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramSummaryDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.report.daos.ReportDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.hibernate.beans.report.Report;

import java.util.LinkedHashMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ReportManagement {
	
	private static final Log log = LogFactory.getLog(ReportManagement.class);

	//Spring loaded beans
	private DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private DiagramSummaryDaoImpl diagramSummaryDaoImpl;
	private GeneratePreview generatePreview;
	private ReportDaoImpl reportDaoImpl;
	
	public DiagramInstanceObjectDaoImpl getDiagramInstanceObjectDaoImpl() {
		return diagramInstanceObjectDaoImpl;
	}

	public void setDiagramInstanceObjectDaoImpl(
			DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl) {
		this.diagramInstanceObjectDaoImpl = diagramInstanceObjectDaoImpl;
	}

	public ProjectRelationDaoImpl getProjectRelationDaoImpl() {
		return projectRelationDaoImpl;
	}
	public void setProjectRelationDaoImpl(
			ProjectRelationDaoImpl projectRelationDaoImpl) {
		this.projectRelationDaoImpl = projectRelationDaoImpl;
	}

	public DiagramSummaryDaoImpl getDiagramSummaryDaoImpl() {
		return diagramSummaryDaoImpl;
	}
	public void setDiagramSummaryDaoImpl(DiagramSummaryDaoImpl diagramSummaryDaoImpl) {
		this.diagramSummaryDaoImpl = diagramSummaryDaoImpl;
	}
	
	public GeneratePreview getGeneratePreview() {
		return generatePreview;
	}
	public void setGeneratePreview(GeneratePreview generatePreview) {
		this.generatePreview = generatePreview;
	}
	
	public ReportDaoImpl getReportDaoImpl() {
		return reportDaoImpl;
	}
	public void setReportDaoImpl(ReportDaoImpl reportDaoImpl) {
		this.reportDaoImpl = reportDaoImpl;
	}

	public void getReportsToGenerate() {
		try {
			
			List<Report> reports = this.reportDaoImpl.getReportsToConvertByType(1L);
			
			
			
		} catch (Exception err) {
			log.error("[getReportsToGenerate]",err);
		}
	}

	
	public void generateReportsForOverview(List<Report> reports) {
		try {
		
			for (Report report : reports ) {
				
				List<ProjectRelation> relationList = this.projectRelationDaoImpl.getProjectRelationsByProjectById(report.getProject().getProjectId());
				
				for (ProjectRelation projectRelation : relationList ) {
					
					Long diagramNo = projectRelation.getDiagramNo();
					
					Diagram diagram = this.diagramSummaryDaoImpl.getDiagramByNo(diagramNo, report.getProject().getOrganisation().getOrganisation_id());
					
					log.debug("diaggetDiagramObjectsByNoram: "+diagram);
					
					List<DiagramInstanceObject> digramInstanceList = this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
					
					Vector diagramMap = new Vector();
					int i = 0;
					for (DiagramInstanceObject diagramInstance : digramInstanceList) {
						
						log.debug("diagramInstance1 : "+diagramInstance);
						
						XStream xStream = new XStream(new XppDriver());
						xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
						
						diagramInstance.setGraphAsObject((Vector) xStream.fromXML(diagramInstance.getGraphObject()));
						diagramInstance.setGraphObject("");
						
						//diagramInstance.setGraphAsObject((LinkedHashMap) xStream.fromXML(diagramInstance.getGraphObject()));
						
						diagramMap.add(diagramInstance.getGraphAsObject());
						i++;
						log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
						
					}
					
					//Generate SVG and Stream to temp-file Dir
					Map<String,File> files = this.generatePreview.generatePreview(diagramMap, diagram.getDiagramType().getDiagramTypeId());
					
					File svg = files.get("svg");
					File png = files.get("png");
					File thumb = files.get("thumb");
					
				}
				
				report.setEndProcessing(new Date());
				this.reportDaoImpl.updateReport(report);
				
			}
			
		} catch (Exception err) {
			log.error("[getReportsToGenerate]",err);
		}
	}
}
