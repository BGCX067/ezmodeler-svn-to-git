package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.SearchQuery;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SearchQueryDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(SearchQueryDaoImpl.class);	
	
	public SearchQuery getSearchQueryById(Long searchQueryId) {
		try {
			String hql = "SELECT c FROM SearchQuery as c " +
					"WHERE c.searchQueryId=:searchQueryId " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("searchQueryId", searchQueryId);
			query.setString("deleted", "true");
			SearchQuery searchQuery = (SearchQuery) query.uniqueResult();

			log.debug("select getSearchQueryById " + searchQuery);

			return searchQuery;
		} catch (HibernateException ex) {
			log.error("[getSearchQueryById]",ex);
		} catch (Exception ex2) {
			log.error("[getSearchQueryById]",ex2);
		}
		return null;
	}
	
	public List<SearchQuery> getSearchQueryByOrganization(Long organization_id) {
		try {
			String hql = "SELECT c FROM SearchQuery as c " +
					"WHERE c.organization_id=:organization_id " +
					"AND c.roleObject.deleted!=:deleted ";

			Query query = getSession().createQuery(hql);
			query.setLong("organization_id",organization_id);
			query.setString("deleted", "true");
			List<SearchQuery> searchQuery = query.list();

			log.debug("select getSearchQueryByOrganization " + searchQuery);

			return searchQuery;
		} catch (HibernateException ex) {
			log.error("[getSearchQueryByOrganization]",ex);
		} catch (Exception ex2) {
			log.error("[getSearchQueryByOrganization]",ex2);
		}
		return null;
	}
	
	public List<SearchQuery> getSearchQueryByUserAndOrganization(Long user_id, Long organization_id) {
		try {
			String hql = "SELECT c FROM SearchQuery as c " +
					"WHERE c.organization_id=:organization_id " +
					"AND c.insertedBy = :insertedBy " +
					"AND c.roleObject.deleted!=:deleted ";

			Query query = getSession().createQuery(hql);
			query.setLong("organization_id",organization_id);
			query.setLong("insertedBy", user_id);
			query.setString("deleted", "true");
			List<SearchQuery> searchQuery = query.list();

			log.debug("select getSearchQueryByOrganization " + searchQuery);

			return searchQuery;
		} catch (HibernateException ex) {
			log.error("[getSearchQueryByOrganization]",ex);
		} catch (Exception ex2) {
			log.error("[getSearchQueryByOrganization]",ex2);
		}
		return null;
	}
	

	public Long addSearchQuery(String sql, String paramsAsXML, Long insertedBy, 
			Long organization_id, String name, String description) {
		try {
			
			SearchQuery searchQuery = new SearchQuery();
			searchQuery.setName(name);
			searchQuery.setDescription(description);
			searchQuery.setInserted(new Date());
			searchQuery.setInsertedBy(insertedBy);
			searchQuery.setOrganization_id(organization_id);
			searchQuery.setDeleted("false");
			searchQuery.setSql(sql);
			searchQuery.setParamsAsXML(paramsAsXML);
			
			return this.addSearchQueryByObject(searchQuery);
			
		} catch (HibernateException ex) {
			log.error("[addSearchQuery]",ex);
		} catch (Exception ex2) {
			log.error("[addSearchQuery]",ex2);
		}
		return null;
	}

	public Long addSearchQueryByObject(SearchQuery searchQuery) {
		try {
			
			Long searchQueryId = (Long) getHibernateTemplate().save(searchQuery);
			
			return searchQueryId;
			
		} catch (HibernateException ex) {
			log.error("[addSearchQueryByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addSearchQueryByObject]",ex2);
		}
		return null;
	}
	
	public Long updateSearchQuery(Long searchQueryId, String sql, String paramsAsXML, Long updatedBy) {
		try {
			
			SearchQuery searchQuery = this.getSearchQueryById(searchQueryId);
			if (searchQuery == null) {
				return -1L;
			}
			searchQuery.setUpdated(new Date());
			searchQuery.setUpdatedBy(updatedBy);
			searchQuery.setSql(sql);
			searchQuery.setParamsAsXML(paramsAsXML);
			
			return this.udpateSearchQueryByObject(searchQuery);
			
		} catch (HibernateException ex) {
			log.error("[updateSearchQuery]",ex);
		} catch (Exception ex2) {
			log.error("[updateSearchQuery]",ex2);
		}
		return null;
	}
	
	public Long deleteSearchQuery(Long searchQueryId, Long updatedBy) {
		try {
			
			SearchQuery searchQuery = this.getSearchQueryById(searchQueryId);
			if (searchQuery == null) {
				return -1L;
			}
			searchQuery.setUpdated(new Date());
			searchQuery.setUpdatedBy(updatedBy);
			searchQuery.setDeleted("true");
			
			return this.udpateSearchQueryByObject(searchQuery);
			
		} catch (HibernateException ex) {
			log.error("[deleteSearchQuery]",ex);
		} catch (Exception ex2) {
			log.error("[deleteSearchQuery]",ex2);
		}
		return null;
	}
	
	public Long udpateSearchQueryByObject(SearchQuery searchQuery) {
		try {
			
			getHibernateTemplate().update(searchQuery);
			
			return searchQuery.getSearchQueryId();
			
		} catch (HibernateException ex) {
			log.error("[udpateSearchQueryByObject]",ex);
		} catch (Exception ex2) {
			log.error("[udpateSearchQueryByObject]",ex2);
		}
		return null;
	}
	
}
