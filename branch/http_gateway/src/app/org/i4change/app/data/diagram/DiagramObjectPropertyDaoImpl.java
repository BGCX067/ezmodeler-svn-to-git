package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class DiagramObjectPropertyDaoImpl {
	
	private static final Log log = LogFactory.getLog(DiagramObjectPropertyDaoImpl.class);	

	public Long addDiagramObjectProperty(Long diagramObjectId, Long diagramId, 
			Long propertyId, String value, Long insertedBy) {
		try {
			
			DiagramObjectProperty diaObjectProperty = new DiagramObjectProperty();
			diaObjectProperty.setDiagramObjectId(diagramObjectId);
			diaObjectProperty.setDiagramId(diagramId);
			diaObjectProperty.setPropertyId(propertyId);
			diaObjectProperty.setInserted(new Date());
			diaObjectProperty.setInsertedBy(insertedBy);
			diaObjectProperty.setValue(value);

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long diagramObjectPropertyId = (Long) session.save(diaObjectProperty);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("select diagramObjectPropertyId " + diagramObjectPropertyId);

			return diagramObjectPropertyId;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectProperty]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectProperty]",ex2);
		}
		return new Long(-1);
	}
	
	public List<DiagramObjectProperty> getDiagramObjectPropertyByObject(Long diagramObjectId) {
		try {
			
			DiagramObjectProperty diaObjectProperty = new DiagramObjectProperty();
			
			String hql = "select c from DiagramObjectProperty as c " +
					"WHERE c.diagramObjectId = :diagramObjectId " +
					"AND c.diagramId = (SELECT MAX(diagramId) FROM DiagramObjectProperty " +
					"WHERE diagramObjectId=c.diagramObjectId) ";

			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			Query query = session.createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);

			List<DiagramObjectProperty> diagramObjectProperties = query.list();

			tx.commit();
			HibernateUtil.closeSession(idf);

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
