package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramSummary;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramSummaryDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DiagramSummaryDaoImpl.class);	
	
	public DiagramSummary getDiagramSummaryByDiagramNo(Long diagramNo) {
		try {
			String hql = "SELECT c FROM DiagramSummary as c " +
					"WHERE c.diagramNo=:diagramNo " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setString("deleted", "true");
			DiagramSummary diagramRevision = (DiagramSummary) query.uniqueResult();

			return diagramRevision;
		} catch (HibernateException ex) {
			log.error("[getDiagramSummaryByDiagramId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramSummaryByDiagramId]",ex2);
		}
		return null;
	}
	
	public Long addDiagramSummary(Diagram diagram) {
		try {
			
			DiagramSummary diagramSummary = new DiagramSummary();
			diagramSummary.setDeleted("false");
			diagramSummary.setInserted(new Date());
			diagramSummary.setDiagram(diagram);
			diagramSummary.setDiagramNo(diagram.getDiagramNo());

			Long diagramSummaryId = (Long) getHibernateTemplate().save(diagramSummary);

			return diagramSummaryId;
		} catch (HibernateException ex) {
			log.error("[addDiagramSummary]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramSummary]",ex2);
		}
		return null;
	}
	
	public Long addOrUpdateDiagramSummary(Diagram diagram) {
		try {
			DiagramSummary diagramSummary = this.getDiagramSummaryByDiagramNo(diagram.getDiagramNo());
			
			if (diagramSummary == null) {
				return this.addDiagramSummary(diagram);
			} else {
				this.updateDiagramSummary(diagramSummary, diagram);
				return diagramSummary.getDiagramSummaryId();
			}
			
		} catch (Exception err) {
			log.error("[addOrUpdateDiagramSummary]",err);
		}
		return null;
	}
	
	public void updateDiagramSummary(DiagramSummary diagramSummary, Diagram diagram) {
		try {
			
			diagramSummary.setDeleted("false");
			diagramSummary.setUpdated(new Date());
			diagramSummary.setDiagram(diagram);

			getHibernateTemplate().update(diagramSummary);

		} catch (HibernateException ex) {
			log.error("[updateDiagramSummary]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramSummary]",ex2);
		}
	}
	
	public void updateDiagramSummaryIndex(){
		try {
			
			log.debug("updateDiagramSummaryIndex");
			
			String hql = "SELECT c FROM Diagram as c " +
								"WHERE c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
								"WHERE diagramNo=c.diagramNo) ";
		
			Query query = getSession().createQuery(hql);
			List<Diagram> diagrams = query.list();
			
			System.out.println("diagrams Size "+diagrams.size());
			
			for (Diagram diagram : diagrams) {
				this.addOrUpdateDiagramSummary(diagram);
			}
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramSummaryIndex]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramSummaryIndex]",ex2);
		}
	}

	public List<DiagramSummary> getDiagramByName(String diagramName,
			Long organisation_id) {
		try {
			
			log.debug("name,organisation_id"+diagramName+" "+organisation_id);
			
			String hql = "SELECT c FROM DiagramSummary as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagram.name=:name " +
					"AND c.diagram.organisation.organisation_id=:organisation_id ";

			Query query = getSession().createQuery(hql);
			query.setString("name", diagramName);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<DiagramSummary> diagramList = query.list();

			log.debug("getDiagramByName diagramList " + diagramList);

			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagramByName]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByName]",ex2);
		}
		return null;
	}

	public Long getMaxDiagram(Long organisation_id, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String hql = "SELECT COUNT(*) as number FROM DiagramSummary as c " +
						"WHERE c.deleted!=:deleted AND c.diagram.deleted!=:deleted " +
						"AND lower(c.diagram.name) LIKE lower(:search) " +
						"AND c.diagram.organisation.organisation_id=:organisation_id";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			
			List ll = query.list();
			log.info((Long)ll.get(0));
			
			return (Long)ll.get(0);	
		} catch (HibernateException ex) {
			log.error("[getMaxDiagram]",ex);
		} catch (Exception ex2) {
			log.error("[getMaxDiagram]",ex2);
		}
		return null;
	}

	public List<Diagram> getDiagramByNoMaxAndOrder(Long organisation_id, String orderBy, boolean orderAsc, 
			int start, int max, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM DiagramSummary as c " +
					"WHERE c.deleted!=:deleted AND c.diagram.deleted!=:deleted " +
					"AND ( lower(c.diagram.name) LIKE lower(:search) OR lower(c.diagram.shortName) LIKE lower(:search) ) " +
					"AND c.diagram.organisation.organisation_id=:organisation_id " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<DiagramSummary> diagrams = query.list();
			
			log.debug("getDiagramByNo diagram " + diagrams.size());
			
			List<Diagram> diagramList = new LinkedList<Diagram>();
			for (int i=0;i<diagrams.size();i++) {
				diagramList.add(diagrams.get(i).getDiagram());
			}

			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNoMaxAndOrder]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNoMaxAndOrder]",ex2);
		}
		return null;
	}

	public Diagram getDiagramByNo(Long diagramNo, Long organisation_id) {
		try {
			
			log.debug("diagramNo,organisation_id"+diagramNo+" "+organisation_id);
			
			String hql = "SELECT c FROM DiagramSummary as c " +
					"WHERE c.deleted!=:deleted AND c.diagram.deleted!=:deleted " +
					"AND c.diagramNo=:diagramNo " +
					"AND c.diagram.organisation.organisation_id=:organisation_id ";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			DiagramSummary diagram = (DiagramSummary) query.uniqueResult();

			//session.refresh(diagram);
			
			log.debug("getDiagramByNo diagram " + diagram);

			return diagram.getDiagram();
		} catch (HibernateException ex) {
			log.error("[getDiagramByNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNo]",ex2);
		}
		return null;
	}

	public List<Diagram> getDiagramByNoMaxAndOrderAndProjectId(
			Long organisation_id, String orderBy, boolean orderAsc, int start,
			int max, String search, Long projectId) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM DiagramSummary as c, ProjectRelation as pr " +
					"WHERE c.diagramNo = pr.diagramNo " +
					"AND pr.project.projectId = :projectId " +
					"AND c.deleted!=:deleted AND c.diagram.deleted!=:deleted " +
					"AND lower(c.diagram.name) LIKE lower(:search) " +
					"AND c.diagram.organisation.organisation_id=:organisation_id " +
					"ORDER BY "+orderBy+ " " + orderItemValue;
	
			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<DiagramSummary> diagrams = query.list();
	
			log.debug("getDiagramByNo diagram " + diagrams.size());
			
			List<Diagram> diagramList = new LinkedList<Diagram>();
			for (int i=0;i<diagrams.size();i++) {
				diagramList.add(diagrams.get(i).getDiagram());
			}
	
			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNoMaxAndOrderAndProjectId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNoMaxAndOrderAndProjectId]",ex2);
		}
		return null;
	}

	public Long getMaxDiagramAndProjectId(Long organisation_id, String search,
			Long projectId) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String hql = "SELECT COUNT(*) as number FROM DiagramSummary as c, ProjectRelation as pr " +
						"WHERE c.diagramNo = pr.diagramNo " +
						"AND pr.project.projectId = :projectId " +
						"AND c.deleted!=:deleted AND c.diagram.deleted!=:deleted " +
						"AND lower(c.diagram.name) LIKE lower(:search) " +
						"AND c.diagram.organisation.organisation_id=:organisation_id";

			Query query = getSession().createQuery(hql);
			query.setLong("projectId", projectId);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			
			List ll = query.list();
			log.info((Long)ll.get(0));
			
			return (Long)ll.get(0);	
		} catch (HibernateException ex) {
			log.error("[getMaxDiagramAndProjectId]",ex);
		} catch (Exception ex2) {
			log.error("[getMaxDiagramAndProjectId]",ex2);
		}
		return null;
	}
	
}
