package org.i4change.app.remote;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.i4change.app.data.basic.AuthLevelmanagement;
import org.i4change.app.data.basic.Fieldmanagment;
import org.i4change.app.data.basic.Sessionmanagement;
import org.i4change.app.data.help.HelpTopicServiceDaoImpl;
import org.i4change.app.data.user.daos.UserDaoImpl;
import org.i4change.app.hibernate.beans.help.HelpTopic;
import org.i4change.app.hibernate.beans.lang.Fieldlanguagesvalues;

public class HelpService implements IHelpService {
	
	//Spring managed beans
	private Fieldmanagment fieldmanagment;
	private Sessionmanagement sessionmanagement;
	private UserDaoImpl userDaoImpl;
	private HelpTopicServiceDaoImpl helpTopicServiceDaoImpl;
	
	public Fieldmanagment getFieldmanagment() {
		return fieldmanagment;
	}
	public void setFieldmanagment(Fieldmanagment fieldmanagment) {
		this.fieldmanagment = fieldmanagment;
	}
	
	public Sessionmanagement getSessionmanagement() {
		return sessionmanagement;
	}
	public void setSessionmanagement(Sessionmanagement sessionmanagement) {
		this.sessionmanagement = sessionmanagement;
	}
	
	public UserDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}
	public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}
	
	public HelpTopicServiceDaoImpl getHelpTopicServiceDaoImpl() {
		return helpTopicServiceDaoImpl;
	}
	public void setHelpTopicServiceDaoImpl(
			HelpTopicServiceDaoImpl helpTopicServiceDaoImpl) {
		this.helpTopicServiceDaoImpl = helpTopicServiceDaoImpl;
	}


	private static final Log log = LogFactory.getLog(HelpService.class);	
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IHelpService#addHelpText(java.lang.String, java.lang.Long, java.lang.String, boolean, int, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public Long addHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkModLevel(user_level)) {
	        	return this.helpTopicServiceDaoImpl.addHelpText(helpId, helpName, isAgentHelp, priority, 
						topicText, helpText, languages_id);
				
	        }
			
		} catch (Exception err) {
			log.error("[addHelpText]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IHelpService#getHelpTopicByHelpIdRange(java.lang.String, java.util.Map)
	 */
	public List<HelpTopic> getHelpTopicByHelpIdRange(String SID, Map helpIdList) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	return this.helpTopicServiceDaoImpl.getHelpTopics(helpIdList);
	        }
	        
		} catch (Exception err) {
			log.error("[addHelpText]",err);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IHelpService#editHelpText(java.lang.String, java.lang.Long, java.lang.String, boolean, int, java.lang.String, java.lang.String, java.lang.Long, java.util.Map)
	 */
	public Long editHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id, Map helpItem) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkModLevel(user_level)) {
	        	
	        	Long labelId = Long.valueOf(helpItem.get("labelId").toString()).longValue();
	        	Long topicLabelId = Long.valueOf(helpItem.get("topicLabelId").toString()).longValue();
	        	
	        	Long helptopicId = Long.valueOf(helpItem.get("helptopicId").toString()).longValue();
	        	
	        	Fieldlanguagesvalues fieldValueTopicLabel = this.fieldmanagment.
				getFieldlanguagesvaluesByLabelAndLang(topicLabelId, languages_id);
				fieldValueTopicLabel.setValue(topicText);
				this.fieldmanagment.updateFieldValueByFieldAndLanguage(fieldValueTopicLabel);

	        	Fieldlanguagesvalues fieldValueLabel = this.fieldmanagment.
	        						getFieldlanguagesvaluesByLabelAndLang(labelId, languages_id);
	        	fieldValueLabel.setValue(helpText);
	        	this.fieldmanagment.updateFieldValueByFieldAndLanguage(fieldValueLabel);
	        	
	        	HelpTopic helpTopic = this.helpTopicServiceDaoImpl.getHelpTopicById(helptopicId);
	        
	        	helpTopic.setUpdated(new Date());
	        	helpTopic.setPriority(priority);
	        	helpTopic.setIsAgentHelp(isAgentHelp);
	        	
	        	this.helpTopicServiceDaoImpl.updateHelpTopic(helpTopic);
	        	
	        	return helptopicId;
	        }
			
		} catch (Exception err) {
			log.error("[editHelpText]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IHelpService#deleteHelpTopic(java.lang.String, java.lang.Long)
	 */
	public Long deleteHelpTopic(String SID, Long helptopicId) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkModLevel(user_level)) {
	        	
	        	HelpTopic helpTopic = this.helpTopicServiceDaoImpl.getHelpTopicById(helptopicId);
	        
	        	helpTopic.setUpdated(new Date());
	        	helpTopic.setDeleted("true");
	        	
	        	this.helpTopicServiceDaoImpl.updateHelpTopic(helpTopic);
	        	
	        	return helptopicId;
	        }
			
		} catch (Exception err) {
			log.error("[editHelpText]",err);
		}
		return new Long(-1);
	}
	
	/* (non-Javadoc)
	 * @see org.i4change.app.remote.IHelpService#searchHelp(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public List<HelpTopic> searchHelp(String SID, String helpStr, Long languages_id) {
		try {
	        Long users_id = this.sessionmanagement.checkSession(SID);
	        Long user_level = this.userDaoImpl.getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.checkUserLevel(user_level)) {
	        	
	        	return this.helpTopicServiceDaoImpl.getHelpTopicsByStr(helpStr, new Long(1));
	        
	        }
			
		} catch (Exception err) {
			log.error("[searchHelp]",err);
		}
		return null;
	}
	
}
