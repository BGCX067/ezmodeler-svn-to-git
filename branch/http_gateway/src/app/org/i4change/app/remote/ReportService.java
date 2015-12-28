package org.i4change.app.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.data.project.daos.ProjectDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.report.GeneratePreview;
import org.i4change.app.data.report.daos.ReportDaoImpl;
import org.i4change.app.data.report.daos.ReportTypesDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.hibernate.beans.report.ReportType;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class ReportService {
	
	private static final Log log = LogFactory.getLog(ReportService.class);
	
	private static ReportService instance = null;
	
	private Application application;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private ProjectDaoImpl projectDaoImpl;
	private GeneratePreview generatePreview;
	private ReportDaoImpl reportDaoImpl;
	private ReportTypesDaoImpl reportTypesDaoImpl;

	public static synchronized ReportService getInstance() {
		if (instance == null) {
			instance = new ReportService();
		}
		return instance;
	}
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}

	public DiagramObjectPropertyDaoImpl getDiagramObjectPropertyDaoImpl() {
		return diagramObjectPropertyDaoImpl;
	}
	public void setDiagramObjectPropertyDaoImpl(
			DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl) {
		this.diagramObjectPropertyDaoImpl = diagramObjectPropertyDaoImpl;
	}
	public DiagramDaoImpl getDiagramDaoImpl() {
		return diagramDaoImpl;
	}
	public void setDiagramDaoImpl(DiagramDaoImpl diagramDaoImpl) {
		this.diagramDaoImpl = diagramDaoImpl;
	}
	public DiagramObjectDaoImpl getDiagramObjectDaoImpl() {
		return diagramObjectDaoImpl;
	}
	public void setDiagramObjectDaoImpl(DiagramObjectDaoImpl diagramObjectDaoImpl) {
		this.diagramObjectDaoImpl = diagramObjectDaoImpl;
	}
	public ProjectRelationDaoImpl getProjectRelationDaoImpl() {
		return projectRelationDaoImpl;
	}
	public void setProjectRelationDaoImpl(
			ProjectRelationDaoImpl projectRelationDaoImpl) {
		this.projectRelationDaoImpl = projectRelationDaoImpl;
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

	public ReportTypesDaoImpl getReportTypesDaoImpl() {
		return reportTypesDaoImpl;
	}
	public void setReportTypesDaoImpl(ReportTypesDaoImpl reportTypesDaoImpl) {
		this.reportTypesDaoImpl = reportTypesDaoImpl;
	}
	
	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}
	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
	}


	public Long startGenerateOverview(String SID, Long projectId, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	List<ProjectRelation> relationList = this.projectRelationDaoImpl.getProjectRelationsByProjectById(projectId);
	        	
	        	//if the Report will be empty return directly to the User
	        	if (relationList.size() == 0) {
	        		return new Long(-52);
	        	}
	        	
	        	Project project = this.projectDaoImpl.getProjectById(projectId);
	        	ReportType reportType = this.reportTypesDaoImpl.getReportTypeById(1L);
	        	
	        	this.reportDaoImpl.addReport(project.getName(), users_id, reportType, project, new Date(), null);
				
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}

}
