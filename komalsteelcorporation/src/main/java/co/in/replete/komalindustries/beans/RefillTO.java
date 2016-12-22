package co.in.replete.komalindustries.beans;

import java.util.Date;

public class RefillTO {
	
	private int inventoryRefillDtlsId;
	
	private Date refillDate;
	
	private double receivedQty;
	
	private double rejectedScrapQty;
	
	private double rejectedrework;
	
	private int vendorId;
	
	private double refillPrice;
	
	private int itemMasterDtlsId;
	
	private double perUnitCostPrice;
	
	private double mrp;
	
	private String comments;
	
	private Date createdTs;
	
	private Date modifiedTs;
	
	public RefillTO(){}

	public int getInventoryRefillDtlsId() {
		return inventoryRefillDtlsId;
	}

	public void setInventoryRefillDtlsId(int inventoryRefillDtlsId) {
		this.inventoryRefillDtlsId = inventoryRefillDtlsId;
	}

	public Date getRefillDate() {
		return refillDate;
	}

	public void setRefillDate(Date refillDate) {
		this.refillDate = refillDate;
	}

	public double getReceivedQty() {
		return receivedQty;
	}

	public void setReceivedQty(double receivedQty) {
		this.receivedQty = receivedQty;
	}

	public double getRejectedScrapQty() {
		return rejectedScrapQty;
	}

	public void setRejectedScrapQty(double rejectedScrapQty) {
		this.rejectedScrapQty = rejectedScrapQty;
	}

	public double getRejectedrework() {
		return rejectedrework;
	}

	public void setRejectedrework(double rejectedrework) {
		this.rejectedrework = rejectedrework;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public double getRefillPrice() {
		return refillPrice;
	}

	public void setRefillPrice(double refillPrice) {
		this.refillPrice = refillPrice;
	}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public double getPerUnitCostPrice() {
		return perUnitCostPrice;
	}

	public void setPerUnitCostPrice(double perUnitCostPrice) {
		this.perUnitCostPrice = perUnitCostPrice;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Date modifiedTs) {
		this.modifiedTs = modifiedTs;
	}
	
	
}
