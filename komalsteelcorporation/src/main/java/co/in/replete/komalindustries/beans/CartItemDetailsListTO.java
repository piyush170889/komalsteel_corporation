package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CartItemDetailsListTO {
	@NotNull(message="error.itemName.required")
	@NotEmpty(message="error.itemName.required")
	private String itemName;
	@NotNull(message="error.itemPrice.required")
	@NotEmpty(message="error.itemPrice.required")
	private String itemPrice;
	@NotNull(message="error.itemQty.required")
	@NotEmpty(message="error.itemQty.required")
	private String itemQty;
	@NotNull(message="error.itemMasterDtlsId.required")
	@NotEmpty(message="error.itemMasterDtlsId.required")
	private String itemMasterDtlsId;
	@NotNull(message="error.isOfferApld.required")
	@NotEmpty(message="error.isOfferApld.required")
	private String isOfferApld;
	@NotNull(message="error.offerId.required")
	@NotEmpty(message="error.offerId.required")
	private String offerId;
	
	private String uom;
	
	public CartItemDetailsListTO() {}

	public CartItemDetailsListTO(String itemName, String itemPrice, String itemQty, String itemMasterDtlsId, String isOfferApld, String offerId) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemQty = itemQty;
		this.itemMasterDtlsId = itemMasterDtlsId;
		this.isOfferApld = isOfferApld;
		this.offerId = offerId;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(String itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public String isOfferApld() {
		return isOfferApld;
	}

	public void setIsOfferApld(String isOfferApld) {
		this.isOfferApld = isOfferApld;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}
	
	
}
