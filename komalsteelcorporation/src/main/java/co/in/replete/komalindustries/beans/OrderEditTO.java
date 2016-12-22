package co.in.replete.komalindustries.beans;

public class OrderEditTO {

	private int orderId;
	
	private String orderStatus;
	
	private String dlvryAddress;
	
	private String paymentstatus;
	
	private String orderprice;
	
	private String orderDate;
	
	private String dlvryDate;
	
	private String city;
	
	private String state;
	
	private String paymode;
	
	private String dlvryAddr;
	
	private String alternateContact;
	
	private String discountVaue;
	
	public OrderEditTO() {}

	public String getAlternateContact() {
		return alternateContact;
	}

	public void setAlternateContact(String alternateContact) {
		this.alternateContact = alternateContact;
	}

	public String getDiscountVaue() {
		return discountVaue;
	}

	public void setDiscountVaue(String discountVaue) {
		this.discountVaue = discountVaue;
	}

	public String getDlvryAddr() {
		return dlvryAddr;
	}

	public void setDlvryAddr(String dlvryAddr) {
		this.dlvryAddr = dlvryAddr;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDlvryAddress() {
		return dlvryAddress;
	}

	public void setDlvryAddress(String dlvryAddress) {
		this.dlvryAddress = dlvryAddress;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(String orderprice) {
		this.orderprice = orderprice;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDlvryDate() {
		return dlvryDate;
	}

	public void setDlvryDate(String dlvryDate) {
		this.dlvryDate = dlvryDate;
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

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	
	
	
}
