package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.dto.property.PropertyCategoryListDTO;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.diagram.PropertyCategory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyCategoryDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(PropertyCategoryDaoImpl.class);
	
	private OrganisationDaoImpl organisationDaoImpl;
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}

	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public Long addPropertyCategory(String categoryName, Long insertedBy, String objectTypeName, 
			boolean isPublic, Long organisation_id, String comment) {
		try {
			PropertyCategory propertyCategory = new PropertyCategory();
			propertyCategory.setInserted(new Date());
			propertyCategory.setInsertedBy(insertedBy);
			propertyCategory.setDeleted("false");
			propertyCategory.setComment(comment);
			
			propertyCategory.setCategoryName(categoryName);
			propertyCategory.setObjectTypeName(objectTypeName);
			propertyCategory.setIsPublic(isPublic);
			if (organisation_id == null) {
				propertyCategory.setOrganisation(null);
			} else {
				propertyCategory.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}

			Long propertyCategoryId = (Long) getHibernateTemplate().save(propertyCategory);

			log.debug("added id " + propertyCategoryId);

			return propertyCategoryId;
		} catch (HibernateException ex) {
			log.error("[addPropertyCategory]",ex);
		} catch (Exception ex2) {
			log.error("[addPropertyCategory]",ex2);
		}
		return null;
	}
	
	public PropertyCategory getPropertyCategoryById(Long propertyCategoryId) {
		try {
			
			log.debug("getPropertyCategoryById "+propertyCategoryId);
			
			String hql = "SELECT c FROM PropertyCategory as c " +
							"WHERE c.propertyCategoryId = :propertyCategoryId " +
							"AND c.deleted != :deleted";

			log.debug("hql "+hql);
			
			Query query = getSession().createQuery(hql);
			query.setLong("propertyCategoryId", propertyCategoryId);
			query.setString("deleted", "true");
			PropertyCategory propertyCategory = (PropertyCategory) query.uniqueResult();
		
			log.debug("select propertyCategory " + propertyCategory);
		
			return propertyCategory;
		} catch (HibernateException ex) {
			log.error("[getPropertyCategoryById]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategoryById]",ex2);
		}
		return null;
	}
	
	public Long updatePropertyCategory(Long propertyCategoryId, String categoryName, Long insertedBy, 
			String objectTypeName, boolean isPublic, Long organisation_id, String comment) {
		try {
			PropertyCategory propertyCategory = this.getPropertyCategoryById(propertyCategoryId);
			propertyCategory.setComment(comment);
			propertyCategory.setCategoryName(categoryName);
			propertyCategory.setObjectTypeName(objectTypeName);
			propertyCategory.setIsPublic(isPublic);
			if (organisation_id == null || organisation_id == 0) {
				propertyCategory.setOrganisation(null);
			} else {
				propertyCategory.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}

			getHibernateTemplate().update(propertyCategory);

			log.debug("added id " + propertyCategoryId);

			return propertyCategoryId;
		} catch (HibernateException ex) {
			log.error("[updatePropertyCategory]",ex);
		} catch (Exception ex2) {
			log.error("[updatePropertyCategory]",ex2);
		}
		return null;
	}
	
	public Long deletePropertyCategory(Long propertyCategoryId) {
		try {
			PropertyCategory propertyCategory = this.getPropertyCategoryById(propertyCategoryId);
			
			propertyCategory.setDeleted("true");

			getHibernateTemplate().update(propertyCategory);

			return propertyCategoryId;
		} catch (HibernateException ex) {
			log.error("[deletePropertyCategory]",ex);
		} catch (Exception ex2) {
			log.error("[deletePropertyCategory]",ex2);
		}
		return null;
	}

	public SearchResult getPropertyCategories(int start, int max,
							String orderby, boolean asc, String search) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setRecords(this.getMaxPropertyCategories(search));
			
			List<PropertyCategory> propertyCategories = this.getPropertyCategoriesList(start, max, orderby, asc, search);
			
			List<PropertyCategoryListDTO> list = new LinkedList<PropertyCategoryListDTO>();
			for (int i=0;i<propertyCategories.size();i++) {
				list.add(this.morphPropertyCategoryToPropertyCategoryListDTO(propertyCategories.get(i)));
			}
			
			sresult.setResult(list);
			sresult.setObjectName(PropertyCategory.class.getName());
			return sresult;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyCategories]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategories]: " ,ex2);
		}		
		return null;
	}
	
	private PropertyCategoryListDTO morphPropertyCategoryToPropertyCategoryListDTO(PropertyCategory propCategory) {
		try {
			
			PropertyCategoryListDTO propertyCategoryListDTO = new PropertyCategoryListDTO();
			
			propertyCategoryListDTO.setCategoryName(propCategory.getCategoryName());
			propertyCategoryListDTO.setPropertyCategoryId(propCategory.getPropertyCategoryId());
			
			return propertyCategoryListDTO;
			
		} catch (Exception err) {
			log.error("[morphPropertyCategoryToPropertyCategoryListDTO]",err);
		}
		return null;
	}
	
	public Long getMaxPropertyCategories(String search) {
		try {
			String searchStr = "%";
			
			if (search.length() == 0) {
				searchStr = "%";
			} if (search != null && search.length() != 0) {
				searchStr = "%" + search + "%";
			} 
			
			String hql = "SELECT COUNT(c) FROM PropertyCategory as c " +
					"WHERE c.categoryName LIKE :categoryName AND c.deleted!=:deleted ";


			Query query = getSession().createQuery(hql);
			query.setString("categoryName", searchStr);
			query.setString("deleted", "true");
			
			List ll = query.list();
			
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);		
			
		} catch (HibernateException ex) {
			log.error("[getMaxPropertyCategories]",ex);
		} catch (Exception ex2) {
			log.error("[getMaxPropertyCategories]",ex2);
		}
		return null;
	}
	
	public List<PropertyCategory> getPropertyCategoriesList(int start ,int max, String orderby, 
										boolean asc, String search) {
		try {
			
			String searchStr = "%";
			
			if (search.length() == 0) {
				searchStr = "%";
			} if (search != null && search.length() != 0) {
				searchStr = "%" + search + "%";
			} 
			
			String hql = "SELECT c FROM PropertyCategory as c " +
					"WHERE c.categoryName LIKE :categoryName AND c.deleted!=:deleted ";

			hql += "ORDER BY " + orderby + " ";
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Query query = getSession().createQuery(hql);
			query.setString("categoryName", searchStr);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<PropertyCategory> propertyCategories = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return propertyCategories;
		} catch (HibernateException ex) {
			log.error("[getPropertyCategoriesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategoriesList]",ex2);
		}
		return null;
	}

	public SearchResult getPropertiesByOrganization(Long organization_id,
			int start, int max, String orderby, boolean asc, String search) {
		try {
			String searchStr = "%";
			if (search != null && search.length() != 0) {
				searchStr = "%" + search + "%";
			}
			
			SearchResult sresult = new SearchResult();
			sresult.setRecords(this.getMaxPropertyCategoriesByOrganization(organization_id, searchStr));
			
			List<PropertyCategory> propertyCategories = this.getPropertyCategoriesByOrganizationList(organization_id, start, max, orderby, asc, searchStr);
			
			List<PropertyCategoryListDTO> list = new LinkedList<PropertyCategoryListDTO>();
			for (int i=0;i<propertyCategories.size();i++) {
				list.add(this.morphPropertyCategoryToPropertyCategoryListDTO(propertyCategories.get(i)));
			}
			
			sresult.setResult(list);
			sresult.setObjectName(Property.class.getName());
			return sresult;
			
		} catch (HibernateException ex) {
			log.error("[getProperties]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getProperties]: " ,ex2);
		}		
		return null;
	}
	

	public Long getMaxPropertyCategoriesByOrganization(Long organization_id, String search) {
		try {
			String hql = "SELECT COUNT(c) FROM PropertyCategory as c " +
					"WHERE c.categoryName LIKE :categoryName AND " +
					"(c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted ";

			Query query = getSession().createQuery(hql);
			query.setString("categoryName", search);
			query.setLong("organisation_id", organization_id);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			
			List ll = query.list();
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);		
			
		} catch (HibernateException ex) {
			log.error("[getMaxPropertyCategories]",ex);
		} catch (Exception ex2) {
			log.error("[getMaxPropertyCategories]",ex2);
		}
		return null;
	}
	
	public List<PropertyCategory> getPropertyCategoriesByOrganizationList(Long organization_id, int start ,
			int max, String orderby, boolean asc, String search) {
		try {
			String hql = "SELECT c FROM PropertyCategory as c " +
					"WHERE c.categoryName LIKE :categoryName AND " +
					"(c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic)  " +
					"AND c.deleted!=:deleted ";

			hql += "ORDER BY " + orderby + " ";
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Query query = getSession().createQuery(hql);
			query.setString("categoryName", search);
			query.setLong("organisation_id", organization_id);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<PropertyCategory> propertyCategories = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return propertyCategories;
		} catch (HibernateException ex) {
			log.error("[getPropertyCategoriesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategoriesList]",ex2);
		}
		return null;
	}
	
	public List<PropertyCategoryListDTO> getPropertyCategoriesByOrganizationList(
				Long organization_id, String objectTypeName) {
		try {
			String hql = "SELECT c FROM PropertyCategory as c " +
					"WHERE (c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic)  " +
					"AND (c.objectTypeName IS NULL OR c.objectTypeName LIKE '' OR c.objectTypeName LIKE :objectTypeName) " +
					"AND c.deleted!=:deleted ";

//			hql += "ORDER BY " + orderby + " ";
//			if (asc) {
//				hql += "ASC";
//			} else {
//				hql += "DESC";
//			}
			
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			
//			query.setFirstResult(start);
//			query.setMaxResults(max);
			
			List<PropertyCategory> propertyCategories = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			
			List<PropertyCategoryListDTO> propertyCategoriesDTO = new LinkedList<PropertyCategoryListDTO>();
			
			for (int i=0;i<propertyCategories.size();i++) {
				propertyCategoriesDTO.add(
							this.morphPropertyCategoryToPropertyCategoryListDTO(
								propertyCategories.get(i)
							)
						);
			}
			
			return propertyCategoriesDTO;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyCategoriesByOrganizationList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategoriesByOrganizationList]",ex2);
		}
		
		return null;
	}
	
	public List<PropertyCategoryListDTO> getPropertyCategoriesByStrictOrganizationList(
			Long organization_id, String objectTypeName) {
		try {
			String hql = "SELECT c FROM PropertyCategory as c " +
					"WHERE (c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic)  " +
					"AND c.objectTypeName LIKE :objectTypeName " +
					"AND c.deleted!=:deleted ";
	
	//		hql += "ORDER BY " + orderby + " ";
	//		if (asc) {
	//			hql += "ASC";
	//		} else {
	//			hql += "DESC";
	//		}
	
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			
	//		query.setFirstResult(start);
	//		query.setMaxResults(max);
			
			List<PropertyCategory> propertyCategories = query.list();
	
			//log.debug("select issueAssignee " + issueAssignee);
	
			
			List<PropertyCategoryListDTO> propertyCategoriesDTO = new LinkedList<PropertyCategoryListDTO>();
			
			for (int i=0;i<propertyCategories.size();i++) {
				propertyCategoriesDTO.add(
							this.morphPropertyCategoryToPropertyCategoryListDTO(
								propertyCategories.get(i)
							)
						);
			}
			
			return propertyCategoriesDTO;
			
		} catch (HibernateException ex) {
			log.error("[getPropertyCategoriesByOrganizationList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyCategoriesByOrganizationList]",ex2);
		}
		
		return null;
	}

}
