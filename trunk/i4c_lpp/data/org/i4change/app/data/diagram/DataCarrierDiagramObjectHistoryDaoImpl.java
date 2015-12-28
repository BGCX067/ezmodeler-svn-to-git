package org.i4change.app.data.diagram;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.i4change.app.hibernate.beans.diagram.DataCarrierDiagramObjectHistory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DataCarrierDiagramObjectHistoryDaoImpl extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(DataCarrierDiagramObjectHistoryDaoImpl.class);	

	public Long addDataCarrierDiagramObjectHistory(Long dataCarrierObjectdiagramObjectId, Long diagramObjectId, Long user_id, Long diagramId) {
		try {
			DataCarrierDiagramObjectHistory dCarrier = new DataCarrierDiagramObjectHistory();
			dCarrier.setDataCarrierObjectdiagramObjectId(dataCarrierObjectdiagramObjectId);
			dCarrier.setDiagramObjectId(diagramObjectId);
			dCarrier.setInserted(new Date());
			dCarrier.setInsertedBy(user_id);
			
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
	
}
