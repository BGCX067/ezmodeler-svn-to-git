package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.remote.DiagramService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class DiagramServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(DiagramServiceRPC.class);
	
	public Long updateDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevsionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.updateDiagram(SID, diagramName, revisionComment, diagramMapObj, organisation_id, 
					diagramType, diagramNo, read, write, onlyIssues, users, language_id, 
					currentRevsionId, forceUpdate, diagramObjectId, projectId);
			
		} catch (Exception err) {
			log.error ("[updateDiagram]",err);
		}
		return null;
	}

	public Diagram getDiagramDrillDownByDiagramObjectIdAndType(String SID, Long organisation_id, 
			Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramDrillDownByDiagramObjectIdAndType(SID, organisation_id, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramDrillDownByDiagramObjectIdAndType]",err);
		}
		return null;
	}

	public Long saveNewDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.saveNewDiagram(SID, diagramName, revisionComment, diagramMapObj, 
					organisation_id, diagramType, read, write, onlyIssues, users, 
					language_id, diagramObjectId, projectId);
			
			
		} catch (Exception err) {
			log.error ("[saveNewDiagram]",err);
		}
		return null;
	}

	public List<Diagram> getDiagramHistoryByNo(String SID, Long organisation_id, 
			Long diagramNo, Long language_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramHistoryByNo(SID, organisation_id, diagramNo, language_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramHistoryByNo]",err);
		}
		return null;
	}

	public Diagram getDiagramById(String SID, Long diagramId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramById(SID, diagramId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramById]",err);
		}
		return null;
	}	

	public SearchResult getDiagramSearch(String SID, int start, int max, String orderBy, boolean asc, 
			Long organization_id, String search){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramSearch(SID, start, max, orderBy, asc, organization_id, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramSearch]",err);
		}
		return null;
	}

	public SearchResult getDiagramObjects(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			log.debug("getDiagramObjects "+objectTypeNames);
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjects(SID, organization_id, start, max, objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjects]",err);
		}
		return null;
	}	

	public SearchResult getDiagramObjectsNoMatterPending(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			log.debug("getDiagramObjectsNoMatterPending "+objectTypeNames);
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsNoMatterPending(SID, organization_id, start, 
					max, objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsNoMatterPending]",err);
		}
		return null;
	}	
	
	public SearchResult getDiagramObjectsIncludingAssingees(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			log.debug("getiagramObjectsIncludingAssingees "+objectTypeNames);
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsIncludingAssingees(SID, organization_id, start, 
					max, objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsNoMatterPending]",err);
		}
		return null;
	}

	public SearchResult getParentDiagramObjects(String SID, Long organization_id, int start, int max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getParentDiagramObjects(SID, organization_id, start, max, 
					objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getParentDiagramObjects]",err);
		}
		return null;
	}		

	public List<DiagramInstanceObject> getDiagramObjectsById(String SID, Long diagram_id, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsById(SID, diagram_id, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsById]",err);
		}
		return null;
	}		

	public List<DiagramInstanceObject> getLatestDiagramObjectsByNo(String SID, Long diagramNo, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getLatestDiagramObjectsByNo(SID, diagramNo, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getLatestDiagramObjectsByNo]",err);
		}
		return null;
	}

	public List<DiagramInstanceObject> getLatestDiagramObjectsById(String SID, Long diagramId, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getLatestDiagramObjectsById(SID, diagramId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getLatestDiagramObjectsById]",err);
		}
		return null;
	}	

	public Long updateDiagramNameById(String SID, Long diagramId, String newName, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.updateDiagramNameById(SID, diagramId, newName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[updateDiagramNameById]",err);
		}
		return null;
	}		

	public int deleteDiagramByNo(String SID, Long diagramNo, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.deleteDiagramByNo(SID, diagramNo, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[deleteDiagramByNo]",err);
		}
		return -1;
	}

	public Long checkForUniqueName(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.checkForUniqueName(SID, objectName, diagramObjectId, typeOfObject, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[checkForUniqueName]",err);
		}
		return null;
	}	

	public List<Diagram> checkDiagramsByDiagramObjectId(String SID, String objectName, 
			Long diagramObjectId, String typeOfObject, Long organisation_id, Long language_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.checkDiagramsByDiagramObjectId(SID, objectName, diagramObjectId, 
					typeOfObject, organisation_id, language_id);
			
			
		} catch (Exception err) {
			log.error ("[checkDiagramsByDiagramObjectId]",err);
		}
		return null;
	}	

	public List<DiagramObject> getIssuesByOrganizationAssignedToMe(String SID, Long organization_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getIssuesByOrganizationAssignedToMe(SID, organization_id);
			
			
		} catch (Exception err) {
			log.error ("[getIssuesByOrganizationAssignedToMe]",err);
		}
		return null;
	}	

	public Long approveSingleDiagramObject(String SID, Long diagramObjectId, boolean approve, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.approveSingleDiagramObject(SID, diagramObjectId, approve);
			
			
		} catch (Exception err) {
			log.error ("[approveSingleDiagramObject]",err);
		}
		return null;
	}	

	public ExportImportJob getItemToImportMap(String SID, Long exportJobId, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getItemToImportMap(SID, exportJobId);
			
			
		} catch (Exception err) {
			log.error ("[getItemToImportMap]",err);
		}
		return null;
	}	

	public DiagramObject getDiagramObjectById(String SID, Long diagramObjectId, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectById(SID, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectById]",err);
		}
		return null;
	}		

	public Long addDiagramObject(String SID, String name, String objectTypeName, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.addDiagramObject(SID, name, objectTypeName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[addDiagramObject]",err);
		}
		return null;
	}	

	public Long addPendingDiagramObject(String SID, String name, String objectTypeName, Long organisation_id, 
			Long default_lang_id, Long assigneeUserId, Boolean isPending, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.addPendingDiagramObject(SID, name, objectTypeName, 
					organisation_id, default_lang_id, assigneeUserId, isPending);
			
			
		} catch (Exception err) {
			log.error ("[addPendingDiagramObject]",err);
		}
		return null;
	}	

	public List<DiagramObjectOrganisation> getOrganizationsByObjects(String SID, Long diagramObjectId, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getOrganizationsByObjects(SID, diagramObjectId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getOrganizationsByObjects]",err);
		}
		return null;
	}	

	public List<DiagramObjectOrganisation> getObjectsByOrganizations(String SID, Long diagramObjectId, Long organisation_id, 
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getObjectsByOrganizations(SID, diagramObjectId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getObjectsByOrganizations]",err);
		}
		return null;
	}	

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(String SID, String objectTypeName, Long organisation_id,
			HttpServletRequest request){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramInstanceObjectsByObjectType(SID, objectTypeName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramInstanceObjectsByObjectType]",err);
		}
		return null;
	}		

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectId(String SID, Long diagramObjectId, 
			Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramInstanceObjectsByObjectId(SID, diagramObjectId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramInstanceObjectsByObjectId]",err);
		}
		return null;
	}	

	public Long addDataCarrier(String SID, String name,  Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DiagramService diagramService = (DiagramService) context.getBean("diagramservice.service");
		
			return diagramService.addDataCarrier(SID, name, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[addDataCarrier]",err);
		}
		return null;
	}		
		
	
	
	
}
