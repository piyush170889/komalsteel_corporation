package co.in.replete.komalindustries.beans;

public class AddItemsToCartTO {

	private int itemMasterDtlsId;
	
	private int cartonQty;
	
	private Float cartonPrice;
	
	private int orderDtlsId;
	
	public AddItemsToCartTO() {}

	public int getOrderDtlsId() {
		return orderDtlsId;
	}

	public void setOrderDtlsId(int orderDtlsId) {
		this.orderDtlsId = orderDtlsId;
	}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public int getCartonQty() {
		return cartonQty;
	}

	public void setCartonQty(int cartonQty) {
		this.cartonQty = cartonQty;
	}

	public Float getCartonPrice() {
		return cartonPrice;
	}

	public void setCartonPrice(Float cartonPrice) {
		this.cartonPrice = cartonPrice;
	}
	
}
