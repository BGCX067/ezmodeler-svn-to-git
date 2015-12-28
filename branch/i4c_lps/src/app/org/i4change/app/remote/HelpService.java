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

public class HelpService {

	private static final Log log = LogFactory.getLog(HelpService.class);	
	
	public Long addHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
	        	return HelpTopicServiceDaoImpl.getInstance().addHelpText(helpId, helpName, isAgentHelp, priority, 
						topicText, helpText, languages_id);
				
	        }
			
		} catch (Exception err) {
			log.error("[addHelpText]",err);
		}
		return new Long(-1);
	}
	
	public List<HelpTopic> getHelpTopicByHelpIdRange(String SID, Map helpIdList) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	return HelpTopicServiceDaoImpl.getInstance().getHelpTopics(helpIdList);
	        }
	        
		} catch (Exception err) {
			log.error("[addHelpText]",err);
		}
		return null;
	}
	
	public Long editHelpText(String SID, Long helpId, String helpName, boolean isAgentHelp, int priority, 
			String topicText, String helpText, Long languages_id, Map helpItem) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
	        	
	        	Long labelId = Long.valueOf(helpItem.get("labelId").toString()).longValue();
	        	Long topicLabelId = Long.valueOf(helpItem.get("topicLabelId").toString()).longValue();
	        	
	        	Long helptopicId = Long.valueOf(helpItem.get("helptopicId").toString()).longValue();
	        	
	        	Fieldlanguagesvalues fieldValueTopicLabel = Fieldmanagment.getInstance().
				getFieldlanguagesvaluesByLabelAndLang(topicLabelId, languages_id);
				fieldValueTopicLabel.setValue(topicText);
				Fieldmanagment.getInstance().updateFieldValueByFieldAndLanguage(fieldValueTopicLabel);

	        	Fieldlanguagesvalues fieldValueLabel = Fieldmanagment.getInstance().
	        						getFieldlanguagesvaluesByLabelAndLang(labelId, languages_id);
	        	fieldValueLabel.setValue(helpText);
	        	Fieldmanagment.getInstance().updateFieldValueByFieldAndLanguage(fieldValueLabel);
	        	
	        	HelpTopic helpTopic = HelpTopicServiceDaoImpl.getInstance().getHelpTopicById(helptopicId);
	        
	        	helpTopic.setUpdated(new Date());
	        	helpTopic.setPriority(priority);
	        	helpTopic.setIsAgentHelp(isAgentHelp);
	        	
	        	HelpTopicServiceDaoImpl.getInstance().updateHelpTopic(helpTopic);
	        	
	        	return helptopicId;
	        }
			
		} catch (Exception err) {
			log.error("[editHelpText]",err);
		}
		return new Long(-1);
	}
	
	public Long deleteHelpTopic(String SID, Long helptopicId) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkModLevel(user_level)) {
	        	
	        	HelpTopic helpTopic = HelpTopicServiceDaoImpl.getInstance().getHelpTopicById(helptopicId);
	        
	        	helpTopic.setUpdated(new Date());
	        	helpTopic.setDeleted("true");
	        	
	        	HelpTopicServiceDaoImpl.getInstance().updateHelpTopic(helpTopic);
	        	
	        	return helptopicId;
	        }
			
		} catch (Exception err) {
			log.error("[editHelpText]",err);
		}
		return new Long(-1);
	}
	
	public List<HelpTopic> searchHelp(String SID, String helpStr, Long languages_id) {
		try {
	        Long users_id = Sessionmanagement.getInstance().checkSession(SID);
	        Long user_level = UserDaoImpl.getInstance().getUserLevelByID(users_id);
	        
	        if (AuthLevelmanagement.getInstance().checkUserLevel(user_level)) {
	        	
	        	return HelpTopicServiceDaoImpl.getInstance().getHelpTopicsByStr(helpStr, new Long(1));
	        
	        }
			
		} catch (Exception err) {
			log.error("[searchHelp]",err);
		}
		return null;
	}
	
}
