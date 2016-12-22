package co.in.replete.komalindustries.beans;

public class ItemInventoryUpdateTO {

	private int itemMasterDtlsId;
	
	private String invProdRefillQty;
	
	private String invProdRefillDt;
	
	private String invProdMrp;
	
	private String itemName;
	
	private String invRefillComments;
	
	public ItemInventoryUpdateTO() {}

	public String getInvRefillComments() {
		return invRefillComments;
	}

	public void setInvRefillComments(String invRefillComments) {
		this.invRefillComments = invRefillComments;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public String getInvProdRefillQty() {
		return invProdRefillQty;
	}

	public void setInvProdRefillQty(String invProdRefillQty) {
		this.invProdRefillQty = invProdRefillQty;
	}

	public String getInvProdRefillDt() {
		return invProdRefillDt;
	}

	public void setInvProdRefillDt(String invProdRefillDt) {
		this.invProdRefillDt = invProdRefillDt;
	}

	public String getInvProdMrp() {
		return invProdMrp;
	}

	public void setInvProdMrp(String invProdMrp) {
		this.invProdMrp = invProdMrp;
	}
	
}
