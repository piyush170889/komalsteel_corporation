package co.in.replete.komalindustries.webcontroller.beans;

import java.util.Date;

public class WUserOrderInfo {

	private int orderId;
	
	private Date orderDate;
	
	private String status;
	
	private int invoiceId;
	
	public WUserOrderInfo(){}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	
}
