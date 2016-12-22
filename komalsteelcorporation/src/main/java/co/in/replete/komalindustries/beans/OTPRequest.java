package co.in.replete.komalindustries.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.in.replete.komalindustries.beans.entity.OtpDetails;

public class OTPRequest extends BaseWrapper{

	@JsonProperty("request")
	private OtpDetails otpDetails;

	public OtpDetails getOtpDetails() {
		return otpDetails;
	}

	public void setOtpDetails(OtpDetails otpDetails) {
		this.otpDetails = otpDetails;
	}
	
}
