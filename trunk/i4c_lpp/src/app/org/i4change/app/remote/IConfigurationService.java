package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.basic.Configuration;

public interface IConfigurationService {

	/*
	 * Configuration Handlers
	 */
	public abstract SearchResult getAllConf(String SID, int start, int max,
			String orderby, boolean asc);

	public abstract Configuration getConfByConfigurationId(String SID,
			long configuration_id);

	public abstract Long saveOrUpdateConfiguration(String SID, Map values);

	public abstract Long deleteConfiguration(String SID, Map values);

	public abstract List<Configuration> getLicenseDefaultConfiguration(
			String SID);

}