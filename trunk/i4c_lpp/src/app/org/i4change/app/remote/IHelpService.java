package org.i4change.app.remote;

import java.util.List;
import java.util.Map;

import org.i4change.app.hibernate.beans.help.HelpTopic;

public interface IHelpService {

	public abstract Long addHelpText(String SID, Long helpId, String helpName,
			boolean isAgentHelp, int priority, String topicText,
			String helpText, Long languages_id);

	public abstract List<HelpTopic> getHelpTopicByHelpIdRange(String SID,
			Map helpIdList);

	public abstract Long editHelpText(String SID, Long helpId, String helpName,
			boolean isAgentHelp, int priority, String topicText,
			String helpText, Long languages_id, Map helpItem);

	public abstract Long deleteHelpTopic(String SID, Long helptopicId);

	public abstract List<HelpTopic> searchHelp(String SID, String helpStr,
			Long languages_id);

}