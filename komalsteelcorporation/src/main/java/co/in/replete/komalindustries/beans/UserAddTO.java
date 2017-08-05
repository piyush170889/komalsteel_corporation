package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserAddTO {

	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String displayName;
	
	private String emailId;
	
	private String status;
	
	private String vatNo;
	
	private String panNo;
	
	private String city;
	
	private String state;
	
	private String contactNo;
	
	private String userType;

	private String associatedDistributor;
	
	private String pincode;
	
	private String staddress3;
	
	private String staddress2;
	
	private String staddress1;
	
	private int otherAddressDtlsId;
	
	private int userDistributorListId;
	
	private String mark;
	
	private String destination;
	
	private String transportName;	
	
	@NotNull(message="error.gstno.required")
	@NotEmpty(message="error.gstno.required")
	private String gstNo;
	
	@NotNull(message="error.discount.required")
	@NotEmpty(message="error.discount.required")
	private Float discount;
	
	public UserAddTO() {}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

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

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public int getOtherAddressDtlsId() {
		return otherAddressDtlsId;
	}

	public void setOtherAddressDtlsId(int otherAddressDtlsId) {
		this.otherAddressDtlsId = otherAddressDtlsId;
	}

	public Integer getUserDistributorListId() {
		return userDistributorListId;
	}

	public void setUserDistributorListId(int userDistributorListId) {
		this.userDistributorListId = userDistributorListId;
	}

	public String getAssociatedDistributor() {
		return associatedDistributor;
	}

	public void setAssociatedDistributor(String associatedDistributor) {
		this.associatedDistributor = associatedDistributor;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getStaddress3() {
		return staddress3;
	}

	public void setStaddress3(String staddress3) {
		this.staddress3 = staddress3;
	}

	public String getStaddress2() {
		return staddress2;
	}

	public void setStaddress2(String staddress2) {
		this.staddress2 = staddress2;
	}

	public String getStaddress1() {
		return staddress1;
	}

	public void setStaddress1(String staddress1) {
		this.staddress1 = staddress1;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
