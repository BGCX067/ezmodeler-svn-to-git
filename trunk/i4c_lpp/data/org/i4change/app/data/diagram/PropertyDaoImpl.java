package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.dto.organization.OrganisationListDTO;
import org.i4change.app.dto.property.PropertyListDTO;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(PropertyDaoImpl.class);	
	
	//Spring loaded Beans
	private OrganisationDaoImpl organisationDaoImpl;
	private PropertyCategoryDaoImpl propertyCategoryDaoImpl;
	private PropertyListItemDaoImpl propertyListItemDaoImpl;
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	public PropertyCategoryDaoImpl getPropertyCategoryDaoImpl() {
		return propertyCategoryDaoImpl;
	}
	public void setPropertyCategoryDaoImpl(
			PropertyCategoryDaoImpl propertyCategoryDaoImpl) {
		this.propertyCategoryDaoImpl = propertyCategoryDaoImpl;
	}
	
	public PropertyListItemDaoImpl getPropertyListItemDaoImpl() {
		return propertyListItemDaoImpl;
	}
	public void setPropertyListItemDaoImpl(
			PropertyListItemDaoImpl propertyListItemDaoImpl) {
		this.propertyListItemDaoImpl = propertyListItemDaoImpl;
	}
	
	public Property getPropertyById(Long propertyId) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.propertyId=:propertyId " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("propertyId", propertyId);
			query.setString("deleted", "true");
			Property property = (Property) query.uniqueResult();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertyById]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertyById]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesByType(String objectTypeName, Long organisation_id) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.objectTypeName = :objectTypeName " +
					"AND ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			List<Property> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertiesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesByType]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesByTypeAndNoCat(String objectTypeName, Long organisation_id) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE (c.objectTypeName IS NULL OR c.objectTypeName LIKE '' OR c.objectTypeName LIKE :objectTypeName) " +
					"AND ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted " +
					"AND c.propertyCategory IS NULL ";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			List<Property> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertiesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesByType]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesByTypeAndCat(String objectTypeName, 
				Long organisation_id, Long propertyCategoryId) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE (c.objectTypeName IS NULL OR c.objectTypeName LIKE '' OR c.objectTypeName LIKE :objectTypeName) " +
					"AND ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted " +
					"AND c.propertyCategory.propertyCategoryId = :propertyCategoryId ";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			query.setLong("propertyCategoryId",propertyCategoryId);
			List<Property> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertiesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesByType]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesByStrictTypeAndNoCat(String objectTypeName, Long organisation_id) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.objectTypeName LIKE :objectTypeName " +
					"AND ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted " +
					"AND c.propertyCategory IS NULL ";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			List<Property> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertiesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesByType]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesByStrictTypeAndCat(String objectTypeName, 
				Long organisation_id, Long propertyCategoryId) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.objectTypeName LIKE :objectTypeName " +
					"AND ( c.organisation.organisation_id = :organisation_id OR c.isPublic = :isPublic) " +
					"AND c.deleted!=:deleted " +
					"AND c.propertyCategory.propertyCategoryId = :propertyCategoryId ";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			query.setLong("propertyCategoryId",propertyCategoryId);
			List<Property> property = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return property;
		} catch (HibernateException ex) {
			log.error("[getPropertiesByType]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesByType]",ex2);
		}
		return null;
	}


	public Long addProperty(String propertyName, String tooltip, Long languages_id, String comment, 
			Boolean isPublic, Long organisation_id, String objectTypeName, Long insertedby,
			Long propertyValidationTypeId, Long propertyCategoryId, Vector propertyListValues) {
		try {
			
			Long maxLabelId = this.fieldmanagment.selectMaxLabelNumberHelp();
			
			if(maxLabelId == null || maxLabelId < 2000) {
				maxLabelId = new Long(2000);
			} else {
				maxLabelId++;
			}
			log.debug("maxLabelId: "+maxLabelId);
			
			this.fieldmanagment.addFieldByLabelNumber("Property_Label_"+maxLabelId,maxLabelId);
			this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, propertyName);
			
			maxLabelId++;
			this.fieldmanagment.addFieldByLabelNumber("Property_Label_Tooltip_"+maxLabelId,maxLabelId);
			this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, tooltip);
			
			Property property = new Property();
			property.setComment(comment);
			property.setLabelid(maxLabelId-1);
			property.setToolTip(maxLabelId);
			if (organisation_id != null && organisation_id != 0) {
				log.debug("(this.organisationDaoImpl: "+this.organisationDaoImpl);
				property.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}
			property.setPropertyCategory(this.propertyCategoryDaoImpl.getPropertyCategoryById(propertyCategoryId));
			property.setIsPublic(isPublic);
			property.setDeleted("false");
			property.setObjectTypeName(objectTypeName);
			property.setInserted(new Date());
			property.setInsertedby(insertedby);
			property.setPropertyValidationTypeId(propertyValidationTypeId);
			property.setUuid(UUID.randomUUID().toString());
			

			Long propertyId = (Long) getHibernateTemplate().save(property);
			
//			session.flush();
//			session.refresh(property);

			this.propertyListItemDaoImpl.addPropertyListItems(propertyId, propertyListValues);
			
			return propertyId;
			
		} catch (HibernateException ex) {
			log.error("[addProperty]",ex);
		} catch (Exception ex) {
			log.error("[addProperty]",ex);
		}
		return new Long(-1);
	}
	
	public Long updateProperty(Long propertyId, String propertyName, String tooltip, Long languages_id, String comment, 
			Boolean isPublic, Long organisation_id, String objectTypeName, Long insertedby, 
			Long propertyValidationTypeId, Long propertyCategoryId, Vector propertyListValues) {
		try {
			
			Property property = this.getPropertyById(propertyId);
			
			Fieldlanguagesvalues fieldValueTopicLabel = this.fieldmanagment.
			getFieldlanguagesvaluesByLabelAndLang(property.getLabelid(), languages_id);
			fieldValueTopicLabel.setValue(propertyName);
			this.fieldmanagment.updateFieldValueByFieldAndLanguage(fieldValueTopicLabel);
			
			Fieldlanguagesvalues fieldValueLabel = this.fieldmanagment.
			getFieldlanguagesvaluesByLabelAndLang(property.getToolTip(), languages_id);
			fieldValueLabel.setValue(tooltip);
			this.fieldmanagment.updateFieldValueByFieldAndLanguage(fieldValueLabel);
			
			property.setComment(comment);
			//log.debug("updateProperty: "+organisation_id);
			if (organisation_id != null && organisation_id != 0) {
				//log.debug("updateProperty UPDATE: "+organisation_id);
				property.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			} else {
				property.setOrganisation(null);
			}
			
			property.setPropertyCategory(this.propertyCategoryDaoImpl.getPropertyCategoryById(propertyCategoryId));
			property.setIsPublic(isPublic);
			property.setDeleted("false");
			property.setObjectTypeName(objectTypeName);
			property.setUpdated(new Date());
			property.setUpdatedby(insertedby);
			property.setPropertyValidationTypeId(propertyValidationTypeId);
			
			getHibernateTemplate().update(property);

			this.propertyListItemDaoImpl.updatePropertyListItems(propertyId, propertyListValues);
			
			return propertyId;
			
		} catch (HibernateException ex) {
			log.error("[updateProperty]",ex);
		} catch (Exception ex) {
			log.error("[updateProperty]",ex);
		}
		return new Long(-1);
	}
	
	public Long deleteProperty(Long propertyId, Long insertedby) {
		try {
			
			Property property = this.getPropertyById(propertyId);
			
			
			property.setDeleted("true");
			
			getHibernateTemplate().update(property);

			return propertyId;
			
		} catch (HibernateException ex) {
			log.error("[deleteProperty]",ex);
		} catch (Exception ex) {
			log.error("[deleteProperty]",ex);
		}
		return new Long(-1);
	}
	
	public SearchResult getProperties(int start ,int max, String orderby, boolean asc) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setRecords(this.getMaxPropertiesList());
			List<Property> propList = this.getPropertiesList(start, max, orderby, asc);
			
			List<PropertyListDTO> propListDTO = new LinkedList<PropertyListDTO>();
			for (int i=0;i<propList.size();i++) {
				propListDTO.add(this.morphPropertyToPropertyListDTO(propList.get(i)));
			}
			
			sresult.setResult(propListDTO);
			sresult.setObjectName(Property.class.getName());
			return sresult;
			
		} catch (HibernateException ex) {
			log.error("[getProperties]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getProperties]: " ,ex2);
		}		
		return null;
	}
	
	public PropertyListDTO morphPropertyToPropertyListDTO(Property prop) {
		try {
			
			PropertyListDTO propDTO = new PropertyListDTO();
			
			propDTO.setLabelid(prop.getLabelid());
			propDTO.setObjectTypeName(prop.getObjectTypeName());
			
			if (prop.getOrganisation() != null) {
				propDTO.setOrganisation(new OrganisationListDTO());
				propDTO.getOrganisation().setName(prop.getOrganisation().getName());
				propDTO.getOrganisation().setOrganisation_id(prop.getOrganisation().getOrganisation_id());
			}
			
			propDTO.setPropertyId(prop.getPropertyId());
			
			return propDTO;
			
		} catch (Exception err) {
			log.error("[morphPropertyToPropertyListDTO]",err);
		}
		return null;
	}
	
	public SearchResult getPropertiesByOrganization(Long organization_id, int start ,int max, String orderby, boolean asc) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setRecords(this.getMaxPropertiesListByOrganiation(organization_id));
			
			List<Property> propList = this.getPropertiesListByOrganiation(organization_id, start, max, orderby, asc);
			
			List<PropertyListDTO> propListDTO = new LinkedList<PropertyListDTO>();
			for (int i=0;i<propList.size();i++) {
				propListDTO.add(this.morphPropertyToPropertyListDTO(propList.get(i)));
			}
			
			sresult.setResult(propListDTO);
			sresult.setObjectName(Property.class.getName());
			return sresult;
			
		} catch (HibernateException ex) {
			log.error("[getProperties]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getProperties]: " ,ex2);
		}		
		return null;
	}
	
	public Long getMaxPropertiesList() {
		try {
			String hql = "SELECT COUNT(c) FROM Property as c " +
					"WHERE c.deleted!=:deleted ";

			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			
			List ll = query.list();
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);		
			
		} catch (HibernateException ex) {
			log.error("[getPropertiesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesList]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesList(int start ,int max, String orderby, boolean asc) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.deleted!=:deleted ";

			hql += "ORDER BY " + orderby + " ";
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Property> properties = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return properties;
		} catch (HibernateException ex) {
			log.error("[getPropertiesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesList]",ex2);
		}
		return null;
	}
	
	public Long getMaxPropertiesListByOrganiation(Long organization_id) {
		try {
			String hql = "SELECT COUNT(c) FROM Property as c " +
					"WHERE c.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted ";
			
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);		
			
		} catch (HibernateException ex) {
			log.error("[getPropertiesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesList]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesListAllByOrganiation(Long organization_id) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.organisation.organisation_id = :organisation_id " +
					"OR c.isPublic = :isPublic " +
					"AND c.deleted!=:deleted ";
			
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			
			List<Property> properties = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return properties;
		} catch (HibernateException ex) {
			log.error("[getPropertiesListAllByOrganiation]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesListAllByOrganiation]",ex2);
		}
		return null;
	}
	
	public List<Property> getPropertiesListByOrganiation(Long organization_id, int start ,int max, String orderby, boolean asc) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.organisation.organisation_id = :organisation_id " +
					"AND c.deleted!=:deleted ";
			
			hql += "ORDER BY " + orderby + " ";
			if (asc) {
				hql += "ASC";
			} else {
				hql += "DESC";
			}
			
			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Property> properties = query.list();

			//log.debug("select issueAssignee " + issueAssignee);

			return properties;
		} catch (HibernateException ex) {
			log.error("[getPropertiesListByOrganiation]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesListByOrganiation]",ex2);
		}
		return null;
	}
	
}
