package org.i4change.app.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DataCarrierDiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDocumentDaoImpl;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectOrganisationDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.data.diagram.DiagramSummaryDaoImpl;
import org.i4change.app.data.diagram.IssueAssigneeDaoImpl;
import org.i4change.app.data.diagram.RoleDaoImpl;
import org.i4change.app.data.diagram.SearchQueryDaoImpl;
import org.i4change.app.data.project.daos.ProjectDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.dto.diagram.DataCarrierDiagramObjectDTO;
import org.i4change.app.dto.diagram.DiagramInstanceConnectedDTO;
import org.i4change.app.dto.diagram.DiagramInstanceObjectDTO;
import org.i4change.app.dto.diagram.DiagramMinimalDTO;
import org.i4change.app.dto.diagram.DiagramObjectCompleteDTO;
import org.i4change.app.dto.diagram.DiagramObjectDataCarrierDTO;
import org.i4change.app.dto.diagram.DiagramObjectListDTO;
import org.i4change.app.dto.diagram.DiagramObjectSearchDTO;
import org.i4change.app.dto.diagram.DiagramObjectSearchDetailedDTO;
import org.i4change.app.dto.diagram.DiagramObjectToolTip;
import org.i4change.app.dto.diagram.DiagramSearchDTO;
import org.i4change.app.dto.diagram.DiagramTypeDTO;
import org.i4change.app.dto.diagram.DiagramrevisionDTO;
import org.i4change.app.dto.diagram.FlowConnectedDiagramDTO;
import org.i4change.app.dto.diagram.IssuesExplorerDTO;
import org.i4change.app.dto.diagram.RoleMinimalDTO;
import org.i4change.app.dto.project.ProjectMinimalDTO;
import org.i4change.app.dto.user.UserMinimalDTO;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramDocument;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.DiagramSummary;
import org.i4change.app.hibernate.beans.diagram.DiagramType;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.i4change.app.hibernate.beans.diagram.Role;
import org.i4change.app.hibernate.beans.diagram.SearchQuery;
import org.i4change.app.hibernate.beans.project.Project;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.http.beans.SearchFilterParam;
import org.i4change.app.session.beans.RoomClient;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author sebastianwagner
 *
 */
public class DiagramService implements IDiagramservice {
	
	private static final Log log = LogFactory.getLog(DiagramService.class);
	
	//Spring loaded Beans
	private IApplication application;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;
	private RoleDaoImpl roleDaoImpl;
	private DiagramSummaryDaoImpl diagramSummaryDaoImpl;
	private ProjectDaoImpl projectDaoImpl;
	private IssueAssigneeDaoImpl issueAssigneeDaoImpl;
	private DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl;
	private DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl;
	private SearchQueryDaoImpl searchQueryDaoImpl;
	private Fieldmanagment fieldmanagment;
	private Mailmanagement mailmanagement;
	private DiagramObjectOrganisationDaoImpl diagramObjectOrganisationDaoImpl;
	private Configurationmanagement configurationmanagement;
	private DiagramDocumentDaoImpl diagramDocumentDaoImpl;
	
//	public static synchronized DiagramService getInstanc_e() {
//		if (instance == null) {
//			instance = new DiagramService();
//		}
//		return instance;
//	}
	
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
	public RoleDaoImpl getRoleDaoImpl() {
		return roleDaoImpl;
	}
	public void setRoleDaoImpl(RoleDaoImpl roleDaoImpl) {
		this.roleDaoImpl = roleDaoImpl;
	}
	public DiagramSummaryDaoImpl getDiagramSummaryDaoImpl() {
		return diagramSummaryDaoImpl;
	}
	public void setDiagramSummaryDaoImpl(DiagramSummaryDaoImpl diagramSummaryDaoImpl) {
		this.diagramSummaryDaoImpl = diagramSummaryDaoImpl;
	}
	public ProjectDaoImpl getProjectDaoImpl() {
		return projectDaoImpl;
	}
	public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
		this.projectDaoImpl = projectDaoImpl;
	}
	public IssueAssigneeDaoImpl getIssueAssigneeDaoImpl() {
		return issueAssigneeDaoImpl;
	}
	public void setIssueAssigneeDaoImpl(IssueAssigneeDaoImpl issueAssigneeDaoImpl) {
		this.issueAssigneeDaoImpl = issueAssigneeDaoImpl;
	}
	public DiagramInstanceObjectDaoImpl getDiagramInstanceObjectDaoImpl() {
		return diagramInstanceObjectDaoImpl;
	}
	public void setDiagramInstanceObjectDaoImpl(
			DiagramInstanceObjectDaoImpl diagramInstanceObjectDaoImpl) {
		this.diagramInstanceObjectDaoImpl = diagramInstanceObjectDaoImpl;
	}
	
	public DataCarrierDiagramObjectDaoImpl getDataCarrierDiagramObjectDaoImpl() {
		return dataCarrierDiagramObjectDaoImpl;
	}
	public void setDataCarrierDiagramObjectDaoImpl(
			DataCarrierDiagramObjectDaoImpl dataCarrierDiagramObjectDaoImpl) {
		this.dataCarrierDiagramObjectDaoImpl = dataCarrierDiagramObjectDaoImpl;
	}
	
	public SearchQueryDaoImpl getSearchQueryDaoImpl() {
		return searchQueryDaoImpl;
	}
	public void setSearchQueryDaoImpl(SearchQueryDaoImpl searchQueryDaoImpl) {
		this.searchQueryDaoImpl = searchQueryDaoImpl;
	}
	
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public Mailmanagement getMailmanagement() {
		return mailmanagement;
	}
	public void setMailmanagement(Mailmanagement mailmanagement) {
		this.mailmanagement = mailmanagement;
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
	
	public DiagramObjectOrganisationDaoImpl getDiagramObjectOrganisationDaoImpl() {
		return diagramObjectOrganisationDaoImpl;
	}
	public void setDiagramObjectOrganisationDaoImpl(
			DiagramObjectOrganisationDaoImpl diagramObjectOrganisationDaoImpl) {
		this.diagramObjectOrganisationDaoImpl = diagramObjectOrganisationDaoImpl;
	}
	
	public Configurationmanagement getConfigurationmanagement() {
		return configurationmanagement;
	}
	public void setConfigurationmanagement(
			Configurationmanagement configurationmanagement) {
		this.configurationmanagement = configurationmanagement;
	}
	
	public DiagramDocumentDaoImpl getDiagramDocumentDaoImpl() {
		return diagramDocumentDaoImpl;
	}
	public void setDiagramDocumentDaoImpl(
			DiagramDocumentDaoImpl diagramDocumentDaoImpl) {
		this.diagramDocumentDaoImpl = diagramDocumentDaoImpl;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateDiagramWithProperties(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Long, java.lang.Long, java.lang.Long, boolean, boolean, boolean, java.lang.Object, java.lang.Long, long, boolean, java.lang.Long, java.lang.Long, java.util.Vector, java.lang.String)
	 */
	public Long updateDiagramWithProperties(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevesionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId, Vector propertyList,
			String shortName){
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        
				Long diagramId = this.updateDiagramImpl(SID, diagramName, revisionComment, 
						diagramMapObj, organisation_id, diagramType, diagramNo, 
						read, write, onlyIssues, users, language_id, currentRevesionId, 
						forceUpdate, diagramObjectId, projectId, users_id, shortName);
				
				if (diagramId <= 0) {
					return diagramId;
				}
				
				//Update Diagram Properties
				if (propertyList != null) {
					log.debug("propertyList: "+propertyList.size());
					
					for (Iterator iter = propertyList.iterator();iter.hasNext();) {
						Map property = (Map) iter.next();
						Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
						String propertyVal = property.get("propertyVal").toString();
						log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
						this.diagramObjectPropertyDaoImpl.addOrUpdateDiagramProperty(diagramNo, propertyId, propertyVal, users_id);
					}
				}
				
				
				return diagramId;
			
	        }
			
		} catch (Exception err) {
			log.error("[updateDiagramWithProperties]",err);
		}
        return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateDiagram(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Long, java.lang.Long, java.lang.Long, boolean, boolean, boolean, java.lang.Object, java.lang.Long, long, boolean, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public Long updateDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevesionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId, String shortName){
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        
				return this.updateDiagramImpl(SID, diagramName, revisionComment, 
						diagramMapObj, organisation_id, diagramType, diagramNo, 
						read, write, onlyIssues, users, language_id, currentRevesionId, 
						forceUpdate, diagramObjectId, projectId, users_id, shortName);
	        } else {
	        	return new Long(-2);
	        }
	        
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return new Long(-1);
	}
	
	@SuppressWarnings("unchecked")
	private Long updateDiagramImpl(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevesionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId, Long users_id, 
			String shortName){
		try {
			
			//A reference for the current Objects to lookup the DiagramObjectInstanceId and fix the 
			//Reference to the underlying Org-Object
			Map<String,Long> currentObjectsMap = new HashMap<String,Long>();
			
	        	
	        	log.debug("updateDiagram diagramMapObj ClassName: "+diagramMapObj.getClass().getName());
	        	
	        	Vector myObjectsList = (Vector) diagramMapObj;
	        	
	        	Map diagramMap = new HashMap();
	        	
	        	int i=0;
	        	for (Iterator iter = myObjectsList.iterator();iter.hasNext();) {
	        		diagramMap.put(i,iter.next());
	        		i++;
	        	}
	        	
	        	XStream xStream = new XStream(new XppDriver());
    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
	        	
	        	log.debug("diagramName: "+diagramName);
	        	log.debug("revisionComment: "+revisionComment);
	        	log.debug("diagramNo: "+diagramNo);
	        	
	        	//Diagram diagram = this.diagramDaoImpl.getDiagramByNo(diagramNo, organisation_id);
	        	Diagram diagram = this.diagramSummaryDaoImpl.getDiagramByNo(diagramNo, organisation_id);
	        	
	        	if (projectId != null && projectId != 0) {
	        		ProjectRelation projectRelation = this.projectRelationDaoImpl.getProjectRelationByDiagramNo(diagram.getDiagramNo());
	        		//ProjectRelation projectRelation = this.projectRelationDaoImpl.getProjectRelationByIdAndDiagramNo(projectId, diagramNo);
	        		if (projectRelation == null) {
	        			this.projectRelationDaoImpl.addProjectRelation(projectId, diagramNo, null);
	        		} else {
	        			projectRelation.setProject(this.projectDaoImpl.getProjectById(projectId));
	        			this.projectRelationDaoImpl.updateProjectRelation(projectRelation);
	        		}
	        	} else {
	        		this.projectRelationDaoImpl.deleteProjectRelation(diagram.getDiagramNo());
	        	}
	        	 
	        	//List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	List<DiagramSummary> diagramList = this.diagramSummaryDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	
	        	if (diagramList.size() == 1) {
	        		if (!diagramList.get(0).getDiagram().getDiagramNo().equals(diagramNo)){
	        			//another diagramNo means duplicates
     	        		//return an Error ID, so that the user gets noticed
     	        		return new Long(-39);
	        		}
	        	} else if (diagramList.size() > 1) {
	        		//more then one means it has duplicates
	        		//return an Error ID, so that the user gets noticed
 	        		return new Long(-39);
	        	}
	        	
	        	//check if you overwrite another Diagram with newer Revision first
	        	//if forceUpdate = true, this is a force update Operation
	        	 
	        	log.debug("currentRevsionId "+currentRevesionId);
	        	log.debug("to be saved revision "+ diagram.getDiagramrevision().getDiagramrevisionId() );
	        	
	        	if (diagram.getDiagramrevision().getDiagramrevisionId() == currentRevesionId || forceUpdate) {
	        	
		        	diagram = this.diagramDaoImpl.updateDiagramByNo(diagramName, users_id, diagramNo, 
		        			revisionComment, organisation_id, diagramType, read, write, onlyIssues, users, 
		        			diagramObjectId, shortName);
		        	
		        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        		Object key = iter.next();
		        		Vector whiteBoardItem = (Vector) diagramMap.get(key);
		        		log.debug("whiteBoardItem: "+whiteBoardItem);
		        		String typeOfObject = whiteBoardItem.get(0).toString();
		        		log.debug("typeOfObject: "+typeOfObject);
		        		
		        		if (!typeOfObject.equals("connector")){
		        			
		        			String objName = whiteBoardItem.get(whiteBoardItem.size()-8).toString();
		        			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
		        			Long diagramobjectId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
		        			if (diagramobjectId == 0) diagramobjectId = null;
		        			log.debug("objName: "+objName);
		        			log.debug("diagramobjectId: "+diagramobjectId);
		        			log.debug("objInternalUID: 1: "+objInternalUID);
		        			
		        			Vector dataCarrierList = this.getDataCarrierObject(whiteBoardItem, typeOfObject, diagramType);
		        			
		        			//Reset Data Carrier to NULL
		        			whiteBoardItem = this.resetDataCarrierIndex(whiteBoardItem, typeOfObject, diagramType);
		        			
			    			String xmlString = xStream.toXML(whiteBoardItem);	
			    			
			    			String flowType = typeOfObject;
			    			if (typeOfObject.equals("inputflow") || typeOfObject.equals("outputflow")) {
			    				typeOfObject = "flow";
			    			}
			    			
			    			Vector propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
			    			
			    			//FIXME: This is a Issue, the pending STatus IS NOT always size()-9
			    			Boolean isPending = this.getPendingStatus(whiteBoardItem, typeOfObject, diagramType);
			    				// Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
			    			Long assigneeUserId = null;
			    			//If the Item is of Status Pending then we need to link to get the Assignee Name/ID
		    				if (isPending){
		    					assigneeUserId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-10).toString()).longValue();
		    				}
		    				
			    			//log.debug("xmlString: "+xmlString);
			    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
										    					typeOfObject, users_id, organisation_id, 
										    					diagram, objName, null, null, xmlString, objInternalUID, isPending, 
										    					assigneeUserId, diagramName, language_id, flowType, dataCarrierList,
										    					propertyMap);
			    			
			    			//In case there was an error or wrong reference show that to the User
			    			if (diagramInstanceObjectId <= 0) {
			    				log.error("### NEGATIVE diagramInstanceObjectId "+diagramInstanceObjectId);
			    				return diagramInstanceObjectId;
			    			}
			    			
			    			//set the diagramInstanceObjectId, to read it later on to set the reference to the underlying Org-Object
			    			currentObjectsMap.put(objInternalUID, diagramInstanceObjectId);
			    			
			    			//If its a new Item, reflect the DiagramObjectId to the XML-Graph
			    			if (diagramobjectId == null) {
			    				
			    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
			    					getDiagramInstanceObjectById(diagramInstanceObjectId);
			    				diagramobjectId = diagramInstance.getDiagramObject().getDiagramObjectId();
			    				whiteBoardItem.set(whiteBoardItem.size()-7,diagramobjectId);
			    				String xmlString_2 = xStream.toXML(whiteBoardItem);
			    				diagramInstance.setGraphObject(xmlString_2);
			    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
			    				
			    				//Only do this for new Issue's
			    				if (typeOfObject.equals("issueflow")) {
				    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
				    				this.issueAssigneeDaoImpl.addIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);
				    			}
			    			} else {
			    				//Update Assignee
			    				if (typeOfObject.equals("issueflow")) {
				    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
				    				this.issueAssigneeDaoImpl.saveOrUpdateIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);
				    			}
			    			}
			    				    			
			    			
			    			log.debug("Set Incomplete: "+whiteBoardItem);
			    			log.debug("SET diagramInstanceObjectId: "+diagramInstanceObjectId);
			    			whiteBoardItem.set(whiteBoardItem.size()-6, diagramInstanceObjectId);
			    			
			    			log.debug("Set Complete: "+whiteBoardItem);
			    			diagramMap.put(key, whiteBoardItem);
		        		}
		        	}
		        	
		        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        		Vector whiteBoardItem = (Vector) diagramMap.get(iter.next());
		        		log.debug("whiteBoardItem: "+whiteBoardItem);
		        		String typeOfObject = whiteBoardItem.get(0).toString();
		        		log.debug("typeOfObject: "+typeOfObject);
		        		
		        		if (typeOfObject.equals("connector")){
		        			
		        			String objName = whiteBoardItem.get(whiteBoardItem.size()-8).toString();
		        			
		        			String startObjName = whiteBoardItem.get(1).toString();
		        			DiagramInstanceObject dStartInstance = this.getWhiteBoardItem(startObjName, diagramMap);
		        			Long diagramobjectStart_id = dStartInstance.getDiagramObject().getDiagramObjectId();
		        			
		        			String endObjName = whiteBoardItem.get(3).toString();
		        			DiagramInstanceObject dEndInstance = this.getWhiteBoardItem(endObjName, diagramMap);
		        			Long diagramobjectEnd_id = dEndInstance.getDiagramObject().getDiagramObjectId();
		        			
		        			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
		        			Long diagramobjectId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
		        			if (diagramobjectId == 0) diagramobjectId = null;
		        			
		        			Vector propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
		        			
		        			//Reset Data Carrier to NULL
		        			whiteBoardItem = this.resetDataCarrierIndex(whiteBoardItem, typeOfObject, diagramType);
		        			
			    			String xmlString = xStream.toXML(whiteBoardItem);	
			    			
			    			//log.debug("xmlString: "+xmlString);
			    			//TODO: Check if the connection between these object does already exist
			    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
										    					typeOfObject, users_id, organisation_id, diagram, objName, 
										    					diagramobjectStart_id, diagramobjectEnd_id, xmlString, objInternalUID, 
										    					false, null, diagramName, language_id, "", null, propertyMap);
			    			
			    			//In case there was an error or wrong reference show that to the User
			    			if (diagramInstanceObjectId <= 0) {
			    				log.error("### NEGATIVE diagramInstanceObjectId "+diagramInstanceObjectId);
			    				return diagramInstanceObjectId;
			    			}
			    			
			    			if (diagramobjectId == null) {
			    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
			    					getDiagramInstanceObjectById(diagramInstanceObjectId);
			    				whiteBoardItem.set(whiteBoardItem.size()-7,diagramInstance.getDiagramObject().getDiagramObjectId());
			    				String xmlString_2 = xStream.toXML(whiteBoardItem);
			    				diagramInstance.setGraphObject(xmlString_2);
			    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
			    			}
		        			
		        		}
		        	}
		        	
		        	//#########################################
		        	//check for Underlying Roles for the Object
		        	log.debug("check for Underlying Roles for the Object: "+diagramType);
		        	if (diagramType == 2 || diagramType == 4) {
		        		
		        		log.debug("Underlying diagramMap SIZE: "+diagramMap.size());
		        		
		        		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        			
		        			Vector whiteBoardItem = (Vector) diagramMap.get(iter.next());
			        		log.debug("Underlying whiteBoardItem: "+whiteBoardItem);
			        		String typeOfObject = whiteBoardItem.get(0).toString();
			        		log.debug("Underlying typeOfObject: "+typeOfObject);
			        		
			        		String objInternalMainUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
			        		
		        			if (typeOfObject.equals("processtree")
		        					|| typeOfObject.equals("activity")
		        					|| typeOfObject.equals("inputflow")
		        					|| typeOfObject.equals("outputflow")){
		        				
		        				log.debug("whiteBoardItem.size()-9: "+(whiteBoardItem.size()-9));
		        				
		        				Vector connectList = (Vector) whiteBoardItem.get(whiteBoardItem.size()-9);
		        				
		        				log.debug("Underlying connectList: "+connectList);
		        				
		        				if (connectList != null) {
		        					
		        					//If we find any Item with a 0 as connected Org-Object we have to fix that
		        					boolean foundOrgWithEmptyReference = false;
		        					
		        					int keyVector = 0;
		        					for (Iterator connectListiter = connectList.iterator();connectListiter.hasNext();) {
		        						Object key = connectListiter.next();
		        						log.debug("key: "+key);
		        						Vector connectItem = (Vector) key;
		        						
		        						String objInternalUID = connectItem.get(0).toString();
		        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
		        						
		        						log.debug("Underlying diagramobjectId: "+diagramobjectId);
		    	        				
		        						if (diagramobjectId == null || diagramobjectId == 0) {
		        							foundOrgWithEmptyReference = true;
		        							diagramobjectId = this.getDiagramObjectIdByCurrentMap(diagramMap, objInternalUID);
		        							connectItem.set(1, diagramobjectId);
		        						}
		        						
		        						connectList.set(keyVector, connectItem);
		        						keyVector++;
		        						
		        					}
		        					
		        					Long diagramInstanceObjectId = currentObjectsMap.get(objInternalMainUID);
		        					
		        					log.debug("Underlying diagramInstanceObjectId: "+diagramInstanceObjectId);
		        					 
		        					whiteBoardItem.set(whiteBoardItem.size()-9, connectList);
		        					
		        					//Do fill the Items Graph-XMLRef
		        					if (foundOrgWithEmptyReference) {
			        					//Update Reference to point to correct DiagramObject Id
					    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
					    					getDiagramInstanceObjectById(diagramInstanceObjectId);
					    				
					    				String xmlString_2 = xStream.toXML(whiteBoardItem);
					    				diagramInstance.setGraphObject(xmlString_2);
					    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
		        					}
		        					
		        					//save Connection Object
		        					for (Iterator connectListiter = connectList.iterator();connectListiter.hasNext();) {
		        						Object key = connectListiter.next();
		        						log.debug("key: "+key);
		        						Vector connectItem = (Vector) key;
		        						
		        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
		        						
		        						this.diagramObjectOrganisationDaoImpl.
		        									addDiagramObjectOrganisation(diagramInstanceObjectId, diagramobjectId, users_id);
		        						
		        					}

		        				}
		        				
		        			}
		        			
		        		}
		        		
		        	}
		        	
		        	//check for Pending Object which are removed
		        	//this can only happen on an Update Method
		        	
		        	//Get previous Diagram-Id Objects
		        	List<Diagram> listDiagram = this.diagramDaoImpl.
		        									getDiagramListByNo(diagram.getDiagramNo(), organisation_id);
		        	
		        	Diagram previous = listDiagram.get(listDiagram.size()-2);
		        	
		        	log.debug("Previous DiagramId: "+previous.getDiagramId());
		        	log.debug("Current DiagramId: "+diagram.getDiagramId());
		        	
		        	List<DiagramInstanceObject> previousDiagramObjectList = this.diagramInstanceObjectDaoImpl.
								getDiagramInstanceObjectPendingListByDiagram(previous.getDiagramId());
		        	
		        	List<DiagramInstanceObject> currentDiagramObjectList = this.diagramInstanceObjectDaoImpl.
	        					getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
		        	
		        	//check which object are Pending and not anymore available on the new Diagram-Revision
		        	for (DiagramInstanceObject preInstance : previousDiagramObjectList) {
		        		log.debug("Check for Previous Object: "+preInstance.getDiagramObject().getName());
		        		
		        		boolean found = false;
		        		for (DiagramInstanceObject dieInstance : currentDiagramObjectList) {
		        			
		        			if (preInstance.getDiagramObject().getDiagramObjectId() == dieInstance.getDiagramObject().getDiagramObjectId()){
		        				log.debug("Found in new TOO");
		        				found = true;
		        				break;
		        			}
		        			
		        		}
		        		
		        		log.debug("Found Object? "+found);
		        		if (!found) {
		        			//In case of not found the Object has been removed and should be also 
			        		//Marked as Deleted + Mail Send of Reject Action
		        			this.mailmanagement.addMailToSpoolAboutRemovedPendingObject(users_id, 
		        					preInstance.getDiagramObject().getInsertedby().getUser_id(), 
		        					diagramName, language_id, preInstance.getDiagramObject().getName());
		        			
		        			this.diagramObjectDaoImpl.deleteDiagramObject(users_id, 
		        					preInstance.getDiagramObject().getDiagramObjectId());
		        			
		        		}
		        		
		        	}
		        	
		        	//Update the Diagram Summary
		        	this.diagramSummaryDaoImpl.addOrUpdateDiagramSummary(diagram);
		        	
		        	//FIXME: Send Update
//		        	IConnection current = Red5.getConnectionLocal();
//		        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
//		        	
//		        	Map message = new HashMap();
//		        	message.put("type", "updateDiagram");
//		        	message.put("diagram", diagram);
//		        	
//		        	log.debug("send Update to all clients of the same Org: "+currentClient.getOrganizationId());
//		        	this.application.sendMessageWithClientByOrganizationId(message, currentClient.getOrganizationId());
		        	
		        	
		        	
		        	
		        	return diagram.getDiagramId();
		        	
	        	} else {
	        		//return an Error ID, so that the user gets noticed that a newer Revision does exist
	        		return new Long(-36);
	        	}
	        	
	        	
		} catch (Exception err) {
			log.error("[updateDiagramImpl]",err);
		}
        return new Long(-1);
	}
	
	@SuppressWarnings("unchecked")
	private Vector getDataCarrierObject (Vector whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			
			log.debug("getDataCarrierObject whiteBoardItem: "+whiteBoardItem);
			log.debug("getDataCarrierObject typeOfObject: "+typeOfObject);
			log.debug("getDataCarrierObject diagramType: "+diagramType);
			
			//Get Neg-item Id
			
			int negativIndex = -1;
			
			if (typeOfObject.equals("flow")) {
				negativIndex = 10;
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 12;
				} else {
					negativIndex = 9;
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 10;
				} else {
					negativIndex = 9;
				}
			} else if (typeOfObject.equals("activity")) {
				negativIndex = 10;
			} else if (typeOfObject.equals("processgroup")) {
				negativIndex = 9;
			}
			
			
			Vector dataCarrierList = null;
			
			if (negativIndex != -1) {
				Object obj = whiteBoardItem.get(whiteBoardItem.size()-negativIndex);
				if (obj == null || obj.equals(null) || obj.equals("null")) {
					dataCarrierList = null;
				} else {
					log.debug("getDataCarrierObject1 "+negativIndex);
					log.debug("getDataCarrierObject2 dC "+whiteBoardItem.get(whiteBoardItem.size()-negativIndex));
					log.debug("getDataCarrierObject3 "+whiteBoardItem);
					
					Object objList = whiteBoardItem.get(whiteBoardItem.size()-negativIndex);
					
					if (Vector.class.isInstance(objList)) {
						return dataCarrierList = (Vector) objList;
					}
					
				}
			}
			
			return dataCarrierList;
			
		} catch (Exception err) {
			log.error("[getDataCarrierObject]",err);
		}
		
		return null;
		
	}
	
	private Boolean getPendingStatus (Vector whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			
//			log.debug("getPendingStatus whiteBoardItem: "+whiteBoardItem);
//			log.debug("getPendingStatus typeOfObject: "+typeOfObject);
//			log.debug("getPendingStatus diagramType: "+diagramType);
			
			//Get Neg-item Id
			
			int negativIndex = -1;
			
			if (typeOfObject.equals("flow")) {
				return false;
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				return Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
			} else if (typeOfObject.equals("processtree")) {
				return false;
			} else if (typeOfObject.equals("activity")) {
				return false;
			} else if (typeOfObject.equals("processgroup")) {
				return false;
			}
			
		} catch (Exception err) {
			log.error("[resetDataCarrierIndex]",err);
		}
		
		return false;
		
	}
	
	private Vector resetDataCarrierIndex (Vector whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			
			log.debug("resetDataCarrierIndex whiteBoardItem: "+whiteBoardItem);
			log.debug("resetDataCarrierIndex typeOfObject: "+typeOfObject);
			log.debug("resetDataCarrierIndex diagramType: "+diagramType);
			
			//Get Neg-item Id
			
			int negativIndex = -1;
			
			if (typeOfObject.equals("flow")) {
				negativIndex = 10;
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 12;
				} else {
					negativIndex = 9;
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 10;
				} else {
					negativIndex = 9;
				}
			} else if (typeOfObject.equals("activity")) {
				negativIndex = 10;
			} else if (typeOfObject.equals("processgroup")) {
				negativIndex = 9;
			}
			
			
			if (negativIndex != -1) {
				Object obj = whiteBoardItem.get(whiteBoardItem.size()-negativIndex);
				if (obj == null || obj.equals(null) || obj.equals("null")) {
					//Nothing happens in this case
				} else {
					log.debug("resetDataCarrierIndex "+negativIndex);
					log.debug("resetDataCarrierIndex dC "+whiteBoardItem.get(whiteBoardItem.size()-negativIndex));
					log.debug("resetDataCarrierIndex "+whiteBoardItem);
					
					whiteBoardItem.set(whiteBoardItem.size()-negativIndex,"");
				}
			}
			
			return whiteBoardItem;
			
		} catch (Exception err) {
			log.error("[resetDataCarrierIndex]",err);
		}
		
		return whiteBoardItem;
		
	}
	
	private Vector getPropertyObject (Vector whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			
			log.debug("getPropertyObject1: "+whiteBoardItem);
			log.debug("getPropertyObject2: "+whiteBoardItem.size());
			log.debug("getPropertyObject3: "+typeOfObject);
			
			//Add DataCarrier
			int negativIndex = -1;
			
			if (typeOfObject.equals("flow")) {
				negativIndex = 11;
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 13;
				} else {
					negativIndex = 10;
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					negativIndex = 11;
				} else { 
					negativIndex = 10;
				}
			} else if (typeOfObject.equals("activity")) {
				negativIndex = 11;
			} else if (typeOfObject.equals("processgroup")) {
				negativIndex = 10;
			} else if (typeOfObject.equals("connector")) {
				negativIndex = 10;
			}	
			
			
			Vector dataCarrierList = null;
			if (negativIndex != -1) {
				Object obj = whiteBoardItem.get(whiteBoardItem.size()-negativIndex);
				if (obj == null || obj.equals(null) || obj.equals("null")) {
					dataCarrierList = null;
				} else {
					dataCarrierList = (Vector) whiteBoardItem.get(whiteBoardItem.size()-negativIndex);
				}
			}
			
			return dataCarrierList;
		} catch (Exception err) {
			log.error("[getPropertyObject]",err);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private List setPropertyObject (List whiteBoardItem, String typeOfObject, Long diagramType, Map propertyMap) {
		try {
			//Add DataCarrier
			if (typeOfObject.equals("flow")) {
				whiteBoardItem.set(whiteBoardItem.size()-11,propertyMap);
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					whiteBoardItem.set(whiteBoardItem.size()-13,propertyMap);
				} else {
					whiteBoardItem.set(whiteBoardItem.size()-10,propertyMap);
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					whiteBoardItem.set(whiteBoardItem.size()-11,propertyMap);
				} else {
					whiteBoardItem.set(whiteBoardItem.size()-10,propertyMap);
				}
			} else if (typeOfObject.equals("activity")) {
				whiteBoardItem.set(whiteBoardItem.size()-11,propertyMap);
			} else if (typeOfObject.equals("processgroup")) {
				whiteBoardItem.set(whiteBoardItem.size()-10,propertyMap);
			} else if (typeOfObject.equals("connector")) {
				whiteBoardItem.set(whiteBoardItem.size()-10,propertyMap);
			}	
			return whiteBoardItem;
		} catch (Exception err) {
			log.error("[setPropertyObject]",err);
		}
		return null;
	}
	
	private DiagramInstanceObject getWhiteBoardItem(String unique_key, Map diagramMap) throws Exception {

		log.debug("getWhiteBoardItem"+unique_key+" "+diagramMap);
		log.debug("diagramMap.SIZE "+diagramMap.size());
		
		for (Iterator keyiter = diagramMap.keySet().iterator();keyiter.hasNext();) {
			Object key = keyiter.next();
			log.debug("key: "+key);
			Vector whiteBoardItem = (Vector) diagramMap.get(key);
			log.debug("whiteBoardItem"+whiteBoardItem);
			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
			
			log.debug("objInternalUID"+objInternalUID);
			
			if (objInternalUID.equals(unique_key)) {
				
				log.debug("Found whiteBoardItem"+whiteBoardItem);
				Long diagramInstanceObjectId = (Long) whiteBoardItem.get(whiteBoardItem.size()-6);
				log.debug("Found diagramInstanceObjectId"+diagramInstanceObjectId);
				return this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectById(diagramInstanceObjectId);
			}
			
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramDrillDownByDiagramObjectIdAndType(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramSearchDTO> getDiagramDrillDownByDiagramObjectIdAndType(String SID, Long organisation_id, Long diagramObjectId) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<Diagram> diagrams = this.diagramDaoImpl.getDiagramByNoAndParentObjectAndType(diagramObjectId);
	        	LinkedList<DiagramSearchDTO> diagramDTOs = new LinkedList<DiagramSearchDTO> ();
	        	
	        	if (diagrams != null) {
	        		for (int i=0;i<diagrams.size();i++) {
	        			diagramDTOs.add(this.morphDiagramToDiagramSearchDTO(diagrams.get(i), 0L, "", false));
	        		}
	        	}
	        	
	        	return diagramDTOs;
	        	
	        } else {
	        	return null;
	        }
		} catch (Exception err) {
			log.error("[getDiagramDrillDownByDiagramObjectIdAndType]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramSurfAcrossByDiagramObjectIdAndType(java.lang.String, java.lang.Long, java.lang.String)
	 */
	public List<DiagramSearchDTO> getDiagramSurfAcrossByDiagramObjectIdAndType(String SID, Long diagramObjectId, String flowType) {
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<Diagram> diagrams = this.diagramDaoImpl.getDiagramSurfAcrossByDiagramObjectIdAndType(diagramObjectId, flowType);
	        	LinkedList<DiagramSearchDTO> diagramDTOs = new LinkedList<DiagramSearchDTO> ();
	        	
	        	if (diagrams != null) {
	        		for (int i=0;i<diagrams.size();i++) {
	        			diagramDTOs.add(this.morphDiagramToDiagramSearchDTO(diagrams.get(i), 0L, "", false));
	        		}
	        	}
	        	
	        	return diagramDTOs;
	        	
	        } else {
	        	return null;
	        }
		} catch (Exception err) {
			log.error("[getDiagramSurfAcrossByDiagramObjectIdAndType]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#saveNewDiagramWithProperties(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Long, java.lang.Long, boolean, boolean, boolean, java.lang.Object, java.lang.Long, java.lang.Long, java.lang.Long, java.util.Vector, java.lang.String)
	 */
	public Long saveNewDiagramWithProperties(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId, 
			Vector propertyList, String shortName){
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	//log.debug("saveNewDiagramWithProperties -2- shortName "+shortName);
	        	
				List<DiagramSummary> diagramList = this.diagramSummaryDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	
	        	if (diagramList.size() > 0) {
	        		//more then zero means it has duplicates
	        		//return an Error ID, so that the user gets noticed
		        		return new Long(-39);
	        	}
				
				Diagram diagram = this.saveNewDiagramImpl(SID, diagramName, revisionComment, diagramMapObj, 
										organisation_id, diagramType, read, write, onlyIssues, users, 
										language_id, diagramObjectId, projectId, users_id, shortName);
				
				
				if (diagram == null) {
					return new Long(-1);
				}
				
				
				//Update Diagram Properties
				if (propertyList != null) {
					log.debug("propertyList: "+propertyList.size());
					
					for (Iterator iter = propertyList.iterator();iter.hasNext();) {
						Map property = (Map) iter.next();
						Long propertyId = Long.valueOf(property.get("propertyId").toString()).longValue();
						String propertyVal = property.get("propertyVal").toString();
						log.debug("propertyId,propertyVal: "+propertyId+" "+propertyVal);
						this.diagramObjectPropertyDaoImpl.addOrUpdateDiagramProperty(diagram.getDiagramNo(), 
								propertyId, propertyVal, users_id);
					}
				}
				
				
				return diagram.getDiagramId();
	        
	        }
		} catch (Exception err) {
			log.error("[saveNewDiagramWithProperties]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#saveNewDiagram(java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.Long, java.lang.Long, boolean, boolean, boolean, java.lang.Object, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public Long saveNewDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId, 
			String shortName){
		try {
			
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
		        	
				List<DiagramSummary> diagramList = this.diagramSummaryDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	
	        	if (diagramList.size() > 0) {
	        		//more then zero means it has duplicates
	        		//return an Error ID, so that the user gets noticed
		        		return new Long(-39);
	        	}
				
				Diagram diagram = this.saveNewDiagramImpl(SID, diagramName, revisionComment, diagramMapObj, 
										organisation_id, diagramType, read, write, onlyIssues, users, 
										language_id, diagramObjectId, projectId, users_id ,shortName);
				
				if (diagram == null) {
					log.error("DIAGRAM IS NULL "+SID+ " diagramName: "+ diagramName + " users_id " +users_id);
					
					return new Long(-1);
				}
				
				return diagram.getDiagramId();
			
	        }
		} catch (Exception err) {
			log.error("[saveNewDiagramWithProperties]",err);
		}
		return new Long(-1);
	}
	
	@SuppressWarnings("unchecked")
	private Diagram saveNewDiagramImpl(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId, 
			Long users_id, String shortName){
		try {
			
			//log.debug("saveNewDiagramImpl -3- shortName "+shortName);
			
			//A reference for the current Objects to lookup the DiagramObjectInstanceId and fix the 
			//Reference to the underlying Org-Object
			Map<String,Long> currentObjectsMap = new HashMap<String,Long>();
		
        	//List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(diagramName, organisation_id);
        	
        	XStream xStream = new XStream(new XppDriver());
			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        	
        	log.debug("saveNewDiagram: "+diagramMapObj);
        	Vector myObjectsList = (Vector) diagramMapObj;
        	
        	Map diagramMap = new HashMap();
        	
        	int i=0;
        	for (Iterator iter = myObjectsList.iterator();iter.hasNext();) {
        		diagramMap.put(i,iter.next());
        		i++;
        	}
        	
        	
        	
        	log.debug("diagramMap: "+diagramMap);
        	log.debug("diagramMap SIZE: "+diagramMap.size());
        	log.debug("diagramName: "+diagramName);
        	log.debug("revisionComment: "+revisionComment);
        	
        	if (shortName.length() == 0) {
        		shortName = diagramName.substring(0, 1);
        		Long diagramNo = this.diagramDaoImpl.getDiagramHighestNumber();
    			if(diagramNo==null){
    				diagramNo = new Long(0);
    			}
    			diagramNo++;
    			shortName += ""+diagramNo;
        	}
        	
        	//iagram diagram = DiagramServiceDaoImpl.getIn_stance().addNewDiagramByName(diagramName, users_id, revisionComment, 
        	//			organisation_id, diagramType, read, write, onlyIssues, users);
        	
        	Diagram diagram = this.diagramDaoImpl.addNewDiagramByName(diagramName, users_id, 
        			revisionComment, organisation_id, diagramType, 
        			read, write, onlyIssues, users, diagramObjectId, shortName);
        	
        	//Update the Diagram Summary
        	this.diagramSummaryDaoImpl.addOrUpdateDiagramSummary(diagram);
        	
        	//auto create orphan Objects
        	//See: http://jira.webbase-design.de/browse/ICH-369
        	if (diagramObjectId == null) {
        		//if diagramType
        	}
        	
        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
        		Object key = iter.next();
        		log.debug("key: "+key);
        		
        		Vector whiteBoardItem = (Vector) diagramMap.get(key);
        		
        		log.debug("whiteBoardItem: "+whiteBoardItem);
        		log.debug("whiteBoardItem 1 "+whiteBoardItem.get(0));
        		String typeOfObject = whiteBoardItem.get(0).toString();
        		log.debug("typeOfObject: "+typeOfObject);
        		
        		if (!typeOfObject.equals("connector")){
        			
        			String objName = whiteBoardItem.get(whiteBoardItem.size()-8).toString();
        			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
        			Long diagramobjectId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
        			if (diagramobjectId == 0) diagramobjectId = null;
        			log.debug("objName: "+objName);
        			log.debug("diagramobjectId 2: "+diagramobjectId);
        			log.debug("objInternalUID:  2 "+objInternalUID);
        			
        			Vector dataCarrierList = this.getDataCarrierObject(whiteBoardItem, typeOfObject, diagramType);
        			
        			//Reset Data Carrier to NULL
        			whiteBoardItem = this.resetDataCarrierIndex(whiteBoardItem, typeOfObject, diagramType);
        			
	    			String xmlString = xStream.toXML(whiteBoardItem);	
	    			
	    			String flowType = typeOfObject;
	    			if (typeOfObject.equals("inputflow") || typeOfObject.equals("outputflow")) {
	    				typeOfObject = "flow";
	    			}
	    			
	    			log.debug("dataCarrierList: "+dataCarrierList);
	    			
	    			Boolean isPending = this.getPendingStatus(whiteBoardItem, typeOfObject, diagramType);
	    			
	    			log.debug("isPending: "+isPending);
	        		
	    			Long assigneeUserId = null;
	    			//If the Item is of Status Pending then we need to link to get the Assignee Name/ID
    				if (isPending){
    					assigneeUserId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-10).toString()).longValue();
    				}
    				
    				log.debug("assigneeUserId: "+assigneeUserId);
    				
    				Vector propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
    				
    				log.debug("propertyMap: "+propertyMap);
    				
	    			//log.debug("xmlString: "+xmlString);
	    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
								    					typeOfObject, users_id, organisation_id, 
								    					diagram, objName, null, null, xmlString, objInternalUID,
								    					isPending, assigneeUserId, diagramName, language_id, flowType, dataCarrierList,
								    					propertyMap);
	    			
	    			//In case there was an error or wrong reference show that to the User
	    			if (diagramInstanceObjectId <= 0) {
	    				log.error("### NEGATIVE diagramInstanceObjectId "+diagramInstanceObjectId);
	    				return null;
	    			}
	    			
	    			log.debug("diagramInstanceObjectId: "+diagramInstanceObjectId);

	    			//set the diagramInstanceObjectId, to read it later on to set the reference to the underlying Org-Object
	    			currentObjectsMap.put(objInternalUID, diagramInstanceObjectId);
	    			
	    			
	    			
	    			if (diagramobjectId == null) {
	    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
	    					getDiagramInstanceObjectById(diagramInstanceObjectId);
	    				diagramobjectId = diagramInstance.getDiagramObject().getDiagramObjectId();
	    				whiteBoardItem.set(whiteBoardItem.size()-7,diagramobjectId);
	    				String xmlString_2 = xStream.toXML(whiteBoardItem);
	    				diagramInstance.setGraphObject(xmlString_2);
	    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
	    				
	    				//Only do this for new Issue's
	    				if (typeOfObject.equals("issueflow")) {
		    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
		    				this.issueAssigneeDaoImpl.addIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);	
		    			}
	    				
	    				
	    			} else {
	    				//Update Assignee
	    				if (typeOfObject.equals("issueflow")) {
		    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
		    				this.issueAssigneeDaoImpl.saveOrUpdateIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);	
		    			}
	    			}
	    			
	    			log.debug("Set Incomplete: "+whiteBoardItem);
	    			log.debug("SET diagramInstanceObjectId: "+diagramInstanceObjectId);
	    			whiteBoardItem.set(whiteBoardItem.size()-6, diagramInstanceObjectId);
	    			
	    			log.debug("Set Complete: "+whiteBoardItem);
	    			diagramMap.put(key, whiteBoardItem);
        		}
        	}
        	
        	log.debug("First Iteration done");
        	
        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
        		
        		Object key = iter.next();
        		
        		Vector whiteBoardItem = (Vector) diagramMap.get(key);
        		
        		//Map whiteBoardItem = (Map) diagramMap.get();
        		log.debug("whiteBoardItem: "+whiteBoardItem);
        		String typeOfObject = whiteBoardItem.get(0).toString();
        		log.debug("typeOfObject: "+typeOfObject);
        		
        		if (typeOfObject.equals("connector")){
        			
        			String objName = whiteBoardItem.get(whiteBoardItem.size()-8).toString();
        			
        			String startObjName = whiteBoardItem.get(1).toString();
        			DiagramInstanceObject dStartInstance = this.getWhiteBoardItem(startObjName, diagramMap);
        			Long diagramobjectStart_id = dStartInstance.getDiagramObject().getDiagramObjectId();
        			
        			String endObjName = whiteBoardItem.get(3).toString();
        			DiagramInstanceObject dEndInstance = this.getWhiteBoardItem(endObjName, diagramMap);
        			Long diagramobjectEnd_id = dEndInstance.getDiagramObject().getDiagramObjectId();
        			
        			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
        			Long diagramobjectId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
        			if (diagramobjectId == 0) diagramobjectId = null;
        			//log.debug("objName: "+objName);
        			log.debug("diagramobjectId: "+diagramobjectId);
        			//log.debug("objInternalUID: "+objInternalUID);
        			
        			Vector propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
        			
        			//Reset Data Carrier to NULL
        			whiteBoardItem = this.resetDataCarrierIndex(whiteBoardItem, typeOfObject, diagramType);
        			
	    			String xmlString = xStream.toXML(whiteBoardItem);	
	    			
	    			//log.debug("Saving Connector: "+xmlString);
	        		
	    			//log.debug("xmlString: "+xmlString);
	    			//Connectors are never pending
	    			//TODO: Check if the connection between these object does already exist
	    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
								    					typeOfObject, users_id, organisation_id, diagram, objName, 
								    					diagramobjectStart_id, diagramobjectEnd_id, xmlString, objInternalUID, 
								    					false, null, diagramName, language_id, "", null, propertyMap);
	    			
	    			//In case there was an error or wrong reference show that to the User
	    			if (diagramInstanceObjectId <= 0) {
	    				log.error("### NEGATIVE diagramInstanceObjectId "+diagramInstanceObjectId);
	    				return null;
	    			}
	    			
	    			if (diagramobjectId == null) {
	    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
	    					getDiagramInstanceObjectById(diagramInstanceObjectId);
	    				whiteBoardItem.set(whiteBoardItem.size()-7,diagramInstance.getDiagramObject().getDiagramObjectId());
	    				String xmlString_2 = xStream.toXML(whiteBoardItem);
	    				diagramInstance.setGraphObject(xmlString_2);
	    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
	    			}
        			
        		}
        	}
        	
        	//check for Underlying Roles for the Object
        	log.debug("check for Underlying Roles for the Object: "+diagramType);
        	if (diagramType == 2 || diagramType == 4) {
        		
        		log.debug("Underlying diagramMap SIZE: "+diagramMap.size());
        		
        		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
        			
        			Object keyHashMap = iter.next();
        			//Map whiteBoardItem = (Map) diagramMap.get(iter.next());
	        		Vector whiteBoardItem = (Vector) diagramMap.get(keyHashMap);
	        		
	        		String typeOfObject = whiteBoardItem.get(0).toString();
	        		
	        		String objInternalMainUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
	        		
        			if (typeOfObject.equals("processtree")
        					|| typeOfObject.equals("activity")
        					|| typeOfObject.equals("inputflow")
        					|| typeOfObject.equals("outputflow")){
        				
        				Vector connectList = (Vector) whiteBoardItem.get(whiteBoardItem.size()-9);
        				if (connectList != null) {
        					
        					//If we find any Item with a 0 as connected Org-Object we have to fix that
        					boolean foundOrgWithEmptyReference = false;
        					
        					int vectorKey = 0;
        					for (Iterator connectListiter = connectList.iterator();connectListiter.hasNext();) {
        						Object key = connectListiter.next();
        						Vector connectItem = (Vector) key;
        						
        						String objInternalUID = connectItem.get(0).toString();
        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
        						//log.debug("Underlying diagramobjectId: "+diagramobjectId);
    	        				
        						if (diagramobjectId == null || diagramobjectId == 0) {
        							foundOrgWithEmptyReference = true;
        							diagramobjectId = this.getDiagramObjectIdByCurrentMap(diagramMap, objInternalUID);
        							connectItem.set(1, diagramobjectId);
        						}
        						connectList.set(vectorKey, connectItem);
        						vectorKey++;
        					}
        					
        					Long diagramInstanceObjectId = currentObjectsMap.get(objInternalMainUID);
        					//log.debug("Underlying diagramInstanceObjectId: "+diagramInstanceObjectId);
        					
        					whiteBoardItem.set(whiteBoardItem.size()-9, connectList);
        					
        					//Do fill the Items Graph-XMLRef
        					if (foundOrgWithEmptyReference) {
	        					//Update Reference to point to correct DiagramObject Id
			    				DiagramInstanceObject diagramInstance = this.diagramInstanceObjectDaoImpl.
			    					getDiagramInstanceObjectById(diagramInstanceObjectId);
			    				
			    				String xmlString_2 = xStream.toXML(whiteBoardItem);
			    				diagramInstance.setGraphObject(xmlString_2);
			    				this.diagramInstanceObjectDaoImpl.updateDiagramInstanceObject(diagramInstance);
        					}
        					
        					//save Connection Object
        					for (Iterator connectListiter = connectList.iterator();connectListiter.hasNext();) {
        						Object key = connectListiter.next();
        						log.debug("key: "+key);
        						Vector connectItem = (Vector) key;
        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
        						this.diagramObjectOrganisationDaoImpl.
        							addDiagramObjectOrganisation(diagramInstanceObjectId, diagramobjectId, users_id);
        						
        					}

        				}
        				
        			}
        			
        		}
        		
        	}
        	
        	//FIXME: SEND UPDATE
//	        	IConnection current = Red5.getConnectionLocal();
//	        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
//	        	
//	        	Map message = new HashMap();
//	        	message.put("type", "addDiagram");
//	        	message.put("diagram", diagram);
//	        	
//	        	this.application.sendMessageWithClientByOrganizationId(message, currentClient.getOrganizationId());
        	
        	//Add Diagram to Projects
        	if (projectId != null && projectId!=0) {
    			this.projectRelationDaoImpl.addProjectRelation(projectId, diagram.getDiagramNo(), null);
        	} else {
        		this.projectRelationDaoImpl.deleteProjectRelation(diagram.getDiagramNo());
        	}
        	
        	return diagram;
	        	
		} catch (Exception err) {
			log.error("[saveNewDiagram]",err);
		}
        return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private Long getDiagramObjectIdByCurrentMap(Map diagramMap, String searchObjInternalUID) throws Exception {
		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
    		Object key = iter.next();
    		log.debug("key: "+key);
    		Vector whiteBoardItem = (Vector) diagramMap.get(key);
    		log.debug("whiteBoardItem: "+whiteBoardItem);
    		String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
    		
    		if (searchObjInternalUID.equals(objInternalUID)) {
    			return Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
    		}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramHistoryByNo(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public SearchResult getDiagramHistoryByNo(String SID, Long organisation_id, 
			Long diagramNo, Long language_id){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramHistoryByNo(organisation_id, diagramNo);
	        	
	        	List<DiagramSearchDTO> diagramReturnList = new LinkedList<DiagramSearchDTO>();
	        	for (Diagram dia : diagramList) {
	        		
//	        		DiagramType dType = dia.getDiagramType();
//	        		dType.setLabel(Fieldmanagment.get_Instance().
//	        				getFieldByLabelNumberAndLanguage(dia.getDiagramType().getFieldId(), language_id));
//	        		dia.setDiagramType(dType);
	        		
	        		diagramReturnList.add(this.morphDiagramToDiagramSearchDTO(dia, 0L, "", true));
	        	}
	        	
	        	SearchResult sResult = new SearchResult();
	        	sResult.setObjectName(DiagramSearchDTO.class.getName());
	        	sResult.setRecords(Integer.valueOf(diagramReturnList.size()).longValue());
	        	sResult.setResult(diagramReturnList);
	        	
	        	return sResult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramList]",err);
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#_getDiagramById(java.lang.String, java.lang.Long)
	 */
	public Diagram _getDiagramById(String SID, Long diagramId){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Diagram diagram = this.diagramDaoImpl.getDiagramById(diagramId);
	        	//diagram.setParentDiagramObject(null);
	        	return diagram;
	        }
		} catch (Exception err) {
			log.error("[getDiagramById]",err);
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramById(java.lang.String, java.lang.Long)
	 */
	public DiagramSearchDTO getDiagramById(String SID, Long diagramId){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Diagram diagram = this.diagramDaoImpl.getDiagramById(diagramId);
	        	
	        	ProjectRelation pr = this.projectRelationDaoImpl.getProjectRelationByDiagramNo(diagram.getDiagramNo());
	        	
	        	Long projectId = 0L;
	        	String projectName = "";
	        	if (pr != null){
	        		projectId = pr.getProject().getProjectId();
	        		projectName = pr.getProject().getName();
	        	}
	        	
	        	//diagram.setParentDiagramObject(null);
	        	return this.morphDiagramToDiagramSearchDTO(diagram, projectId, projectName, false);
	        }
		} catch (Exception err) {
			log.error("[getDiagramById]",err);
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramList(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.Long)
	 */
	public SearchResult getDiagramList(String SID, int start, int max, String orderby, boolean asc, 
			Long organisation_id, Long language_id){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramList(organisation_id);
	        	
	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
	        	for (Diagram dia : diagramList) {
	        		
	        		Diagram returnDia = this.diagramSummaryDaoImpl.getDiagramByNo(dia.getDiagramNo(), organisation_id);
	        		
	        		DiagramType dType = returnDia.getDiagramType();
	        		dType.setLabel(this.fieldmanagment.
	        				getFieldByLabelNumberAndLanguage(dia.getDiagramType().getFieldId(), language_id));
	        		returnDia.setDiagramType(dType);
	        		
	        		diagramReturnList.add(returnDia);
	        	}

	        	SearchResult sResult = new SearchResult();
	        	sResult.setObjectName(Diagram.class.getName());
	        	sResult.setResult(diagramReturnList);
	        	
	        	return sResult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramList]",err);
		} 
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramSearch(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.String)
	 */
	public SearchResult getDiagramSearch(String SID, int start, int max, String orderBy, boolean asc, 
			Long organization_id, String search) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	if (search.equals(null) || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%"+search+"%";
	        	}
	        	
	        	//List<Diagram> listResult = this.diagramDaoImpl.getDiagramByNoMaxAndOrder(organization_id, orderBy, asc, start, max, search);
	        	
	        	List<Diagram> listResult = this.diagramSummaryDaoImpl.
							getDiagramByNoMaxAndOrder(organization_id, orderBy, asc, start, max, search);

				SearchResult sresult = new SearchResult();
				sresult.setObjectName(Diagram.class.getName());
				//sresult.setRecords(this.diagramDaoImpl.getMaxDiagram(organization_id, search));
				sresult.setRecords(this.diagramSummaryDaoImpl.getMaxDiagram(organization_id, search));
				sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramList]",err);
		} 
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjects(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getDiagramObjects(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			
			return this.getDiagramObjectsIncludingAssingees(SID, organization_id, start, max, objectTypeNames, orderBy, asc, search);
//			Long users_id = this.sessionmanagement.checkSession(SID);
//	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
//				
////	        	LinkedList<String> objectTypeNames = new LinkedList<String>();
////	        	objectTypeNames.add("departementFixed");
////	        	objectTypeNames.add("unitFixed");
////	        	objectTypeNames.add("companyFixed");
//	        	if (search == null || search.length() == 0) {
//	        		search = "%";
//	        	} else {
//	        		search = "%" + search + "%";
//	        	}
//	        	
//	        	List<DiagramObject> listResult = this.diagramObjectDaoImpl.
//	        											selectDiagramObjects(organization_id, start, max, objectTypeNames, 
//	        													orderBy, asc, search, true);
//
//	        	SearchResult sresult = new SearchResult();
//	        	sresult.setObjectName(DiagramObject.class.getName());
//	        	sresult.setRecords(this.diagramObjectDaoImpl.
//	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, true));
//	        	sresult.setResult(listResult);
//				
//				return sresult;
//	        }
		} catch (Exception err) {
			log.error("[getDiagramObjects]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsFastPath(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getDiagramObjectsFastPath(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
//		        	LinkedList<String> objectTypeNames = new LinkedList<String>();
//		        	objectTypeNames.add("departementFixed");
//		        	objectTypeNames.add("unitFixed");
//		        	objectTypeNames.add("companyFixed");
	        	if (search == null || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%" + search + "%";
	        	}
	        	
	        	List<Map> listResult = this.diagramObjectDaoImpl.
	        								selectSmallDiagramObjects(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search, null);

//	        	for (Map diaObject : listResult) {
//	        		log.debug(diaObject);
//	        		log.debug(diaObject.get("name"));
//	        	}
	        	for (Map item : listResult) {
	        		Object inserted = item.get("inserted");
	        		item.put("inserted", inserted.toString());
	        		//log.debug("inserted: "+inserted+" Class: "+inserted.getClass().getName());
	        	}
	        	
	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramObject.class.getName());
	        	sresult.setRecords(this.diagramObjectDaoImpl.
	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, null));
	        	
	        	sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsFastPath]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectCompleteById(java.lang.String, java.lang.Long)
	 */
	public DiagramObjectCompleteDTO getDiagramObjectCompleteById(String SID, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	DiagramObject diaObject =  this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
	        	
	        	if (diaObject != null) {
	        		DiagramObjectCompleteDTO diagramObjectCompleteDTO = new DiagramObjectCompleteDTO();
		        	
	        		diagramObjectCompleteDTO.setDiagramObjectId(diaObject.getDiagramObjectId());
	        		diagramObjectCompleteDTO.setName(diaObject.getName());
	        		diagramObjectCompleteDTO.setObjectTypeName(diaObject.getObjectTypeName());
	        		diagramObjectCompleteDTO.setComment(diaObject.getComment());
	        		
	        		diagramObjectCompleteDTO.setDiagramDocuments(this.diagramDocumentDaoImpl.getDiagramDocumentByDiagramObjectId(diaObject.getDiagramObjectId()));
	        		
	        		diagramObjectCompleteDTO.setDataCarrierDiagramObject(new LinkedList<DataCarrierDiagramObjectDTO>());
	        		
	        		for (Iterator<DataCarrierDiagramObject> iter = diaObject.getDataCarrierDiagramObject().iterator();iter.hasNext();) {
	        			
	        			DataCarrierDiagramObject dataCarrierDiagramObject = iter.next();
	        			
	        			DataCarrierDiagramObjectDTO dataCarrierDiagramObjectDTO = new DataCarrierDiagramObjectDTO();
	        			
	        			dataCarrierDiagramObjectDTO.setDataCarrierDiagramObjectId(dataCarrierDiagramObject.getDataCarrierDiagramObjectId());
	        			dataCarrierDiagramObjectDTO.setDataCarrierObjectdiagramObjectId(dataCarrierDiagramObject.getDataCarrierObjectdiagramObjectId());
	        			
	        			dataCarrierDiagramObjectDTO.setDiagramObjectId(dataCarrierDiagramObject.getDiagramObjectId());
	        			
	        			if (dataCarrierDiagramObject.getDataCarrierObjectdiagramObject() != null) {
	        				dataCarrierDiagramObjectDTO.setDataCarrierObjectdiagramObject(new DiagramObjectDataCarrierDTO());
	        				dataCarrierDiagramObjectDTO.getDataCarrierObjectdiagramObject().setDiagramObjectId(
	        						dataCarrierDiagramObject.getDataCarrierObjectdiagramObject().getDiagramObjectId());
	        				dataCarrierDiagramObjectDTO.getDataCarrierObjectdiagramObject().setName(
	        						dataCarrierDiagramObject.getDataCarrierObjectdiagramObject().getName());
	        				dataCarrierDiagramObjectDTO.getDataCarrierObjectdiagramObject().setObjectTypeName(
	        						dataCarrierDiagramObject.getDataCarrierObjectdiagramObject().getObjectTypeName());
	        				
	        			}
	        			
	        		}
	        		return diagramObjectCompleteDTO;
	        	}
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectCompleteById]",err);
		}
        return null;	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectPureById(java.lang.String, java.lang.Long)
	 */
	public DiagramObjectToolTip getDiagramObjectPureById(String SID, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	DiagramObject diaObject =  this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
	        	
	        	if (diaObject != null) {
		        	List<Role> listRoles = this.roleDaoImpl.getRolesByDiagramObjectId(diaObject.getDiagramObjectId());
		        	
		        	DiagramObjectToolTip diagramObjectToolTip = new DiagramObjectToolTip();
		        	
		        	diagramObjectToolTip.setDiagramObjectId(diaObject.getDiagramObjectId());
		        	if (diaObject.getInsertedby() != null) {
			        	diagramObjectToolTip.setInsertedby(new UserMinimalDTO());
			        	diagramObjectToolTip.getInsertedby().setFirstname(diaObject.getInsertedby().getFirstname());
			        	diagramObjectToolTip.getInsertedby().setLastname(diaObject.getInsertedby().getLastname());
			        	diagramObjectToolTip.getInsertedby().setUser_id(diaObject.getInsertedby().getUser_id());
			        	diagramObjectToolTip.getInsertedby().setLogin(diaObject.getInsertedby().getLogin());
		        	}
		        	
		        	if (listRoles != null) {
		        		diagramObjectToolTip.setRoles(new LinkedList<RoleMinimalDTO>());
		        	
		        		for (Role role : listRoles) {
		        			RoleMinimalDTO roleMinimalDTO = new RoleMinimalDTO();
		        			roleMinimalDTO.setRolesId(role.getRolesId());
		        			roleMinimalDTO.setAssignee(new UserMinimalDTO());
		        			roleMinimalDTO.getAssignee().setFirstname(role.getAssignee().getFirstname());
				        	roleMinimalDTO.getAssignee().setLastname(role.getAssignee().getLastname());
				        	roleMinimalDTO.getAssignee().setUser_id(role.getAssignee().getUser_id());
				        	roleMinimalDTO.getAssignee().setLogin(role.getAssignee().getLogin());
				        	
				        	diagramObjectToolTip.getRoles().add(roleMinimalDTO);
		        		}
		        		
		        		
		        	}
		        	
		        	return diagramObjectToolTip;
	        	}
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectPureById]",err);
		}
        return null;	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsIncludingAssingees(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getDiagramObjectsIncludingAssingees(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
//	        	LinkedList<String> objectTypeNames = new LinkedList<String>();
//	        	objectTypeNames.add("departementFixed");
//	        	objectTypeNames.add("unitFixed");
//	        	objectTypeNames.add("companyFixed");
	        	if (search == null || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%" + search + "%";
	        	}
	        	
	        	List<DiagramObject> listResult = this.diagramObjectDaoImpl.
	        											selectDiagramObjects(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search, null);

	        	//for (DiagramObject diaObject : listResult) {
	        	//	diaObject.setRoles(this.roleDaoImpl.getRolesByDiagramObjectId(diaObject.getDiagramObjectId()));
	        	//}
	        	
	        	List<DiagramObjectSearchDTO> dSearchDTO = new LinkedList<DiagramObjectSearchDTO>();
	        	
	        	for (DiagramObject diaObject : listResult) {
	        		dSearchDTO.add(this.morphDiagramObjectToDiagramObjectSearchDTO(diaObject));
	        	}
	        	
	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramObjectSearchDTO.class.getName());
	        	sresult.setRecords(this.diagramObjectDaoImpl.
	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, null));
	        	
	        	sresult.setResult(dSearchDTO);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjects]",err);
		}
        return null;
	}
	
	private DiagramObjectSearchDTO morphDiagramObjectToDiagramObjectSearchDTO(
			DiagramObject diaObject) {
		try {
			
			DiagramObjectSearchDTO dSearchDTO = new DiagramObjectSearchDTO();
			
			dSearchDTO.setDiagramObjectId(diaObject.getDiagramObjectId());
			dSearchDTO.setName(diaObject.getName());
			dSearchDTO.setObjectTypeName(diaObject.getObjectTypeName());
			dSearchDTO.setPending(diaObject.getPending());
			dSearchDTO.setInserted(diaObject.getInserted());
			dSearchDTO.setInsertedBy(diaObject.getInsertedby().getUser_id());
			
			return dSearchDTO;
			
		} catch (Exception err) {
			log.error("[morphDiagramObjectToDiagramObjectSearchDTO]",err);
		}
        return null;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsNoMatterPending(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getDiagramObjectsNoMatterPending(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
//	        	LinkedList<String> objectTypeNames = new LinkedList<String>();
//	        	objectTypeNames.add("departementFixed");
//	        	objectTypeNames.add("unitFixed");
//	        	objectTypeNames.add("companyFixed");
	        	if (search == null || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%" + search + "%";
	        	}
	        	
	        	List<DiagramObject> listResult = this.diagramObjectDaoImpl.
	        											selectDiagramObjects(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search, null);

	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramObject.class.getName());
	        	sresult.setRecords(this.diagramObjectDaoImpl.
	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, null));
	        	sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjects]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getParentDiagramObjects(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.lang.String)
	 */
	public SearchResult getParentDiagramObjects(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
//	        	LinkedList<String> objectTypeNames = new LinkedList<String>();
//	        	objectTypeNames.add("departementFixed");
//	        	objectTypeNames.add("unitFixed");
//	        	objectTypeNames.add("companyFixed");
	        	if (search == null || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%" + search + "%";
	        	}
	        	
	        	
	        	
	        	List<Map> listResult = this.diagramObjectDaoImpl.
	        											selectParentDiagramObjects(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search);

	        	for (Map item : listResult) {
	        		Object inserted = item.get("inserted");
	        		item.put("inserted", inserted.toString());
	        		//log.debug("inserted: "+inserted+" Class: "+inserted.getClass().getName());
	        	}
	        	
	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramObject.class.getName());
	        	sresult.setRecords(this.diagramObjectDaoImpl.
	        				selectParentMaxDiagramObjects(organization_id, objectTypeNames, search));
	        	sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getParentDiagramObjects]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsById(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramInstanceObject> getDiagramObjectsById(String SID, Long diagram_id, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	Diagram diagram = this.diagramDaoImpl.getDiagramById(diagram_id);
				
	        	XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				List<DiagramInstanceObject> digramInstanceList = this.diagramInstanceObjectDaoImpl.
								getDiagramInstanceObjectListByDiagram(diagram_id);
				
				for (DiagramInstanceObject diagramInstance : digramInstanceList) {
					
					log.debug("getDiagramObjectsById ObjectTypeName: "+diagramInstance.getDiagramObject().getObjectTypeName());
					String objectTypeName = diagramInstance.getDiagramObject().getObjectTypeName();
					
					//diagramInstance.setGraphAsObject((Map) xStream.fromXML(diagramInstance.getGraphObject()));
					
					diagramInstance.setGraphAsObject((Vector) xStream.fromXML(diagramInstance.getGraphObject()));
					
					diagramInstance.setGraphObject("");
					
					//Overwrite the properties with the one from the latest Object-Version
					List<DiagramObjectProperty> diagramProperties = this.diagramObjectPropertyDaoImpl.
								getDiagramObjectPropertyByObject(diagramInstance.getDiagramObject().getDiagramObjectId());
					
					Map<Integer,Map<String,Object>> propertyMap = new HashMap<Integer,Map<String,Object>>();
					int key = 0;
					for (DiagramObjectProperty diaProperty : diagramProperties) {
						Map<String,Object> property = new HashMap<String,Object>();
						property.put("propertyId", diaProperty.getPropertyId());
						property.put("propertyVal", diaProperty.getValue());
						propertyMap.put(key, property);
						key++;
					}
					
					this.setPropertyObject(diagramInstance.getGraphAsObject(), diagramInstance.getDiagramObject().getObjectTypeName(), 
							diagram.getDiagramType().getDiagramTypeId(),propertyMap);
					
					//overwrite Text in Object-Instances with Parent Text
					diagramInstance.getGraphAsObject().set(diagramInstance.getGraphAsObject().size()-8,diagramInstance.getDiagramObject().getName());
					
					//only overwrite pending status in case of flow and MyRole Diagram
					if (diagram.getDiagramType().getDiagramTypeId() == 2 || diagram.getDiagramType().getDiagramTypeId() == 4) {
						//In case its an Org.-Object / Pending Object, overwrite the Status of the Object
						//with the one stored in the DiagramObject, cause its possible to 
						//approve Objects outside the Diagram/without making a new Diagram Revision
						if ( objectTypeName.equals("companyFixed") || objectTypeName.equals("unitFixed") ||
								objectTypeName.equals("departementFixed") ) {
							diagramInstance.getGraphAsObject().set(diagramInstance.getGraphAsObject().size()-9,diagramInstance.getDiagramObject().getPending());
						}
					}
					
					diagramInstance.setDiagram(null);
					diagramInstance.setDiagramObject(null);
					
					log.debug("getDiagramObjectsById getGraphAsObject: "+diagramInstance.getGraphAsObject());
					
				}
				
				return digramInstanceList;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getLatestDiagramObjectsByNo(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramInstanceObjectDTO> getLatestDiagramObjectsByNo(String SID, 
			Long diagramNo, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				Diagram diagram = this.diagramSummaryDaoImpl.getDiagramByNo(diagramNo, organisation_id);
				
				log.debug("diaggetDiagramObjectsByNoram: "+diagram);
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				List<DiagramInstanceObject> digramInstanceList = this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
				
				List<DiagramInstanceObjectDTO> dInstances = new LinkedList<DiagramInstanceObjectDTO>();
				
				for (DiagramInstanceObject diagramInstance : digramInstanceList) {
					
					log.debug("diagramInstance1 : "+diagramInstance);
					DiagramInstanceObjectDTO diagramInstanceObjectDTO = new DiagramInstanceObjectDTO();
					diagramInstanceObjectDTO.setGraphAsObject((Vector) xStream.fromXML(diagramInstance.getGraphObject()));
					diagramInstanceObjectDTO.setDiagramInstanceObjectId(diagramInstance.getDiagramInstanceObjectId());
					log.debug("diagramInstance2 : "+diagramInstanceObjectDTO.getGraphAsObject());
					
					diagramInstanceObjectDTO.setAddInfos(new HashMap());
					diagramInstanceObjectDTO.getAddInfos().put("drillDown", this.getHasChildDiagrams(diagramInstance.getDiagramObject().getDiagramObjectId(),
							diagramInstance.getDiagramObject().getObjectTypeName()));
					diagramInstanceObjectDTO.getAddInfos().put("surfAcross", this.getHasSurfAcrossDiagrams(diagramInstance.getDiagramObject().getDiagramObjectId(), 
							diagramInstance.getFlowType()));
					
					diagramInstanceObjectDTO.setDiagramDocuments(this.diagramDocumentDaoImpl.getDiagramDocumentByDiagramObjectId(diagramInstance.getDiagramObject().getDiagramObjectId()));
					
					dInstances.add(diagramInstanceObjectDTO);
				}
				
				return dInstances;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}
	
	private Boolean getHasChildDiagrams(Long diagramObjectId, String objectTypeName){
		try {
			
			if (!objectTypeName.equals("flow") || !objectTypeName.equals("connector") 
					|| !objectTypeName.equals("connectorText")) {
				List<Diagram> diagrams = this.diagramDaoImpl.getDiagramByNoAndParentObjectAndType(diagramObjectId);
        	
	        	if (diagrams != null) {
	        		if (diagrams.size() > 0) {
	        			return true;
	        		}
	        	}
			}
			
        	return false;
			
		} catch (Exception err) {
			log.error("[getHasChildDiagrams]",err);
		}
		return null;
	}
	
	private Boolean getHasSurfAcrossDiagrams(Long diagramObjectId, String flowType){
		try {
			
			if (flowType.equals("inputflow")) {
				List<Diagram> diagrams = this.diagramDaoImpl.getDiagramSurfAcrossByDiagramObjectIdAndType(diagramObjectId, "outputflow");
	        	if (diagrams != null) {
	        		if (diagrams.size() > 0) {
	        			return true;
	        		}
	        	}
			} else if (flowType.equals("outputflow")) {
				List<Diagram> diagrams = this.diagramDaoImpl.getDiagramSurfAcrossByDiagramObjectIdAndType(diagramObjectId, "inputflow");
	        	if (diagrams != null) {
	        		if (diagrams.size() > 0) {
	        			return true;
	        		}
	        	}
			}
			
        	return false;
			
		} catch (Exception err) {
			log.error("[getHasChildDiagrams]",err);
		}
		return null;
	}
	 
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsByDiagramId(java.lang.String, java.lang.Long)
	 */
	public List<DiagramInstanceObjectDTO> getDiagramObjectsByDiagramId(String SID, Long diagramId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				//Diagram diagram = this.diagramSummaryDaoImpl.getDiagramByNo(diagramNo, organisation_id);
				
				log.debug("diaggetDiagramObjectsByNoram: "+diagramId);
				XStream xStream = new XStream(new XppDriver());
				xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
				
				List<DiagramInstanceObject> digramInstanceList = this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectListByDiagram(diagramId);
				List<DiagramInstanceObjectDTO> dInstances = new LinkedList<DiagramInstanceObjectDTO>();
				
				for (DiagramInstanceObject diagramInstance : digramInstanceList) {
					
					log.debug("diagramInstance1 : "+diagramInstance);
					DiagramInstanceObjectDTO diagramInstanceObjectDTO = new DiagramInstanceObjectDTO();
					diagramInstanceObjectDTO.setGraphAsObject((Vector) xStream.fromXML(diagramInstance.getGraphObject()));
					diagramInstanceObjectDTO.setDiagramInstanceObjectId(diagramInstance.getDiagramInstanceObjectId());
					log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
					
					diagramInstanceObjectDTO.setAddInfos(new HashMap());
					diagramInstanceObjectDTO.getAddInfos().put("drillDown", this.getHasChildDiagrams(diagramInstance.getDiagramInstanceObjectId(),
							diagramInstance.getDiagramObject().getObjectTypeName()));
					diagramInstanceObjectDTO.getAddInfos().put("surfAcross", this.getHasSurfAcrossDiagrams(diagramInstance.getDiagramObject().getDiagramObjectId(), 
							diagramInstance.getFlowType()));
					
					diagramInstanceObjectDTO.setDiagramDocuments(this.diagramDocumentDaoImpl.getDiagramDocumentByDiagramObjectId(diagramInstance.getDiagramObject().getDiagramObjectId()));
					
					dInstances.add(diagramInstanceObjectDTO);
				}
				
				return dInstances;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByDiagramId]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getLatestDiagramObjectsById(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramInstanceObjectDTO> getLatestDiagramObjectsById(String SID, Long diagramId, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	Diagram dia = this.diagramDaoImpl.getDiagramById(diagramId);
	        	
				return this.getLatestDiagramObjectsByNo(SID, dia.getDiagramNo(), organisation_id);
	        }
		} catch (Exception err) {
			log.error("[getLatestDiagramObjectsById]",err);
		}
        return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateDiagramNameById(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public Long updateDiagramNameById(String SID, Long diagramId, String newName, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	Diagram remoteDiagram = this.diagramDaoImpl.getDiagramById(diagramId);
	        	
	        	//List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(newName, organisation_id);
	        	List<DiagramSummary> diagramList = this.diagramSummaryDaoImpl.getDiagramByName(newName, organisation_id);
	        	
	        	if (diagramList.size() == 1) {
	        		if (!diagramList.get(0).getDiagram().getDiagramNo().equals(remoteDiagram.getDiagramNo())){
	        			//another diagramNo means duplicates
     	        		//return an Error ID, so that the user gets noticed
     	        		return new Long(-39);
	        		}
	        	} else if (diagramList.size() > 1) {
	        		//more then one means it has duplicates
	        		//return an Error ID, so that the user gets noticed
 	        		return new Long(-39);
	        	}
	        	return this.diagramDaoImpl.updateDiagramNameById(newName, users_id, diagramId);
	        }
		} catch (Exception err) {
			log.error("[updateDiagramByName]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#deleteDiagramByNo(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public int deleteDiagramByNo(String SID, Long diagramNo, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.diagramDaoImpl.deleteDiagramByNo(users_id, diagramNo, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagramByName]",err);
		}
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#checkForUniqueName(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public Long checkForUniqueName(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<DiagramObject> diagramObjList = this.diagramObjectDaoImpl.
	        		checkDiagramObjectsForUniqueName(organisation_id, objectName, typeOfObject);
	        	
	        	if (diagramObjList.size() == 0) {
	        		return new Long(1);
	        	}
	        	
	        	if (diagramObjectId != null) {
		        	for (DiagramObject diaObject : diagramObjList) {
		        		if (diaObject.getDiagramObjectId() == diagramObjectId.longValue() ) {
		        			return new Long(1);
		        		}
		        	}
	        	}
	        	if (diagramObjList.size() > 0) {
	        		return new Long(-35);
	        	} else {
	        		new Long(1);
	        	}
	        }
		} catch (Exception err) {
			log.error("[checkForUniqueName]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#checkDiagramsByDiagramObjectId(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<Diagram> checkDiagramsByDiagramObjectId(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id, Long language_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<DiagramObject> diagramObjList = this.diagramObjectDaoImpl.
	        		checkDiagramObjectsForUniqueName(organisation_id, objectName, typeOfObject);
	        	
	        	if (diagramObjList.size() == 0) {
	        		return null;
	        	}
	        	
	        	if (diagramObjectId != null) {
		        	for (DiagramObject diaObject : diagramObjList) {
		        		if (diaObject.getDiagramObjectId() == diagramObjectId.longValue() ) {
		        			return null;
		        		}
		        	}
	        	}
	        	if (diagramObjList.size() == 1) {
	        		DiagramObject diaObject = diagramObjList.get(0);
	        		return this.getListOfDiagramsByDiagramObjectId(diaObject.getDiagramObjectId(), organisation_id, language_id);
	        	} else {
	        		log.error("Several Diagram Objects Found "+diagramObjList);
	        	}
	        }
		} catch (Exception err) {
			log.error("[checkForUniqueName]",err);
		}
		return null;
	}
	
	private List<Diagram> getListOfDiagramsByDiagramObjectId(Long diagramObjectId, Long organisation_id, Long language_id) {
		try {
	        	
        	log.debug("diagramObjectId: "+diagramObjectId);
        	log.debug("organisation_id: "+organisation_id);
        	log.debug("language_id: "+language_id);
        	
        	List<DiagramInstanceObject> listDiagramObjects = this.diagramInstanceObjectDaoImpl.
        		getDiagramInstanceObjectListByDiagramObjectId(diagramObjectId);
        	
        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
        	for (DiagramInstanceObject dia : listDiagramObjects) {
        		
        		Diagram returnDia = this.diagramSummaryDaoImpl.getDiagramByNo(dia.getDiagram().getDiagramNo(), organisation_id);
        		
        		//Can be NULL if the returnDia has been deleted
        		if (returnDia != null) {
        			DiagramType dType = returnDia.getDiagramType();
            		dType.setLabel(this.fieldmanagment.
            				getFieldByLabelNumberAndLanguage(dia.getDiagram().getDiagramType().getFieldId(), language_id));
            		returnDia.setDiagramType(dType);
            		
            		diagramReturnList.add(returnDia);
        		} else {
        			log.debug("Diagram Deleted No: "+dia.getDiagram().getDiagramNo()+" Orga: "+organisation_id);
        		}
        	}
        	return diagramReturnList;
	        	
		} catch (Exception err) {
			log.error("[getListOfDiagramsByDiagramObjectId]",err);
		}
		return null;
	}
	
	/**
	 * returns a List of all DiagramObjects of type Issue together with the Assignee
	 * @param SID
	 * @param organization_id
	 * @return
	 */
	private List<DiagramObject> getIssuesByOrganization(String SID, Long organization_id) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	log.debug("organization_id: "+organization_id);
	        	
	        	List<DiagramObject> diagramObjectList = this.diagramObjectDaoImpl.
	        			getDiagramObjectsIssues(organization_id);
	        	
	        	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();
	        	for (DiagramObject diaObject : diagramObjectList) {
	        		diaObject.setIssueassignee(this.issueAssigneeDaoImpl.getIssueAssignee(diaObject.getDiagramObjectId()));
	        		returnDiagramObjectList.add(diaObject);
	        	}
	        	
	        	return returnDiagramObjectList;
	        }
        	
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	/**
	 * returns a List of all DiagramObjects of type Issue together with the Assignee, which are assigned to me
	 * @param SID
	 * @param organization_id
	 * @return
	 */
//	public List<DiagramObject> getIssuesByOrganizationAssignedToMe(String SID, Long organization_id) {
//		try {
//        	
//			Long users_id = this.sessionmanagement.checkSession(SID);
//	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
//	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
//	        	log.debug("organization_id: "+organization_id);
//	        	
//	        	//First get all Diagrams available, for THIS moment
//	        	List<Diagram> diagramList = DiagramServiceDaoImpl.getIns_tance().getDiagramList(organization_id);
//	        	
//	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
//	        	for (Diagram dia : diagramList) {
//	        		Diagram returnDia = DiagramServiceDaoImpl.getI_nstance().getDiagramByNo(dia.getDiagramNo(), organization_id);
//	        		//.. for THIS moment
//	        		diagramReturnList.add(returnDia);
//	        	}
//	        	
//	        	//Return List
//	        	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();
//	        	
//	        	//Get all Issues
//	        	List<DiagramObject> diagramObjectList = DiagramObjectServiceDaoImpl.getInsta_nce().
//	        			getDiagramObjectsIssues(organization_id);
//	        	
//	        	//Check if this Issue is part of the latest Diagram Revision and still up to date
//	        	for (DiagramObject diaObject : diagramObjectList) {
//	        		
//	        		log.debug("DiagramObject NAME: "+diaObject.getName());
//	        		IssueAssignee issueAssignee = this.issueAssigneeDaoImpl.getIssueAssignee(diaObject.getDiagramObjectId());
//	        		
//	        		if (issueAssignee != null) {
//	        			log.debug("issueAssignee Login: "+issueAssignee.getAssignee().getLogin());
//		        		diaObject.setIssueassignee(issueAssignee);
//		        		if (diaObject.getIssueassignee().getAssignee().getUser_id().equals(users_id)) {
//		        			
//		        			DiagramInstanceObject diagramInstanceObjectList = DiagramObjectServiceDaoImpl.getInstan_ce().
//		        			getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
//		        			
//		        			//Only add if this Item is found on the latest/current Diagrams available at this moment
//		        			if (diagramInstanceObjectList != null) {
//	//		        			Diagram diagram = DiagramServiceDaoImpl.getInsta_nce().getDiagramByNo(
//	//		        						diagramInstanceObjectList.get(0).getDiagram().getDiagramNo(), organization_id);
//			        			
//			        			for (Diagram dia : diagramReturnList) {
//			        				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
//			        					//Add the Diagram Object to the return List to open it
//			        					diaObject.setDiagram(dia);
//			        					returnDiagramObjectList.add(diaObject);
//			        				}
//			        			}
//		        			}
//		        			
//		        		}
//	        		}
//	        	}
//	        	
//	        	
//	        	//Get all Assigned Objects (Pending Role at the moment only)
//	        	List<DiagramObject> diagramObjectListPending = DiagramObjectServiceDaoImpl.getI_nstance().
//	        							getDiagramObjectsPendingObjectsAssignedToMe(organization_id, users_id);
//	        	//Check if this Issue is part of the latest Diagram Revision and still up to date
//	        	for (DiagramObject diaObject : diagramObjectListPending) {
//	        		DiagramInstanceObject diagramInstanceObjectList = DiagramObjectServiceDaoImpl.getI_nstance().
//        			getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
//        			
//        			//Only add if this Item is found on the latest/current Diagrams available at this moment
//        			if (diagramInstanceObjectList != null) {
////		        			Diagram diagram = DiagramServiceDaoImpl.getInst_ance().getDiagramByNo(
////		        						diagramInstanceObjectList.get(0).getDiagram().getDiagramNo(), organization_id);
//	        			
//	        			for (Diagram dia : diagramReturnList) {
//	        				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
//	        					//Add the Diagram Object to the return List to open it
//	        					diaObject.setDiagram(dia);
//	        					returnDiagramObjectList.add(diaObject);
//	        				}
//	        			}
//        			}
//	        	}
//	        	
//	        	return returnDiagramObjectList;
//	        }
//	        
//		} catch (Exception err) {
//			log.error("[getIssuesByOrganization]",err);
//		}
//		return null;
//	}
	
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getIssuesByOrganization(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.String, long)
	 */
	public SearchResult getIssuesByOrganization(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search, long ideasType) {
		try {
        	
			Long user_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(user_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	log.debug("orderBy: "+orderBy);
	        	
	        	log.debug("organization_id: "+organization_id);
		    	
	        	List<IssueAssignee> assignees = null;
        		List<DiagramInstanceObject> diagramInstanceObjects = null;
        		Long numberOfRecords = 0L;
        		
	        	if (ideasType == 1) {
		        	assignees = this.issueAssigneeDaoImpl.getIssuesBySearchByAssginee(start, max, orderBy, 
		        										asc, organization_id, search, user_id);
		        	diagramInstanceObjects = this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectsForIssuesByAssignee(start, max, orderBy, 
		        																asc, organization_id, search, user_id);
		        	
		        	numberOfRecords = this.issueAssigneeDaoImpl.getIssuesBySearchMaxByAssignee(start, max, orderBy, asc, organization_id, 
		        							search, user_id);
		        	
	        	} else {
	        		//Get all
		        	assignees = this.issueAssigneeDaoImpl.getIssuesBySearch(start, max, orderBy, 
							asc, organization_id, search);
	        		diagramInstanceObjects = this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectsForIssues(start, max, orderBy, 
													asc, organization_id, search);
	        		numberOfRecords = this.issueAssigneeDaoImpl.getIssuesBySearchMax(start, max, orderBy, asc, organization_id, search);
	        	
	        	}
	        	
	        	List<IssuesExplorerDTO> returnList = new LinkedList<IssuesExplorerDTO>();
	        	for (int i=0;i<assignees.size();i++) {
	        		IssueAssignee issueAssignee = assignees.get(i);
	        		DiagramInstanceObject diagramInstanceObjectRef = null;
	        		//In theory they should have the same index, but its better to verify that by
	        		//iteration
	        		for (DiagramInstanceObject diagramInstanceObject : diagramInstanceObjects) {
	        			//dio.diagramObject.diagramObjectId = c.diagramObject.diagramObjectId
	        			if (diagramInstanceObject.getDiagramObject().getDiagramObjectId() == issueAssignee.getDiagramObject().getDiagramObjectId()) {
	        				diagramInstanceObjectRef = diagramInstanceObject;
	        			}
	        		}
	        		returnList.add(this.morphDiagramObjectToIssuesExplorerDTO(issueAssignee.getDiagramObject(),issueAssignee,diagramInstanceObjectRef));
	        	}
	        	
	        	SearchResult sResult = new SearchResult();
	        	sResult.setObjectName(IssuesExplorerDTO.class.getName());
	        	sResult.setRecords(numberOfRecords);
	        	sResult.setResult(returnList);
	        	
	        	return sResult;
	        }
	        
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getPendingOrganizationAssignedToMe(java.lang.String, java.lang.Long)
	 */
	public SearchResult getPendingOrganizationAssignedToMe(String SID, Long organization_id) {
		try {
        	
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	log.debug("organization_id: "+organization_id);
	        	
	        	log.debug("organization_id: "+organization_id);
		    	
		    	//First get all Diagrams available, for THIS moment
		    	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramList(organization_id);
		    	
		    	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
		    	for (Diagram dia : diagramList) {
		    		Diagram returnDia = this.diagramSummaryDaoImpl.getDiagramByNo(dia.getDiagramNo(), organization_id);
		    		//.. for THIS moment
		    		diagramReturnList.add(returnDia);
		    	}
		    	
		    	//Return List
		    	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();

		    	//Get all Assigned Objects (Pending Role at the moment only)
		    	List<DiagramObject> diagramObjectListPending = this.diagramObjectDaoImpl.
		    							getDiagramObjectsPendingObjectsAssignedToMe(organization_id, users_id);
		    	//Check if this Issue is part of the latest Diagram Revision and still up to date
		    	for (DiagramObject diaObject : diagramObjectListPending) {
		    		DiagramInstanceObject diagramInstanceObjectList = this.diagramInstanceObjectDaoImpl.
									getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
					
					//Always add this Item
					if (diagramInstanceObjectList != null) {
		    			for (Diagram dia : diagramReturnList) {
		    				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
		    					//Add the Diagram to the Diagram Object of the return List to open it
		    					diaObject.setDiagram(dia);
		    				}
		    			}
					}
					
					returnDiagramObjectList.add(diaObject);
		    	}
	        	
	        	LinkedList<IssuesExplorerDTO> returnList = new LinkedList<IssuesExplorerDTO>();
	        	
	        	for (int i=0;i<returnDiagramObjectList.size();i++) {
	        		returnList.add(
	        				this.morphDiagramObjectToIssuesExplorerDTO(returnDiagramObjectList.get(i), null, null)
        				);
	        	}
	        	
	        	SearchResult sResult = new SearchResult();
	        	sResult.setObjectName(IssuesExplorerDTO.class.getName());
	        	sResult.setRecords(Integer.valueOf(returnDiagramObjectList.size()).longValue());
	        	sResult.setResult(returnList);
	        	
	        	return sResult;
	        }
	        
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	private IssuesExplorerDTO morphDiagramObjectToIssuesExplorerDTO(
			DiagramObject diagramObject, IssueAssignee issueAssignee, DiagramInstanceObject diagramInstanceObjectRef) {
		IssuesExplorerDTO issuesExplorerDTO = new IssuesExplorerDTO();
		try {
		
			issuesExplorerDTO.setDiagram(new DiagramMinimalDTO());
			if (diagramInstanceObjectRef != null && diagramInstanceObjectRef.getDiagram() != null) {
				issuesExplorerDTO.getDiagram().setDiagramId(diagramInstanceObjectRef.getDiagram().getDiagramId());
				issuesExplorerDTO.getDiagram().setDiagramNo(diagramInstanceObjectRef.getDiagram().getDiagramNo());
				issuesExplorerDTO.getDiagram().setName(diagramInstanceObjectRef.getDiagram().getName());
			}
			
			issuesExplorerDTO.setDiagramObjectId(diagramObject.getDiagramObjectId());
			issuesExplorerDTO.setInserted(diagramObject.getInserted());
			issuesExplorerDTO.setInsertedby(new UserMinimalDTO());
			issuesExplorerDTO.getInsertedby().setFirstname(diagramObject.getInsertedby().getFirstname());
			issuesExplorerDTO.getInsertedby().setLastname(diagramObject.getInsertedby().getLastname());
			issuesExplorerDTO.getInsertedby().setLogin(diagramObject.getInsertedby().getLogin());
			issuesExplorerDTO.getInsertedby().setUser_id(diagramObject.getInsertedby().getUser_id());
		
			issuesExplorerDTO.setName(diagramObject.getName());
			issuesExplorerDTO.setObjectTypeName(diagramObject.getObjectTypeName());
			
			issuesExplorerDTO.setAssignee(new UserMinimalDTO());
			if (issueAssignee != null && issueAssignee.getAssignee() != null) {
				issuesExplorerDTO.getAssignee().setFirstname(issueAssignee.getAssignee().getFirstname());
				issuesExplorerDTO.getAssignee().setLastname(issueAssignee.getAssignee().getLastname());
				issuesExplorerDTO.getAssignee().setLogin(issueAssignee.getAssignee().getLogin());
				issuesExplorerDTO.getAssignee().setUser_id(issueAssignee.getAssignee().getUser_id());
			}
			
		} catch (Exception err) {
			log.error("[morphDiagramObjectToIssuesExplorerDTO]",err);
		}
		return issuesExplorerDTO;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#approveSingleDiagramObject(java.lang.String, java.lang.Long, boolean)
	 */
	public Long approveSingleDiagramObject(String SID, Long diagramObjectId, boolean approve) {
		try {
        	
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	DiagramObject diagramObject = this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
        		if (diagramObject == null) {
        			return new Long(-46);
        		}
        		
        		Long default_lang_id = Long.valueOf(this.configurationmanagement.getConfKey(3,"default_lang_id").getConf_value()).longValue();
        		
	        	if (approve) {
	        		diagramObject.setPending(false);
	        		diagramObject.setUpdated(new Date());
	        		diagramObject.setUpdatedby(this.userDaoImpl.getUserById(users_id));
	        		//Cannot be NULL!
					if (diagramObject.getAssignee() != null) {
						//Add a mail to the Spool for the Assignee
						this.mailmanagement.addMailToSpoolAboutApprovedPendingObject(users_id, diagramObject.getAssignee().getInsertedby(), 
								"No Diagram", default_lang_id, diagramObject.getName());
					}
	        		diagramObject.setAssignee(null);
	        		this.diagramObjectDaoImpl.updateDiagramObjectByObject(diagramObject);
	        	} else {
	        		
	        		//send Mail to initial creator of the Pending Org. that his Org has been removed
	        		this.mailmanagement.addMailToSpoolAboutRemovedPendingObject(users_id, 
	        				diagramObject.getInsertedby().getUser_id(), 
	        				"No Diagram", default_lang_id, diagramObject.getName());
	        		
	        		this.diagramObjectDaoImpl.deleteDiagramObject(users_id, diagramObjectId);
	        		
	        		
	        	}
	        	
	        }
	        
	        return new Long(1);
	        
		} catch (Exception err) {
			log.error("[approveSingleDiagramObject]",err);
		}
		return new Long(-1);	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getItemToImportMap(java.lang.String, java.lang.Long)
	 */
	public ExportImportJob getItemToImportMap(String SID, Long exportJobId) {
		try {
			return Application.getExportJob(exportJobId);
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectById(java.lang.String, java.lang.Long)
	 */
	public DiagramObject getDiagramObjectById(String SID, Long diagramObjectId) {
		try {

			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectSearchDetailsById(java.lang.String, java.lang.Long)
	 */
	public DiagramObjectSearchDetailedDTO getDiagramObjectSearchDetailsById(String SID, Long diagramObjectId) {
		try {

			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				DiagramObject diaObject = this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
				
				List<DiagramInstanceObject> latestDiaInstanceObjects = this.diagramInstanceObjectDaoImpl.getLatestDiagramInstanceObjectsByObjectId(diagramObjectId, 
																diaObject.getOrganisation().getOrganisation_id(), "c.name", true);
				
				IssueAssignee assignee = this.issueAssigneeDaoImpl.getIssueAssignee(diaObject.getDiagramObjectId());
				
				return this.morphDiagramObjectSearchDetailedDTO(diaObject, assignee, latestDiaInstanceObjects);
				
				//FIXME: Add connected Object through connector and connected Objects through Org.-Object
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	private DiagramObjectSearchDetailedDTO morphDiagramObjectSearchDetailedDTO(
			DiagramObject diaObject, IssueAssignee issueAssignee, 
			List<DiagramInstanceObject> latestDiaInstanceObjects) {
		try {
		
			DiagramObjectSearchDetailedDTO detailedDTO = new DiagramObjectSearchDetailedDTO();
			
			detailedDTO.setDiagramObjectId(diaObject.getDiagramObjectId());
			detailedDTO.setName(diaObject.getName());
			detailedDTO.setComment(diaObject.getComment());
			detailedDTO.setObjectTypeName(diaObject.getObjectTypeName());
			
			if (diaObject.getInserted() != null){
				detailedDTO.setInserted(diaObject.getInserted().toString());
			}
			if (diaObject.getUpdated() != null){
				detailedDTO.setUpdated(diaObject.getUpdated().toString());
			}
			
			if (diaObject.getInsertedby() != null) {
				detailedDTO.setInsertedby(new UserMinimalDTO());
				detailedDTO.getInsertedby().setFirstname(diaObject.getInsertedby().getFirstname());
				detailedDTO.getInsertedby().setLastname(diaObject.getInsertedby().getLastname());
				detailedDTO.getInsertedby().setLogin(diaObject.getInsertedby().getLogin());
				detailedDTO.getInsertedby().setUser_id(diaObject.getInsertedby().getUser_id());
			}
			
			if (diaObject.getUpdatedby() != null) {
				detailedDTO.setUpdatedby(new UserMinimalDTO());
				detailedDTO.getUpdatedby().setFirstname(diaObject.getUpdatedby().getFirstname());
				detailedDTO.getUpdatedby().setLastname(diaObject.getUpdatedby().getLastname());
				detailedDTO.getUpdatedby().setLogin(diaObject.getUpdatedby().getLogin());
				detailedDTO.getUpdatedby().setUser_id(diaObject.getUpdatedby().getUser_id());
			}
			
			detailedDTO.setIspending(diaObject.getPending());
			
			if (issueAssignee != null && issueAssignee.getAssignee() != null) {
				detailedDTO.setAssignee(new UserMinimalDTO());
				detailedDTO.getAssignee().setFirstname(issueAssignee.getAssignee().getFirstname());
				detailedDTO.getAssignee().setLastname(issueAssignee.getAssignee().getLastname());
				detailedDTO.getAssignee().setLogin(issueAssignee.getAssignee().getLogin());
				detailedDTO.getAssignee().setUser_id(issueAssignee.getAssignee().getUser_id());
			}
			
			detailedDTO.setDiagram(new LinkedList<DiagramMinimalDTO>());
			if (latestDiaInstanceObjects != null) {
				
				for (DiagramInstanceObject diaInstance : latestDiaInstanceObjects) {
				
					DiagramMinimalDTO diagramMinimalDTO = new DiagramMinimalDTO();
					diagramMinimalDTO.setDiagramId(diaInstance.getDiagram().getDiagramId());
					diagramMinimalDTO.setDiagramNo(diaInstance.getDiagram().getDiagramNo());
					diagramMinimalDTO.setName(diaInstance.getDiagram().getName());
					diagramMinimalDTO.setDiagramType(diaInstance.getDiagram().getDiagramType().getFieldId());
					diagramMinimalDTO.setRevision(diaInstance.getDiagram().getDiagramrevision().getDiagramrevisionId());
					diagramMinimalDTO.setShortName(diaInstance.getDiagram().getShortName());
					
					ProjectRelation projectRelation = this.projectRelationDaoImpl.getProjectRelationByDiagramNo(diaInstance.getDiagram().getDiagramNo());
					
					if (projectRelation != null) {
						diagramMinimalDTO.setProject(projectRelation.getProject().getName());
					} else {
						diagramMinimalDTO.setProject("");
					}
					
					detailedDTO.getDiagram().add(diagramMinimalDTO);
				}
			}
			
			return detailedDTO;
			
		} catch (Exception err) {
			log.error("[morphDiagramObjectSearchDetailedDTO]",err);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#addDiagramObject(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public Long addDiagramObject(String SID, String name, String objectTypeName, Long organisation_id) {
		try {

			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, objectTypeName, organisation_id, false, null, "", null);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#addPendingDiagramObject(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Boolean)
	 */
	public Long addPendingDiagramObject(String SID, String name, String objectTypeName, Long organisation_id, 
			Long default_lang_id, Long assigneeUserId, Boolean isPending) {
		try {

			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				//Do send mails at this Point!
				String diagramName = "Pending Role added through Registration";
				
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, objectTypeName, organisation_id, isPending, assigneeUserId, diagramName, default_lang_id);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getOrganizationsByObjects(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramObjectOrganisation> getOrganizationsByObjects(String SID, Long diagramObjectId, Long organisation_id) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				return this.diagramObjectOrganisationDaoImpl.
					getOrganisationsObjectsByDiagramObject(diagramObjectId, organisation_id, "c.name", true);
			}

		} catch (Exception err) {
			log.error("getOrganizationsByObjects: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getObjectsByOrganizations(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramObjectOrganisation> getObjectsByOrganizations(String SID, Long diagramObjectId, Long organisation_id) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				return this.diagramObjectOrganisationDaoImpl.
					getObjectsByOrganisationObject(diagramObjectId, organisation_id, "c.name", true);
			}

		} catch (Exception err) {
			log.error("getOrganizationsByObjects: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramInstanceObjectsByObjectType(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(String SID, String objectTypeName, 
			Long organisation_id) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				return this.diagramInstanceObjectDaoImpl.getDiagramInstanceObjectsByObjectType(objectTypeName, 
							organisation_id, "c.name", true);
			}			
			
		} catch (Exception err) {
			log.error("getDiagramInstanceObjectsByObjectType: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramInstanceObjectsByObjectId(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public List<DiagramInstanceConnectedDTO> getDiagramInstanceObjectsByObjectId(String SID, Long diagramObjectId, 
			Long organisation_id) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				List<DiagramInstanceObject> diaInstances = this.diagramInstanceObjectDaoImpl.getLatestDiagramInstanceObjectsByObjectId(diagramObjectId, 
																organisation_id, "c.name", true);
				
				List<DiagramInstanceConnectedDTO> diaConnectedDTO = new LinkedList<DiagramInstanceConnectedDTO>();
				
				for (DiagramInstanceObject diaObject : diaInstances) {
					diaConnectedDTO.add(this.morphDiagramInstanceObjectToDiaConnectedDTO(diaObject));
				}
				
				return diaConnectedDTO;
			}			
			
		} catch (Exception err) {
			log.error("getDiagramInstanceObjectsByObjectId: ",err);
		}
		return null;
	}
	
	private DiagramInstanceConnectedDTO morphDiagramInstanceObjectToDiaConnectedDTO(
			DiagramInstanceObject diaObject) {
		try {
			
			DiagramInstanceConnectedDTO diaConnectedDTO = new DiagramInstanceConnectedDTO();
			
			diaConnectedDTO.setDiagramInstanceObjectId(diaObject.getDiagramInstanceObjectId());
			
			diaConnectedDTO.setDiagram(new DiagramMinimalDTO());
			if (diaObject.getDiagram() != null) {
				diaConnectedDTO.getDiagram().setDiagramId(diaObject.getDiagram().getDiagramId());
				diaConnectedDTO.getDiagram().setDiagramNo(diaObject.getDiagram().getDiagramNo());
				diaConnectedDTO.getDiagram().setName(diaObject.getDiagram().getName());
				diaConnectedDTO.getDiagram().setShortName(diaObject.getDiagram().getShortName());
				diaConnectedDTO.getDiagram().setRevision(diaObject.getDiagram().getRevisionNumber());
			}
			
			diaConnectedDTO.setFlowType(diaObject.getFlowType());
			diaConnectedDTO.setInserted(diaObject.getInserted());
			diaConnectedDTO.setInsertedby(diaObject.getInsertedby());
			
			diaConnectedDTO.setDiagramObject(new DiagramObjectListDTO());
			if (diaObject.getDiagramObject() != null) {
				diaConnectedDTO.getDiagramObject().setDiagramObjectId(diaObject.getDiagramObject().getDiagramObjectId());
				diaConnectedDTO.getDiagramObject().setName(diaObject.getDiagramObject().getName());
				diaConnectedDTO.getDiagramObject().setObjectTypeName(diaObject.getDiagramObject().getObjectTypeName());
				diaConnectedDTO.getDiagramObject().setInserted(diaObject.getDiagramObject().getInserted());
			}
			
			
			return diaConnectedDTO;
			
		} catch (Exception err) {
			log.error("[morphDiagramInstanceObjectToDiaConnectedDTO] ",err);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#addDataCarrier(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public Long addDataCarrier(String SID, String name, Long organisation_id) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, "datacarrier", organisation_id, 
															false, null, "", null);
				
			}			
			
		} catch (Exception err) {
			log.error("addDataCarrier: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#addNonVisualObject(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.util.Vector)
	 */
	public Long addNonVisualObject(String SID, String name, Long organisation_id, 
			String type, String comment, Vector properties) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.addDiagramObjectNonVisualAndProperties(user_id, name, type, organisation_id, 
															false, null, "", null, comment, properties);
				
			}			
			
		} catch (Exception err) {
			log.error("addNonVisualObject: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateNonVisualObject(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.util.Vector)
	 */
	public Long updateNonVisualObject(String SID, Long diagramObjectId, String name, Long organisation_id, 
			String type, String comment, Vector properties) {
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.updateDiagramObjectNonVisualAndProperties(diagramObjectId, 
						user_id, name, type, organisation_id, 
						false, null, "", null, comment, properties);
				
			}			
			
		} catch (Exception err) {
			log.error("addNonVisualObject: ",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateDiagramSummaryIndex(java.lang.String)
	 */
	public void updateDiagramSummaryIndex(String SID){
		try {
			Long user_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(user_id);

			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				this.diagramSummaryDaoImpl.updateDiagramSummaryIndex();
			}
			
		} catch (Exception err) {
			log.error("[updateDiagramSummaryIndex]",err);
		}
	}

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramSearchByProject(java.lang.String, int, int, java.lang.String, boolean, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public SearchResult getDiagramSearchByProject(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search, Long projectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	if (search.equals(null) || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%"+search+"%";
	        	}
	        	
	        	String projectName = "";
	        	List<Diagram> listResult = null;
	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramSearchDTO.class.getName());
	        	
	        	if (projectId.equals(0) || projectId == 0) {
	        		listResult = this.diagramSummaryDaoImpl.
								getDiagramByNoMaxAndOrder(organization_id, orderBy, asc, start, max, search);
					sresult.setRecords(this.diagramSummaryDaoImpl.getMaxDiagram(organization_id, search));
					
	        	} else {
	        		
	        		Project project = this.projectDaoImpl.getProjectById(projectId);
	        		projectName = project.getName();
	        		
	        		listResult = this.diagramSummaryDaoImpl.
	        				getDiagramByNoMaxAndOrderAndProjectId(organization_id, orderBy, 
	        								asc, start, max, search, projectId);
					sresult.setRecords(this.diagramSummaryDaoImpl.getMaxDiagramAndProjectId(
							organization_id, search, projectId));
		
	        		
	        	}
	        	
	        	
	        	
	        	if (listResult != null) {
	        		List<DiagramSearchDTO> returnDTOList = new LinkedList<DiagramSearchDTO>();
	        		for (int i=0;i<listResult.size();i++) {
	        			Diagram diagram = listResult.get(i);
	        			returnDTOList.add(this.morphDiagramToDiagramSearchDTO(diagram, projectId,projectName, false));
	        		}
	        		sresult.setResult(returnDTOList);
	        	}
	        	
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramSearchByProject]",err);
		} 
		return null;
	}
	
	private DiagramSearchDTO morphDiagramToDiagramSearchDTO(Diagram diagram, Long projectId, String projectName, 
			boolean setComment) throws Exception {
		DiagramSearchDTO diagramSearchDTO = new DiagramSearchDTO();
		
		diagramSearchDTO.setDiagramId(diagram.getDiagramId());
		diagramSearchDTO.setDiagramrevision(new DiagramrevisionDTO());
		diagramSearchDTO.getDiagramrevision().setDiagramrevisionId(diagram.getDiagramrevision().getDiagramrevisionId());
		diagramSearchDTO.setDiagramType(new DiagramTypeDTO());
		diagramSearchDTO.getDiagramType().setDiagramTypeId(diagram.getDiagramType().getDiagramTypeId());
		diagramSearchDTO.getDiagramType().setFieldId(diagram.getDiagramType().getFieldId());
		diagramSearchDTO.setInserted(diagram.getInserted());
		diagramSearchDTO.setInsertedby(new UserMinimalDTO());
		diagramSearchDTO.getInsertedby().setFirstname(diagram.getInsertedby().getFirstname());
		diagramSearchDTO.getInsertedby().setLastname(diagram.getInsertedby().getLastname());
		diagramSearchDTO.getInsertedby().setLogin(diagram.getInsertedby().getLogin());
		diagramSearchDTO.getInsertedby().setUser_id(diagram.getInsertedby().getUser_id());
		diagramSearchDTO.setName(diagram.getName());
		diagramSearchDTO.setShortName(diagram.getShortName());
		
		diagramSearchDTO.setProject(new ProjectMinimalDTO());
		if (projectId != null) {
			if (projectId.equals(0) || projectId == 0) {
				
				ProjectRelation pr = this.projectRelationDaoImpl.getProjectRelationByDiagramNo(diagram.getDiagramNo());
				
				if (pr != null) {
					diagramSearchDTO.getProject().setName(pr.getProject().getName());
					diagramSearchDTO.getProject().setProjectId(pr.getProject().getProjectId());
				} else {
					diagramSearchDTO.getProject().setName("");
					diagramSearchDTO.getProject().setProjectId(projectId);
				}
			} else {
				diagramSearchDTO.getProject().setName(projectName);
				diagramSearchDTO.getProject().setProjectId(0);
			}
		}
		
		if (diagram.getParentDiagramObject() != null) {
			diagramSearchDTO.setParentDiagramObject(new DiagramObjectSearchDTO());
			diagramSearchDTO.getParentDiagramObject().setDiagramObjectId(diagram.getParentDiagramObject().getDiagramObjectId());
			diagramSearchDTO.getParentDiagramObject().setName(diagram.getParentDiagramObject().getName());
			diagramSearchDTO.getParentDiagramObject().setObjectTypeName(diagram.getParentDiagramObject().getObjectTypeName());
			diagramSearchDTO.getParentDiagramObject().setPending(diagram.getParentDiagramObject().getPending());
		}
		
		diagramSearchDTO.setDiagramNo(diagram.getDiagramNo());
		
		if (setComment) {
			diagramSearchDTO.setComment(diagram.getDiagramrevision().getComment());
		}

		return diagramSearchDTO;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsLinked(java.lang.String, java.lang.Long)
	 */
	public List<DiagramObjectListDTO> getDiagramObjectsLinked(String SID, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	log.debug("getDiagramObjectsLinked "+diagramObjectId);
				
	        	List<DataCarrierDiagramObject> dCarriers = this.dataCarrierDiagramObjectDaoImpl.getDataCarrierDiagramObjectsByDiagramObject(diagramObjectId);
	        	
	        	List<Map> diaObjectMap = this.diagramObjectDaoImpl.getDiagramObjectsNameById(dCarriers);
				
	        	LinkedList<DiagramObjectListDTO> returnList = new LinkedList<DiagramObjectListDTO>();
	        	
				for (int i=0;i<diaObjectMap.size();i++) {
					Map diaObject = diaObjectMap.get(i);
					
					DiagramObjectListDTO diagramObjectListDTO = new DiagramObjectListDTO();
					
					diagramObjectListDTO.setDiagramObjectId(Long.valueOf(diaObject.get("diagramObjectId").toString()).longValue());
					diagramObjectListDTO.setName(diaObject.get("name").toString());
					diagramObjectListDTO.setObjectTypeName(diaObject.get("objectTypeName").toString());
					
					
					returnList.add(diagramObjectListDTO);
				}
				
				return returnList;
	        }
	        
		} catch (Exception err) {
			log.error("[getDiagramObjectsLinked]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getFlowConnectedDiagramList(java.lang.String, java.util.Vector, java.util.Vector)
	 */
	public List<FlowConnectedDiagramDTO> getFlowConnectedDiagramList(String SID, 
			Vector inputFlowIds, Vector outputFlowIds){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	LinkedList<FlowConnectedDiagramDTO> flowsConnected = new LinkedList<FlowConnectedDiagramDTO>();
	        	
	        	for (int i=0;i<inputFlowIds.size();i++) {
	        		Long diagramObjectId = Long.valueOf(inputFlowIds.get(i).toString()).longValue();
	        		List<Diagram> diagrams = this.diagramDaoImpl.getDiagramSurfAcrossByDiagramObjectIdAndType(diagramObjectId,"outputflow");
	        		
	        		FlowConnectedDiagramDTO flowConnectedDiagram = new FlowConnectedDiagramDTO();
	        		flowConnectedDiagram.setDiagramObjectId(diagramObjectId);
	        		
	        		flowConnectedDiagram.setDiagrams(new LinkedList<DiagramSearchDTO>());
	        		for (Diagram diagram : diagrams) {
	        			flowConnectedDiagram.getDiagrams().add(this.morphDiagramToDiagramSearchDTO(diagram, null, "", false));
	        		}
	        		
	        		flowsConnected.add(flowConnectedDiagram);
	        		
	        	}
				//Map diagramObject = 
				
	        	for (int i=0;i<outputFlowIds.size();i++) {
	        		Long diagramObjectId = Long.valueOf(outputFlowIds.get(i).toString()).longValue();
	        		List<Diagram> diagrams = this.diagramDaoImpl.getDiagramSurfAcrossByDiagramObjectIdAndType(diagramObjectId,"inputflow");
	        		
	        		FlowConnectedDiagramDTO flowConnectedDiagram = new FlowConnectedDiagramDTO();
	        		flowConnectedDiagram.setDiagramObjectId(diagramObjectId);
	        		
	        		flowConnectedDiagram.setDiagrams(new LinkedList<DiagramSearchDTO>());
	        		for (Diagram diagram : diagrams) {
	        			flowConnectedDiagram.getDiagrams().add(this.morphDiagramToDiagramSearchDTO(diagram, null, "", false));
	        		}
	        		
	        		flowsConnected.add(flowConnectedDiagram);
	        		
	        	}
	        	
	        	return flowsConnected;
	        	
	        }
		} catch (Exception err) {
			log.error("[getFlowConnectedDiagramList]",err);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramObjectsFullSearch(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.util.Vector, java.util.Vector, java.lang.Integer)
	 */
	public SearchResult getDiagramObjectsFullSearch(String SID, Long organization_id, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, Vector properties, 
			Integer itemStatus){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
				
//		        	LinkedList<String> objectTypeNames = new LinkedList<String>();
//		        	objectTypeNames.add("departementFixed");
//		        	objectTypeNames.add("unitFixed");
//		        	objectTypeNames.add("companyFixed");
	        	
	        	return this.diagramObjectDaoImpl.selectSearchDiagramObjectsQueryBuilder(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search, properties, itemStatus);

////	        	for (Map diaObject : listResult) {
////	        		log.debug(diaObject);
////	        		log.debug(diaObject.get("name"));
////	        	}
//	        	for (Object[] item : listResult) {
////	        		Object inserted = item.get("inserted");
////	        		item.put("inserted", inserted.toString());
//	        		//log.debug("inserted: "+inserted+" Class: "+inserted.getClass().getName());
//	        	}
//	        	
//	        	SearchResult sresult = new SearchResult();
//	        	sresult.setObjectName(DiagramObject.class.getName());
//	        	sresult.setRecords(this.diagramObjectDaoImpl.
//	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, null));
//	        	
//	        	sresult.setResult(listResult);
				
				//return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsFastPath]",err);
		}
        return null;
	} 
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#saveSearchFilter(java.lang.String, java.lang.Long, int, int, java.util.Vector, java.lang.String, boolean, java.util.Vector, java.util.Vector, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Long saveSearchFilter(String SID, Long organization_id,  int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, 
			Vector properties, Integer itemStatus, String sql, String name, String description) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	SearchFilterParam searchFilterParam = new SearchFilterParam();
	        	searchFilterParam.setSearch(search);
	        	searchFilterParam.setStart(start);
	        	searchFilterParam.setAsc(asc);
	        	searchFilterParam.setItemStatus(itemStatus);
	        	searchFilterParam.setMax(max);
	        	searchFilterParam.setObjectTypeNames(objectTypeNames);
	        	searchFilterParam.setOrderBy(orderBy);
	        	searchFilterParam.setProperties(properties);
	        	
	        	XStream xStream = new XStream(new XppDriver());
    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
	        	
    			String paramsAsXML = xStream.toXML(searchFilterParam);
    			
    			return this.searchQueryDaoImpl.addSearchQuery(sql, paramsAsXML, users_id, organization_id, name, description);
	        	
	        }
		} catch (Exception err) {
			log.error("[saveSearchFilter]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#updateSearchFilter(java.lang.Long, java.lang.String, int, int, java.util.Vector, java.lang.String, boolean, java.util.Vector, java.util.Vector, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Long updateSearchFilter(Long searchQueryId, String SID, int start, int max, 
			Vector objectTypeNames, String orderBy, boolean asc, Vector search, 
			Vector properties, Integer itemStatus, String sql, String name, String description) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	SearchFilterParam searchFilterParam = new SearchFilterParam();
	        	searchFilterParam.setSearch(search);
	        	searchFilterParam.setStart(start);
	        	searchFilterParam.setAsc(asc);
	        	searchFilterParam.setItemStatus(itemStatus);
	        	searchFilterParam.setMax(max);
	        	searchFilterParam.setObjectTypeNames(objectTypeNames);
	        	searchFilterParam.setOrderBy(orderBy);
	        	searchFilterParam.setProperties(properties);
	        	
	        	XStream xStream = new XStream(new XppDriver());
    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
	        	
    			String paramsAsXML = xStream.toXML(searchFilterParam);
    			
    			return this.searchQueryDaoImpl.updateSearchQuery(searchQueryId, sql, paramsAsXML, users_id);
	        	
	        }
		} catch (Exception err) {
			log.error("[saveSearchFilter]",err);
		}
        return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#deleteSearchFilter(java.lang.String, java.lang.Long)
	 */
	public Long deleteSearchFilter(String SID, Long searchQueryId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	return this.searchQueryDaoImpl.deleteSearchQuery(searchQueryId, users_id);
	        }
		} catch (Exception err) {
			log.error("[saveSearchFilter]",err);
		}
        return null;	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getMySearchFilter(java.lang.String, java.lang.Long)
	 */
	public List<SearchQuery> getMySearchFilter(String SID, Long organization_id){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<SearchQuery> queryList = this.searchQueryDaoImpl.getSearchQueryByUserAndOrganization(users_id, organization_id);
	        	
	        	for (SearchQuery sQuery : queryList) {
	        		sQuery.setParamsAsXML("");
	        	}
	        	
	        	return queryList;
	        	
	        }
		} catch (Exception err) {
			log.error("[getMySearchFilter]",err);
		}
        return null;	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getSearchFilter(java.lang.String, java.lang.Long)
	 */
	public List<SearchQuery> getSearchFilter(String SID, Long organization_id){
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	List<SearchQuery> queryList = this.searchQueryDaoImpl.getSearchQueryByOrganization(organization_id);
	        	
	        	for (SearchQuery sQuery : queryList) {
	        		sQuery.setParamsAsXML("");
	        	}
	        	
	        	return queryList;
	        }
		} catch (Exception err) {
			log.error("[getMySearchFilter]",err);
		}
        return null;	        
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getSearchFilterById(java.lang.String, java.lang.Long)
	 */
	public SearchQuery getSearchFilterById(String SID, Long searchQueryId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	SearchQuery sQuery = this.searchQueryDaoImpl.getSearchQueryById(searchQueryId);
	        	
	        	XStream xStream = new XStream(new XppDriver());
    			xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
	        	
    			SearchFilterParam searchFilterParam = (SearchFilterParam) xStream.fromXML(sQuery.getParamsAsXML());
	        	
    			sQuery.setSParam(searchFilterParam);
    			
	        	return sQuery;
	        }
		} catch (Exception err) {
			log.error("[getSearchFilterById]",err);
		}
        return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#getDiagramDocumentByObject(java.lang.String, java.lang.Long)
	 */
	public List<DiagramDocument> getDiagramDocumentByObject(String SID, Long diagramObjectId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        
	        	return this.diagramDocumentDaoImpl.getDiagramDocumentByDiagramObjectId(diagramObjectId);
	        	
	        }
		} catch (Exception err) {
			log.error("[getDiagramDocumentByObject]",err);
		}
	    return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#saveDiagramDocumentByObject(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	public Long saveDiagramDocumentByObject(String SID, Long diagramObjectId, String fileName, String fileIconName,
			String fileHash, Boolean isInternal, String url) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        
	        	return this.diagramDocumentDaoImpl.addDiagramDocument(fileName, fileIconName, fileHash, 
	        			diagramObjectId, users_id, isInternal, url);
	        	
	        }
		} catch (Exception err) {
			log.error("[getDiagramDocumentByObject]",err);
		}
	    return -1L;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.i4change.app.remote.IDiagramservice#deleteDiagramDocumentByObject(java.lang.String, java.lang.Long)
	 */
	public Long deleteDiagramDocumentByObject(String SID, Long diagramDocumentId) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        
	        	return this.diagramDocumentDaoImpl.deleteDiagramDocument(diagramDocumentId, users_id);
	        	
	        }
		} catch (Exception err) {
			log.error("[getDiagramDocumentByObject]",err);
		}
	    return -1L;
	}
	
	
}
