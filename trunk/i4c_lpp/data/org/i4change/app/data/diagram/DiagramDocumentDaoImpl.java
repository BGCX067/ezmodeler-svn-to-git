package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.DiagramDocument;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramDocumentDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DiagramDocumentDaoImpl.class);	
	
	public DiagramDocument getDiagramDocumentById(Long diagramDocumentId) {
		try {
			
			String hql = "SELECT c FROM DiagramDocument as c " +
					"WHERE c.diagramDocumentId=:diagramDocumentId";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramDocumentId", diagramDocumentId);
			DiagramDocument diagramDocument = (DiagramDocument) query.uniqueResult();

			log.debug("select diagramDocument " + diagramDocument);

			return diagramDocument;
			
		} catch (HibernateException ex) {
			log.error("[getDiagramDocumentById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramDocumentById]",ex2);
		}
		return null;
	}
	

	public List<DiagramDocument> getDiagramDocumentByDiagramObjectId(Long diagramObjectId) {
		try {
			
			String hql = "SELECT c FROM DiagramDocument as c " +
					"WHERE c.diagramObjectId=:diagramObjectId " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			
			List<DiagramDocument> diagramDocuments = query.list();

			log.debug("select diagramDocuments " + diagramDocuments.size());

			return diagramDocuments;
			
		} catch (HibernateException ex) {
			log.error("[getDiagramDocumentByDiagramObjectId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramDocumentByDiagramObjectId]",ex2);
		}
		return null;
	}
	
	public Long addDiagramDocument(String fileName, String fileIconName, String fileHash, 
			Long diagramObjectId, Long user_id, Boolean isInternal, String url) {
		try {
			
			DiagramDocument diagramDocument = new DiagramDocument();
			diagramDocument.setUrl(url);
			diagramDocument.setIsInternal(isInternal);
			diagramDocument.setFileName(fileName);
			diagramDocument.setFileIconName(fileIconName);
			diagramDocument.setFileHash(fileHash);
			diagramDocument.setDiagramObjectId(diagramObjectId);
			diagramDocument.setDeleted("false");
			diagramDocument.setInserted(new Date());
			diagramDocument.setInsertedBy(user_id);
			
			Long diagramDocumentId = (Long) getHibernateTemplate().save(diagramDocument);

			log.debug("dataCarrierDiagramObjectId: " + diagramDocumentId);

			return diagramDocumentId;
			
		} catch (HibernateException ex) {
			log.error("[addDiagramDocument]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramDocument]",ex2);
		}
		return null;
	}
	

	public Long updateDiagramDocument(Long diagramDocumentId, String fileName, String fileIconName, String fileHash, 
			Long diagramObjectId, Long user_id, Boolean isInternal, String url) {
		try {
			
			DiagramDocument diagramDocument = this.getDiagramDocumentById(diagramDocumentId);
			
			if (diagramDocument == null) {
				throw new Exception("DiagramDocument does not exist diagramDocumentId: "+diagramDocumentId);
			}
			
			diagramDocument.setUrl(url);
			diagramDocument.setIsInternal(isInternal);
			diagramDocument.setFileName(fileName);
			diagramDocument.setFileIconName(fileIconName);
			diagramDocument.setFileHash(fileHash);
			diagramDocument.setDiagramObjectId(diagramObjectId);
			diagramDocument.setUpdated(new Date());
			diagramDocument.setUpdatedBy(user_id);
			
			getHibernateTemplate().update(diagramDocument);

			log.debug("dataCarrierDiagramObjectId: " + diagramDocumentId);

			return diagramDocumentId;
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramDocument]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramDocument]",ex2);
		}
		return -1L;
	}

	public Long deleteDiagramDocument(Long diagramDocumentId, Long user_id) {
		try {
			
			DiagramDocument diagramDocument = this.getDiagramDocumentById(diagramDocumentId);
			
			if (diagramDocument == null) {
				throw new Exception("DiagramDocument does not exist diagramDocumentId: "+diagramDocumentId);
			}
			
			diagramDocument.setDeleted("true");
			diagramDocument.setUpdated(new Date());
			diagramDocument.setUpdatedBy(user_id);
			
			getHibernateTemplate().update(diagramDocument);

			log.debug("dataCarrierDiagramObjectId: " + diagramDocumentId);

			return diagramDocumentId;
			
		} catch (HibernateException ex) {
			log.error("[deleteDiagramDocument]",ex);
		} catch (Exception ex2) {
			log.error("[deleteDiagramDocument]",ex2);
		}
		return -1L;
	}
	

}
