package co.in.replete.komalindustries.webcontroller.beans;

public class WUserCartDetails {

	
	private int cartDtlsId;
	
	private int cartDlvyDtlId;
	
	private double cartPrice;
	
	private String cartStatus;
	
	public WUserCartDetails(){}

	public int getCartDtlsId() {
		return cartDtlsId;
	}

	public void setCartDtlsId(int cartDtlsId) {
		this.cartDtlsId = cartDtlsId;
	}

	public int getCartDlvyDtlId() {
		return cartDlvyDtlId;
	}

	public void setCartDlvyDtlId(int cartDlvyDtlId) {
		this.cartDlvyDtlId = cartDlvyDtlId;
	}

	public double getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(double cartPrice) {
		this.cartPrice = cartPrice;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}
	
}
