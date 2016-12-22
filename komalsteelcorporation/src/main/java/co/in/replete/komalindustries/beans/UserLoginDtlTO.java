package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginDtlTO {

	@NotNull(message="error.cmpnyInfoId.required")
	@NotEmpty(message="error.cmpnyInfoId.required")
	private String cmpnyInfoId;
	
	@NotNull(message="error.loginId.required")
	@NotEmpty(message="error.loginId.required")
	private String loginId;
	
	@NotNull(message="error.password.required")
	@NotEmpty(message="error.password.required")
	private String password;
	
	private String userType;
	
	public UserLoginDtlTO() {}

	public String getCompanyInfoId() {
		return cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
