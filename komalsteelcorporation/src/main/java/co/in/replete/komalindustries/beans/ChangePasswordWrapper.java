package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordWrapper extends BaseWrapper{
	

	@JsonProperty("request")
	@Valid
	private ChangePasswordTO changePassword;
	
	ChangePasswordWrapper(){}

	public ChangePasswordTO getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(ChangePasswordTO changePassword) {
		this.changePassword = changePassword;
	}
	
}
