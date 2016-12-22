package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RefilInventoryTO {

	private int itemMasterDtlsId;
	
	@NotNull(message="error.cmpnyInfoId.required")
	@NotEmpty(message="error.cmpnyInfoId.required")
	private String cmpnyInfoId;
	
	private double avlQty;
	
	private double bookedQty ;
	
	private double thrhldVal;
	
	private double mrp;
	
	@NotNull(message="error.shelfCode.required")
	@NotEmpty(message="error.shelfCode.required")
	private String shelfCode;
	
    private double rejectedScrapQty;
    
    private double rejectedReworkQty;
    
    private int vendorId;
    
    private double refilPrice;
    
    private double perUnitCostPrice;
    
    private String comments;    
    
    public RefilInventoryTO(){}

	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public String getCmpnyInfoId() {
		return cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public double getAvlQty() {
		return avlQty;
	}

	public void setAvlQty(double avlQty) {
		this.avlQty = avlQty;
	}

	public double getBookedQty() {
		return bookedQty;
	}

	public void setBookedQty(double bookedQty) {
		this.bookedQty = bookedQty;
	}

	public double getThrhldVal() {
		return thrhldVal;
	}

	public void setThrhldVal(double thrhldVal) {
		this.thrhldVal = thrhldVal;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public String getShelfCode() {
		return shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}

	public double getRejectedScrapQty() {
		return rejectedScrapQty;
	}

	public void setRejectedScrapQty(double rejectedScrapQty) {
		this.rejectedScrapQty = rejectedScrapQty;
	}

	public double getRejectedReworkQty() {
		return rejectedReworkQty;
	}

	public void setRejectedReworkQty(double rejectedReworkQty) {
		this.rejectedReworkQty = rejectedReworkQty;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public double getRefilPrice() {
		return refilPrice;
	}

	public void setRefilPrice(double refilPrice) {
		this.refilPrice = refilPrice;
	}

	public double getPerUnitCostPrice() {
		return perUnitCostPrice;
	}

	public void setPerUnitCostPrice(double perUnitCostPrice) {
		this.perUnitCostPrice = perUnitCostPrice;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
