package org.i4change.app.remote;

import java.util.List;
import java.util.Vector;

import org.i4change.app.data.basic.beans.ExportImportJob;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.diagram.DiagramInstanceConnectedDTO;
import org.i4change.app.dto.diagram.DiagramInstanceObjectDTO;
import org.i4change.app.dto.diagram.DiagramObjectCompleteDTO;
import org.i4change.app.dto.diagram.DiagramObjectListDTO;
import org.i4change.app.dto.diagram.DiagramObjectSearchDetailedDTO;
import org.i4change.app.dto.diagram.DiagramObjectToolTip;
import org.i4change.app.dto.diagram.DiagramSearchDTO;
import org.i4change.app.dto.diagram.FlowConnectedDiagramDTO;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramDocument;
import org.i4change.app.hibernate.beans.diagram.DiagramInstanceObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectOrganisation;
import org.i4change.app.hibernate.beans.diagram.SearchQuery;

public interface IDiagramservice {

	public abstract Long updateDiagramWithProperties(String SID,
			String diagramName, String revisionComment, Object diagramMapObj,
			Long organisation_id, Long diagramType, Long diagramNo,
			boolean read, boolean write, boolean onlyIssues, Object users,
			Long language_id, long currentRevesionId, boolean forceUpdate,
			Long diagramObjectId, Long projectId, Vector propertyList,
			String shortName);

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
	public abstract Long updateDiagram(String SID, String diagramName,
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, Long diagramNo, boolean read, boolean write,
			boolean onlyIssues, Object users, Long language_id,
			long currentRevesionId, boolean forceUpdate, Long diagramObjectId,
			Long projectId, String shortName);

	/**
	 * get Diagram Drilled Down by DiagramObjectIdAndType
	 * 
	 * @param SID
	 * @param organisation_id
	 * @param diagramobjectId
	 * @return the Latest Diagram-Version Object
	 */
	public abstract List<DiagramSearchDTO> getDiagramDrillDownByDiagramObjectIdAndType(
			String SID, Long organisation_id, Long diagramObjectId);

	public abstract List<DiagramSearchDTO> getDiagramSurfAcrossByDiagramObjectIdAndType(
			String SID, Long diagramObjectId, String flowType);

	public abstract Long saveNewDiagramWithProperties(String SID,
			String diagramName, String revisionComment, Object diagramMapObj,
			Long organisation_id, Long diagramType, boolean read,
			boolean write, boolean onlyIssues, Object users, Long language_id,
			Long diagramObjectId, Long projectId, Vector propertyList,
			String shortName);

	public abstract Long saveNewDiagram(String SID, String diagramName,
			String revisionComment, Object diagramMapObj, Long organisation_id,
			Long diagramType, boolean read, boolean write, boolean onlyIssues,
			Object users, Long language_id, Long diagramObjectId,
			Long projectId, String shortName);

	public abstract SearchResult getDiagramHistoryByNo(String SID,
			Long organisation_id, Long diagramNo, Long language_id);

	/**
	 * @deprecated
	 * @param SID
	 * @param diagramId
	 * @return
	 */
	public abstract Diagram _getDiagramById(String SID, Long diagramId);

	public abstract DiagramSearchDTO getDiagramById(String SID, Long diagramId);

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
	public abstract SearchResult getDiagramList(String SID, int start, int max,
			String orderby, boolean asc, Long organisation_id, Long language_id);

	/**
	 * Returns a List of Diagrams, all params here should work and be active
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
	public abstract SearchResult getDiagramSearch(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search);

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
	public abstract SearchResult getDiagramObjects(String SID,
			Long organization_id, int start, int max, Vector objectTypeNames,
			String orderBy, boolean asc, String search);

	public abstract SearchResult getDiagramObjectsFastPath(String SID,
			Long organization_id, int start, int max, Vector objectTypeNames,
			String orderBy, boolean asc, String search);

	public abstract DiagramObjectCompleteDTO getDiagramObjectCompleteById(
			String SID, Long diagramObjectId);

	public abstract DiagramObjectToolTip getDiagramObjectPureById(String SID,
			Long diagramObjectId);

	public abstract SearchResult getDiagramObjectsIncludingAssingees(
			String SID, Long organization_id, int start, int max,
			Vector objectTypeNames, String orderBy, boolean asc, String search);

	public abstract SearchResult getDiagramObjectsNoMatterPending(String SID,
			Long organization_id, int start, int max, Vector objectTypeNames,
			String orderBy, boolean asc, String search);

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
	public abstract SearchResult getParentDiagramObjects(String SID,
			Long organization_id, int start, int max, Vector objectTypeNames,
			String orderBy, boolean asc, String search);

	/**
	 * Loading all Items of a given Diagram
	 * 
	 * @param SID
	 * @param diagram_id
	 * @param organisation_id
	 * 
	 * @deprecated see getLatestDiagramObjectsById
	 * @return
	 */
	public abstract List<DiagramInstanceObject> getDiagramObjectsById(
			String SID, Long diagram_id, Long organisation_id);

	/**
	 * 
	 * @param SID
	 * @param diagramNo
	 * @param organisation_id
	 * @return
	 */
	public abstract List<DiagramInstanceObjectDTO> getLatestDiagramObjectsByNo(
			String SID, Long diagramNo, Long organisation_id);

	public abstract List<DiagramInstanceObjectDTO> getDiagramObjectsByDiagramId(
			String SID, Long diagramId);

	public abstract List<DiagramInstanceObjectDTO> getLatestDiagramObjectsById(
			String SID, Long diagramId, Long organisation_id);

	public abstract Long updateDiagramNameById(String SID, Long diagramId,
			String newName, Long organisation_id);

	public abstract int deleteDiagramByNo(String SID, Long diagramNo,
			Long organisation_id);

	public abstract Long checkForUniqueName(String SID, String objectName,
			Long diagramObjectId, String typeOfObject, Long organisation_id);

	public abstract List<Diagram> checkDiagramsByDiagramObjectId(String SID,
			String objectName, Long diagramObjectId, String typeOfObject,
			Long organisation_id, Long language_id);

	/**
	 * returns a List of all DiagramObjects of type Issue together with the Assignee, which are assigned to me
	 * @param SID
	 * @param organization_id
	 * @return
	 */
	public abstract SearchResult getIssuesByOrganization(String SID, int start,
			int max, String orderBy, boolean asc, Long organization_id,
			String search, long ideasType);

	public abstract SearchResult getPendingOrganizationAssignedToMe(String SID,
			Long organization_id);

	public abstract Long approveSingleDiagramObject(String SID,
			Long diagramObjectId, boolean approve);

	public abstract ExportImportJob getItemToImportMap(String SID,
			Long exportJobId);

	public abstract DiagramObject getDiagramObjectById(String SID,
			Long diagramObjectId);

	public abstract DiagramObjectSearchDetailedDTO getDiagramObjectSearchDetailsById(
			String SID, Long diagramObjectId);

	public abstract Long addDiagramObject(String SID, String name,
			String objectTypeName, Long organisation_id);

	public abstract Long addPendingDiagramObject(String SID, String name,
			String objectTypeName, Long organisation_id, Long default_lang_id,
			Long assigneeUserId, Boolean isPending);

	public abstract List<DiagramObjectOrganisation> getOrganizationsByObjects(
			String SID, Long diagramObjectId, Long organisation_id);

	public abstract List<DiagramObjectOrganisation> getObjectsByOrganizations(
			String SID, Long diagramObjectId, Long organisation_id);

	public abstract List<DiagramInstanceObject> getDiagramInstanceObjectsByObjectType(
			String SID, String objectTypeName, Long organisation_id);

	/**
	 * FIXME: Morph to DTO-Structure
	 * 
	 * @param SID
	 * @param diagramObjectId
	 * @param organisation_id
	 * @return
	 */
	public abstract List<DiagramInstanceConnectedDTO> getDiagramInstanceObjectsByObjectId(
			String SID, Long diagramObjectId, Long organisation_id);

	/**
	 * @deprecated
	 * @param SID
	 * @param name
	 * @param organisation_id
	 * @return
	 */
	public abstract Long addDataCarrier(String SID, String name,
			Long organisation_id);

	public abstract Long addNonVisualObject(String SID, String name,
			Long organisation_id, String type, String comment, Vector properties);

	public abstract Long updateNonVisualObject(String SID,
			Long diagramObjectId, String name, Long organisation_id,
			String type, String comment, Vector properties);

	public abstract void updateDiagramSummaryIndex(String SID);

	public abstract SearchResult getDiagramSearchByProject(String SID,
			int start, int max, String orderBy, boolean asc,
			Long organization_id, String search, Long projectId);

	public abstract List<DiagramObjectListDTO> getDiagramObjectsLinked(
			String SID, Long diagramObjectId);

	public abstract List<FlowConnectedDiagramDTO> getFlowConnectedDiagramList(
			String SID, Vector inputFlowIds, Vector outputFlowIds);

	public abstract SearchResult getDiagramObjectsFullSearch(String SID,
			Long organization_id, int start, int max, Vector objectTypeNames,
			String orderBy, boolean asc, Vector search, Vector properties,
			Integer itemStatus);

	public abstract Long saveSearchFilter(String SID, Long organization_id,
			int start, int max, Vector objectTypeNames, String orderBy,
			boolean asc, Vector search, Vector properties, Integer itemStatus,
			String sql, String name, String description);

	public abstract Long updateSearchFilter(Long searchQueryId, String SID,
			int start, int max, Vector objectTypeNames, String orderBy,
			boolean asc, Vector search, Vector properties, Integer itemStatus,
			String sql, String name, String description);

	public abstract Long deleteSearchFilter(String SID, Long searchQueryId);

	public abstract List<SearchQuery> getMySearchFilter(String SID,
			Long organization_id);

	public abstract List<SearchQuery> getSearchFilter(String SID,
			Long organization_id);

	public abstract SearchQuery getSearchFilterById(String SID,
			Long searchQueryId);
	
	public abstract List<DiagramDocument> getDiagramDocumentByObject(String SID, Long diagramObjectId);
	
	public abstract Long saveDiagramDocumentByObject(String SID, Long diagramObjectId, String fileName, String fileIconName,
			String fileHash, Boolean isInternal, String url);
	
	public abstract Long deleteDiagramDocumentByObject(String SID, Long diagramDocumentId);

}