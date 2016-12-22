package co.in.replete.komalindustries.beans;

import java.util.Date;

public class UserOrderDetailsTO {

	private int cartDtlId;
	
	private Date orderDate;
	
	private String cartStatus;
	
	private double cartPrice;
	
	private String paymentStatus;
	
	private String cartNotes;
	
	private String lrNo;
    
    private String lrDate;
    
    private String itemsLoaded;
        
    private Date expectedDlvrDate;    
    
    private String alternateContactNo;
    
    private String st1;
    
    private String city;
    
    private String state;
    
	private int invoiceId;
	
	private double subTotal;
	
	private double discount;
	
	private double discountValue;
	
	private double serviceTax;
	
	private double serviceTaxValue;
	
	private double vat;
	
	private double vatValue;
	
	private double miscCharges;
	
	private double grandTotal;
	
	private double amountPaid;
	
	private double amountBal;
	
    private String firstName;
    
    private String taxRefNo;
    
    private String payMentMode;
    
/*    private int shippingAddressId;
    
    private String country;
    
    private String postalCode;
    
    private String paymentdtlsId;
    
    private String paymentGateway;
    
    private String bankRefNo;
    
    private String txnData;
    
    private double paymentAmount;
    
    private String lastName;
*/    
    private String mark;
    
    private String destination;
	
	private String tranNm;
	
	private String tinNo;
	
	public UserOrderDetailsTO(){}

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

	public String getCartNotes() {
		return cartNotes;
	}

	public void setCartNotes(String cartNotes) {
		this.cartNotes = cartNotes;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public String getLrDate() {
		return lrDate;
	}

	public void setLrDate(String lrDate) {
		this.lrDate = lrDate;
	}

	public String getItemsLoaded() {
		return itemsLoaded;
	}

	public void setItemsLoaded(String itemsLoaded) {
		this.itemsLoaded = itemsLoaded;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getServiceTaxValue() {
		return serviceTaxValue;
	}

	public void setServiceTaxValue(double serviceTaxValue) {
		this.serviceTaxValue = serviceTaxValue;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getVatValue() {
		return vatValue;
	}

	public void setVatValue(double vatValue) {
		this.vatValue = vatValue;
	}

/*	public double getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}*/

	public double getMiscCharges() {
		return miscCharges;
	}

	public void setMiscCharges(double miscCharges) {
		this.miscCharges = miscCharges;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getAmountBal() {
		return amountBal;
	}

	public void setAmountBal(double amountBal) {
		this.amountBal = amountBal;
	}

/*	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
*/
	public int getCartDtlId() {
		return cartDtlId;
	}

	public void setCartDtlId(int cartDtlId) {
		this.cartDtlId = cartDtlId;
	}

	public double getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(double cartPrice) {
		this.cartPrice = cartPrice;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

/*	public int getCartdtlsId() {
		return cartdtlsId;
	}

	public void setCartdtlsId(int cartdtlsId) {
		this.cartdtlsId = cartdtlsId;
	}
*/
	public Date getExpectedDlvrDate() {
		return expectedDlvrDate;
	}

	public void setExpectedDlvrDate(Date expectedDlvrDate) {
		this.expectedDlvrDate = expectedDlvrDate;
	}

/*	public Date getActualDlvryDate() {
		return actualDlvryDate;
	}

	public void setActualDlvryDate(Date actualDlvryDate) {
		this.actualDlvryDate = actualDlvryDate;
	}

	public String getDeliveyBy() {
		return deliveyBy;
	}

	public void setDeliveyBy(String deliveyBy) {
		this.deliveyBy = deliveyBy;
	}

	public String getDlvryType() {
		return dlvryType;
	}

	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}*/

	public String getAlternateContactNo() {
		return alternateContactNo;
	}

	public void setAlternateContactNo(String alternateContactNo) {
		this.alternateContactNo = alternateContactNo;
	}

/*	public int getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(int shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}*/

	public String getSt1() {
		return st1;
	}

	public void setSt1(String st1) {
		this.st1 = st1;
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

/*	public String getCountry() {
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

	public String getPaymentdtlsId() {
		return paymentdtlsId;
	}

	public void setPaymentdtlsId(String paymentdtlsId) {
		this.paymentdtlsId = paymentdtlsId;
	}*/

	public String getTaxRefNo() {
		return taxRefNo;
	}

	public void setTaxRefNo(String taxRefNo) {
		this.taxRefNo = taxRefNo;
	}

	public String getPayMentMode() {
		return payMentMode;
	}

	public void setPayMentMode(String payMentMode) {
		this.payMentMode = payMentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

/*	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public String getTxnData() {
		return txnData;
	}

	public void setTxnData(String txnData) {
		this.txnData = txnData;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}*/

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

/*	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}*/

	
}
