package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObject;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DataCarrierDiagramObjectDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(DataCarrierDiagramObjectDaoImpl.class);	

	public Long addOrUpdateDataCarrierDiagramObject(Long dataCarrierObjectdiagramObjectId, Long diagramObjectId, Long user_id) {
		try {
			
			log.debug("addDataCarrierDiagramObject "+dataCarrierObjectdiagramObjectId+" "+diagramObjectId);
			
			DataCarrierDiagramObject dCarrier = this.getDataCarrierDiagramObjectByDiagramObjectAndDataCarrier(
													diagramObjectId, dataCarrierObjectdiagramObjectId);
			
			if (dCarrier == null) {
				log.debug("ADD NEW");
				dCarrier = new DataCarrierDiagramObject();
				dCarrier.setInserted(new Date());
				dCarrier.setInsertedBy(user_id);
			
				dCarrier.setDataCarrierObjectdiagramObjectId(dataCarrierObjectdiagramObjectId);
				dCarrier.setDiagramObjectId(diagramObjectId);
	
				Long dataCarrierDiagramObjectId = (Long) getHibernateTemplate().save(dCarrier);
	
				log.debug("dataCarrierDiagramObjectId: " + dataCarrierDiagramObjectId);
			
				return dataCarrierDiagramObjectId;
			} else {
				log.debug("Update Existing");
				
				dCarrier.setUpdated(new Date());
				dCarrier.setUpdatedBy(user_id);
	
				getHibernateTemplate().update(dCarrier);
	
				return dCarrier.getDataCarrierObjectdiagramObjectId();
			}
			
		} catch (HibernateException ex) {
			log.error("[addDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDataCarrierDiagramObject]",ex2);
		}
		return null;
	}
	
	
	public Long addDataCarrierDiagramObjectByObject(DataCarrierDiagramObject dCarrier) {
		try {

			Long dataCarrierDiagramObjectId = (Long) getHibernateTemplate().save(dCarrier);

			log.debug("dataCarrierDiagramObjectId: " + dataCarrierDiagramObjectId);

			return dataCarrierDiagramObjectId;
			
		} catch (HibernateException ex) {
			log.error("[addDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDataCarrierDiagramObject]",ex2);
		}
		return null;
	}
	
	public DataCarrierDiagramObject getDataCarrierDiagramObjectByDiagramObjectAndDataCarrier(Long diagramObjectId, 
			Long dataCarrierObjectdiagramObjectId) {
		try {
			String hql = "SELECT c FROM DataCarrierDiagramObject as c " +
						"WHERE c.diagramObjectId = :diagramObjectId " +
						"AND c.dataCarrierObjectdiagramObjectId = :dataCarrierObjectdiagramObjectId";
		
			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setLong("dataCarrierObjectdiagramObjectId", dataCarrierObjectdiagramObjectId);
			List<DataCarrierDiagramObject> dCarrier = query.list();
		
			log.debug("select dCarrier " + dCarrier);
		
			if (dCarrier == null || dCarrier.size() == 0) {
				return null;
			} else {
				return dCarrier.get(0);
			}
			
		} catch (HibernateException ex) {
			log.error("[getDataCarrierDiagramObjectByDiagramObjectAndDataCarrier]",ex);
		} catch (Exception ex2) {
			log.error("[getDataCarrierDiagramObjectByDiagramObjectAndDataCarrier]",ex2);
		}
		return null;
	}
	
	public List<DataCarrierDiagramObject> getDataCarrierDiagramObjectsByDiagramObject(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM DataCarrierDiagramObject as c " +
						"WHERE c.diagramObjectId = :diagramObjectId ";
		
			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			List<DataCarrierDiagramObject> dCarriers = query.list();
		
			log.debug("select dCarriers " + dCarriers);
		
			return dCarriers;
			
		} catch (HibernateException ex) {
			log.error("[getDataCarrierDiagramObjectsByDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[getDataCarrierDiagramObjectsByDiagramObject]",ex2);
		}
		return null;
	}
	
	public DataCarrierDiagramObject getDataCarrierDiagramObjectById(Long dataCarrierDiagramObjectId) {
		try {
			String hql = "SELECT c FROM DataCarrierDiagramObject as c " +
						"WHERE c.dataCarrierDiagramObjectId=:dataCarrierDiagramObjectId ";
		
			Query query = getSession().createQuery(hql);
			query.setLong("dataCarrierDiagramObjectId", dataCarrierDiagramObjectId);
			DataCarrierDiagramObject dCarrier = (DataCarrierDiagramObject) query.uniqueResult();
		
			log.debug("select dCarrier " + dCarrier);
		
			return dCarrier;
			
		} catch (HibernateException ex) {
			log.error("[getDataCarrierDiagramObjectById]",ex);
		} catch (Exception ex2) {
			log.error("[getDataCarrierDiagramObjectById]",ex2);
		}
		return null;
	}
	
	public void removeDataCarrierDiagramObject(DataCarrierDiagramObject carrierObject) {
		try {

			getSession().delete(carrierObject);
			
		} catch (HibernateException ex) {
			log.error("[removeDataCarrierDiagramObject]",ex);
		} catch (Exception ex2) {
			log.error("[removeDataCarrierDiagramObject]",ex2);
		}
	}
	
}
