package org.i4change.app.dt.payment;

import com.paypal.soap.api.CreateRecurringPaymentsProfileResponseType;

public class PaymentStatus {
	
	private CreateRecurringPaymentsProfileResponseType paypalResponse;
	private Boolean status; 
	
	public CreateRecurringPaymentsProfileResponseType getPaypalResponse() {
		return paypalResponse;
	}
	public void setPaypalResponse(CreateRecurringPaymentsProfileResponseType paypalResponse) {
		this.paypalResponse = paypalResponse;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

}
