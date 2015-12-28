package org.i4change.app.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Configurationmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Mailmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.diagram.DiagramInstanceObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectOrganisationDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectDaoImpl;
import org.i4change.app.data.diagram.DiagramDaoImpl;
import org.i4change.app.data.diagram.DiagramObjectPropertyDaoImpl;
import org.i4change.app.data.diagram.IssueAssigneeDaoImpl;
import org.i4change.app.data.project.daos.ProjectRelationDaoImpl;
import org.i4change.app.data.user.Usermanagement;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.DiagramType;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.i4change.app.hibernate.beans.project.ProjectRelation;
import org.i4change.app.session.beans.RoomClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * @author sebastianwagner
 *
 */
public class DiagramService {
	
	private static final Log log = LogFactory.getLog(DiagramService.class);
	
	private static DiagramService instance = null;
	
	private Application application;
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	private DiagramDaoImpl diagramDaoImpl;
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private ProjectRelationDaoImpl projectRelationDaoImpl;

	public static synchronized DiagramService getInstance() {
		if (instance == null) {
			instance = new DiagramService();
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

	/**
	 * 
	 * @param SID
	 * @param diagramName
	 * @param revisionComment
	 * @param diagramMapObj
	 * @param organisation_id
	 * @param diagramType
	 * @param diagramNo
	 * @param read
	 * @param write
	 * @param onlyIssues
	 * @param users
	 * @param language_id
	 * @param currentRevsionId
	 * @param forceUpdate to overwrite an old Revision this must be set to true
	 * @param diagramObjectId the ID of the Parent Object (can only happen if this Diagram is created by Drill-Down/Explode)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long updateDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write, 
			boolean onlyIssues, Object users, Long language_id, long currentRevsionId,
			boolean forceUpdate, Long diagramObjectId, Long projectId){
		try {
			
			//A reference for the current Objects to lookup the DiagramObjectInstanceId and fix the 
			//Reference to the underlying Org-Object
			Map<String,Long> currentObjectsMap = new HashMap<String,Long>();
			
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	log.debug("updateDiagram diagramMapObj ClassName: "+diagramMapObj.getClass().getName());
	        	
	        	Map diagramMap = (Map) diagramMapObj;
	        	log.debug("diagramName: "+diagramName);
	        	log.debug("revisionComment: "+revisionComment);
	        	log.debug("diagramNo: "+diagramNo);
	        	
	        	Diagram diagram = this.diagramDaoImpl.getDiagramByNo(diagramNo, organisation_id);
	        	
	        	if (projectId != null && projectId != 0) {
	        		ProjectRelation projectRelation = this.projectRelationDaoImpl.getProjectRelationByIdAndDiagramNo(projectId, diagramNo);
	        		if (projectRelation == null) {
	        			this.projectRelationDaoImpl.addProjectRelation(projectId, diagramNo, null);
	        		}
	        	}
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	
	        	if (diagramList.size() == 1) {
	        		if (!diagramList.get(0).getDiagramNo().equals(diagramNo)){
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
	        	if (diagram.getDiagramrevision().getDiagramrevisionId() == currentRevsionId || forceUpdate) {
	        	
		        	diagram = this.diagramDaoImpl.updateDiagramByNo(diagramName, users_id, diagramNo, 
		        			revisionComment, organisation_id, diagramType, read, write, onlyIssues, users, 
		        			diagramObjectId);
		        	
		        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        		Object key = iter.next();
		        		Map whiteBoardItem = (Map) diagramMap.get(key);
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
		        			
			    			XStream xStream = new XStream(new XppDriver());
			    			xStream.setMode(XStream.NO_REFERENCES);
			    			String xmlString = xStream.toXML(whiteBoardItem);	
			    			
			    			String flowType = typeOfObject;
			    			if (typeOfObject.equals("inputflow") || typeOfObject.equals("outputflow")) {
			    				typeOfObject = "flow";
			    			}
			    			
			    			Map dataCarrierList = this.getDataCarrierObject(whiteBoardItem, typeOfObject, diagramType);
			    			
			    			Map propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
			    			
			    			Boolean isPending = Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
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
			    			
			    			//set the diagramInstanceObjectId, to read it later on to set the reference to the underlying Org-Object
			    			currentObjectsMap.put(objInternalUID, diagramInstanceObjectId);
			    			
			    			//If its a new Item, reflect the DiagramObjectId to the XML-Graph
			    			if (diagramobjectId == null) {
			    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
			    					getDiagramInstanceObjectById(diagramInstanceObjectId);
			    				diagramobjectId = diagramInstance.getDiagramObject().getDiagramObjectId();
			    				whiteBoardItem.put(whiteBoardItem.size()-7,diagramobjectId);
			    				String xmlString_2 = xStream.toXML(whiteBoardItem);
			    				diagramInstance.setGraphObject(xmlString_2);
			    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
			    				
			    				//Only do this for new Issue's
			    				if (typeOfObject.equals("issueflow")) {
				    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
				    				IssueAssigneeDaoImpl.getInstance().addIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);
				    			}
			    			} else {
			    				//Update Assignee
			    				if (typeOfObject.equals("issueflow")) {
				    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
				    				IssueAssigneeDaoImpl.getInstance().saveOrUpdateIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);
				    			}
			    			}
			    				    			
			    			
			    			log.debug("Set Incomplete: "+whiteBoardItem);
			    			log.debug("SET diagramInstanceObjectId: "+diagramInstanceObjectId);
			    			whiteBoardItem.put(whiteBoardItem.size()-6, diagramInstanceObjectId);
			    			
			    			log.debug("Set Complete: "+whiteBoardItem);
			    			diagramMap.put(key, whiteBoardItem);
		        		}
		        	}
		        	
		        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        		Map whiteBoardItem = (Map) diagramMap.get(iter.next());
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
		        			
			    			XStream xStream = new XStream(new XppDriver());
			    			xStream.setMode(XStream.NO_REFERENCES);
			    			String xmlString = xStream.toXML(whiteBoardItem);	
			    			
			    			Map propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
			    			
			    			//log.debug("xmlString: "+xmlString);
			    			//TODO: Check if the connection between these object does already exist
			    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
										    					typeOfObject, users_id, organisation_id, diagram, objName, 
										    					diagramobjectStart_id, diagramobjectEnd_id, xmlString, objInternalUID, 
										    					false, null, diagramName, language_id, "", null, propertyMap);
			    			
			    			if (diagramobjectId == null) {
			    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
			    					getDiagramInstanceObjectById(diagramInstanceObjectId);
			    				whiteBoardItem.put(whiteBoardItem.size()-7,diagramInstance.getDiagramObject().getDiagramObjectId());
			    				String xmlString_2 = xStream.toXML(whiteBoardItem);
			    				diagramInstance.setGraphObject(xmlString_2);
			    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
			    			}
		        			
		        		}
		        	}
		        	
		        	//#########################################
		        	//check for Underlying Roles for the Object
		        	log.debug("check for Underlying Roles for the Object: "+diagramType);
		        	if (diagramType == 2 || diagramType == 4) {
		        		
		        		log.debug("Underlying diagramMap SIZE: "+diagramMap.size());
		        		
		        		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
		        			
		        			Map whiteBoardItem = (Map) diagramMap.get(iter.next());
			        		log.debug("Underlying whiteBoardItem: "+whiteBoardItem);
			        		String typeOfObject = whiteBoardItem.get(0).toString();
			        		log.debug("Underlying typeOfObject: "+typeOfObject);
			        		
			        		String objInternalMainUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
			        		
		        			if (typeOfObject.equals("processtree")
		        					|| typeOfObject.equals("activity")
		        					|| typeOfObject.equals("inputflow")
		        					|| typeOfObject.equals("outputflow")){
		        				
		        				Map connectList = (Map) whiteBoardItem.get(whiteBoardItem.size()-9);
		        				
		        				log.debug("Underlying connectList: "+connectList);
		        				
		        				if (connectList != null) {
		        					
		        					//If we find any Item with a 0 as connected Org-Object we have to fix that
		        					boolean foundOrgWithEmptyReference = false;
		        					
		        					for (Iterator connectListiter = connectList.keySet().iterator();connectListiter.hasNext();) {
		        						Object key = connectListiter.next();
		        						log.debug("key: "+key);
		        						Map connectItem = (Map) connectList.get(key);
		        						
		        						String objInternalUID = connectItem.get(0).toString();
		        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
		        						
		        						log.debug("Underlying diagramobjectId: "+diagramobjectId);
		    	        				
		        						if (diagramobjectId == null || diagramobjectId == 0) {
		        							foundOrgWithEmptyReference = true;
		        							diagramobjectId = this.getDiagramObjectIdByCurrentMap(diagramMap, objInternalUID);
		        							connectItem.put(1, diagramobjectId);
		        						}
		        						
		        						connectList.put(key, connectItem);
		        						
		        					}
		        					
		        					Long diagramInstanceObjectId = currentObjectsMap.get(objInternalMainUID);
		        					
		        					log.debug("Underlying diagramInstanceObjectId: "+diagramInstanceObjectId);
		        					
		        					whiteBoardItem.put(whiteBoardItem.size()-9, connectList);
		        					
		        					//Do fill the Items Graph-XMLRef
		        					if (foundOrgWithEmptyReference) {
			        					//Update Reference to point to correct DiagramObject Id
					    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
					    					getDiagramInstanceObjectById(diagramInstanceObjectId);
					    				
					    				XStream xStream = new XStream(new XppDriver());
						    			xStream.setMode(XStream.NO_REFERENCES);
					    				String xmlString_2 = xStream.toXML(whiteBoardItem);
					    				diagramInstance.setGraphObject(xmlString_2);
					    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
		        					}
		        					
		        					//save Connection Object
		        					for (Iterator connectListiter = connectList.keySet().iterator();connectListiter.hasNext();) {
		        						Object key = connectListiter.next();
		        						log.debug("key: "+key);
		        						Map connectItem = (Map) connectList.get(key);
		        						
		        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
		        						
		        						DiagramObjectOrganisationDaoImpl.getInstance().addDiagramObjectOrganisation(diagramInstanceObjectId, diagramobjectId, users_id);
		        						
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
		        	
		        	List<DiagramInstanceObject> previousDiagramObjectList = DiagramInstanceObjectDaoImpl.getInstance().
								getDiagramInstanceObjectPendingListByDiagram(previous.getDiagramId());
		        	
		        	List<DiagramInstanceObject> currentDiagramObjectList = DiagramInstanceObjectDaoImpl.getInstance().
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
		        			Mailmanagement.getInstance().addMailToSpoolAboutRemovedPendingObject(users_id, 
		        					preInstance.getDiagramObject().getInsertedby().getUser_id(), 
		        					diagramName, language_id, preInstance.getDiagramObject().getName());
		        			
		        			this.diagramObjectDaoImpl.deleteDiagramObject(users_id, 
		        					preInstance.getDiagramObject().getDiagramObjectId());
		        			
		        		}
		        		
		        	}
		        	
		        	IConnection current = Red5.getConnectionLocal();
		        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
		        	
		        	Map message = new HashMap();
		        	message.put("type", "updateDiagram");
		        	message.put("diagram", diagram);
		        	
		        	log.debug("send Update to all clients of the same Org: "+currentClient.getOrganizationId());
		        	this.application.sendMessageWithClientByOrganizationId(message, currentClient.getOrganizationId());
		        	
		        	return diagram.getDiagramId();
		        	
	        	} else {
	        		//return an Error ID, so that the user gets noticed that a newer Revision does exist
	        		return new Long(-36);
	        	}
	        	
	        	
	        } else {
	        	return new Long(-2);
	        }
		} catch (Exception err) {
			log.error("[updateDiagram]",err);
		}
        return new Long(-1);
	}
	
	@SuppressWarnings("unchecked")
	private Map getDataCarrierObject (Map whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			
			log.debug("getDataCarrierObject whiteBoardItem: "+whiteBoardItem);
			log.debug("getDataCarrierObject typeOfObject: "+typeOfObject);
			log.debug("getDataCarrierObject diagramType: "+diagramType);
			
			//Add DataCarrier
			Map dataCarrierList = null;
			if (typeOfObject.equals("flow")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-12);
				} else {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-9);
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
				} else {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-9);
				}
			} else if (typeOfObject.equals("activity")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
			} else if (typeOfObject.equals("processgroup")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-9);
			}	
			return dataCarrierList;
			
		} catch (Exception err) {
			log.error("[getDataCarrierObject]",err);
		}
		
		return null;
		
	}
	
	private Map getPropertyObject (Map whiteBoardItem, String typeOfObject, Long diagramType) {
		try {
			//Add DataCarrier
			Map dataCarrierList = null;
			if (typeOfObject.equals("flow")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-11);
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-13);
				} else {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-11);
				} else {
					dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
				}
			} else if (typeOfObject.equals("activity")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-11);
			} else if (typeOfObject.equals("processgroup")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
			} else if (typeOfObject.equals("connector")) {
				dataCarrierList = (Map) whiteBoardItem.get(whiteBoardItem.size()-10);
			}	
			return dataCarrierList;
		} catch (Exception err) {
			log.error("[getPropertyObject]",err);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private Map setPropertyObject (Map whiteBoardItem, String typeOfObject, Long diagramType, Map propertyMap) {
		try {
			//Add DataCarrier
			if (typeOfObject.equals("flow")) {
				whiteBoardItem.put(whiteBoardItem.size()-11,propertyMap);
			} else if (typeOfObject.equals("unitFixed")
					 || typeOfObject.equals("departementFixed")
					 || typeOfObject.equals("companyFixed")) {
				if (diagramType == 2 || diagramType == 4) {
					whiteBoardItem.put(whiteBoardItem.size()-13,propertyMap);
				} else {
					whiteBoardItem.put(whiteBoardItem.size()-10,propertyMap);
				}
			} else if (typeOfObject.equals("processtree")) {
				if (diagramType == 2 || diagramType == 4) {
					whiteBoardItem.put(whiteBoardItem.size()-11,propertyMap);
				} else {
					whiteBoardItem.put(whiteBoardItem.size()-10,propertyMap);
				}
			} else if (typeOfObject.equals("activity")) {
				whiteBoardItem.put(whiteBoardItem.size()-11,propertyMap);
			} else if (typeOfObject.equals("processgroup")) {
				whiteBoardItem.put(whiteBoardItem.size()-10,propertyMap);
			} else if (typeOfObject.equals("connector")) {
				whiteBoardItem.put(whiteBoardItem.size()-10,propertyMap);
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
			Map whiteBoardItem = (Map) diagramMap.get(key);
			log.debug("whiteBoardItem"+whiteBoardItem);
			String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
			
			log.debug("objInternalUID"+objInternalUID);
			
			if (objInternalUID.equals(unique_key)) {
				
				log.debug("Found whiteBoardItem"+whiteBoardItem);
				Long diagramInstanceObjectId = (Long) whiteBoardItem.get(whiteBoardItem.size()-6);
				log.debug("Found diagramInstanceObjectId"+diagramInstanceObjectId);
				return DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectById(diagramInstanceObjectId);
			}
			
		}
		
		return null;
	}
	
	/**
	 * get Diagram Drilled Down by DiagramObjectIdAndType
	 * 
	 * @param SID
	 * @param organisation_id
	 * @param diagramobjectId
	 * @return the Latest Diagram-Version Object
	 */
	public Diagram getDiagramDrillDownByDiagramObjectIdAndType(String SID, Long organisation_id, Long diagramObjectId) {
		try {
			
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.diagramDaoImpl.getDiagramByNoAndParentObjectAndType(organisation_id, diagramObjectId);
	        	
	        } else {
	        	return null;
	        }
		} catch (Exception err) {
			log.error("[getDiagramDrillDownByDiagramObjectIdAndType]",err);
		}
        return null;
	}
	
	@SuppressWarnings("unchecked")
	public Long saveNewDiagram(String SID, String diagramName, 
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues, 
			Object users, Long language_id, Long diagramObjectId, Long projectId){
		try {
			//A reference for the current Objects to lookup the DiagramObjectInstanceId and fix the 
			//Reference to the underlying Org-Object
			Map<String,Long> currentObjectsMap = new HashMap<String,Long>();
			
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(diagramName, organisation_id);
	        	
	        	if (diagramList.size() > 0) {
	        		//more then zero means it has duplicates
	        		//return an Error ID, so that the user gets noticed
 	        		return new Long(-39);
	        	}
	        	
	        	
	        	
	        	Map diagramMap = (Map) diagramMapObj;
	        	log.debug("diagramMap: "+diagramMap);
	        	log.debug("diagramMap SIZE: "+diagramMap.size());
	        	log.debug("diagramName: "+diagramName);
	        	log.debug("revisionComment: "+revisionComment);
	        	
	        	//iagram diagram = DiagramServiceDaoImpl.getInstance().addNewDiagramByName(diagramName, users_id, revisionComment, 
	        	//			organisation_id, diagramType, read, write, onlyIssues, users);
	        	
	        	Diagram diagram = this.diagramDaoImpl.addNewDiagramByName(diagramName, users_id, 
	        			revisionComment, organisation_id, diagramType, 
	        			read, write, onlyIssues, users, diagramObjectId);
	        	
	        	//auto create orphan Objects
	        	//See: http://jira.webbase-design.de/browse/ICH-369
	        	if (diagramObjectId == null) {
	        		//if diagramType
	        	}
	        	
	        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
	        		Object key = iter.next();
	        		log.debug("key: "+key);
	        		Map whiteBoardItem = (Map) diagramMap.get(key);
	        		log.debug("whiteBoardItem: "+whiteBoardItem);
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
	        			
		    			XStream xStream = new XStream(new XppDriver());
		    			xStream.setMode(XStream.NO_REFERENCES);
		    			String xmlString = xStream.toXML(whiteBoardItem);	
		    			
		    			String flowType = typeOfObject;
		    			if (typeOfObject.equals("inputflow") || typeOfObject.equals("outputflow")) {
		    				typeOfObject = "flow";
		    			}
		    			
		    			Map dataCarrierList = this.getDataCarrierObject(whiteBoardItem, typeOfObject, diagramType);
		    			
		    			log.debug("dataCarrierList: "+dataCarrierList);
		    			
		    			Boolean isPending = Boolean.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).booleanValue();
		    			
		    			log.debug("isPending: "+isPending);
		        		
		    			Long assigneeUserId = null;
		    			//If the Item is of Status Pending then we need to link to get the Assignee Name/ID
	    				if (isPending){
	    					assigneeUserId = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-10).toString()).longValue();
	    				}
	    				
	    				log.debug("assigneeUserId: "+assigneeUserId);
	    				
	    				Map propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
	    				
	    				log.debug("propertyMap: "+propertyMap);
	    				
		    			//log.debug("xmlString: "+xmlString);
		    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
									    					typeOfObject, users_id, organisation_id, 
									    					diagram, objName, null, null, xmlString, objInternalUID,
									    					isPending, assigneeUserId, diagramName, language_id, flowType, dataCarrierList,
									    					propertyMap);
		    			
		    			log.debug("diagramInstanceObjectId: "+diagramInstanceObjectId);

		    			//set the diagramInstanceObjectId, to read it later on to set the reference to the underlying Org-Object
		    			currentObjectsMap.put(objInternalUID, diagramInstanceObjectId);
		    			
		    			
		    			
		    			if (diagramobjectId == null) {
		    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
		    					getDiagramInstanceObjectById(diagramInstanceObjectId);
		    				diagramobjectId = diagramInstance.getDiagramObject().getDiagramObjectId();
		    				whiteBoardItem.put(whiteBoardItem.size()-7,diagramobjectId);
		    				String xmlString_2 = xStream.toXML(whiteBoardItem);
		    				diagramInstance.setGraphObject(xmlString_2);
		    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
		    				
		    				//Only do this for new Issue's
		    				if (typeOfObject.equals("issueflow")) {
			    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
			    				IssueAssigneeDaoImpl.getInstance().addIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);	
			    			}
		    				
		    				
		    			} else {
		    				//Update Assignee
		    				if (typeOfObject.equals("issueflow")) {
			    				Long assigneeUser_id = Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-9).toString()).longValue();
			    				IssueAssigneeDaoImpl.getInstance().saveOrUpdateIssueAssignee(diagramobjectId, assigneeUser_id, users_id, diagramName, language_id);	
			    			}
		    			}
		    			
		    			log.debug("Set Incomplete: "+whiteBoardItem);
		    			log.debug("SET diagramInstanceObjectId: "+diagramInstanceObjectId);
		    			whiteBoardItem.put(whiteBoardItem.size()-6, diagramInstanceObjectId);
		    			
		    			log.debug("Set Complete: "+whiteBoardItem);
		    			diagramMap.put(key, whiteBoardItem);
	        		}
	        	}
	        	
	        	log.debug("First Iteration done");
	        	
	        	for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
	        		Map whiteBoardItem = (Map) diagramMap.get(iter.next());
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
	        			//log.debug("diagramobjectId: "+diagramobjectId);
	        			//log.debug("objInternalUID: "+objInternalUID);
	        			
		    			XStream xStream = new XStream(new XppDriver());
		    			xStream.setMode(XStream.NO_REFERENCES);
		    			String xmlString = xStream.toXML(whiteBoardItem);	
		    			
		    			Map propertyMap = this.getPropertyObject(whiteBoardItem, typeOfObject, diagramType);
		        		
		    			//log.debug("xmlString: "+xmlString);
		    			//Connectors are never pending
		    			//TODO: Check if the connection between these object does already exist
		    			Long diagramInstanceObjectId = this.diagramObjectDaoImpl.saveDiagramInstanceObject(diagramobjectId, 
									    					typeOfObject, users_id, organisation_id, diagram, objName, 
									    					diagramobjectStart_id, diagramobjectEnd_id, xmlString, objInternalUID, 
									    					false, null, diagramName, language_id, "", null, propertyMap);
		    			
		    			if (diagramobjectId == null) {
		    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
		    					getDiagramInstanceObjectById(diagramInstanceObjectId);
		    				whiteBoardItem.put(whiteBoardItem.size()-7,diagramInstance.getDiagramObject().getDiagramObjectId());
		    				String xmlString_2 = xStream.toXML(whiteBoardItem);
		    				diagramInstance.setGraphObject(xmlString_2);
		    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
		    			}
	        			
	        		}
	        	}
	        	
	        	//check for Underlying Roles for the Object
	        	log.debug("check for Underlying Roles for the Object: "+diagramType);
	        	if (diagramType == 2 || diagramType == 4) {
	        		
	        		log.debug("Underlying diagramMap SIZE: "+diagramMap.size());
	        		
	        		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
	        			
	        			Map whiteBoardItem = (Map) diagramMap.get(iter.next());
		        		String typeOfObject = whiteBoardItem.get(0).toString();
		        		
		        		String objInternalMainUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
		        		
	        			if (typeOfObject.equals("processtree")
	        					|| typeOfObject.equals("activity")
	        					|| typeOfObject.equals("inputflow")
	        					|| typeOfObject.equals("outputflow")){
	        				
	        				Map connectList = (Map) whiteBoardItem.get(whiteBoardItem.size()-9);
	        				if (connectList != null) {
	        					
	        					//If we find any Item with a 0 as connected Org-Object we have to fix that
	        					boolean foundOrgWithEmptyReference = false;
	        					
	        					for (Iterator connectListiter = connectList.keySet().iterator();connectListiter.hasNext();) {
	        						Object key = connectListiter.next();
	        						Map connectItem = (Map) connectList.get(key);
	        						
	        						String objInternalUID = connectItem.get(0).toString();
	        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
	        						//log.debug("Underlying diagramobjectId: "+diagramobjectId);
	    	        				
	        						if (diagramobjectId == null || diagramobjectId == 0) {
	        							foundOrgWithEmptyReference = true;
	        							diagramobjectId = this.getDiagramObjectIdByCurrentMap(diagramMap, objInternalUID);
	        							connectItem.put(1, diagramobjectId);
	        						}
	        						connectList.put(key, connectItem);
	        					}
	        					
	        					Long diagramInstanceObjectId = currentObjectsMap.get(objInternalMainUID);
	        					//log.debug("Underlying diagramInstanceObjectId: "+diagramInstanceObjectId);
	        					
	        					whiteBoardItem.put(whiteBoardItem.size()-9, connectList);
	        					
	        					//Do fill the Items Graph-XMLRef
	        					if (foundOrgWithEmptyReference) {
		        					//Update Reference to point to correct DiagramObject Id
				    				DiagramInstanceObject diagramInstance = DiagramInstanceObjectDaoImpl.getInstance().
				    					getDiagramInstanceObjectById(diagramInstanceObjectId);
				    				
				    				XStream xStream = new XStream(new XppDriver());
					    			xStream.setMode(XStream.NO_REFERENCES);
				    				String xmlString_2 = xStream.toXML(whiteBoardItem);
				    				diagramInstance.setGraphObject(xmlString_2);
				    				DiagramInstanceObjectDaoImpl.getInstance().updateDiagramInstanceObject(diagramInstance);
	        					}
	        					
	        					//save Connection Object
	        					for (Iterator connectListiter = connectList.keySet().iterator();connectListiter.hasNext();) {
	        						Object key = connectListiter.next();
	        						log.debug("key: "+key);
	        						Map connectItem = (Map) connectList.get(key);
	        						Long diagramobjectId = Long.valueOf(connectItem.get(1).toString()).longValue();
	        						DiagramObjectOrganisationDaoImpl.getInstance().
	        							addDiagramObjectOrganisation(diagramInstanceObjectId, diagramobjectId, users_id);
	        						
	        					}

	        				}
	        				
	        			}
	        			
	        		}
	        		
	        	}
	        	
	        	IConnection current = Red5.getConnectionLocal();
	        	RoomClient currentClient = Application.getClientList().get(current.getClient().getId());
	        	
	        	Map message = new HashMap();
	        	message.put("type", "addDiagram");
	        	message.put("diagram", diagram);
	        	
	        	this.application.sendMessageWithClientByOrganizationId(message, currentClient.getOrganizationId());
	        	
	        	//Add Diagram to Projects
	        	if (projectId != null && projectId!=0) {
        			this.projectRelationDaoImpl.addProjectRelation(projectId, diagram.getDiagramNo(), null);
	        	}
	        	
	        	return diagram.getDiagramId();
	        	
	        } else {
	        	return new Long(-2);
	        }
		} catch (Exception err) {
			log.error("[saveNewDiagram]",err);
		}
        return new Long(-1);
	}
	
	
	@SuppressWarnings("unchecked")
	private Long getDiagramObjectIdByCurrentMap(Map diagramMap, String searchObjInternalUID) throws Exception {
		for (Iterator iter = diagramMap.keySet().iterator();iter.hasNext();) {
    		Object key = iter.next();
    		log.debug("key: "+key);
    		Map whiteBoardItem = (Map) diagramMap.get(key);
    		log.debug("whiteBoardItem: "+whiteBoardItem);
    		String objInternalUID = whiteBoardItem.get(whiteBoardItem.size()-1).toString();
    		
    		if (searchObjInternalUID.equals(objInternalUID)) {
    			return Long.valueOf(whiteBoardItem.get(whiteBoardItem.size()-7).toString()).longValue();
    		}
		}
		return null;
	}
	
	public List<Diagram> getDiagramHistoryByNo(String SID, Long organisation_id, 
			Long diagramNo, Long language_id){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramHistoryByNo(organisation_id, diagramNo);
	        	
	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
	        	for (Diagram dia : diagramList) {
	        		
	        		DiagramType dType = dia.getDiagramType();
	        		dType.setLabel(Fieldmanagment.getInstance().
	        				getFieldByLabelNumberAndLanguage(dia.getDiagramType().getFieldId(), language_id));
	        		dia.setDiagramType(dType);
	        		
	        		diagramReturnList.add(dia);
	        	}
	        	return diagramReturnList;
	        }
		} catch (Exception err) {
			log.error("[getDiagramList]",err);
		} 
		return null;
	}
	
	public Diagram getDiagramById(String SID, Long diagramId){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return this.diagramDaoImpl.getDiagramById(diagramId);
	        }
		} catch (Exception err) {
			log.error("[getDiagramById]",err);
		} 
		return null;
	}
	
	/**
	 * 
	 * @deprecated
	 * @param SID
	 * @param start
	 * @param max
	 * @param orderby
	 * @param asc
	 * @param organisation_id
	 * @param language_id
	 * @return
	 */
	public SearchResult getDiagramList(String SID, int start, int max, String orderby, boolean asc, 
			Long organisation_id, Long language_id){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramList(organisation_id);
	        	
	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
	        	for (Diagram dia : diagramList) {
	        		
	        		Diagram returnDia = this.diagramDaoImpl.getDiagramByNo(dia.getDiagramNo(), organisation_id);
	        		
	        		DiagramType dType = returnDia.getDiagramType();
	        		dType.setLabel(Fieldmanagment.getInstance().
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
	
	/**
	 * Returns a List of Diagrams, all params here should work and be active
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
			Long organization_id, String search) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	if (search.equals(null) || search.length() == 0) {
	        		search = "%";
	        	} else {
	        		search = "%"+search+"%";
	        	}
	        	
	        	List<Diagram> listResult = this.diagramDaoImpl.
	        								getDiagramByNoMaxAndOrder(organization_id, orderBy, asc, start, max, search);
	    		
				SearchResult sresult = new SearchResult();
				sresult.setObjectName(Diagram.class.getName());
				sresult.setRecords(this.diagramDaoImpl.getMaxDiagram(organization_id, search));
				sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramList]",err);
		} 
		return null;
	}
	
	
	/**
	 * 
	 * ###########################################
	 * General Search Method to get DiagramObjects
	 * ###########################################
	 *
	 * @param SID
	 * @param organization_id
	 * @param start
	 * @param max
	 * @param objectTypeNames
	 * @param orderBy
	 * @param asc
	 * @param search
	 * @return
	 */
	public SearchResult getDiagramObjects(String SID, Long organization_id, int start, int max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
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
	        													orderBy, asc, search, true);

	        	SearchResult sresult = new SearchResult();
	        	sresult.setObjectName(DiagramObject.class.getName());
	        	sresult.setRecords(this.diagramObjectDaoImpl.
	        				selectMaxDiagramObjects(organization_id, objectTypeNames, search, true));
	        	sresult.setResult(listResult);
				
				return sresult;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjects]",err);
		}
        return null;
	}
	
	public SearchResult getDiagramObjectsNoMatterPending(String SID, Long organization_id, int start, int max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
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
	
	/**
	 * search for Diagram Object but only for the Parent Diagram Object,
	 * meaning the object cannot be already used as Parent Diagram Object
	 * @param SID
	 * @param organization_id
	 * @param start
	 * @param max
	 * @param objectTypeNames
	 * @param orderBy
	 * @param asc
	 * @param search
	 * @return
	 */
	public SearchResult getParentDiagramObjects(String SID, Long organization_id, int start, int max, 
			Map<Integer,String> objectTypeNames, String orderBy, boolean asc, String search){
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
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
	        											selectParentDiagramObjects(organization_id, start, max, objectTypeNames, 
	        													orderBy, asc, search);

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
	
	/**
	 * Loading all Items of a given Diagram
	 * 
	 * @param SID
	 * @param diagram_id
	 * @param organisation_id
	 * @return
	 */
	public List<DiagramInstanceObject> getDiagramObjectsById(String SID, Long diagram_id, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	Diagram diagram = this.diagramDaoImpl.getDiagramById(diagram_id);
				
				List<DiagramInstanceObject> digramInstanceList = DiagramInstanceObjectDaoImpl.getInstance().
								getDiagramInstanceObjectListByDiagram(diagram_id);
				
				for (DiagramInstanceObject diagramInstance : digramInstanceList) {
					
					log.debug("getDiagramObjectsById ObjectTypeName: "+diagramInstance.getDiagramObject().getObjectTypeName());
					String objectTypeName = diagramInstance.getDiagramObject().getObjectTypeName();
					
					XStream xStream = new XStream(new XppDriver());
					xStream.setMode(XStream.NO_REFERENCES);
					
					diagramInstance.setGraphAsObject((LinkedHashMap) xStream.fromXML(diagramInstance.getGraphObject()));
					
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
					diagramInstance.getGraphAsObject().put(diagramInstance.getGraphAsObject().size()-8,diagramInstance.getDiagramObject().getName());
					
					//only overwrite pending status in case of flow and MyRole Diagram
					if (diagram.getDiagramType().getDiagramTypeId() == 2 || diagram.getDiagramType().getDiagramTypeId() == 4) {
						//In case its an Org.-Object / Pending Object, overwrite the Status of the Object
						//with the one stored in the DiagramObject, cause its possible to 
						//approve Objects outside the Diagram/without making a new Diagram Revision
						if ( objectTypeName.equals("companyFixed") || objectTypeName.equals("unitFixed") ||
								objectTypeName.equals("departementFixed") ) {
							diagramInstance.getGraphAsObject().put(diagramInstance.getGraphAsObject().size()-9,diagramInstance.getDiagramObject().getPending());
						}
					}
					
					log.debug("getDiagramObjectsById getGraphAsObject: "+diagramInstance.getGraphAsObject());
					
				}
				
				return digramInstanceList;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}
	
	/**
	 * TODO:check if Needed
	 * @param SID
	 * @param diagramNo
	 * @param organisation_id
	 * @return
	 */
	public List<DiagramInstanceObject> getLatestDiagramObjectsByNo(String SID, Long diagramNo, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				Diagram diagram = this.diagramDaoImpl.getDiagramByNo(diagramNo, organisation_id);
				
				log.debug("diaggetDiagramObjectsByNoram: "+diagram);
				
				List<DiagramInstanceObject> digramInstanceList = DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectListByDiagram(diagram.getDiagramId());
				
				for (DiagramInstanceObject diagramInstance : digramInstanceList) {
					
					log.debug("diagramInstance1 : "+diagramInstance);
					
					XStream xStream = new XStream(new XppDriver());
					xStream.setMode(XStream.NO_REFERENCES);
					
					diagramInstance.setGraphAsObject((LinkedHashMap) xStream.fromXML(diagramInstance.getGraphObject()));
					
					log.debug("diagramInstance2 : "+diagramInstance.getGraphAsObject());
					
				}
				
				return digramInstanceList;
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}
	
	public List<DiagramInstanceObject> getLatestDiagramObjectsById(String SID, Long diagramId, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	Diagram dia = this.diagramDaoImpl.getDiagramById(diagramId);
	        	
				return this.getLatestDiagramObjectsByNo(SID, dia.getDiagramNo(), organisation_id);
	        }
		} catch (Exception err) {
			log.error("[getDiagramObjectsByNo]",err);
		}
        return null;
	}
	
	
	public Long updateDiagramNameById(String SID, Long diagramId, String newName, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	Diagram remoteDiagram = this.diagramDaoImpl.getDiagramById(diagramId);
	        	
	        	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramByName(newName, organisation_id);
	        	
	        	if (diagramList.size() == 1) {
	        		if (!diagramList.get(0).getDiagramNo().equals(remoteDiagram.getDiagramNo())){
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
	
	public int deleteDiagramByNo(String SID, Long diagramNo, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return this.diagramDaoImpl.deleteDiagramByNo(users_id, diagramNo, organisation_id);
	        }
		} catch (Exception err) {
			log.error("[updateDiagramByName]",err);
		}
		return -1;
	}
	
	public Long checkForUniqueName(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
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
	
	public List<Diagram> checkDiagramsByDiagramObjectId(String SID, String objectName, Long diagramObjectId, String typeOfObject, Long organisation_id, Long language_id) {
		try {
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
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
        	
        	List<DiagramInstanceObject> listDiagramObjects = DiagramInstanceObjectDaoImpl.getInstance().
        		getDiagramInstanceObjectListByDiagramObjectId(diagramObjectId);
        	
        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
        	for (DiagramInstanceObject dia : listDiagramObjects) {
        		
        		Diagram returnDia = this.diagramDaoImpl.getDiagramByNo(dia.getDiagram().getDiagramNo(), organisation_id);
        		
        		//Can be NULL if the returnDia has been deleted
        		if (returnDia != null) {
        			DiagramType dType = returnDia.getDiagramType();
            		dType.setLabel(Fieldmanagment.getInstance().
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
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	log.debug("organization_id: "+organization_id);
	        	
	        	List<DiagramObject> diagramObjectList = this.diagramObjectDaoImpl.
	        			getDiagramObjectsIssues(organization_id);
	        	
	        	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();
	        	for (DiagramObject diaObject : diagramObjectList) {
	        		diaObject.setIssueassignee(IssueAssigneeDaoImpl.getInstance().getIssueAssignee(diaObject.getDiagramObjectId()));
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
//			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
//	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
//	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
//	        	log.debug("organization_id: "+organization_id);
//	        	
//	        	//First get all Diagrams available, for THIS moment
//	        	List<Diagram> diagramList = DiagramServiceDaoImpl.getInstance().getDiagramList(organization_id);
//	        	
//	        	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
//	        	for (Diagram dia : diagramList) {
//	        		Diagram returnDia = DiagramServiceDaoImpl.getInstance().getDiagramByNo(dia.getDiagramNo(), organization_id);
//	        		//.. for THIS moment
//	        		diagramReturnList.add(returnDia);
//	        	}
//	        	
//	        	//Return List
//	        	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();
//	        	
//	        	//Get all Issues
//	        	List<DiagramObject> diagramObjectList = DiagramObjectServiceDaoImpl.getInstance().
//	        			getDiagramObjectsIssues(organization_id);
//	        	
//	        	//Check if this Issue is part of the latest Diagram Revision and still up to date
//	        	for (DiagramObject diaObject : diagramObjectList) {
//	        		
//	        		log.debug("DiagramObject NAME: "+diaObject.getName());
//	        		IssueAssignee issueAssignee = IssueAssigneeDaoImpl.getInstance().getIssueAssignee(diaObject.getDiagramObjectId());
//	        		
//	        		if (issueAssignee != null) {
//	        			log.debug("issueAssignee Login: "+issueAssignee.getAssignee().getLogin());
//		        		diaObject.setIssueassignee(issueAssignee);
//		        		if (diaObject.getIssueassignee().getAssignee().getUser_id().equals(users_id)) {
//		        			
//		        			DiagramInstanceObject diagramInstanceObjectList = DiagramObjectServiceDaoImpl.getInstance().
//		        			getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
//		        			
//		        			//Only add if this Item is found on the latest/current Diagrams available at this moment
//		        			if (diagramInstanceObjectList != null) {
//	//		        			Diagram diagram = DiagramServiceDaoImpl.getInstance().getDiagramByNo(
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
//	        	List<DiagramObject> diagramObjectListPending = DiagramObjectServiceDaoImpl.getInstance().
//	        							getDiagramObjectsPendingObjectsAssignedToMe(organization_id, users_id);
//	        	//Check if this Issue is part of the latest Diagram Revision and still up to date
//	        	for (DiagramObject diaObject : diagramObjectListPending) {
//	        		DiagramInstanceObject diagramInstanceObjectList = DiagramObjectServiceDaoImpl.getInstance().
//        			getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
//        			
//        			//Only add if this Item is found on the latest/current Diagrams available at this moment
//        			if (diagramInstanceObjectList != null) {
////		        			Diagram diagram = DiagramServiceDaoImpl.getInstance().getDiagramByNo(
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
	
	/**
	 * returns a List of all DiagramObjects of type Issue together with the Assignee, which are assigned to me
	 * @param SID
	 * @param organization_id
	 * @return
	 */
	public List<DiagramObject> getIssuesByOrganizationAssignedToMe(String SID, Long organization_id) {
		try {
        	
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	log.debug("organization_id: "+organization_id);
	        	
	        	log.debug("organization_id: "+organization_id);
		    	
		    	//First get all Diagrams available, for THIS moment
		    	List<Diagram> diagramList = this.diagramDaoImpl.getDiagramList(organization_id);
		    	
		    	List<Diagram> diagramReturnList = new LinkedList<Diagram>();
		    	for (Diagram dia : diagramList) {
		    		Diagram returnDia = this.diagramDaoImpl.getDiagramByNo(dia.getDiagramNo(), organization_id);
		    		//.. for THIS moment
		    		diagramReturnList.add(returnDia);
		    	}
		    	
		    	//Return List
		    	List<DiagramObject> returnDiagramObjectList = new LinkedList<DiagramObject>();

		    	List<DiagramObject> diagramObjectList = this.diagramObjectDaoImpl.
		    												getDiagramObjectsIssuesAssignedToMe(organization_id, users_id);

		    	//Check if this Issue is part of the latest Diagram Revision and still up to date
		    	for (DiagramObject diaObject : diagramObjectList) {
		    		DiagramInstanceObject diagramInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().
									getLatestDiagramInstanceObjectListByDiagramObjectId(diaObject.getDiagramObjectId());
					
					//Only add if this Item is found on the latest/current Diagrams available at this moment
					if (diagramInstanceObjectList != null) {	
		    			for (Diagram dia : diagramReturnList) {
		    				if (dia.getDiagramId() == diagramInstanceObjectList.getDiagram().getDiagramId()) {
		    					//Add the Diagram Object to the return List to open it
		    					diaObject.setDiagram(dia);
		    					returnDiagramObjectList.add(diaObject);
		    				}
		    			}
					}
		    	}
		    	
		    	//Get all Assigned Objects (Pending Role at the moment only)
		    	List<DiagramObject> diagramObjectListPending = this.diagramObjectDaoImpl.
		    							getDiagramObjectsPendingObjectsAssignedToMe(organization_id, users_id);
		    	//Check if this Issue is part of the latest Diagram Revision and still up to date
		    	for (DiagramObject diaObject : diagramObjectListPending) {
		    		DiagramInstanceObject diagramInstanceObjectList = DiagramInstanceObjectDaoImpl.getInstance().
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
	        	
	        	return returnDiagramObjectList;
	        }
	        
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	public Long approveSingleDiagramObject(String SID, Long diagramObjectId, boolean approve) {
		try {
        	
			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	DiagramObject diagramObject = this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
        		if (diagramObject == null) {
        			return new Long(-46);
        		}
        		
        		Long default_lang_id = Long.valueOf(Configurationmanagement.getInstance().getConfKey(3,"default_lang_id").getConf_value()).longValue();
        		
	        	if (approve) {
	        		diagramObject.setPending(false);
	        		diagramObject.setUpdated(new Date());
	        		diagramObject.setUpdatedby(users_id);
	        		//Cannot be NULL!
					if (diagramObject.getAssignee() != null) {
						//Add a mail to the Spool for the Assignee
						Mailmanagement.getInstance().addMailToSpoolAboutApprovedPendingObject(users_id, diagramObject.getAssignee().getInsertedby(), 
								"No Diagram", default_lang_id, diagramObject.getName());
					}
	        		diagramObject.setAssignee(null);
	        		this.diagramObjectDaoImpl.updateDiagramObjectByObject(diagramObject);
	        	} else {
	        		
	        		//send Mail to initial creator of the Pending Org. that his Org has been removed
	        		Mailmanagement.getInstance().addMailToSpoolAboutRemovedPendingObject(users_id, 
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
	
	public ExportImportJob getItemToImportMap(String SID, Long exportJobId) {
		try {
			return Application.getExportJob(exportJobId);
		} catch (Exception err) {
			log.error("[getIssuesByOrganization]",err);
		}
		return null;
	}
	
	public DiagramObject getDiagramObjectById(String SID, Long diagramObjectId) {
		try {

			Long users_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	
	public Long addDiagramObject(String SID, String name, String objectTypeName, Long organisation_id) {
		try {

			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, objectTypeName, organisation_id, false, null, "", null);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	public Long addPendingDiagramObject(String SID, String name, String objectTypeName, Long organisation_id, 
			Long default_lang_id, Long assigneeUserId, Boolean isPending) {
		try {

			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				//Do send mails at this Point!
				String diagramName = "Pending Role added through Registration";
				
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, objectTypeName, organisation_id, isPending, assigneeUserId, diagramName, default_lang_id);
				
			}
			
		} catch (Exception err) {
			log.error("[getDiagramObjectById]", err);
		}
		return null;
	}
	
	
	public List<DiagramObjectOrganisation> getOrganizationsByObjects(String SID, Long diagramObjectId, Long organisation_id) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				return DiagramObjectOrganisationDaoImpl.getInstance().
					getOrganisationsObjectsByDiagramObject(diagramObjectId, organisation_id, "c.name", true);
			}

		} catch (Exception err) {
			log.error("getOrganizationsByObjects: ",err);
		}
		return null;
	}
	
	public List<DiagramObjectOrganisation> getObjectsByOrganizations(String SID, Long diagramObjectId, Long organisation_id) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				return DiagramObjectOrganisationDaoImpl.getInstance().
					getObjectsByOrganisationObject(diagramObjectId, organisation_id, "c.name", true);
			}

		} catch (Exception err) {
			log.error("getOrganizationsByObjects: ",err);
		}
		return null;
	}
	
	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(String SID, String objectTypeName, 
			Long organisation_id) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				return DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectsByObjectType(objectTypeName, 
							organisation_id, "c.name", true);
			}			
			
		} catch (Exception err) {
			log.error("getDiagramInstanceObjectsByObjectType: ",err);
		}
		return null;
	}
	

	public List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectId(String SID, Long diagramObjectId, 
			Long organisation_id) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				//Do not send any mails at this Point!
				return DiagramInstanceObjectDaoImpl.getInstance().getDiagramInstanceObjectsByObjectId(diagramObjectId, 
							organisation_id, "c.name", true);
			}			
			
		} catch (Exception err) {
			log.error("getDiagramInstanceObjectsByObjectId: ",err);
		}
		return null;
	}
	
	public Long addDataCarrier(String SID, String name,  Long organisation_id) {
		try {
			Long user_id = Sessionmanagement.getInstance().checkSession(SID);
			Long user_level = UserDaoImpl.getInstance().getUserLevelByID(user_id);

			if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
				
				return this.diagramObjectDaoImpl.addDiagramObject(user_id, name, "datacarrier", organisation_id, 
															false, null, "", null);
				
			}			
			
		} catch (Exception err) {
			log.error("addDataCarrier: ",err);
		}
		return null;
	}
	
}
