package co.in.replete.komalindustries.beans;

import java.util.Date;
import java.util.List;

public class NewCartDetailsTO {
	
	private  int cartDtlsId;
	
	private Date expDlvryDate;
	
	private Date actualDlvryDate;
	
	private String dlvryType;
	
	private String firstName;
	
	private String lastName;
	
	private double cartPrice;
	
	private String cartNotes;
	
	private String cartStatus;
	
	private String stAddress1;
	
	private String stAddress2;
	
	private String stAddress3;
	
	private String addressType;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String postalCode;
	
	private Date orderDate;

	private String lrNo;
	
	private String lrNoDate;
	
	private String noOfCartonLoaded;
	
	private List<CartItemDetailsListTO> itemOrderDetailsList;
	
	public NewCartDetailsTO(){}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public String getLrNoDate() {
		return lrNoDate;
	}

	public void setLrNoDate(String lrNoDate) {
		this.lrNoDate = lrNoDate;
	}

	public String getNoOfCartonLoaded() {
		return noOfCartonLoaded;
	}

	public void setNoOfCartonLoaded(String noOfCartonLoaded) {
		this.noOfCartonLoaded = noOfCartonLoaded;
	}

	public int getCartDtlsId() {
		return cartDtlsId;
	}

	public void setCartDtlsId(int cartDtlsId) {
		this.cartDtlsId = cartDtlsId;
	}

	public Date getExpDlvryDate() {
		return expDlvryDate;
	}

	public void setExpDlvryDate(Date expDlvryDate) {
		this.expDlvryDate = expDlvryDate;
	}

	public Date getActualDlvryDate() {
		return actualDlvryDate;
	}

	public void setActualDlvryDate(Date actualDlvryDate) {
		this.actualDlvryDate = actualDlvryDate;
	}

	public String getDlvryType() {
		return dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}

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

	public double getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(double cartPrice) {
		this.cartPrice = cartPrice;
	}

	public String getCartNotes() {
		return cartNotes;
	}

	public void setCartNotes(String cartNotes) {
		this.cartNotes = cartNotes;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public String getStAddress1() {
		return stAddress1;
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

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
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

	public List<CartItemDetailsListTO> getItemOrderDetailsList() {
		return itemOrderDetailsList;
	}

	public void setItemOrderDetailsList(List<CartItemDetailsListTO> itemOrderDetailsList) {
		this.itemOrderDetailsList = itemOrderDetailsList;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
