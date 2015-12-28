package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.beans.SearchResult;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.hibernate.beans.basic.Configuration;
import org.i4change.app.hibernate.beans.diagram.IssueAssignee;
import org.i4change.app.hibernate.beans.diagram.Property;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class PropertyDaoImpl {

	private static final Log log = LogFactory.getLog(PropertyDaoImpl.class);	
	
	private OrganisationDaoImpl organisationDaoImpl;
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}

	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	
	
	public Property getPropertyById(Long propertyId) {
		try {
			String hql = "SELECT c FROM Property as c " +
					"WHERE c.propertyId=:propertyId " +
					"AND c.deleted!=:deleted";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("propertyId", propertyId);
			query.setString("deleted", "true");
			Property property = (Property) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);

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

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("objectTypeName", objectTypeName);
			query.setBoolean("isPublic", true);
			query.setString("deleted", "true");
			List<Property> property = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

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
			Long propertyValidationTypeId) {
		try {
			
			Long maxLabelId = Fieldmanagment.getInstance().selectMaxLabelNumberHelp();
			
			if(maxLabelId == null || maxLabelId < 2000) {
				maxLabelId = new Long(2000);
			} else {
				maxLabelId++;
			}
			log.debug("maxLabelId: "+maxLabelId);
			
			Fieldmanagment.getInstance().addFieldByLabelNumber("Property_Label_"+maxLabelId,maxLabelId);
			Fieldmanagment.getInstance().addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, propertyName);
			
			maxLabelId++;
			Fieldmanagment.getInstance().addFieldByLabelNumber("Property_Label_Tooltip_"+maxLabelId,maxLabelId);
			Fieldmanagment.getInstance().addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, tooltip);
			
			Property property = new Property();
			property.setComment(comment);
			property.setLabelid(maxLabelId-1);
			property.setToolTip(maxLabelId);
			if (organisation_id != null && organisation_id != 0) {
				log.debug("(this.organisationDaoImpl: "+this.organisationDaoImpl);
				property.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			}
			property.setIsPublic(isPublic);
			property.setDeleted("false");
			property.setObjectTypeName(objectTypeName);
			property.setInserted(new Date());
			property.setInsertedby(insertedby);
			property.setPropertyValidationTypeId(propertyValidationTypeId);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long propertyId = (Long) session.save(property);
			
			session.flush();
			session.refresh(property);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			Long propertyValidationTypeId) {
		try {
			
			Property property = this.getPropertyById(propertyId);
			
			Fieldlanguagesvalues fieldValueTopicLabel = Fieldmanagment.getInstance().
			getFieldlanguagesvaluesByLabelAndLang(property.getLabelid(), languages_id);
			fieldValueTopicLabel.setValue(propertyName);
			Fieldmanagment.getInstance().updateFieldValueByFieldAndLanguage(fieldValueTopicLabel);
			
			Fieldlanguagesvalues fieldValueLabel = Fieldmanagment.getInstance().
			getFieldlanguagesvaluesByLabelAndLang(property.getToolTip(), languages_id);
			fieldValueLabel.setValue(tooltip);
			Fieldmanagment.getInstance().updateFieldValueByFieldAndLanguage(fieldValueLabel);
			
			property.setComment(comment);
			//log.debug("updateProperty: "+organisation_id);
			if (organisation_id != null && organisation_id != 0) {
				//log.debug("updateProperty UPDATE: "+organisation_id);
				property.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			} else {
				property.setOrganisation(null);
			}
			property.setIsPublic(isPublic);
			property.setDeleted("false");
			property.setObjectTypeName(objectTypeName);
			property.setUpdated(new Date());
			property.setUpdatedby(insertedby);
			property.setPropertyValidationTypeId(propertyValidationTypeId);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(property);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(property);

			tx.commit();
			HibernateUtil.closeSession(idf);
			
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
			sresult.setResult(this.getPropertiesList(start, max, orderby, asc));
			sresult.setObjectName(Property.class.getName());
			return sresult;
			
		} catch (HibernateException ex) {
			log.error("[getProperties]: " ,ex);
		} catch (Exception ex2) {
			log.error("[getProperties]: " ,ex2);
		}		
		return null;
	}
	
	public SearchResult getPropertiesByOrganization(Long organization_id, int start ,int max, String orderby, boolean asc) {
		try {
			SearchResult sresult = new SearchResult();
			sresult.setRecords(this.getMaxPropertiesListByOrganiation(organization_id));
			sresult.setResult(this.getPropertiesListByOrganiation(organization_id, start, max, orderby, asc));
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

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Property> properties = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("deleted", "true");
			
			List ll = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);
			log.error((Long)ll.get(0));
			return (Long)ll.get(0);		
			
		} catch (HibernateException ex) {
			log.error("[getPropertiesList]",ex);
		} catch (Exception ex2) {
			log.error("[getPropertiesList]",ex2);
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
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setLong("organisation_id", organization_id);
			query.setString("deleted", "true");
			
			query.setFirstResult(start);
			query.setMaxResults(max);
			
			List<Property> properties = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

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
