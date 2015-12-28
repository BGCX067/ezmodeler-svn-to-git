package org.i4change.app.data.basic.beans;

public class ErrorResult {
	
	private Long errorId;
	private String errmessage;
	private String Errortypes;

	public ErrorResult(Long errorId, String errmessage, String Errortypes) {
		super();
		this.errorId = errorId;
		this.errmessage = errmessage;
		this.Errortypes = Errortypes;
	}

	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long errorId) {
		this.errorId = errorId;
	}

	public String getErrmessage() {
		return errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	public String getErrortypes() {
		return Errortypes;
	}

	public void setErrortypes(String Errortypes) {
		this.Errortypes = Errortypes;
	}

}
