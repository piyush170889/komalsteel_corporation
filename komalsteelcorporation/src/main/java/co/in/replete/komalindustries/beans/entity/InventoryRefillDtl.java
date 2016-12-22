package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the inventory_refill_dtls database table.
 * 
 */
@Entity
@Table(name="inventory_refill_dtls")
@NamedQuery(name="InventoryRefillDtl.findAll", query="SELECT i FROM InventoryRefillDtl i")
public class InventoryRefillDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7220897542154766021L;

	@Id
	@Column(name="INVENTORY_REFILL_DTLS_ID")
	private int inventoryRefillDtlsId;

	private String comments;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="ITEM_MASTER_DTLS_ID")
	private int itemMasterDtlsId;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	private BigDecimal mrp;

	@Column(name="PER_UNIT_COST_PRICE")
	private BigDecimal perUnitCostPrice;

	@Column(name="RECEIVED_QTY")
	private BigDecimal receivedQty;

	@Column(name="REFILL_DT")
	private Timestamp refillDt;

	@Column(name="REFILL_PRICE")
	private BigDecimal refillPrice;

	@Column(name="REJECTED_REWORK_QTY")
	private BigDecimal rejectedReworkQty;

	@Column(name="REJECTED_SCRAP_QTY")
	private BigDecimal rejectedScrapQty;

	@Column(name="VENDOR_ID")
	private int vendorId;
	
	@Column(name="CMPNY_INFO_ID")
	private int cmpnyInfoId;

	public int getCmpnyInfoId() {
		return cmpnyInfoId;
	}

	public void setCmpnyInfoId(int cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}

	public InventoryRefillDtl() {
	}

	public int getInventoryRefillDtlsId() {
		return this.inventoryRefillDtlsId;
	}

	public void setInventoryRefillDtlsId(int inventoryRefillDtlsId) {
		this.inventoryRefillDtlsId = inventoryRefillDtlsId;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public int getItemMasterDtlsId() {
		return this.itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public BigDecimal getMrp() {
		return this.mrp;
	}

	public void setMrp(BigDecimal mrp) {
		this.mrp = mrp;
	}

	public BigDecimal getPerUnitCostPrice() {
		return this.perUnitCostPrice;
	}

	public void setPerUnitCostPrice(BigDecimal perUnitCostPrice) {
		this.perUnitCostPrice = perUnitCostPrice;
	}

	public BigDecimal getReceivedQty() {
		return this.receivedQty;
	}

	public void setReceivedQty(BigDecimal receivedQty) {
		this.receivedQty = receivedQty;
	}

	public Timestamp getRefillDt() {
		return this.refillDt;
	}

	public void setRefillDt(Timestamp refillDt) {
		this.refillDt = refillDt;
	}

	public BigDecimal getRefillPrice() {
		return this.refillPrice;
	}

	public void setRefillPrice(BigDecimal refillPrice) {
		this.refillPrice = refillPrice;
	}

	public BigDecimal getRejectedReworkQty() {
		return this.rejectedReworkQty;
	}

	public void setRejectedReworkQty(BigDecimal rejectedReworkQty) {
		this.rejectedReworkQty = rejectedReworkQty;
	}

	public BigDecimal getRejectedScrapQty() {
		return this.rejectedScrapQty;
	}

	public void setRejectedScrapQty(BigDecimal rejectedScrapQty) {
		this.rejectedScrapQty = rejectedScrapQty;
	}

	public int getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

}