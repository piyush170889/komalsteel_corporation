package co.in.replete.komalindustries.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import co.in.replete.komalindustries.beans.entity.UserDetailsAssociatedTO;

public class UserDetailsTO {

	
	@NotNull(message="error.firstname.required")
	@NotEmpty(message="error.firstname.required")
    private String firstName; 
	
    private String middleName; 
     
    private String lastName;
     
    public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private String loginDtlsId;
	
    @NotNull(message="error.panno.required")
    @NotEmpty(message="error.panno.required")
    private String panNo; 
     
    private Date dob;
     
    @NotNull(message="error.gender.required")
    @NotEmpty(message="error.gender.required")
    private String gender;
	 
	 @NotNull(message="error.contactno.required")
	 @NotEmpty(message="error.contactno.required")
	 private String cntc_num;
	 
	 @NotNull(message="error.companyinfoid.required")
	 @NotEmpty(message="error.companyinfoid.required")
	 private String cmpnyInfoId;
	 
	 @NotNull(message="error.loginid.required")
	 @NotEmpty(message="error.loginid.required")
	 private String loginId;
	 
	 @NotNull(message="error.usertype.required")
	 @NotEmpty(message="error.usertype.required")
	 private String userType;
	 
	 @NotNull(message="error.password.required")
	 @NotEmpty(message="error.password.required")
	 private String password;
     
     private String status;	
     
     private String displayName;
     
     private String userTrackId;
     
     private List<UserDetailsAssociatedTO> associatedDistributorsList;
  
     private String stAddress1;
     
     private String stAddress2;
     
     private String stAddress3;
     
     private String city;
     
     private String state;
     
     private String country;
     
     private String postalCode;
     
     private BigDecimal longitude;
     
     private BigDecimal latitude;
     
     private String addressType;
     
     private int OtherAddressId;
     
     private String mark;
 	
 	private String destination;
 	
 	private String tranNm;
 	
 	private String tinNo;
 	
	public UserDetailsTO() {	}


	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getTranNm() {
		return tranNm;
	}


	public void setTranNm(String tranNm) {
		this.tranNm = tranNm;
	}


	public String getStAddress1() {
		return stAddress1;
	}


	public String getLoginDtlsId() {
		return loginDtlsId;
	}


	public void setLoginDtlsId(String loginDtlsId) {
		this.loginDtlsId = loginDtlsId;
	}


	public void setStAddress1(String stAddress1) {
		this.stAddress1 = stAddress1;
	}


	public String getStAddress2() {
		return stAddress2;
	}


	public void setStAddress2(String stAddress2) {
		this.stAddress2 = stAddress2;
	}


	public String getStAddress3() {
		return stAddress3;
	}


	public void setStAddress3(String stAddress3) {
		this.stAddress3 = stAddress3;
	}


	public BigDecimal getLongitude() {
		return longitude;
	}


	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}


	public BigDecimal getLatitude() {
		return latitude;
	}


	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}


	public String getAddressType() {
		return addressType;
	}


	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}


	public int getOtherAddressId() {
		return OtherAddressId;
	}

	public void setOtherAddressId(int otherAddressId) {
		OtherAddressId = otherAddressId;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public List<UserDetailsAssociatedTO> getAssociatedDistributorsList() {
		return associatedDistributorsList;
	}


	public void setAssociatedDistributorsList(List<UserDetailsAssociatedTO> associatedDistributorsList2) {
		this.associatedDistributorsList = associatedDistributorsList2;
	}


	public String getUserTrackId() {
		return userTrackId;
	}

	public void setUserTrackId(String userTrackId) {
		this.userTrackId = userTrackId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
