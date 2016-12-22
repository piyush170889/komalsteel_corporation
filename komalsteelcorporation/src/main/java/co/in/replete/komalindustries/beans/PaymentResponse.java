package co.in.replete.komalindustries.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponse extends BaseWrapper {

	@JsonProperty("paymentUrlDetails")
	private SingleValueCommonClass singleValueCommonClass;
	
	public PaymentResponse() {}

	public SingleValueCommonClass getSingleValueCommonClass() {
		return singleValueCommonClass;
	}

	public void setSingleValueCommonClass(SingleValueCommonClass singleValueCommonClass) {
		this.singleValueCommonClass = singleValueCommonClass;
	}
	
}
