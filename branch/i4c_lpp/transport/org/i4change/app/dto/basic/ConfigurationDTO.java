package org.i4change.app.dto.basic;

public class ConfigurationDTO {

	private Long configuration_id;
	private String conf_key;
	private String conf_value;	
	private String comment;
	
	public Long getConfiguration_id() {
		return configuration_id;
	}
	public void setConfiguration_id(Long configuration_id) {
		this.configuration_id = configuration_id;
	}
	public String getConf_key() {
		return conf_key;
	}
	public void setConf_key(String conf_key) {
		this.conf_key = conf_key;
	}
	public String getConf_value() {
		return conf_value;
	}
	public void setConf_value(String conf_value) {
		this.conf_value = conf_value;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
