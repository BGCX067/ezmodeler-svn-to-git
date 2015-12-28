package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.DiagramType;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramTypeDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DiagramTypeDaoImpl.class);		
	
	public Long addDiagramType(Long fieldId, String internalName) {
		try {
			DiagramType diagramType = new DiagramType();
			diagramType.setFieldId(fieldId);
			diagramType.setInserted(new Date());
			diagramType.setInternalName(internalName);

			Long diagramTypeId = (Long) getHibernateTemplate().save(diagramType);

			log.debug("added id " + diagramTypeId);

			return diagramTypeId;
		} catch (HibernateException ex) {
			log.error("[addDiagramType]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramType]",ex2);
		}
		return null;
	}
	
	public Long addDiagramTypeByObject(DiagramType diagramType) {
		try {

			Long diagramTypeId = (Long) getHibernateTemplate().save(diagramType);

			log.debug("added id " + diagramTypeId);

			return diagramTypeId;
		} catch (HibernateException ex) {
			log.error("[addDiagramType]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramType]",ex2);
		}
		return null;
	}
	
	public DiagramType getDiagramTypeById(Long diagramTypeId) {
		try {
			String hql = "SELECT c FROM DiagramType as c " +
					"WHERE c.diagramTypeId=:diagramTypeId";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramTypeId", diagramTypeId);
			DiagramType diagramType = (DiagramType) query.uniqueResult();

			log.debug("select diagramType " + diagramType);

			return diagramType;
		} catch (HibernateException ex) {
			log.error("[getDiagramrevisionById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramrevisionById]",ex2);
		}
		return null;
	}
	
}
