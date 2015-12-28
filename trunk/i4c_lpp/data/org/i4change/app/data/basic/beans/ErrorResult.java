package org.i4change.app.data.basic.beans;

public class ErrorResult {
	
	private Long errorId;
	private String errmessage;
	private String errortypeAsString;
	Long errorlabelId;
	Long errorTypeLabelId;
	
	public ErrorResult(String errmessage, Long errorId, Long errorTypeLabelId,
			Long errorlabelId, String errortype) {
		super();
		this.errmessage = errmessage;
		this.errorId = errorId;
		this.errorTypeLabelId = errorTypeLabelId;
		this.errorlabelId = errorlabelId;
		this.errortypeAsString = errortype;
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
	public String getErrortypeAsString() {
		return errortypeAsString;
	}
	public void setErrortypeAsString(String errortypeAsString) {
		this.errortypeAsString = errortypeAsString;
	}
	public Long getErrorlabelId() {
		return errorlabelId;
	}
	public void setErrorlabelId(Long errorlabelId) {
		this.errorlabelId = errorlabelId;
	}
	public Long getErrorTypeLabelId() {
		return errorTypeLabelId;
	}
	public void setErrorTypeLabelId(Long errorTypeLabelId) {
		this.errorTypeLabelId = errorTypeLabelId;
	}

	
}
