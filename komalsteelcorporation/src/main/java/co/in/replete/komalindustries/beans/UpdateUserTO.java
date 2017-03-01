package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateUserTO {

	@NotNull(message="error.firstname.required")
	@NotEmpty(message="error.firstname.required")
    private String firstName; 
	
	@NotNull(message="error.email.required")
	private String email;
	
	@NotNull(message="error.lastName.required")
    private String lastName;
     
	 @NotNull(message="error.displayName.required")
	 private String displayName;
	 
	 @NotNull(message="error.pincode.required")
	 private String pincode;
	 
	 @NotNull(message="error.address.required")
	 private String address;
	 
	 @NotNull(message="error.city.required")
	 private String city;
	 
	 @NotNull(message="error.state.required")
	 private String state;

	 private String mark;
	 
	 private String destination;
	 
	 private String tranNm;
	 
	 private String tinNo;
	 
	 private String vatNo;
	 
	 private String panNo;
	 
     public UpdateUserTO() {}

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

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
     
}
