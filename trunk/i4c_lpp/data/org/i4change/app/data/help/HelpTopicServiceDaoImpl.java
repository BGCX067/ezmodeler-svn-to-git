package org.i4change.app.data.help;

import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HelpTopicServiceDaoImpl extends HibernateDaoSupport {
	
	private static final Log log = LogFactory.getLog(HelpTopicServiceDaoImpl.class);	

	//Spring loaded beans
	private Fieldmanagment fieldmanagment;

	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
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
			
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");

			List<HelpTopic> helpList = query.list();

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
			
			Query query = getSession().createQuery(hql);
			query.setString("deleted", "true");

			query.setLong("language_id", language_id);
			query.setString("value", value);
			
			List<HelpTopic> helpList = query.list();

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
			
			Long maxLabelId = this.fieldmanagment.selectMaxLabelNumberHelp();
			
			if(maxLabelId == null || maxLabelId < 2000) {
				maxLabelId = new Long(2000);
			} else {
				maxLabelId++;
			}
			log.debug("maxLabelId: "+maxLabelId);
			
			this.fieldmanagment.addFieldByLabelNumber("HelpLabelText_"+maxLabelId,maxLabelId);
			this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
					maxLabelId, languages_id, topicText);
			
			maxLabelId++;
			this.fieldmanagment.addFieldByLabelNumber("HelpLabelTopic_"+maxLabelId,maxLabelId);
			this.fieldmanagment.addFieldValueByLabeldNumberAndLanguage(
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
			
			Long helptopicId = (Long) getHibernateTemplate().save(helpTopic);

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
			
			Query query = getSession().createQuery(hql);
			query.setLong("helptopicId", helptopicId);
			query.setString("deleted", "true");

			HelpTopic helptopic = (HelpTopic) query.uniqueResult();

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
			
			getHibernateTemplate().update(helptopic);

		} catch (HibernateException ex) {
			log.error("[updateHelpTopic]",ex);
		} catch (Exception ex2) {
			log.error("[updateHelpTopic]",ex2);
		}
		return null;
	}
	
}
