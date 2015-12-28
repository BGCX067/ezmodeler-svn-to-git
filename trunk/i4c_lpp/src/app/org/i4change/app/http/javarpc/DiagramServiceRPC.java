package org.i4change.app.http.javarpc;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.dto.diagram.DiagramInstanceConnectedDTO;
import org.i4change.app.dto.diagram.DiagramInstanceObjectDTO;
import org.i4change.app.dto.diagram.DiagramObjectCompleteDTO;
import org.i4change.app.dto.diagram.DiagramObjectListDTO;
import org.i4change.app.dto.diagram.DiagramObjectSearchDetailedDTO;
import org.i4change.app.dto.diagram.DiagramObjectToolTip;
import org.i4change.app.dto.diagram.DiagramSearchDTO;
import org.i4change.app.dto.diagram.FlowConnectedDiagramDTO;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramDocument;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.beans.diagram.SearchQuery;
import org.i4change.app.remote.IDiagramservice;
import org.springframework.context.ApplicationContext; 
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

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
			boolean forceUpdate, Long diagramObjectId, Long projectId, String shortName){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.updateDiagram(SID, diagramName, revisionComment, diagramMapObj, organisation_id, 
					diagramType, diagramNo, read, write, onlyIssues, users, language_id, 
					currentRevsionId, forceUpdate, diagramObjectId, projectId, shortName);
			
		} catch (Exception err) {
			log.error ("[updateDiagram]",err);
		}
		return null;
	}
	
	public Long updateDiagramWithProperties(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevsionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId, 
			Vector properties, String shortName){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.updateDiagramWithProperties(SID, diagramName, revisionComment, diagramMapObj, organisation_id, 
					diagramType, diagramNo, read, write, onlyIssues, users, language_id, 
					currentRevsionId, forceUpdate, diagramObjectId, projectId, 
					properties, shortName);
			
		} catch (Exception err) {
			log.error ("[updateDiagram]",err);
		}
		return null;
	}
	
	public DiagramInstanceObject getTest1(Long number) {
		try {
			  
//			DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectById(number);
//			XStream xStream = new XStream(new XppDriver());
//			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
//			
//			diagramInstance.setDiagram(null);
//			//diagramInstance.setDiagramObject(null);
//			for (Iterator<DataCarrierDiagramObject> iter = diagramInstance.getDiagramObject().getDataCarrierDiagramObject().iterator();iter.hasNext();) {
//				DataCarrierDiagramObject diaObject = iter.next();
//				//diaObject.setDiagramObject(null);
//			}
//			
//			//diagramInstance.getDiagramObject().getDataCarrierDiagramObject()
//			
//			//diagramInstance.setGraphAsObject((Vector) xStream.fromXML(diagramInstance.getGraphObject()));
//			
//			diagramInstance.setGraphObject("");
//			
//			log.debug("::"+xStream.toXML(diagramInstance));
//			
//			log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
//			
//			return diagramInstance;
		} catch (Exception err) {
			log.error("[getTest]",err);
		}
        return null;
	}
	
	public DiagramInstanceObject getTest2(Long number) {
		try {
//			
//			DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectById(number);
//			
//			log.debug(diagramInstance);
//			
//			//diagramInstance.setDiagramObject(null);
//			
//			diagramInstance.setGraphObject("");
//			log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
//			
//			return diagramInstance;
			
		} catch (Exception err) {
			log.error("[getTest]",err);
		}
        return null;
	}

	public List<DiagramSearchDTO> getDiagramDrillDownByDiagramObjectIdAndType(String SID, Long organisation_id, 
			Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramDrillDownByDiagramObjectIdAndType(SID, organisation_id, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramDrillDownByDiagramObjectIdAndType]",err);
		}
		return null;
	}
	
	public List<DiagramSearchDTO> getDiagramSurfAcrossByDiagramObjectIdAndType(String SID, Long diagramObjectId, String flowType) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramSurfAcrossByDiagramObjectIdAndType(SID, diagramObjectId, flowType);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramSurfAcrossByDiagramObjectIdAndType]",err);
		}
		return null;
	}

	public Long saveNewDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, 
			Long projectId, String shortName){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.saveNewDiagram(SID, diagramName, revisionComment, diagramMapObj, 
					organisation_id, diagramType, read, write, onlyIssues, users, 
					language_id, diagramObjectId, projectId, shortName);
			
			
		} catch (Exception err) {
			log.error ("[saveNewDiagram]",err);
		}
		return null;
	} 
	 
	public Long saveNewDiagramWithProperties(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId, 
			Vector propertyList, String shortName) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			//log.debug("saveNewDiagramWithProperties shortName "+shortName);
			
			return diagramService.saveNewDiagramWithProperties(SID, diagramName,
					revisionComment, diagramMapObj, organisation_id, diagramType, 
					read, write, onlyIssues, users, language_id, diagramObjectId, 
					projectId, propertyList, shortName);
			
			
		} catch (Exception err) {
			log.error ("[saveNewDiagram]",err);
		}
		return null;
	}

	public SearchResult getDiagramHistoryByNo(String SID, Long organisation_id, 
			Long diagramNo, Long language_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramHistoryByNo(SID, organisation_id, diagramNo, language_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramHistoryByNo]",err);
		}
		return null;
	}

	public DiagramSearchDTO getDiagramById(String SID, Long diagramId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramById(SID, diagramId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramById]",err);
		}
		return null;
	}	

	/**
	 * @deprecated
	 * @param SID
	 * @param start
	 * @param max
	 * @param orderBy
	 * @param asc
	 * @param organization_id
	 * @param search
	 * @return
	 */
	public SearchResult getDiagramSearch(String SID, int start, int max, String orderBy, boolean asc, 
			Long organization_id, String search){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramSearch(SID, start, max, orderBy, asc, organization_id, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramSearch]",err);
		}
		return null;
	}
	
	public SearchResult getDiagramSearchByProject(String SID, int start, int max, String orderBy, boolean asc, 
			Long organization_id, String search, Long projectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramSearchByProject(SID, start, max, orderBy, asc, organization_id, search, projectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramSearchByProject]",err);
		}
		return null;
	}


	public SearchResult getDiagramObjects(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			log.debug("getDiagramObjects "+objectTypeNames);
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
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
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
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
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsIncludingAssingees(SID, organization_id, start, 
					max, objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsNoMatterPending]",err);
		}
		return null;
	}

	public SearchResult getParentDiagramObjects(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			log.debug("objectTypeNames: "+objectTypeNames);
			if (objectTypeNames != null) {
				log.debug("objectTypeNames: SIZE "+objectTypeNames.size());
			}
			
			return diagramService.getParentDiagramObjects(SID, organization_id, start, max, 
					objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getParentDiagramObjects]",err);
		}
		return null;
	}		

	public List<DiagramInstanceObject> getDiagramObjectsById(String SID, Long diagram_id, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsById(SID, diagram_id, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsById]",err);
		}
		return null;
	}		

	public List<DiagramInstanceObjectDTO> getLatestDiagramObjectsByNo(String SID, Long diagramNo, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getLatestDiagramObjectsByNo(SID, diagramNo, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getLatestDiagramObjectsByNo]",err);
		}
		return null;
	}
	
	public List<DiagramInstanceObjectDTO> getDiagramObjectsByDiagramId(String SID, Long diagramId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsByDiagramId(SID, diagramId);
			
			
		} catch (Exception err) {
			log.error ("[getLatestDiagramObjectsByNo]",err);
		}
		return null;
	}
	
	public List<DiagramInstanceObjectDTO> getLatestDiagramObjectsById(String SID, Long diagramId, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getLatestDiagramObjectsById(SID, diagramId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getLatestDiagramObjectsById]",err);
		}
		return null;
	}	

	public Long updateDiagramNameById(String SID, Long diagramId, String newName, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.updateDiagramNameById(SID, diagramId, newName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[updateDiagramNameById]",err);
		}
		return null;
	}		

	public int deleteDiagramByNo(String SID, Long diagramNo, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.deleteDiagramByNo(SID, diagramNo, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[deleteDiagramByNo]",err);
		}
		return -1;
	}

	public Long checkForUniqueName(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.checkForUniqueName(SID, objectName, diagramObjectId, typeOfObject, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[checkForUniqueName]",err);
		}
		return null;
	}	

	public List<Diagram> checkDiagramsByDiagramObjectId(String SID, String objectName, 
			Long diagramObjectId, String typeOfObject, Long organisation_id, Long language_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.checkDiagramsByDiagramObjectId(SID, objectName, diagramObjectId, 
					typeOfObject, organisation_id, language_id);
			
			
		} catch (Exception err) {
			log.error ("[checkDiagramsByDiagramObjectId]",err);
		}
		return null;
	}	

	public SearchResult getIssuesByOrganizationAssignedToMe(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search, Long ideasType){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getIssuesByOrganization(SID, start, max, orderBy, asc, 
					organization_id, search, ideasType);
			
			
		} catch (Exception err) {
			log.error ("[getIssuesByOrganizationAssignedToMe]",err);
		}
		return null;
	}	

	public Long approveSingleDiagramObject(String SID, Long diagramObjectId, boolean approve){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.approveSingleDiagramObject(SID, diagramObjectId, approve);
			
			
		} catch (Exception err) {
			log.error ("[approveSingleDiagramObject]",err);
		}
		return null;
	}	

	public ExportImportJob getItemToImportMap(String SID, Long exportJobId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getItemToImportMap(SID, exportJobId);
			
			
		} catch (Exception err) {
			log.error ("[getItemToImportMap]",err);
		}
		return null;
	}	

	/**
	 * @deprecated
	 * @param SID
	 * @param diagramObjectId
	 * @return
	 */
	public DiagramObject getDiagramObjectById(String SID, Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectById(SID, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectById]",err);
		}
		return null;
	}	
	
	public DiagramObjectSearchDetailedDTO getDiagramObjectSearchDetailsById(String SID, Long diagramObjectId){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectSearchDetailsById(SID, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectSearchDetailsById]",err);
		}
		return null;
	}	
	

	public Long addDiagramObject(String SID, String name, String objectTypeName, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.addDiagramObject(SID, name, objectTypeName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[addDiagramObject]",err);
		}
		return null;
	}	

	public Long addPendingDiagramObject(String SID, String name, String objectTypeName, Long organisation_id, 
			Long default_lang_id, Long assigneeUserId, Boolean isPending){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.addPendingDiagramObject(SID, name, objectTypeName, 
					organisation_id, default_lang_id, assigneeUserId, isPending);
			
			
		} catch (Exception err) {
			log.error ("[addPendingDiagramObject]",err);
		}
		return null;
	}	

	public List<DiagramObjectOrganisation> getOrganizationsByObjects(String SID, Long diagramObjectId, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getOrganizationsByObjects(SID, diagramObjectId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getOrganizationsByObjects]",err);
		}
		return null;
	}	

	public List<DiagramObjectOrganisation> getObjectsByOrganizations(String SID, Long diagramObjectId, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getObjectsByOrganizations(SID, diagramObjectId, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getObjectsByOrganizations]",err);
		}
		return null;
	}	

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(String SID, String objectTypeName, Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramInstanceObjectsByObjectType(SID, objectTypeName, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramInstanceObjectsByObjectType]",err);
		}
		return null;
	}		

	public List<DiagramInstanceConnectedDTO> getDiagramInstanceObjectsByObjectId(String SID, Long diagramObjectId, 
			Long organisation_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
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
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.addDataCarrier(SID, name, organisation_id);
			
			
		} catch (Exception err) {
			log.error ("[addDataCarrier]",err);
		}
		return null;
	}		
		
	public SearchResult getDiagramObjectsFastPath(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsFastPath(SID, organization_id, 
					start, max, objectTypeNames, orderBy, asc, search);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectsFastPath]",err);
		}
		return null;
	}
	
	/**
	 * Just get the Diagram Object, for the ToolTip in the Library
	 * 
	 * @param SID
	 * @param diagramObjectId
	 * @return
	 */
	public DiagramObjectToolTip getDiagramObjectPureById(String SID, Long diagramObjectId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectPureById(SID, diagramObjectId);
			
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectPureById]",err);
		}
		return null;
	}
	
	/**
	 * Updates the Index Table for the Diagrams
	 * 
	 * @param SID
	 * @return
	 */
	public Long updateDiagramSummaryIndex(String SID){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			diagramService.updateDiagramSummaryIndex(SID);
			
			return new Long(1);
		} catch (Exception err) {
			log.error ("[updateDiagramSummaryIndex]",err);
		}
		return new Long(-1);
	}
	
	public DiagramObjectCompleteDTO getDiagramObjectCompleteById(String SID, Long diagramObjectId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectCompleteById(SID, diagramObjectId);
			
		} catch (Exception err) {
			log.error ("[getDiagramObjectCompleteById]",err);
		}
		return null;
	}
	
	public Long addNonVisualObject(String SID, String name, Long organisation_id, 
			String type, String comment, Vector properties) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.addNonVisualObject(SID, name, organisation_id, 
					type, comment, properties);
			
		} catch (Exception err) {
			log.error ("[addNonVisualObject]",err);
		}
		return null;
	}
	
	public Long updateNonVisualObject(String SID, Long diagramObjectId, String name, Long organisation_id, 
			String type, String comment, Vector properties) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.updateNonVisualObject(SID, diagramObjectId, name, 
					organisation_id, type, comment, properties);
			
		} catch (Exception err) {
			log.error ("[upateNonVisualObject]",err);
		}
		return null;
	}
	
	public SearchResult getDiagramObjectsFullSearch(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, Vector properties, Integer itemStatus){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsFullSearch(SID, organization_id, 
								start, max, objectTypeNames, orderBy, asc, search, properties, 
								itemStatus);
			
		} catch (Exception err) {
			log.error ("[upateNonVisualObject]",err);
		}
		return null;
	}
	
	public List<DiagramObjectListDTO> getDiagramObjectsLinked(String SID, Long diagramObjectId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramObjectsLinked(SID, diagramObjectId);
			
		} catch (Exception err) {
			log.error ("[upateNonVisualObject]",err);
		}
		return null;
	}
	
	public List<FlowConnectedDiagramDTO> getFlowConnectedDiagramList(String SID, 
			Vector inputFlowIds, Vector outputFlowIds) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getFlowConnectedDiagramList(SID, inputFlowIds, outputFlowIds);
			
		} catch (Exception err) {
			log.error ("[getFlowConnectedDiagramList]",err);
		}
		return null;
	}
	
	public Long saveSearchFilter(String SID, Long organization_id,  int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, 
			Vector properties, Integer itemStatus, String sql, String name, String description) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.saveSearchFilter(SID, organization_id, start, max, objectTypeNames, 
					orderBy, asc, search, properties, itemStatus, sql, name, description);
			
		} catch (Exception err) {
			log.error ("[saveSearchFilter]",err);
		}
		return null;
	}
	
	public Long updateSearchFilter(String SID, Long searchQueryId, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, 
			Vector properties, Integer itemStatus, String sql, String name, String description) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.updateSearchFilter(searchQueryId, SID, start, max, objectTypeNames, 
					orderBy, asc, search, properties, itemStatus, sql, name, description);
			
		} catch (Exception err) {
			log.error ("[updateSearchFilter]",err);
		}
		return null;
	}
	
	public Long deleteSearchFilter(String SID, Long searchQueryId) {
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.deleteSearchFilter(SID, searchQueryId);
			
		} catch (Exception err) {
			log.error ("[deleteSearchFilter]",err);
		}
		return null;
	}
	
	public List<SearchQuery> getMySearchFilter(String SID, Long organization_id){
		try {
			
			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getMySearchFilter(SID, organization_id);
			
		} catch (Exception err) {
			log.error ("[getMySearchFilter]",err);
		}
		return null;
	}
	
	
	public List<SearchQuery> getSearchFilter(String SID, Long organization_id){
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getSearchFilter(SID, organization_id);
			
		} catch (Exception err) {
			log.error ("[getSearchFilter]",err);
		}
		return null;
	}
	
	public SearchQuery getSearchFilterById(String SID, Long searchQueryId) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getSearchFilterById(SID, searchQueryId);
			
		} catch (Exception err) {
			log.error ("[getSearchFilterById]",err);
		}
		return null;
	}
	
	public List<DiagramDocument> getDiagramDocumentByObject(String SID, Long diagramObjectId) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.getDiagramDocumentByObject(SID, diagramObjectId);
			
		} catch (Exception err) {
			log.error ("[getDiagramDocumentByObject]",err);
		}
		return null;
	}
	
	public Long saveDiagramDocumentByObject(String SID, Long diagramObjectId, String fileName, String fileIconName,
			String fileHash, Boolean isInternal, String url) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.saveDiagramDocumentByObject(SID, diagramObjectId, fileName, 
								fileIconName, fileHash, isInternal, url);
			
		} catch (Exception err) {
			log.error ("[getDiagramDocumentByObject]",err);
		}
		return null;
	}
	
	public Long deleteDiagramDocumentByObject(String SID, Long diagramDocumentId) {
		try {

			ServletContext servletContext = this.servletRequest.getSession().getServletContext();
			
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDiagramservice diagramService = (IDiagramservice) context.getBean("diagramservice.service");
		
			return diagramService.deleteDiagramDocumentByObject(SID, diagramDocumentId);
			
		} catch (Exception err) {
			log.error ("[deleteDiagramDocumentByObject]",err);
		}
		return null;
	}
	
	
}