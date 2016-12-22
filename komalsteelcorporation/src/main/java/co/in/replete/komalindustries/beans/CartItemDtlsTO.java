package co.in.replete.komalindustries.beans;

public class CartItemDtlsTO {

	private int itemMasterDtlsId;
	
	private String itemNm;
	
	private Float itemPrice;

	private int itemQty;
	
	private String uom;
	
	
	public CartItemDtlsTO() {}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
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
	
}
