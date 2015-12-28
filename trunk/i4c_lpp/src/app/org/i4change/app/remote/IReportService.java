package org.i4change.app.remote;

public interface IReportService {

	public abstract Long startGenerateOverview(String SID, Long projectId,
			Long organisation_id);

}