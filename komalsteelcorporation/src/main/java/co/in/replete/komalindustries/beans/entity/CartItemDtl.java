package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the cart_item_dtls database table.
 * 
 */
@Entity
@Table(name="cart_item_dtls")
public class CartItemDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6720321203859855872L;

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="CART_ITEM_DTLS_ID")
	private int cartItemDtlsId;

	@Column(name="IS_OFFER_APPLD")
	private String isOfferAppld;

	@Column(name="ITEM_PRICE")
	private Float itemPrice;

	@Column(name="ITEM_QTY")
	private int itemQty;

	@Column(name="CART_DTLS_ID")
	private int cartDtlsId;

	@Column(name="ITEM_MASTER_DTLS_ID")
	private int itemMasterDtlsId;

	@Column(name="OFFER_DTLS_ID")
	private Integer offerDtlsId;

	@Column(name="TRACK_ID")
	private String trackId;

	private Timestamp modifiedTs;

	public CartItemDtl() {
	}


	public CartItemDtl(String isOfferAppld, Float itemPrice, int itemQty, int cartDtlsId,
			int itemMasterDtlsId, Integer offerDtlsId, String trackId) {
		this.isOfferAppld = isOfferAppld;
		this.itemPrice = itemPrice;
		this.itemQty = itemQty;
		this.cartDtlsId = cartDtlsId;
		this.itemMasterDtlsId = itemMasterDtlsId;
		this.offerDtlsId = offerDtlsId;
		this.trackId = trackId;
	}


	public int getCartItemDtlsId() {
		return cartItemDtlsId;
	}

	public void setCartItemDtlsId(int cartItemDtlsId) {
		this.cartItemDtlsId = cartItemDtlsId;
	}

	public String getIsOfferAppld() {
		return isOfferAppld;
	}

	public void setIsOfferAppld(String isOfferAppld) {
		this.isOfferAppld = isOfferAppld;
	}

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public int getCartDtlsId() {
		return cartDtlsId;
	}

	public void setCartDtlsId(int cartDtlsId) {
		this.cartDtlsId = cartDtlsId;
	}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public Integer getOfferDtlsId() {
		return offerDtlsId;
	}

	public void setOfferDtlsId(Integer offerDtlsId) {
		this.offerDtlsId = offerDtlsId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}
}