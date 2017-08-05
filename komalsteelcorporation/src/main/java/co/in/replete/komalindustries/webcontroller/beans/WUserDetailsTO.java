package co.in.replete.komalindustries.webcontroller.beans;

import java.util.Date;

public class WUserDetailsTO {

	private String userTrackid;
	
	private String userFirstName;
	
	private String userLastName;
	
	private String displayName;
	
	private String status;
	
	private String panNo;
	
	private String vatNo;
	
	private String contactNo;
	
	private String emailId;
	
	private String userType;
	
	private Date regDate;
	
	private String stAddress1;
	
	private String state;
	
	private String city;
	
	private String pincode;
	
	private String associatedDistributor;
	
	private int otherAddressDtlsId;
	
	private int userDistributorListId;
	
	private String mark;

	private String destination;
	
	private String transportName;
	
	private String gstNo;
	
	private Float discount;
	
	public WUserDetailsTO(){}

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

	public int getUserDistributorListId() {
		return userDistributorListId;
	}

	public void setUserDistributorListId(int userDistributorListId) {
		this.userDistributorListId = userDistributorListId;
	}

	public String getStAddress1() {
		return stAddress1;
	}


	public void setStAddress1(String stAddress1) {
		this.stAddress1 = stAddress1;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getAssociatedDistributor() {
		return associatedDistributor;
	}


	public void setAssociatedDistributor(String associatedDistributor) {
		this.associatedDistributor = associatedDistributor;
	}


	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}



	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getUserTrackid() {
		return userTrackid;
	}

	public void setUserTrackid(String userTrackid) {
		this.userTrackid = userTrackid;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	
}
