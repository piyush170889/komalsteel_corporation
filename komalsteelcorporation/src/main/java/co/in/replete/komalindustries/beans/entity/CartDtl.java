package co.in.replete.komalindustries.beans.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the cart_dtls database table.
 * 
 */
@Entity
@Table(name="cart_dtls")
@NamedQuery(name="CartDtl.findAll", query="SELECT c FROM CartDtl c")
public class CartDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3734253490478794759L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="CART_DTLS_ID")
	private int cartDtlsId;

	@Column(name="CART_NOTES")
	private String cartNotes;

	@Column(name="CART_PRICE")
	private Float cartPrice;

	@Column(name="IS_OFFER_APPLD")
	private String isOfferApld;

	@Column(name="CART_DLVRY_DTLS_ID")
	private int cartDlvryDtlsId;

	@Column(name="INVOICE_DTLS_ID")
	private int invoiceDtlsId;

	@Column(name="OFFER_DTLS_ID")
	private Integer offerDtlId;

	@Column(name="PAYMENT_DTLS_ID")
	private String paymentDtlsId;

	@Column(name="CART_STATUS")
	private String cartStatus;

	@Column(name="TRACK_ID")
	private String trackId;

	@Column(name="LR_NO")
	private String lrNo;
	
	@Column(name="LR_NO_DATE")
	private String lrNoDate;
	
	@Column(name="NO_OF_CARTON_LOADED")
	private String noOfCartonLoaded;
	
	public CartDtl() {
	}

	public CartDtl(String cartNotes, Float cartPrice, String isOfferApld, int cartDlvryDtl, 
			int invoiceDtl, Integer offerDtl, String paymentDtl, String cartStatusCd, String trackId) {
		this.cartNotes = cartNotes;
		this.cartPrice = cartPrice;
		this.isOfferApld = isOfferApld;
		this.cartDlvryDtlsId = cartDlvryDtl;
		this.invoiceDtlsId = invoiceDtl;
		this.offerDtlId = offerDtl;
		this.paymentDtlsId = paymentDtl;
		this.cartStatus = cartStatusCd;
		this.trackId = trackId;
	}

	public CartDtl(String cartNotes, Float cartPrice, String isOfferApld, int cartDlvryDtl, 
			int invoiceDtl, String paymentDtl, String cartStatusCd, String trackId) {
		this.cartNotes = cartNotes;
		this.cartPrice = cartPrice;
		this.isOfferApld = isOfferApld;
		this.cartDlvryDtlsId = cartDlvryDtl;
		this.invoiceDtlsId = invoiceDtl;
		this.paymentDtlsId = paymentDtl;
		this.cartStatus = cartStatusCd;
		this.trackId = trackId;
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

	public String getCartNotes() {
		return cartNotes;
	}

	public void setCartNotes(String cartNotes) {
		this.cartNotes = cartNotes;
	}

	public Float getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(Float cartPrice) {
		this.cartPrice = cartPrice;
	}

	public String getIsOfferApld() {
		return isOfferApld;
	}

	public void setIsOfferApld(String isOfferApld) {
		this.isOfferApld = isOfferApld;
	}

	public Integer getOfferDtlId() {
		return offerDtlId;
	}

	public void setOfferDtlId(Integer offerDtlId) {
		this.offerDtlId = offerDtlId;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public int getCartDlvryDtlsId() {
		return cartDlvryDtlsId;
	}

	public void setCartDlvryDtlsId(int cartDlvryDtlsId) {
		this.cartDlvryDtlsId = cartDlvryDtlsId;
	}

	public int getInvoiceDtlsId() {
		return invoiceDtlsId;
	}

	public void setInvoiceDtlsId(int invoiceDtlsId) {
		this.invoiceDtlsId = invoiceDtlsId;
	}

	public String getPaymentDtlsId() {
		return paymentDtlsId;
	}

	public void setPaymentDtlsId(String paymentDtlsId) {
		this.paymentDtlsId = paymentDtlsId;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

}