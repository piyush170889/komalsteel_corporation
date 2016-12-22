package co.in.replete.komalindustries.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.InvoiceDtl;
import co.in.replete.komalindustries.beans.entity.PaymentDtl;
import co.in.replete.komalindustries.beans.entity.ShippingAddressDetail;
import co.in.replete.komalindustries.utils.UDValues;

public class CartDetailsTO {
	private String cartDtlsId;
	private String cartNotes;
	
	@NotNull(message="error.cartPrice.required")
	@NotEmpty(message="error.cartPrice.required")
	private String cartPrice;
	@NotNull(message="error.isOfferApld.required")
	@NotEmpty(message="error.isOfferApld.required")
	private String isOfferApld;
	
	@NotNull(message="error.offerApldId.required")
	@NotEmpty(message="error.offerApldId.required")
	private String offerApldId;
	
	private String cartStatus;
	private String actualDlvryDt;
	@NotNull(message="error.alternateCntc.required")
	@NotEmpty(message="error.alternateCntc.required")
	private String alternateCntc;
	private String dlvryByTrackId;
	@NotNull(message="error.dlvryType.required")
	@NotEmpty(message="error.dlvryType.required")
	private String dlvryType;
	private String expDlvryDt;
	@NotNull(message="error.addressType.required")
	@NotEmpty(message="error.addressType.required")
	private String addressType;
	@NotNull(message="error.city.required")
	@NotEmpty(message="error.city.required")
	private String city;
	@NotNull(message="error.country.required")
	@NotEmpty(message="error.country.required")
	private String country;
	private String latitude;
	private String longitude;
	@NotNull(message="error.postalCode.required")
	@NotEmpty(message="error.postalCode.required")
	private String postalCode;
	@NotNull(message="error.stAddress1.required")
	@NotEmpty(message="error.stAddress1.required")
	private String stAddress1;
	private String stAddress2;
	private String stAddress3;
	@NotNull(message="error.state.required")
	@NotEmpty(message="error.state.required")
	private String state;
	@NotNull(message="error.paymentAmount.required")
	@NotEmpty(message="error.paymentAmount.required")
	private String paymentAmount;
	private String paymentTs;
	private String txnData;
	private String txnRefNo;
	@NotNull(message="error.paymentGateway.required")
	@NotEmpty(message="error.paymentGateway.required")
	private String paymentGateway;
	@NotNull(message="error.paymentMode.required")
	@NotEmpty(message="error.paymentMode.required")
	private String paymentMode;
	private String paymentStatus;
	@NotNull(message="error.amountBal.required")
	@NotEmpty(message="error.amountBal.required")
	private String amountBal;
	@NotNull(message="error.amountPaid.required")
	@NotEmpty(message="error.amountPaid.required")
	private String amountPaid;
	private String discount;
	private String discountValue;
	@NotNull(message="error.grandTotal.required")
	@NotEmpty(message="error.grandTotal.required")
	private String grandTotal;
	@NotNull(message="error.miscCharges.required")
	@NotEmpty(message="error.miscCharges.required")
	private String miscCharges;
	@NotNull(message="error.serviceTax.required")
	@NotEmpty(message="error.serviceTax.required")
	private String serviceTax;
	@NotNull(message="error.serviceTaxValue.required")
	@NotEmpty(message="error.serviceTaxValue.required")
	private String serviceTaxValue;
	@NotNull(message="error.shippingCharges.required")
	@NotEmpty(message="error.shippingCharges.required")
	private String shippingCharges;
	@NotNull(message="error.subTotal.required")
	@NotEmpty(message="error.subTotal.required")
	private String subTotal;
	@NotNull(message="error.vat.required")
	@NotEmpty(message="error.vat.required")
	private String vat;
	@NotNull(message="error.vatValue.required")
	@NotEmpty(message="error.vatValue.required")
	private String vatValue;
	
	@NotNull(message="error.cartItemsList.required")
	private List<CartItemDetailsListTO> cartItemsList;

	private String mark;
	
	private String destination;
	
	private String tranNm;
	
	private String tinNo;
	
	private String isDefaultAddress;
	
	private String shippingDtlsId;
	
	public CartDetailsTO(String cartDtlsId, String cartNotes, String cartPrice, String isOfferApld, String cartStatus,
			String actualDlvryDt, String alternateCntc, String dlvryByTrackId, String dlvryType,
			String expDlvryDt, String addressType, String city, String country, String latitude, String longitude,
			String postalCode, String stAddress1, String stAddress2, String stAddress3, String state,
			String paymentAmount, String paymentTs, String txnData, String txnRefNo, String paymentGateway,
			String paymentMode, String paymentStatus, String amountBal, String amountPaid, String discount,
			String discountValue, String grandTotal, String miscCharges, String serviceTax, String serviceTaxValue,
			String shippingCharges, String subTotal, String vat, String vatValue,
			List<CartItemDetailsListTO> cartItemsList, String mark,String destination, String tranNm, String tinNo, 
			String isDefaultAddress, String shippingDtlsId) {
		this.cartDtlsId = cartDtlsId;
		this.cartNotes = cartNotes;
		this.cartPrice = cartPrice;
		this.isOfferApld = isOfferApld;
		this.cartStatus = cartStatus;
		this.actualDlvryDt = actualDlvryDt;
		this.alternateCntc = alternateCntc;
		this.dlvryByTrackId = dlvryByTrackId;
		this.dlvryType = dlvryType;
		this.expDlvryDt = expDlvryDt;
		this.addressType = addressType;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postalCode = postalCode;
		this.stAddress1 = stAddress1;
		this.stAddress2 = stAddress2;
		this.stAddress3 = stAddress3;
		this.state = state;
		this.paymentAmount = paymentAmount;
		this.paymentTs = paymentTs;
		this.txnData = txnData;
		this.txnRefNo = txnRefNo;
		this.paymentGateway = paymentGateway;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
		this.amountBal = amountBal;
		this.amountPaid = amountPaid;
		this.discount = discount;
		this.discountValue = discountValue;
		this.grandTotal = grandTotal;
		this.miscCharges = miscCharges;
		this.serviceTax = serviceTax;
		this.serviceTaxValue = serviceTaxValue;
		this.shippingCharges = shippingCharges;
		this.subTotal = subTotal;
		this.vat = vat;
		this.vatValue = vatValue;
		this.cartItemsList = cartItemsList;
		this.mark=mark;
		this.destination=destination;
		this.tranNm=tranNm;
		this.tinNo= tinNo;
		this.isDefaultAddress=isDefaultAddress;
		this.shippingDtlsId=shippingDtlsId;
	}
	
	public CartDetailsTO() {}
	
	public String getShippingDtlsId() {
		return shippingDtlsId;
	}

	public void setShippingDtlsId(String shippingDtlsId) {
		this.shippingDtlsId = shippingDtlsId;
	}

	public String getIsDefaultAddress() {
		return isDefaultAddress;
	}

	public void setIsDefaultAddress(String isDefaultAddress) {
		this.isDefaultAddress = isDefaultAddress;
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

	public String getOfferApldId() {
		return offerApldId;
	}

	public void setOfferApldId(String offerApldId) {
		this.offerApldId = offerApldId;
	}
	public String getCartDtlsId() {
		return cartDtlsId;
	}
	public void setCartDtlsId(String cartDtlsId) {
		this.cartDtlsId = cartDtlsId;
	}
	public String getCartNotes() {
		return cartNotes;
	}
	public void setCartNotes(String cartNotes) {
		this.cartNotes = cartNotes;
	}
	public String getCartPrice() {
		return cartPrice;
	}
	public void setCartPrice(String cartPrice) {
		this.cartPrice = cartPrice;
	}
	public String isOfferApld() {
		return isOfferApld;
	}
	public void setIsOfferApld(String isOfferApld) {
		this.isOfferApld = isOfferApld;
	}
	public String getCartStatus() {
		return cartStatus;
	}
	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}
	public String getActualDlvryDt() {
		return actualDlvryDt;
	}
	public void setActualDlvryDt(String actualDlvryDt) {
		this.actualDlvryDt = actualDlvryDt;
	}
	public String getAlternateCntc() {
		return alternateCntc;
	}
	public void setAlternateCntc(String alternateCntc) {
		this.alternateCntc = alternateCntc;
	}
	public String getDlvryByTrackId() {
		return dlvryByTrackId;
	}
	public void setDlvryByTrackId(String dlvryByTrackId) {
		this.dlvryByTrackId = dlvryByTrackId;
	}
	public String getDlvryType() {
		return dlvryType;
	}
	public void setDlvryType(String dlvryType) {
		this.dlvryType = dlvryType;
	}
	public String getExpDlvryDt() {
		return expDlvryDt;
	}
	public void setExpDlvryDt(String expDlvryDt) {
		this.expDlvryDt = expDlvryDt;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressTypeCd(String addressType) {
		this.addressType = addressType;
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
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentTs() {
		return paymentTs;
	}
	public void setPaymentTs(String paymentTs) {
		this.paymentTs = paymentTs;
	}
	public String getTxnData() {
		return txnData;
	}
	public void setTxnData(String txnData) {
		this.txnData = txnData;
	}
	public String getTxnRefNo() {
		return txnRefNo;
	}
	public void setTxnRefNo(String txnRefNo) {
		this.txnRefNo = txnRefNo;
	}
	public String getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
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
	public String getAmountBal() {
		return amountBal;
	}
	public void setAmountBal(String amountBal) {
		this.amountBal = amountBal;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getMiscCharges() {
		return miscCharges;
	}
	public void setMiscCharges(String miscCharges) {
		this.miscCharges = miscCharges;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getServiceTaxValue() {
		return serviceTaxValue;
	}
	public void setServiceTaxValue(String serviceTaxValue) {
		this.serviceTaxValue = serviceTaxValue;
	}
	public String getShippingCharges() {
		return shippingCharges;
	}
	public void setShippingCharges(String shippingCharges) {
		this.shippingCharges = shippingCharges;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getVat() {
		return vat;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public String getVatValue() {
		return vatValue;
	}
	public void setVatValue(String vatValue) {
		this.vatValue = vatValue;
	}
	public List<CartItemDetailsListTO> getCartItemsList() {
		return cartItemsList;
	}
	public void setCartItemsList(List<CartItemDetailsListTO> cartItemsList) {
		this.cartItemsList = cartItemsList;
	}
	
	public ShippingAddressDetail convertToAddressDetailsEntity(String trackId) {
		
		ShippingAddressDetail addressDetail = new ShippingAddressDetail(this.addressType, this.city, this.country,
				BigDecimal.valueOf(Double.parseDouble(this.latitude)), BigDecimal.valueOf(Double.parseDouble(this.longitude)), 
				this.postalCode, this.stAddress1, this.stAddress2, this.stAddress3, this.state, trackId, (null == this.mark) ? "" : this.mark, 
						(null == this.destination) ? "" : this.destination, (null == this.tranNm) ? "" : this.tranNm, 
								(null == this.tinNo) ? "" : this.tinNo);
		return addressDetail;
	}

	public CartDlvryDtl convertToCartDlvryDetailsEntity(int addressDtlsId) throws Exception {

		CartDlvryDtl cartDelivryDtls = new CartDlvryDtl(null, this.alternateCntc, null, this.dlvryType, this.expDlvryDt, addressDtlsId);
		return cartDelivryDtls;
	}

	public InvoiceDtl convertToInvoiceDtlsEntity(String trackId) {

		InvoiceDtl invoiceDtl = new InvoiceDtl(Float.parseFloat(this.amountBal), Float.parseFloat(this.amountPaid), 
				Float.parseFloat(this.discount.isEmpty() ? "0" : this.discount), Float.parseFloat(this.discountValue.isEmpty() ? "0" : this.discountValue),Float.parseFloat(this.grandTotal), 
				Float.parseFloat(this.miscCharges.isEmpty() ? "0" : this.miscCharges), Float.parseFloat(this.serviceTax), Float.parseFloat(this.serviceTaxValue), 
				Float.parseFloat(this.shippingCharges.isEmpty() ? "0" : this.shippingCharges), Float.parseFloat(this.subTotal), trackId, Float.parseFloat(this.vat), 
				Float.parseFloat(this.vatValue));
		return invoiceDtl;
	}

	public PaymentDtl convertToPaymentDtlEntity() {
//TODO Add approproriate ts for payment ts added this.paymentTs at hardcoded date 
		PaymentDtl paymentDtl = new PaymentDtl(null, Float.parseFloat(this.paymentAmount), new Timestamp(new java.util.Date().getTime()), this.txnData,
				this.getTxnRefNo(), this.paymentGateway, this.paymentMode, /*this.paymentStatus*/ UDValues.CART_STATUS_PENDING.toString());
		return paymentDtl;
	}

	public CartDtl convertToCartDtlEntity(int cartDlvryDtlsId, int invoiceDtlsId, Integer offerAppldId, String paymentDtlId, String trackId) {

		CartDtl cartDetail = new CartDtl(this.cartNotes, Float.parseFloat(this.cartPrice.isEmpty() ? "0.0" : this.cartPrice), this.isOfferApld, cartDlvryDtlsId, 
				invoiceDtlsId, offerAppldId, paymentDtlId, UDValues.CART_STATUS_BOOKED.toString(), trackId);
		return cartDetail;
	}

	@Override
	public String toString() {
		return "CartDetailsTO [cartDtlsId=" + cartDtlsId + ", cartNotes=" + cartNotes + ", cartPrice=" + cartPrice
				+ ", isOfferApld=" + isOfferApld + ", offerApldId=" + offerApldId + ", cartStatus=" + cartStatus
				+ ", actualDlvryDt=" + actualDlvryDt + ", alternateCntc=" + alternateCntc + ", dlvryByTrackId="
				+ dlvryByTrackId + ", dlvryType=" + dlvryType + ", expDlvryDt=" + expDlvryDt + ", addressType="
				+ addressType + ", city=" + city + ", country=" + country + ", latitude=" + latitude + ", longitude="
				+ longitude + ", postalCode=" + postalCode + ", stAddress1=" + stAddress1 + ", stAddress2=" + stAddress2
				+ ", stAddress3=" + stAddress3 + ", state=" + state + ", paymentAmount=" + paymentAmount
				+ ", paymentTs=" + paymentTs + ", txnData=" + txnData + ", txnRefNo=" + txnRefNo + ", paymentGateway="
				+ paymentGateway + ", paymentMode=" + paymentMode + ", paymentStatus=" + paymentStatus + ", amountBal="
				+ amountBal + ", amountPaid=" + amountPaid + ", discount=" + discount + ", discountValue="
				+ discountValue + ", grandTotal=" + grandTotal + ", miscCharges=" + miscCharges + ", serviceTax="
				+ serviceTax + ", serviceTaxValue=" + serviceTaxValue + ", shippingCharges=" + shippingCharges
				+ ", subTotal=" + subTotal + ", vat=" + vat + ", vatValue=" + vatValue + ", cartItemsList="
				+ cartItemsList + "]";
	}
	
	
}
