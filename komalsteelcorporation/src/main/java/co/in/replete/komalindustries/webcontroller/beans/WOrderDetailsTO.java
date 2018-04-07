package co.in.replete.komalindustries.webcontroller.beans;

import java.util.Date;
import java.util.List;

import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;

public class WOrderDetailsTO {

	private String trackId;
	private String firstName;
	
	private String lastName;
	
	private String cnctNum;
	
	private String status;
	
	private String paymentMode;
	
	private String paymentStatus;
	
	private String city;
	
	private String country;
	
	private String postalCode;
	
	private String state;
	
	private String street1;
	
	private String street2;
	
	private String street3;
	
	private String deliveryType;
	
	private String notes;
	
	private String orderPrice;
	
	private Date orderDate;
	
	private Date deliveryDate;
	
	private  int cartDtlId;
	
	private int invoiceId;
	
	private String lrNo;
	
	private Date lrNoDate;
	
	private String noOfCartonLoaded;
	
	private String mark;
	
	private String destination;
	
	private String transNm;
	
	private String vatTinNo;
	
	private String courierNm;
	
	private String docateNo;
	
	private String delvryDate;
	
	private List<ItemMasterDtl> orderedItems;
	
	public WOrderDetailsTO(){}
	
	public String getCourierNm() {
		return courierNm;
	}


	public void setCourierNm(String courierNm) {
		this.courierNm = courierNm;
	}


	public String getDocateNo() {
		return docateNo;
	}


	public void setDocateNo(String docateNo) {
		this.docateNo = docateNo;
	}


	public String getDelvryDate() {
		return delvryDate;
	}


	public void setDelvryDate(String delvryDate) {
		this.delvryDate = delvryDate;
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

	public String getTransNm() {
		return transNm;
	}

	public void setTransNm(String transNm) {
		this.transNm = transNm;
	}

	public String getVatTinNo() {
		return vatTinNo;
	}

	public void setVatTinNo(String vatTinNo) {
		this.vatTinNo = vatTinNo;
	}

	public Date getLrNoDate() {
		return lrNoDate;
	}

	public void setLrNoDate(Date lrNoDate) {
		this.lrNoDate = lrNoDate;
	}

	public String getNoOfCartonLoaded() {
		return noOfCartonLoaded;
	}

	public void setNoOfCartonLoaded(String noOfCartonLoaded) {
		this.noOfCartonLoaded = noOfCartonLoaded;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public List<ItemMasterDtl> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<ItemMasterDtl> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public String getCnctNum() {
		return cnctNum;
	}

	public void setCnctNum(String cnctNum) {
		this.cnctNum = cnctNum;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet3() {
		return street3;
	}

	public void setStreet3(String street3) {
		this.street3 = street3;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getCartDtlId() {
		return cartDtlId;
	}

	public void setCartDtlId(int cartDtlId) {
		this.cartDtlId = cartDtlId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

}
