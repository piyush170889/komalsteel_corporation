package co.in.replete.komalindustries.beans;

public class CartAndCartItemDetailTO {

	private int cartDtlsId;
	
	private Float masterCartonPrice;
	
	public CartAndCartItemDetailTO() {}

	public int getCartDtlsId() {
		return cartDtlsId;
	}

	public void setCartDtlsId(int cartDtlsId) {
		this.cartDtlsId = cartDtlsId;
	}

	public Float getMasterCartonPrice() {
		return masterCartonPrice;
	}

	public void setMasterCartonPrice(Float masterCartonPrice) {
		this.masterCartonPrice = masterCartonPrice;
	}
	
}
