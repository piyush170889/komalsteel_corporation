package co.in.replete.komalindustries.beans;

public class PrintOrder {

	private String fNm;
	
	private String lNm;
	
	private String displayName;
	
	private String cntcNum;
	
	private UserOrderDetailsTO orderDetails;
	
	public PrintOrder() {}

	public String getfNm() {
		return fNm;
	}

	public void setfNm(String fNm) {
		this.fNm = fNm;
	}

	public String getlNm() {
		return lNm;
	}

	public void setlNm(String lNm) {
		this.lNm = lNm;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCntcNum() {
		return cntcNum;
	}

	public void setCntcNum(String cntcNum) {
		this.cntcNum = cntcNum;
	}

	public UserOrderDetailsTO getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(UserOrderDetailsTO orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
