package co.in.replete.komalindustries.webcontroller.beans;

public class EditCartItemDtlsTO {

	private int orderDtlsId;
	
	private int cartItemDtlsId;
	
	private int itemQty;
	
	private Float itemPrice;
	
	public EditCartItemDtlsTO() {}

	public int getOrderDtlsId() {
		return orderDtlsId;
	}

	public void setOrderDtlsId(int orderDtlsId) {
		this.orderDtlsId = orderDtlsId;
	}

	public int getCartItemDtlsId() {
		return cartItemDtlsId;
	}

	public void setCartItemDtlsId(int cartItemDtlsId) {
		this.cartItemDtlsId = cartItemDtlsId;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public Float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Float itemPrice) {
		this.itemPrice = itemPrice;
	}

}
