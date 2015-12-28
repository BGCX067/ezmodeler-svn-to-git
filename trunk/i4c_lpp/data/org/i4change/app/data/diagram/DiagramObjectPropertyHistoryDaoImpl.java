package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectProperty;
import org.i4change.app.hibernate.beans.diagram.DiagramObjectPropertyHistory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramObjectPropertyHistoryDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(DiagramObjectPropertyHistoryDaoImpl.class);
	
	private DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl;
	
	public DiagramObjectPropertyDaoImpl getDiagramObjectPropertyDaoImpl() {
		return diagramObjectPropertyDaoImpl;
	}
	public void setDiagramObjectPropertyDaoImpl(
			DiagramObjectPropertyDaoImpl diagramObjectPropertyDaoImpl) {
		this.diagramObjectPropertyDaoImpl = diagramObjectPropertyDaoImpl;
	}

	public void addDiagramObjectProperties(long diagramObjectId, long diagramId) {
		try {
			
			List<DiagramObjectProperty> diagramObjectProperties =  this.diagramObjectPropertyDaoImpl.
															getDiagramObjectPropertyByDiagramObjectId(diagramObjectId);
			
			
			for (DiagramObjectProperty diagramObjectProperty : diagramObjectProperties) {
				this.addDiagramObjectPropertyHistory(diagramObjectId, diagramId, 
						diagramObjectProperty.getPropertyId(), diagramObjectProperty.getValue(), 
						diagramObjectProperty.getInsertedBy());
			}
		
		} catch (Exception err) {
			log.error("[addDiagramObjectProperties]",err);
		}
	}
	
	public Long addDiagramObjectPropertyHistory(Long diagramObjectId, Long diagramId, 
			Long propertyId, String value, Long insertedBy) {
		try {
			
			DiagramObjectPropertyHistory diagramObjectPropertyHistory = new DiagramObjectPropertyHistory();
			
			diagramObjectPropertyHistory.setDiagramObjectId(diagramObjectId);
			diagramObjectPropertyHistory.setPropertyId(propertyId);
			diagramObjectPropertyHistory.setInserted(new Date());
			diagramObjectPropertyHistory.setInsertedBy(insertedBy);
			diagramObjectPropertyHistory.setValue(value);
			diagramObjectPropertyHistory.setDiagramId(diagramId);

			Long diagramObjectPropertyHistoryId = (Long) getHibernateTemplate().save(diagramObjectPropertyHistory);
			
			log.debug("add diagramObjectPropertyHistoryId " + diagramObjectPropertyHistoryId);

			return diagramObjectPropertyHistoryId;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramObjectPropertyHistory]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramObjectPropertyHistory]",ex2);
		}
		return new Long(-1);
	}
	
}
