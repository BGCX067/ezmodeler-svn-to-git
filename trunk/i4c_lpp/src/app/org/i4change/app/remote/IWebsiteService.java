package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.dto.website.WebItemDTO;
import org.i4change.app.dto.website.WebItemListDTO;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemType;

public interface IWebsiteService {

	public abstract WebItemDTO getWebItemById(String SID, long webItemId);

	public abstract List<WebItemType> getWebItemTypes(String SID);

	public abstract SearchResult getWebItemSearchList(String SID, int start,
			int max, String orderby, boolean asc, Integer searchOnlyHelpItems,
			String search);

	public abstract SearchResult getWebItemList(String SID, int start, int max,
			String orderby, boolean asc);

	public abstract Long saveOrUpdateWebItem(String SID, Map objectMap);

	public abstract Long deleteWebItem(String SID, Long webItemId);

	public abstract WebItemDTO getFrontendItems(String SID);

	public abstract WebItemDTO getWebItemsParentChilds(String SID,
			Long webItemId);

	public abstract WebItemDTO getWebItemsChilds(String SID, Long webItemId);

	/**
	 * @deprecated => use the DTOs
	 * @param SID
	 * @param webItemId
	 * @return
	 */
	public abstract List<WebItem> getWebItemsFrontendByParent(String SID,
			Long webItemId);

	public abstract List<WebItemListDTO> getHelpRootItems(String SID);

	public abstract List<WebItemListDTO> getTreeHelpItemsByParent(String SID,
			Long webItemId);

	public abstract List<WebItemListDTO> getSearchHelpItems(String SID,
			String search);

	public abstract WebItemDTO getHelpMasterRoot(String sid);

}