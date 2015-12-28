package org.i4change.app.remote;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.data.website.daos.WebItemDaoImpl;
import org.i4change.app.data.website.daos.WebItemRelationDaoImpl;
import org.i4change.app.data.website.daos.WebItemTypeDaoImpl;
import org.i4change.app.dto.website.WebItemDTO;
import org.i4change.app.dto.website.WebItemListDTO;
import org.i4change.app.dto.website.WebItemMinimalDTO;
import org.i4change.app.dto.website.WebItemRelationDTO;
import org.i4change.app.dto.website.WebItemTypeDTO;
import org.i4change.app.hibernate.beans.website.WebItem;
import org.i4change.app.hibernate.beans.website.WebItemRelation;
import org.i4change.app.hibernate.beans.website.WebItemType;

/**
 * 
 * @author swagner
 * 
 */
public class WebsiteService implements IWebsiteService {

	private static final Log log = LogFactory.getLog(WebsiteService.class);

	// Spring Bean Injection
	private IApplication application;
	private WebItemDaoImpl webItemDaoImpl = null;
	private WebItemTypeDaoImpl webItemTypeDaoImpl = null;
	private WebItemRelationDaoImpl webItemRelationDaoImpl = null;
	private UserDaoImpl userDaoImpl = null;
	private Sessionmanagement sessionmanagement = null;

	public IApplication getApplication() {
		return application;
	}

	public void setApplication(IApplication application) {
		this.application = application;
	}

	public WebItemDaoImpl getWebItemDaoImpl() {
		return webItemDaoImpl;
	}

	public void setWebItemDaoImpl(WebItemDaoImpl webItemDaoImpl) {
		this.webItemDaoImpl = webItemDaoImpl;
	}

	public WebItemTypeDaoImpl getWebItemTypeDaoImpl() {
		return webItemTypeDaoImpl;
	}

	public void setWebItemTypeDaoImpl(WebItemTypeDaoImpl webItemTypeDaoImpl) {
		this.webItemTypeDaoImpl = webItemTypeDaoImpl;
	}

	public WebItemRelationDaoImpl getWebItemRelationDaoImpl() {
		return webItemRelationDaoImpl;
	}

	public void setWebItemRelationDaoImpl(
			WebItemRelationDaoImpl webItemRelationDaoImpl) {
		this.webItemRelationDaoImpl = webItemRelationDaoImpl;
	}

	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}

	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemById(java.lang.String,
	 * long)
	 */
	public WebItemDTO getWebItemById(String SID, long webItemId) {
		try {
			return this.morphWebItemToWebItemDTO(this.webItemDaoImpl
					.getWebItemById(webItemId));
		} catch (Exception err) {
			log.error("[getWebItemById]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemTypes(java.lang.String)
	 */
	public List<WebItemType> getWebItemTypes(String SID) {
		try {
			return this.webItemTypeDaoImpl.getWebItemTypes();
		} catch (Exception err) {
			log.error("[getWebItemTypes]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemSearchList(java.lang
	 * .String, int, int, java.lang.String, boolean, java.lang.Integer,
	 * java.lang.String)
	 */
	public SearchResult getWebItemSearchList(String SID, int start, int max,
			String orderby, boolean asc, Integer searchOnlyHelpItems,
			String search) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				SearchResult sResult = new SearchResult();
				sResult.setObjectName(WebItem.class.getName());
				sResult.setRecords(this.webItemDaoImpl.getItemsSelectBySearch(
						search, searchOnlyHelpItems));

				List<WebItem> webItems = this.webItemDaoImpl
						.getWebItemsBySearch(start, max, orderby, asc,
								searchOnlyHelpItems, search);

				List<WebItemListDTO> webListItems = new LinkedList<WebItemListDTO>();

				for (int i = 0; i < webItems.size(); i++) {
					webListItems.add(this.morphWebItemToWebItemListDTO(webItems
							.get(i)));
				}

				sResult.setResult(webListItems);
				return sResult;
			}
		} catch (Exception err) {
			log.error("[getWebItemTypes]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemList(java.lang.String,
	 * int, int, java.lang.String, boolean)
	 */
	public SearchResult getWebItemList(String SID, int start, int max,
			String orderby, boolean asc) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
			if (AuthLevelmanagement.checkAdminLevel(user_level)) {
				SearchResult sResult = new SearchResult();
				sResult.setObjectName(WebItem.class.getName());
				sResult.setRecords(this.webItemDaoImpl.getItemsSelect());

				List<WebItem> webItems = this.webItemDaoImpl.getWebItems(start,
						max, orderby, asc);

				List<WebItemListDTO> webListItems = new LinkedList<WebItemListDTO>();

				for (int i = 0; i < webItems.size(); i++) {
					webListItems.add(this.morphWebItemToWebItemListDTO(webItems
							.get(i)));
				}

				sResult.setResult(webListItems);
				return sResult;
			}
		} catch (Exception err) {
			log.error("[getWebItemTypes]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#saveOrUpdateWebItem(java.lang
	 * .String, java.util.Map)
	 */
	public Long saveOrUpdateWebItem(String SID, Map objectMap) {
		try {
			Long users_id = this.sessionmanagement.checkSession(SID);
			Long user_level = this.userDaoImpl.getUserLevelByID(users_id);

			Long webItemId = Long
					.valueOf(objectMap.get("webItemId").toString()).longValue();

			Vector parentItems = (Vector) objectMap.get("parentItems");
			Vector childItems = (Vector) objectMap.get("childItems");

			Integer orderInt = null;

			if (objectMap.get("orderInt").toString().length() != 0) {
				orderInt = Integer.parseInt(objectMap.get("orderInt")
						.toString());
			}

			if (webItemId == null || webItemId == 0 || webItemId.equals(0)) {
				webItemId = this.webItemDaoImpl.addWebItem(
						objectMap.get("webItemName").toString(),
						objectMap.get("webItemImagepath").toString(),
						objectMap.get("webItemText").toString(),
						Long.valueOf(objectMap.get("webItemTypeId").toString())
								.longValue(),
						Integer.valueOf(objectMap.get("position").toString())
								.intValue(),
						Boolean.valueOf(objectMap.get("isRoot").toString())
								.booleanValue(),
						objectMap.get("videoURL").toString(),
						objectMap.get("userDefiniedType").toString(),
						Boolean.valueOf(
								objectMap.get("changeOnlyNeeded").toString())
								.booleanValue(),
						Boolean.valueOf(
								objectMap.get("isMasterRoot").toString())
								.booleanValue(), objectMap.get("webItemLayout")
								.toString(),
						Boolean.valueOf(objectMap.get("isHelpItem").toString())
								.booleanValue(), orderInt);
			} else {
				this.webItemDaoImpl.updateWebItem(
						webItemId,
						objectMap.get("webItemName").toString(),
						objectMap.get("webItemImagepath").toString(),
						objectMap.get("webItemText").toString(),
						Long.valueOf(objectMap.get("webItemTypeId").toString())
								.longValue(),
						Integer.valueOf(objectMap.get("position").toString())
								.intValue(),
						Boolean.valueOf(objectMap.get("isRoot").toString())
								.booleanValue(),
						objectMap.get("videoURL").toString(),
						objectMap.get("userDefiniedType").toString(),
						Boolean.valueOf(
								objectMap.get("changeOnlyNeeded").toString())
								.booleanValue(),
						Boolean.valueOf(
								objectMap.get("isMasterRoot").toString())
								.booleanValue(), objectMap.get("webItemLayout")
								.toString(),
						Boolean.valueOf(objectMap.get("isHelpItem").toString())
								.booleanValue(), orderInt);
			}

			log.debug("childItems" + childItems);
			// Handle Parent Child Relation
			this.webItemRelationDaoImpl.updateWebItemsByChild(webItemId,
					childItems);

			log.debug("childItems" + childItems);
			// Handle Child Parent Relation
			this.webItemRelationDaoImpl.updateWebItemsByParent(webItemId,
					parentItems);

			return webItemId;
		} catch (Exception err) {
			log.error("[saveOrUpdateWebItem]", err);
		}
		return new Long(-1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#deleteWebItem(java.lang.String,
	 * java.lang.Long)
	 */
	public Long deleteWebItem(String SID, Long webItemId) {
		try {
			this.webItemDaoImpl.deleteWebItemById(webItemId);

			return webItemId;
		} catch (Exception err) {
			log.error("[saveOrUpdateWebItem]", err);
		}
		return new Long(-1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getFrontendItems(java.lang.String
	 * )
	 */
	public WebItemDTO getFrontendItems(String SID) {
		try {
			List<WebItem> webItems = this.webItemDaoImpl.getRootItems();

			WebItem webitem = this.webItemDaoImpl.geMastertRootItem();
			WebItemDTO webitemDTO = this.morphWebItemToWebItemDTO(webitem);
			webitemDTO.setChilds(this.parseWebItemsToDTO(webItems));

			return webitemDTO;

		} catch (Exception err) {
			log.error("[getFrontendItems]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemsParentChilds(java.
	 * lang.String, java.lang.Long)
	 */
	public WebItemDTO getWebItemsParentChilds(String SID, Long webItemId) {
		try {
			WebItem webItem = this.webItemDaoImpl.getWebItemById(webItemId);

			if (webItem.getIsRoot()) {
				return this.getFrontendItems(SID);
			} else {
				Long webItemParentId = webItem.getParentItems().get(0)
						.getParent_webitem_id();
				WebItem webitem = this.webItemDaoImpl
						.getWebItemById(webItemParentId);

				List<WebItem> webItems = this.webItemRelationDaoImpl
						.getWebItemsChilds(webItemParentId);
				WebItemDTO webitemDTO = this.morphWebItemToWebItemDTO(webitem);
				webitemDTO.setChilds(this.parseWebItemsToDTO(webItems));

				return webitemDTO;
			}

		} catch (Exception err) {
			log.error("[getRooItems]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemsChilds(java.lang.String
	 * , java.lang.Long)
	 */
	public WebItemDTO getWebItemsChilds(String SID, Long webItemId) {
		try {
			// List<WebItem> webItems =
			// this.webItemRelationDaoImpl.getWebItemsChilds(webItemId);

			WebItem webitem = this.webItemDaoImpl.getWebItemById(webItemId);

			List<WebItem> webItems = this.webItemRelationDaoImpl
					.getWebItemsChilds(webItemId);
			WebItemDTO webitemDTO = this.morphWebItemToWebItemDTO(webitem);
			webitemDTO.setChilds(this.parseWebItemsToDTO(webItems));

			return webitemDTO;

		} catch (Exception err) {
			log.error("[getWebItemsChilds]", err);
		}
		return null;
	}

	private List<WebItemDTO> parseWebItemsToDTO(List<WebItem> webItems) {
		try {

			List<WebItemDTO> webItemsDTO = new LinkedList<WebItemDTO>();
			for (int i = 0; i < webItems.size(); i++) {
				WebItem webItem = webItems.get(i);
				webItemsDTO.add(this.morphWebItemToWebItemDTO(webItem));
			}

			return webItemsDTO;

		} catch (Exception err) {
			log.error("[parseWebItemsToDTO]", err);
		}
		return null;
	}

	private WebItemListDTO morphWebItemToWebItemListDTO(WebItem webItem) {
		try {

			WebItemListDTO webItemListDTO = new WebItemListDTO();

			webItemListDTO.setWebItemType(new WebItemTypeDTO());
			webItemListDTO.getWebItemType().setWebItemTypeId(
					webItem.getWebItemType().getWebItemTypeId());
			webItemListDTO.getWebItemType().setTypeName(
					webItem.getWebItemType().getTypeName());

			webItemListDTO.setWebItemId(webItem.getWebItemId());
			webItemListDTO.setWebItemName(webItem.getWebItemName());

			webItemListDTO.setIsHelpItem(webItem.getIsHelpItem());

			return webItemListDTO;

		} catch (Exception err) {
			log.error("[morphWebItemToWebItemListDTO]", err);
		}
		return null;
	}

	private WebItemDTO morphWebItemToWebItemDTO(WebItem webItem) {
		try {

			log.debug("morphWebItemToWebItemDTO" + webItem);

			if (webItem == null) {
				return null;
			}

			WebItemDTO webItemDTO = new WebItemDTO();

			// Type
			webItemDTO.setWebItemType(new WebItemTypeDTO());
			webItemDTO.getWebItemType().setWebItemTypeId(
					webItem.getWebItemType().getWebItemTypeId());

			webItemDTO.setUserDefiniedType(webItem.getUserDefiniedType());
			webItemDTO.setChangeOnlyNeeded(webItem.getChangeOnlyNeeded());

			webItemDTO.setIsMasterRoot(webItem.getIsMasterRoot());
			webItemDTO.setWebItemLayout(webItem.getWebItemLayout());

			webItemDTO.setIsHelpItem(webItem.getIsHelpItem());

			webItemDTO.setOrderInt(webItem.getOrderInt());

			// Set Child List Length
			if (webItem.getChildItems() != null) {
				webItemDTO.setChildItems(new LinkedList<WebItemRelationDTO>());
				for (Iterator<WebItemRelation> iter = webItem.getChildItems()
						.iterator(); iter.hasNext();) {
					WebItemRelation webItemRelation = iter.next();
					WebItemRelationDTO webItemRelationDTO = new WebItemRelationDTO();
					webItemRelationDTO.setWebItemRelationId(webItemRelation
							.getWebItemRelationId());
					webItemRelationDTO.setChild_webitem_id(webItemRelation
							.getChild_webitem_id());
					webItemRelationDTO.setParent_webitem_id(webItemRelation
							.getParent_webitem_id());

					if (webItemRelation.getChildItem() != null) {
						WebItemMinimalDTO webItemMinimalDTO = new WebItemMinimalDTO();
						webItemMinimalDTO.setWebItemId(webItemRelation
								.getChildItem().getWebItemId());
						webItemMinimalDTO.setWebItemName(webItemRelation
								.getChildItem().getWebItemName());
						webItemRelationDTO.setChildItem(webItemMinimalDTO);
					}

					webItemDTO.getChildItems().add(webItemRelationDTO);
				}
			}

			webItemDTO.setIsRoot(webItem.getIsRoot());

			// Set Parent List Length
			if (webItem.getParentItems() != null) {
				webItemDTO.setParentItems(new LinkedList<WebItemRelationDTO>());
				for (Iterator<WebItemRelation> iter = webItem.getParentItems()
						.iterator(); iter.hasNext();) {
					WebItemRelation webItemRelation = iter.next();
					WebItemRelationDTO webItemRelationDTO = new WebItemRelationDTO();
					webItemRelationDTO.setWebItemRelationId(webItemRelation
							.getWebItemRelationId());
					webItemRelationDTO.setChild_webitem_id(webItemRelation
							.getChild_webitem_id());
					webItemRelationDTO.setParent_webitem_id(webItemRelation
							.getParent_webitem_id());

					if (webItemRelation.getParentItem() != null) {
						WebItemMinimalDTO webItemMinimalDTO = new WebItemMinimalDTO();
						webItemMinimalDTO.setWebItemId(webItemRelation
								.getParentItem().getWebItemId());
						webItemMinimalDTO.setWebItemName(webItemRelation
								.getParentItem().getWebItemName());
						webItemRelationDTO.setParentItem(webItemMinimalDTO);
					}

					webItemDTO.getParentItems().add(webItemRelationDTO);
				}
			}

			webItemDTO.setPosition(webItem.getPosition());
			webItemDTO.setVideoURL(webItem.getVideoURL());
			webItemDTO.setWebItemId(webItem.getWebItemId());
			webItemDTO.setWebItemImagepath(webItem.getWebItemImagepath());
			webItemDTO.setWebItemName(webItem.getWebItemName());
			webItemDTO.setWebItemText(webItem.getWebItemText());

			return webItemDTO;

		} catch (Exception err) {
			log.error("[morphWebItemToWebItemDTO]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getWebItemsFrontendByParent(java
	 * .lang.String, java.lang.Long)
	 */
	public List<WebItem> getWebItemsFrontendByParent(String SID, Long webItemId) {
		try {

			return this.webItemDaoImpl.getWebItemsFrontendByParent(webItemId);

		} catch (Exception err) {
			log.error("[getWebItemsFrontendByParent]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getHelpRootItems(java.lang.String
	 * )
	 */
	public List<WebItemListDTO> getHelpRootItems(String SID) {
		try {

			List<WebItem> helpRootItems = this.webItemDaoImpl
					.getHelpRootItems();

			List<WebItemListDTO> helpRootItemsDTO = new LinkedList<WebItemListDTO>();

			for (int i = 0; i < helpRootItems.size(); i++) {

				WebItem webItem = helpRootItems.get(i);

				WebItemListDTO webItemListDTO = this
						.morphWebItemToWebItemListDTO(webItem);

				Long count = this.webItemRelationDaoImpl
						.countWebItemsByParent(webItem.getWebItemId());

				if (count > 0) {
					webItemListDTO.setHasChildItems(true);
				} else {
					webItemListDTO.setHasChildItems(false);
				}

				helpRootItemsDTO.add(webItemListDTO);
			}

			return helpRootItemsDTO;

		} catch (Exception err) {
			log.error("[getHelpRootItems]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getTreeHelpItemsByParent(java
	 * .lang.String, java.lang.Long)
	 */
	public List<WebItemListDTO> getTreeHelpItemsByParent(String SID,
			Long webItemId) {
		try {

			List<WebItemRelation> helpItemsByParent = this.webItemRelationDaoImpl
					.getWebItemsByParent(webItemId);

			List<WebItemListDTO> helpItemsByParentDTO = new LinkedList<WebItemListDTO>();

			for (int i = 0; i < helpItemsByParent.size(); i++) {

				// helpItemsByParentDTO.add(this.morphWebItemToWebItemListDTO(helpItemsByParent.get(i).getChildItem()));

				WebItemRelation webItemRelation = helpItemsByParent.get(i);

				WebItemListDTO webItemListDTO = this
						.morphWebItemToWebItemListDTO(webItemRelation
								.getChildItem());

				Long count = this.webItemRelationDaoImpl
						.countWebItemsByParent(webItemListDTO.getWebItemId());

				if (count > 0) {
					webItemListDTO.setHasChildItems(true);
				} else {
					webItemListDTO.setHasChildItems(false);
				}

				log.debug("-1- webItemListDTO.getWebItemId() "
						+ webItemListDTO.getWebItemId());
				log.debug("-1- count " + count);

				helpItemsByParentDTO.add(webItemListDTO);
			}

			return helpItemsByParentDTO;

		} catch (Exception err) {
			log.error("[getTreeHelpItemsByParent]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getSearchHelpItems(java.lang.
	 * String, java.lang.String)
	 */
	public List<WebItemListDTO> getSearchHelpItems(String SID, String search) {
		try {

			if (search.length() == 0) {
				return this.getHelpRootItems(SID);
			}

			List<WebItem> helpItemsByParent = this.webItemDaoImpl
					.getSearchHelpItems(search);

			log.debug("helpItemsByParent " + helpItemsByParent.size());

			List<WebItem> webItemRootNodesWithPath = new LinkedList<WebItem>();

			List<WebItemListDTO> helpItemsByParentDTO = new LinkedList<WebItemListDTO>();

			// detect for each Items the Path to the Root Node

			for (int i = 0; i < helpItemsByParent.size(); i++) {

				WebItem webItem = helpItemsByParent.get(i);

				if (webItem.getIsRoot()) {

					webItemRootNodesWithPath.add(webItem);

				} else {

					List<WebItem> webItemRootNodesWithPathTemp = this.webItemRelationDaoImpl
							.getPathToRootNode(webItem);

					for (WebItem rootPathWebItem : webItemRootNodesWithPathTemp) {

						webItemRootNodesWithPath.add(rootPathWebItem);

					}

				}
			}

			// Resort List and handle Duplicates

			List<WebItem> webItemRootNodesWithPathSorted = this
					.checkForDuplicates(new LinkedList<WebItem>(),
							webItemRootNodesWithPath);

			log.debug("webItemRootNodesWithPath "
					+ webItemRootNodesWithPath.size());

			for (int i = 0; i < webItemRootNodesWithPathSorted.size(); i++) {

				WebItem webItem = webItemRootNodesWithPathSorted.get(i);

				WebItemListDTO webItemListDTO = this
						.morphWebItemToWebItemListDTOWithPath(webItem);

				helpItemsByParentDTO.add(webItemListDTO);
			}

			// webItemRootNodesWithPathSorted.

			return helpItemsByParentDTO;

		} catch (Exception err) {
			log.error("[getTreeHelpItemsByParent]", err);
		}
		return null;
	}

	private List<WebItem> checkForDuplicates(
			List<WebItem> webItemRootNodesWithPathSorted,
			List<WebItem> webItemRootNodesWithPath) throws Exception {

		// = new LinkedList<WebItem>();

		for (int i = 0; i < webItemRootNodesWithPath.size(); i++) {

			boolean found = false;
			WebItem newWebItem = webItemRootNodesWithPath.get(i);

			// Check for duplicate
			for (int k = 0; k < webItemRootNodesWithPathSorted.size(); k++) {
				WebItem existingWebItem = webItemRootNodesWithPathSorted.get(k);

				if (newWebItem.getWebItemId() == existingWebItem.getWebItemId()) {
					found = true;

					// Go ahead and in some cases you may found another Sub item
					// that is not
					// yet in the result tree
					if (existingWebItem.getChildTreeItems() == null) {
						existingWebItem
								.setChildTreeItems(new LinkedList<WebItem>());
					}

					this.checkForDuplicates(
							existingWebItem.getChildTreeItems(),
							newWebItem.getChildTreeItems());

					break;
				}
			}

			if (!found) {
				webItemRootNodesWithPathSorted.add(newWebItem);
			}

		}

		Collections.sort(webItemRootNodesWithPathSorted,
				new WebItemsComperator());

		return webItemRootNodesWithPathSorted;
	}

	class WebItemsComperator implements Comparator {
		public int compare(Object a, Object b) {
			WebItem s1 = (WebItem) a;
			WebItem s2 = (WebItem) b;

			if (s1.getOrderInt() == s2.getOrderInt()) {
				return 0;
			} else if (s1.getOrderInt() > s2.getOrderInt()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	private WebItemListDTO morphWebItemToWebItemListDTOWithPath(WebItem webItem) {
		try {

			WebItemListDTO webItemListDTO = new WebItemListDTO();

			webItemListDTO.setWebItemType(new WebItemTypeDTO());
			webItemListDTO.getWebItemType().setWebItemTypeId(
					webItem.getWebItemType().getWebItemTypeId());
			webItemListDTO.getWebItemType().setTypeName(
					webItem.getWebItemType().getTypeName());

			webItemListDTO.setWebItemId(webItem.getWebItemId());
			webItemListDTO.setWebItemName(webItem.getWebItemName());

			webItemListDTO.setIsHelpItem(webItem.getIsHelpItem());

			Long count = this.webItemRelationDaoImpl
					.countWebItemsByParent(webItemListDTO.getWebItemId());

			if (count > 0) {
				webItemListDTO.setHasChildItems(true);
			} else {
				webItemListDTO.setHasChildItems(false);
			}

			if (webItem.getChildTreeItems() != null
					&& webItem.getChildTreeItems().size() > 0) {

				webItemListDTO.setChildItems(new LinkedList<WebItemListDTO>());

				for (WebItem childItem : webItem.getChildTreeItems()) {
					webItemListDTO
							.getChildItems()
							.add(this
									.morphWebItemToWebItemListDTOWithPath(childItem));
				}

			}

			return webItemListDTO;

		} catch (Exception err) {
			log.error("[morphWebItemToWebItemListDTO]", err);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.i4change.app.remote.IWebsiteService#getHelpMasterRoot(java.lang.String
	 * )
	 */
	public WebItemDTO getHelpMasterRoot(String sid) {
		try {

			return this.morphWebItemToWebItemDTO(this.webItemDaoImpl
					.getHelpMasterRoot());

		} catch (Exception err) {
			log.error("[getHelpMasterRoot]", err);
		}
		return null;
	}

}
