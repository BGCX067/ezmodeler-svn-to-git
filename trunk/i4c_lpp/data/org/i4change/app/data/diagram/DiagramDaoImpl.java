package org.i4change.app.data.diagram;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.domain.daos.OrganisationDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.diagram.Diagram;
import org.i4change.app.hibernate.beans.diagram.DiagramObject;
import org.i4change.app.hibernate.beans.diagram.Diagramrevision;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DiagramDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(DiagramDaoImpl.class);	
	
	//Spring loaded Beans
	private DiagramObjectDaoImpl diagramObjectDaoImpl;
	private DiagramTypeDaoImpl diagramTypeDaoImpl;
	private OrganisationDaoImpl organisationDaoImpl;
	private UserDaoImpl userDaoImpl;
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	public DiagramObjectDaoImpl getDiagramObjectDaoImpl() {
		return diagramObjectDaoImpl;
	}
	public void setDiagramObjectDaoImpl(DiagramObjectDaoImpl diagramObjectDaoImpl) {
		this.diagramObjectDaoImpl = diagramObjectDaoImpl;
	}
	
	public DiagramTypeDaoImpl getDiagramTypeDaoImpl() {
		return diagramTypeDaoImpl;
	}
	public void setDiagramTypeDaoImpl(DiagramTypeDaoImpl diagramTypeDaoImpl) {
		this.diagramTypeDaoImpl = diagramTypeDaoImpl;
	}
	
	public OrganisationDaoImpl getOrganisationDaoImpl() {
		return organisationDaoImpl;
	}
	public void setOrganisationDaoImpl(OrganisationDaoImpl organisationDaoImpl) {
		this.organisationDaoImpl = organisationDaoImpl;
	}
	/**
	 * 
	 */
	public Long addDiagramrevision(String comment, Long user_id) {
		try {
			Diagramrevision diagramrevision = new Diagramrevision();
			diagramrevision.setDeleted("false");
			diagramrevision.setInserted(new Date());
			diagramrevision.setInsertedby(user_id);
			diagramrevision.setComment(comment);

			Long diagramrevisionId = (Long) getHibernateTemplate().save(diagramrevision);

			log.debug("added id " + diagramrevisionId);

			return diagramrevisionId;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevision]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevision]",ex2);
		}
		return null;
	}
	
	public Long addDiagramrevisionByObject(Diagramrevision diagramrevision) {
		try {

			Long diagramrevisionId = (Long) getHibernateTemplate().save(diagramrevision);

			log.debug("added id " + diagramrevisionId);

			return diagramrevisionId;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevisionByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevisionByObject]",ex2);
		}
		return null;
	}
	
	public Diagramrevision getDiagramrevisionById(Long diagramrevisionId) {
		try {
			String hql = "SELECT c FROM Diagramrevision as c " +
					"WHERE c.diagramrevisionId=:diagramrevisionId " +
					"AND c.deleted!=:deleted";


			Query query = getSession().createQuery(hql);
			query.setLong("diagramrevisionId", diagramrevisionId);
			query.setString("deleted", "true");
			Diagramrevision diagramRevision = (Diagramrevision) query.uniqueResult();

			log.debug("select diagramRevision " + diagramRevision);

			return diagramRevision;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevision]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevision]",ex2);
		}
		return null;
	}
	
	public Long updateDiagramParentbyId(Long parentDiagramObjectId, Long diagramId) {
		try {
			Diagram diagram = this.getDiagramById(diagramId);
			diagram.setParentDiagramObject(this.diagramObjectDaoImpl.getDiagramObjectById(parentDiagramObjectId));
			
			getHibernateTemplate().update(diagram);
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramParentbyId]",ex);
		} catch (Exception err) {
			log.error("[updateDiagramParentbyId]",err);
		}
		return null;
	}
	
	public Diagram getDiagramById(Long diagramId) {
		try {
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.diagramId=:diagramId " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramId", diagramId);
			query.setString("deleted", "true");
			Diagram diagram = (Diagram) query.uniqueResult();

			log.debug("select diagram " + diagram);

			return diagram;
		} catch (HibernateException ex) {
			log.error("[getDiagramById]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramById]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagrams() {
		try {
			String hql = "FROM Diagram";

			Query query = getSession().createQuery(hql);
			List<Diagram> diagramList = query.list();

			log.debug("select diagramList " + diagramList);

			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagrams]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagrams]",ex2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param diagramObjectId
	 * @return
	 */
	public List<Diagram> getDiagramByParentId(Long diagramObjectId) {
		try {
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.parentDiagramObject.diagramObjectId=:diagramObjectId " +
					"AND c.deleted!=:deleted";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			List<Diagram> diagram = query.list();

			log.debug("getDiagramByParentId diagram SIZE " + diagram.size());

			return diagram;
		} catch (HibernateException ex) {
			log.error("[getDiagramByParentId]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByParentId]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagramHistoryByNo(Long organisation_id, Long diagramNo) {
		try {
			if (diagramNo == null || diagramNo == 0) {
				return null;
			}
			
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramNo=:diagramNo " +
					"ORDER BY c.diagramrevision.diagramrevisionId DESC";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("diagramNo", diagramNo);
			query.setString("deleted", "true");
			List<Diagram> diagrams = query.list();

			log.debug("select diagrams " + diagrams);

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[getDiagramHistoryByNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramHistoryByNo]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagramList(Long organisation_id) {
		try {
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"GROUP BY c.diagramNo";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Diagram> diagrams = query.list();

			log.debug("select diagrams " + diagrams);

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevision]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevision]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagramListAtLeastRead(Long organisation_id) {
		try {
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND (c.canRead=true OR c.canWrite=true OR c.onlyIssues=true) " +
					"GROUP BY c.diagramNo";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Diagram> diagrams = query.list();

			log.debug("select diagrams " + diagrams);

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevision]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevision]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagramListByNo(Long diagramNo, Long organisation_id) {
		try {
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramNo=:diagramNo " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"ORDER BY c.diagramId";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Diagram> diagrams = query.list();

			log.debug("getDiagramListByNo diagrams " + diagrams);

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[getDiagramListByNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramListByNo]",ex2);
		}
		return null;
	}	
	
	public Diagram _getDiagramByNo(Long diagramNo, Long organisation_id) {
		try {
			
			log.debug("diagramNo,organisation_id"+diagramNo+" "+organisation_id);
			
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.diagramNo=:diagramNo " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=:diagramNo)";
			
			Query query = getSession().createQuery(hql);
			query.setLong("diagramNo", diagramNo);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			Diagram diagram = (Diagram) query.uniqueResult();

			log.debug("getDiagramByNo diagram " + diagram);

			return diagram;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNo]",ex2);
		}
		return null;
	}
	
	/**
	 * @deprecated
	 * @param name
	 * @param organisation_id
	 * @return
	 */
	public List<Diagram> _getDiagramByName(String name, Long organisation_id) {
		try {
			
			log.debug("name,organisation_id"+name+" "+organisation_id);
			
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.name=:name " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=c.diagramNo)";

			Query query = getSession().createQuery(hql);
			query.setString("name", name);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			List<Diagram> diagramList = query.list();

			log.debug("getDiagramByName diagramList " + diagramList);

			return diagramList;
		} catch (HibernateException ex) {
			log.error("[getDiagramByName]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByName]",ex2);
		}
		return null;
	}
	
	/**
	 * @deprecated
	 * @param organisation_id
	 * @param diagramObjectId
	 * @return
	 */
	public Diagram _getDiagramByNoAndParentObjectAndType(Long organisation_id, Long diagramObjectId) {
		try {
			
			log.debug("organisation_id "+organisation_id+ " diagramObjectId: " + diagramObjectId);
			
			
//			c near line 1, column 211 [
//SELECT c FROM org.i4change.app.hibernate.beans.diagram.Diagram as c WHERE c.deleted!=:deleted AND c.organisation.organisation_id=:organisation_id AND c.parentDiagramObject.diagramObjectId = :diagramObjectIdAND c.diagramType.diagramTypeId = :diagramTypeId AND c.diagramId=(SELECT MAX(diagramId) FROM org.i4change.app.hibernate.beans.diagram.Diagram as subc WHERE subc.deleted!=:deleted AND subc.parentDiagramObject.diagramObjectId = :diagramObjectId AND subc.diagramType.diagramTypeId = :diagramTypeId)]
	
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.parentDiagramObject.diagramObjectId = :diagramObjectId " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram as subc " +
					"WHERE subc.deleted!=:deleted " +
					"AND subc.parentDiagramObject.diagramObjectId = :diagramObjectId)";

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			Diagram diagram = (Diagram) query.uniqueResult();

			log.debug("getDiagramByNo diagram " + diagram);

			return diagram;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNo]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNo]",ex2);
		}
		return null;
	}
	
	public List<Diagram> getDiagramByNoAndParentObjectAndType(Long diagramObjectId) {
		try {
			
			log.debug(" diagramObjectId: " + diagramObjectId);
			
			
//			c near line 1, column 211 [
//SELECT c FROM org.i4change.app.hibernate.beans.diagram.Diagram as c WHERE c.deleted!=:deleted AND c.organisation.organisation_id=:organisation_id AND c.parentDiagramObject.diagramObjectId = :diagramObjectIdAND c.diagramType.diagramTypeId = :diagramTypeId AND c.diagramId=(SELECT MAX(diagramId) FROM org.i4change.app.hibernate.beans.diagram.Diagram as subc WHERE subc.deleted!=:deleted AND subc.parentDiagramObject.diagramObjectId = :diagramObjectId AND subc.diagramType.diagramTypeId = :diagramTypeId)]
	
			String hql = "SELECT c FROM Diagram as c, DiagramSummary as ds " +
					"WHERE c.deleted!=:deleted AND ds.deleted!=:deleted " +
					"AND c.parentDiagramObject.diagramObjectId = :diagramObjectId " +
					"AND ds.diagram.diagramId = c.diagramId ";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			List<Diagram> diagrams = query.list();

			log.debug("getDiagramByNo diagrams " + diagrams);

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNoAndParentObjectAndType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNoAndParentObjectAndType]",ex2);
		}
		return null;
	}
	
	
	public List<Diagram> getDiagramSurfAcrossByDiagramObjectIdAndType(Long diagramObjectId, String flowType) {
		try {
			
			log.debug(" getDiagramSurfAcrossByDiagramObjectIdAndType: " + diagramObjectId + " flowType " + flowType);
			
			
//			c near line 1, column 211 [
//SELECT c FROM org.i4change.app.hibernate.beans.diagram.Diagram as c WHERE c.deleted!=:deleted AND c.organisation.organisation_id=:organisation_id AND c.parentDiagramObject.diagramObjectId = :diagramObjectIdAND c.diagramType.diagramTypeId = :diagramTypeId AND c.diagramId=(SELECT MAX(diagramId) FROM org.i4change.app.hibernate.beans.diagram.Diagram as subc WHERE subc.deleted!=:deleted AND subc.parentDiagramObject.diagramObjectId = :diagramObjectId AND subc.diagramType.diagramTypeId = :diagramTypeId)]
	
			String hql = "SELECT c FROM Diagram as c, DiagramSummary as ds, " +
					"DiagramInstanceObject dioInstance " +
					"WHERE c.deleted != :deleted AND ds.deleted != :deleted " +
					"AND dioInstance.diagramObject.diagramObjectId = :diagramObjectId " +
					"AND dioInstance.diagram.diagramId = c.diagramId " +
					"AND ds.diagram.diagramId = c.diagramId " +
					"AND dioInstance.flowType LIKE :flowType";

			Query query = getSession().createQuery(hql);
			query.setLong("diagramObjectId", diagramObjectId);
			query.setString("deleted", "true");
			query.setString("flowType", flowType);
			List<Diagram> diagrams = query.list();

			log.debug("getDiagramSurfAcrossByDiagramObjectIdAndType diagrams " + diagrams.size());

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNoAndParentObjectAndType]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNoAndParentObjectAndType]",ex2);
		}
		return null;
	}
	
	/**
	 * @deprecated
	 * @param organisation_id
	 * @param search
	 * @return
	 */
	public Long _getMaxDiagram(Long organisation_id, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String hql = "SELECT COUNT(*) as number FROM Diagram as c " +
						"WHERE c.deleted!=:deleted " +
						"AND lower(c.name) LIKE lower(:search) " +
						"AND c.organisation.organisation_id=:organisation_id " +
						"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
						"WHERE diagramNo=c.diagramNo) ";

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
	
	/*
	 * ###########################################
	 * General Search Method to get Diagram - List
	 * ###########################################
	 */
	
	public List<Diagram> _getDiagramByNoMaxAndOrder(Long organisation_id, String orderBy, boolean orderAsc, 
			int start, int max, String search) {
		try {
			
			log.debug("diagramNo,organisation_id "+organisation_id);
			
			String orderItemValue = "ASC";
			if (!orderAsc) {
				orderItemValue = "DESC";
			}
			
			String hql = "SELECT c FROM Diagram as c " +
					"WHERE c.deleted!=:deleted " +
					"AND lower(c.name) LIKE lower(:search) " +
					"AND c.organisation.organisation_id=:organisation_id " +
					"AND c.diagramId=(SELECT MAX(diagramId) FROM Diagram " +
					"WHERE diagramNo=c.diagramNo) " +
					"ORDER BY "+orderBy+ " " + orderItemValue;

			Query query = getSession().createQuery(hql);
			query.setLong("organisation_id", organisation_id);
			query.setString("deleted", "true");
			query.setString("search", search);
			query.setMaxResults(max);
			query.setFirstResult(start);
			List<Diagram> diagrams = query.list();

			log.debug("getDiagramByNo diagram " + diagrams.size());

			return diagrams;
		} catch (HibernateException ex) {
			log.error("[getDiagramByNoMax]",ex);
		} catch (Exception ex2) {
			log.error("[getDiagramByNoMax]",ex2);
		}
		return null;
	}
	
	public Long getDiagramHighestNumber() {
		try {
			String hql = "SELECT MAX(diagramNo) FROM Diagram";

			Query query = getSession().createQuery(hql);
			Long diagramNo = (Long) query.uniqueResult();

			log.debug("select getDiagramWithHighestNumber diagramNo " + diagramNo);

			return diagramNo;
		} catch (HibernateException ex) {
			log.error("[addDiagramrevision]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramrevision]",ex2);
		}
		return null;
	}
	
	public Long updateDiagramNameById(String newName, Long user_id, Long diagramId) {
		try {
			
			log.debug("updateDiagramNameById "+diagramId);
			Diagram diagram = this.getDiagramById(diagramId);
			
			diagram.setName(newName);
			diagram.setUpdated(new Date());
			diagram.setUpdatedby(this.userDaoImpl.getUserById(user_id));

			getHibernateTemplate().update(diagram);
			
			return diagramId;
			
		} catch (HibernateException ex) {
			log.error("[addNewDiagramByName]",ex);
		} catch (Exception ex2) {
			log.error("[addNewDiagramByName]",ex2);
		}
		return new Long(-1);
	}
	

	public Long updateDiagramNameByObject(Diagram diagram) {
		try {
			
			//log.debug("[updateDiagramNameByObject] diagram.getDiagramId: "+diagram.getDiagramId());

			getHibernateTemplate().update(diagram);
			
			return diagram.getDiagramId();
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramNameByObject]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramNameByObject]",ex2);
		}
		return new Long(-1);
	}
	
	public Diagram updateDiagramByNo(String name, Long user_id, Long diagramNo, 
			 String comment, Long organisation_id, Long diagramType,
			 boolean read, boolean write, boolean onlyIssues, Object users, 
			 Long diagramObjectId, String shortName) {
		try {
			
			log.debug("updateDiagramByNo "+diagramNo);
			
			Long diagramId = this.addDiagram(name, diagramNo, user_id, 
						comment, organisation_id, diagramType,
					 read, write, onlyIssues, diagramObjectId, shortName);
			
			if (diagramObjectId != null && diagramObjectId != 0) {
        		DiagramObject diaObject = this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId);
        		diaObject.setName(name);
        		diaObject.setUpdated(new Date());
        		diaObject.setUpdatedby(this.userDaoImpl.getUserById(user_id));
        		this.diagramObjectDaoImpl.updateDiagramObjectByObject(diaObject);
        	}
			
			return this.getDiagramById(diagramId);
			
		} catch (HibernateException ex) {
			log.error("[updateDiagramByNo]",ex);
		} catch (Exception ex2) {
			log.error("[updateDiagramByNo]",ex2);
		}
		return null;
	}
	
	public Diagram addNewDiagramByName(String name, Long user_id,
			 String comment, Long organisation_id, Long diagramType,
			 boolean read, boolean write, boolean onlyIssues, Object users, 
			 Long diagramObjectId, String shortName) {
		try {
			
			//log.debug("addNewDiagramByName -4- shortName "+shortName);
			
			Long diagramNo = this.getDiagramHighestNumber();
			if(diagramNo==null){
				diagramNo = new Long(0);
			}
			diagramNo++;
			
			Long diagramId = this.addDiagram(name, diagramNo, user_id, comment, organisation_id, diagramType,
					read,write,onlyIssues, diagramObjectId, shortName);
			
			return this.getDiagramById(diagramId);
			
		} catch (HibernateException ex) {
			log.error("[addNewDiagramByName]",ex);
		} catch (Exception ex2) {
			log.error("[addNewDiagramByName]",ex2);
		}
		return null;
	}
	
	public Long addDiagram(String name, Long diagramNo, Long user_id, String comment, 
			Long organisation_id, Long diagramType,  boolean read, boolean write, 
			boolean onlyIssues, Long diagramObjectId, String shortName) {
		try {
			
			//log.debug("addDiagram -5- shortName "+shortName);
			
			Diagram diagram = new Diagram();
			diagram.setDeleted("false");
			diagram.setInserted(new Date());
			diagram.setInsertedby(this.userDaoImpl.getUserById(user_id));
			diagram.setDiagramNo(diagramNo);
			diagram.setShortName(shortName);
			log.debug("set setDiagramType: "+diagramType);
			diagram.setDiagramType(this.diagramTypeDaoImpl.getDiagramTypeById(diagramType));
			diagram.setDiagramrevision(this.getDiagramrevisionById(this.addDiagramrevision(comment, user_id)));
			diagram.setName(name);
			diagram.setOrganisation(this.organisationDaoImpl.getOrganisationById(organisation_id));
			diagram.setParentDiagramObject(this.diagramObjectDaoImpl.getDiagramObjectById(diagramObjectId));
			
			//Access Params
			diagram.setCanRead(read);
			diagram.setCanWrite(write);
			diagram.setOnlyIssues(onlyIssues);

			return this.addDiagramByObject(diagram);
		} catch (HibernateException ex) {
			log.error("[addDiagram]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagram]",ex2);
		}
		return null;
	}
	
	public Long addDiagramByObject(Diagram diagram) {
		try {

			Long diagramId = (Long) getHibernateTemplate().save(diagram);

			log.debug("added diagramId " + diagramId);

			return diagramId;
		} catch (HibernateException ex) {
			log.error("[addDiagramByObject]",ex);
		} catch (Exception ex2) {
			log.error("[addDiagramByObject]",ex2);
		}
		return null;
	}

	public Long deleteDiagramById(Long user_id, Long diagramId) {
		try {
			
			log.debug("updateDiagramNameById "+diagramId);
			Diagram diagram = this.getDiagramById(diagramId);
			
			diagram.setDeleted("true");
			diagram.setUpdated(new Date());
			diagram.setUpdatedby(this.userDaoImpl.getUserById(user_id));

			getHibernateTemplate().update(diagram);
			
			return diagramId;
			
		} catch (HibernateException ex) {
			log.error("[deleteDiagramById]",ex);
		} catch (Exception ex2) {
			log.error("[deleteDiagramById]",ex2);
		}
		return new Long(-1);
	}

	public int deleteDiagramByNo(Long users_id, Long diagramNo, Long organisation_id) {
		try {
			
			log.debug("deleteDiagramByNo "+diagramNo);
			
			
			String hql = "UPDATE Diagram c SET deleted = 'true' " +
					"WHERE c.diagramNo=:diagramNo " +
					"AND c.organisation.organisation_id=:organisation_id";

			Query query = getSession().createQuery(hql);
			
			query.setLong("diagramNo", diagramNo);
			query.setLong("organisation_id", organisation_id);
			
			int ret = query.executeUpdate();
			
			log.debug("deleteDiagramByNo return "+ret);
			
			return ret;
			
		} catch (HibernateException ex) {
			log.error("[deleteDiagramById]",ex);
		} catch (Exception ex2) {
			log.error("[deleteDiagramById]",ex2);
		}
		return -1;
	}
	
	

}
