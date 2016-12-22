package co.in.replete.komalindustries.beans;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseWrapper extends BaseWrapper {

	@JsonProperty("userDetails")
	private UserDetailsTO userDetailsTO;

	public LoginResponseWrapper() {}

	public UserDetailsTO getUserDetailsTO() {
		return userDetailsTO;
	}

	public void setUserDetailsTO(UserDetailsTO userDetailsTO) {
		this.userDetailsTO = userDetailsTO;
	}

}
