package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramObjectPropertyDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DiagramObjectPropertyDaoImpl.class);	

	public Long addOrUpdateDiagramObjectProperty(Long diagramObjectId, 
			Long propertyId, String value, Long insertedBy) {
		try {
			
			boolean isNew = false;
			DiagramObjectProperty diaObjectProperty = this.getDiagramObjectPropertyByObjectAndProperty(diagramObjectId, propertyId);
			
			if (diaObjectProperty == null) {
				isNew = true;
				diaObjectProperty = new DiagramObjectProperty();
				diaObjectProperty.setDiagramObjectId(diagramObjectId);
				diaObjectProperty.setPropertyId(propertyId);
				diaObjectProperty.setInserted(new Date());
				diaObjectProperty.setInsertedBy(insertedBy);
			}
			
			diaObjectProperty.setUpdated(new Date());
			diaObjectProperty.setUpdatedBy(insertedBy);
			diaObjectProperty.setValue(value);

			Long diagramObjectPropertyId = null;
			if (isNew) {
				diagramObjectPropertyId = (Long) getHibernateTemplate().save(diaObjectProperty);
			} else {
				getHibernateTemplate().update(diaObjectProperty);
				diagramObjectPropertyId = diaObjectProperty.getDiagramObjectId();
			}
			
			log.debug("select diagramObjectPropertyId " + diagramObjectPropertyId);

			return diagramObjectPropertyId;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectProperty]",ex2);
		}
		return new Long(-1);
	}
	
	public Long addOrUpdateDiagramProperty(Long diagramNo, 
			Long propertyId, String value, Long insertedBy) {
		try {
			
			boolean isNew = false;
			DiagramObjectProperty diaObjectProperty = this.getDiagramObjectPropertyByDiagramAndProperty(diagramNo, propertyId);
			
			if (diaObjectProperty == null) {
				isNew = true;
				diaObjectProperty = new DiagramObjectProperty();
				diaObjectProperty.setDiagramNo(diagramNo);
				diaObjectProperty.setPropertyId(propertyId);
				diaObjectProperty.setInserted(new Date());
				diaObjectProperty.setInsertedBy(insertedBy);
			}
			
			diaObjectProperty.setUpdated(new Date());
			diaObjectProperty.setUpdatedBy(insertedBy);
			diaObjectProperty.setValue(value);

			Long diagramObjectPropertyId = null;
			if (isNew) {
				diagramObjectPropertyId = (Long) getHibernateTemplate().save(diaObjectProperty);
			} else {
				getHibernateTemplate().update(diaObjectProperty);
				diagramObjectPropertyId = diaObjectProperty.getDiagramObjectId();
			}

			log.debug("select diagramObjectPropertyId " + diagramObjectPropertyId);

			return diagramObjectPropertyId;
			
		} catch (HibernateException ex) {
			log.error("[addOrUpdateDiagramProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addOrUpdateDiagramProperty]",ex2);
		}
		return new Long(-1);
	}
	
	public DiagramObjectProperty getDiagramObjectPropertyByObjectAndProperty(Long diagramObjectId, Long propertyId) {
		try {
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramObjectId = :diagramObjectId " +
					"AND c.propertyId = :propertyId ";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setLong("propertyId", propertyId);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			//NOTE: size > 1 MUST be an accident!! Cause that should never happen
			if (diagramObjectProperties.size() >= 1) {
				return diagramObjectProperties.get(0);
			}

			return null;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectProperty]",ex2);
		}
		return null;
	}
	
	public DiagramObjectProperty getDiagramObjectPropertyByDiagramAndProperty(Long diagramNo, Long propertyId) {
		try {
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramNo = :diagramNo " +
					"AND c.propertyId = :propertyId ";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("propertyId", propertyId);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			//NOTE: size > 1 MUST be an accident!! Cause that should never happen
			if (diagramObjectProperties.size() >= 1) {
				return diagramObjectProperties.get(0);
			}

			return null;
			
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectPropertyByDiagramAndProperty]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectPropertyByDiagramAndProperty]",ex2);
		}
		return null;
	}
	
	public List<DiagramObjectProperty> getDiagramObjectPropertyByDiagramObjectId(Long diagramObjectId) {
		try {
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramObjectId = :diagramObjectId ";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			return diagramObjectProperties;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectProperty]",ex2);
		}
		return null;
	}
	
	public List<DiagramObjectProperty> getDiagramObjectPropertyByDiagramNo(Long diagramNo) {
		try {
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramNo = :diagramNo ";
			
			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			return diagramObjectProperties;
			
		} catch (HibernateException ex) {
			log.error("[getDiagramObjectPropertyByDiagramNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramObjectPropertyByDiagramNo]",ex2);
		}
		return null;
	}
	
	/**
	 * @deprecated We do not load the properties anymore at the Loading of the Diagram
	 * and also diagramId does not exist anymore
	 * 
	 * @param diagramObjectId
	 * @return
	 */
	public List<DiagramObjectProperty> getDiagramObjectPropertyByObject(Long diagramObjectId) {
		try {
			
			DiagramObjectProperty diaObjectProperty = new DiagramObjectProperty();
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramObjectId = :diagramObjectId " +
					"AND c.diagramId = (SELECT MAX(diagramId) FROM DiagramObjectProperty " +
					"WHERE diagramObjectId=c.diagramObjectId) ";
			
			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			log.debug("select diagramObjectProperties " + diagramObjectProperties);

			return diagramObjectProperties;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectProperty]",ex2);
		}
		return null;
	}
	
}
