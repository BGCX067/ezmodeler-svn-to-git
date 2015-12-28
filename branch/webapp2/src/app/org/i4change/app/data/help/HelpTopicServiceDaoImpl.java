package org.i4change.app.data.help;

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.i4change.app.hibernate.utils.HibernateUtil;

public class HelpTopicServiceDaoImpl {
	
	private static final Log log = LogFactory.getLog(HelpTopicServiceDaoImpl.class);	

	private static HelpTopicServiceDaoImpl instance = null;

	public static synchronized HelpTopicServiceDaoImpl getInstance() {
		if (instance == null) {
			instance = new HelpTopicServiceDaoImpl();
		}
		return instance;
	}
	
	public List<HelpTopic> getHelpTopics(Map helpIds) {
		try {
			
			String hql = "SELECT c FROM HelpTopic c " +
					"WHERE c.deleted!=:deleted ";
			
			if (helpIds.size() != 0) {
				hql += "AND ( ";
			}
			
			int k = 0;
			for (Iterator iter = helpIds.keySet().iterator();iter.hasNext();){
				if (k!=0) {
					hql += " OR ";
				}
				k++;
				hql += "c.helpId = " + helpIds.get(iter.next());
			}
			
			if (helpIds.size() != 0) {
				hql += " )";
			}
			
			hql += " ORDER BY priority, helpId";
			log.debug("getHelpTopics hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("deleted", "true");

			List<HelpTopic> helpList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getHelpTopics SIZE: " + helpList.size());

			return helpList;
		} catch (HibernateException ex) {
			log.error("[getHelpTopics]",ex);
		} catch (Exception ex2) {
			log.error("[getHelpTopics]",ex2);
		}
		return null;
	}
	

	public List<HelpTopic> getHelpTopicsByStr(String value, Long language_id) {
		try {
			
			String hql = "SELECT c FROM HelpTopic c, Fieldlanguagesvalues flv " +
						"WHERE c.deleted!=:deleted " +
						"AND ( ( c.labelId = flv.label_number AND flv.language_id = :language_id AND lower(value) LIKE lower(:value)) " +
						"OR ( c.topicLabelId = flv.label_number AND flv.language_id = :language_id AND lower(value) LIKE lower(:value)) ) " +
						" GROUP BY c.helptopicId " +
						" ORDER BY c.priority, c.helpId";
			
			
			
			log.debug("getHelpTopics hql: "+hql);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery(hql);
			query.setString("deleted", "true");

			query.setLong("language_id", language_id);
			query.setString("value", value);
			
			List<HelpTopic> helpList = query.list();
			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("getHelpTopics SIZE: " + helpList.size());

			return helpList;
		} catch (HibernateException ex) {
			log.error("[getHelpTopics]",ex);
		} catch (Exception ex2) {
			log.error("[getHelpTopics]",ex2);
		}
		return null;
	}
	
	public Long addHelpText(Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id) {
		try {
			
			Long maxLabelId = Fieldmanagment.getInstance().selectMaxLabelNumberHelp();
			
			if(maxLabelId == null || maxLabelId < 2000) {
				maxLabelId = new Long(2000);
			} else {
				maxLabelId++;
			}
			log.debug("maxLabelId: "+maxLabelId);
			
			Fieldmanagment.getInstance().addFieldByLabelNumber("HelpLabelText_"+maxLabelId,maxLabelId);
			Fieldmanagment.getInstance().addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, topicText);
			
			maxLabelId++;
			Fieldmanagment.getInstance().addFieldByLabelNumber("HelpLabelTopic_"+maxLabelId,maxLabelId);
			Fieldmanagment.getInstance().addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, helpText);
			
			return this.addHelpTopic(maxLabelId,maxLabelId-1, helpId, helpName, isAgentHelp, priority);
			
		} catch (HibernateException ex) {
			log.error("[addHelpText]",ex);
		} catch (Exception ex) {
			log.error("[addHelpText]",ex);
		}
		return null;
	}
	
	public Long addHelpTopic(Long labelId, Long topicLabelId, Long helpId, 
			String helpName, boolean isAgentHelp, int priority) {
		try {
			
			HelpTopic helpTopic = new HelpTopic();
			helpTopic.setDeleted("false");
			helpTopic.setHelpId(helpId);
			helpTopic.setInserted(new Date());
			helpTopic.setLabelId(labelId);
			helpTopic.setIsAgentHelp(isAgentHelp);
			helpTopic.setPriority(priority);
			helpTopic.setTopicLabelId(topicLabelId);
			helpTopic.setHelpName(helpName);
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			Long helptopicId = (Long) session.save(helpTopic);

			tx.commit();
			HibernateUtil.closeSession(idf);

			log.debug("addHelpTopic: " + helptopicId);

			return helptopicId;
		} catch (HibernateException ex) {
			log.error("[addHelpTopic]",ex);
		} catch (Exception ex2) {
			log.error("[addHelpTopic]",ex2);
		}
		return null;
	}
	
	public HelpTopic getHelpTopicById(Long helptopicId) {
		try {
			
			String hql = "SELECT c FROM HelpTopic c " +
					"WHERE c.helptopicId=:helptopicId " +
					"AND c.deleted!=:deleted";
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setLong("helptopicId", helptopicId);
			query.setString("deleted", "true");

			HelpTopic helptopic = (HelpTopic) query.uniqueResult();

			tx.commit();
			HibernateUtil.closeSession(idf);
			
			return helptopic;
			
		} catch (HibernateException ex) {
			log.error("[getHelpTopicById]",ex);
		} catch (Exception ex2) {
			log.error("[getHelpTopicById]",ex2);
		}
		return null;
	}

	public Long updateHelpTopic(HelpTopic helptopic) {
		try {
			
			Object idf = HibernateUtil.createSession();
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();

			session.update(helptopic);

			tx.commit();
			HibernateUtil.closeSession(idf);

		} catch (HibernateException ex) {
			log.error("[updateHelpTopic]",ex);
		} catch (Exception ex2) {
			log.error("[updateHelpTopic]",ex2);
		}
		return null;
	}
	
}
