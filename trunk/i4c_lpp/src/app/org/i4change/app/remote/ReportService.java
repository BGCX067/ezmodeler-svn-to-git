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

public class ReportService implements IReportService {
	
	private static final Log log = LogFactory.getLog(ReportService.class);
	
	private static IReportService instance = null;
	
	private IApplication application;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private ProjectDaoImpl projectDaoImpl;
	private GeneratePreview generatePreview;
	private ReportDaoImpl reportDaoImpl;
	private ReportTypesDaoImpl reportTypesDaoImpl;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;

	public IApplication getApplication() {
		return application;
	}
	public void setApplication(IApplication application) {
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

	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IReportService#startGenerateOverview(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public Long startGenerateOverview(String SID, Long projectId, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
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
