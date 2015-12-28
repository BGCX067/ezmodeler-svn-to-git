package org.i4change.app.remote;

public interface IApplication {

	public abstract Long generateExportJob(String SID, Object printObjectList,
			String diagramName, Long diagramId, Long diagramType,
			Long pTemplateId, Boolean includeText, Boolean printIdeas,
			Boolean includeDetails);

	public abstract Long getNewExportJobId(String SID);

	public abstract Long getObjectIdentifier(long organization_id);

}