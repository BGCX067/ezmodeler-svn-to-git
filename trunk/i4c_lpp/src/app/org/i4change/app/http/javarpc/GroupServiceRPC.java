package org.i4change.app.http.javarpc;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.remote.ConfigurationService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author sebastianwagner
 *
 */
public class GroupServiceRPC extends BaseAdapterRPC {
	
	private static final Log log = LogFactory.getLog(GroupServiceRPC.class);
	
	
}
