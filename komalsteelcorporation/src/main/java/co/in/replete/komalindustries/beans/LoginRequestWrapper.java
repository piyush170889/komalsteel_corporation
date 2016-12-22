package co.in.replete.komalindustries.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestWrapper extends BaseWrapper {

	@NotNull(message="error.request.empty")
	@Valid 
	@JsonProperty("request")
	private UserLoginDtlTO userLoginDtl;
	
	public LoginRequestWrapper() {}

	public UserLoginDtlTO getUserLoginDtl() {
		return userLoginDtl;
	}

	public void setUserLoginDtl(UserLoginDtlTO userLoginDtl) {
		this.userLoginDtl = userLoginDtl;
	}

	
 }
