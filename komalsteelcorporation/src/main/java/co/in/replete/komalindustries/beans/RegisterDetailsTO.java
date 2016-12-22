package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


public class RegisterDetailsTO {

	
	 @NotNull(message="error.firstname.required")
	 @NotEmpty(message="error.firstname.required")
     private String firstName; 
	
     private String lastName;
     
     private String vatTinNo;
     
     private String panNo; 
     
	 @NotNull(message="error.contactno.required")
	 @NotEmpty(message="error.contactno.required")
	 private String cntc_num;
	 
	 @NotNull(message="error.companyinfoid.required")
	 @NotEmpty(message="error.companyinfoid.required")
	 private String cmpnyInfoId;
	 
	 private String loginId;
	 
	 private String userType;
	 
	 @NotNull(message="error.password.required")
	 @NotEmpty(message="error.password.required")
	 private String password;
     
     private String displayName;
     
     public RegisterDetailsTO() {	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getVatTinNo() {
		return vatTinNo;
	}

	public void setVatTinNo(String vatTinNo) {
		this.vatTinNo = vatTinNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getCntc_num() {
		return cntc_num;
	}

	public void setCntc_num(String cntc_num) {
		this.cntc_num = cntc_num;
	}

	public String getCmpnyInfoId() {
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
   
     
}
