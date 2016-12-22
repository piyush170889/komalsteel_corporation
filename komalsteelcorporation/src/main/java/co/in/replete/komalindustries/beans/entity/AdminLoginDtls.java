package co.in.replete.komalindustries.beans.entity;

public class AdminLoginDtls {

	private String adminLoginDtlsId;
	
	private String emailId;
	
	private String password;
	
	private String userType;
	
	private String cntcNum;
	
	public AdminLoginDtls() {}

	public String getAdminLoginDtlsId() {
		return adminLoginDtlsId;
	}

	public void setAdminLoginDtlsId(String adminLoginDtlsId) {
		this.adminLoginDtlsId = adminLoginDtlsId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getCntcNum() {
		return cntcNum;
	}

	public void setCntcNum(String cntcNum) {
		this.cntcNum = cntcNum;
	}
	
}
