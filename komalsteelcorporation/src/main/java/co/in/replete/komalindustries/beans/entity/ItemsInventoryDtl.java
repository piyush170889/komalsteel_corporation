package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the items_inventory_dtls database table.
 * 
 */
@Entity
@Table(name="items_inventory_dtls")
@NamedQuery(name="ItemsInventoryDtl.findAll", query="SELECT i FROM ItemsInventoryDtl i")
public class ItemsInventoryDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -653482534294448166L;

	@Id
	@Column(name="ITEMS_INVENTORY_DTLS_ID")
	private int itemsInventoryDtlsId;

	@Column(name="AVL_QTY")
	private Float avlQty;

	@Column(name="BOOKED_QTY")
	private Float bookedQty;

	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="ITEM_MASTER_DTLS_ID")
	private int itemMasterDtlsId;

	@Column(name="LAST_REFILL_DT")
	private Timestamp lastRefillDt;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	private Float mrp;

	@Column(name="SHELF_CODE")
	private String shelfCode;

	@Column(name="THRHLD_VAL")
	private Float thrhldVal;

	public ItemsInventoryDtl() {
	}

	public int getItemsInventoryDtlsId() {
		return this.itemsInventoryDtlsId;
	}

	public void setItemsInventoryDtlsId(int itemsInventoryDtlsId) {
		this.itemsInventoryDtlsId = itemsInventoryDtlsId;
	}

	public Float getAvlQty() {
		return this.avlQty;
	}

	public void setAvlQty(Float avlQty) {
		this.avlQty = avlQty;
	}

	public Float getBookedQty() {
		return this.bookedQty;
	}

	public void setBookedQty(Float bookedQty) {
		this.bookedQty = bookedQty;
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
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

	public Timestamp getLastRefillDt() {
		return this.lastRefillDt;
	}

	public void setLastRefillDt(Timestamp lastRefillDt) {
		this.lastRefillDt = lastRefillDt;
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

	public Float getMrp() {
		return this.mrp;
	}

	public void setMrp(Float mrp) {
		this.mrp = mrp;
	}

	public String getShelfCode() {
		return this.shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}

	public Float getThrhldVal() {
		return this.thrhldVal;
	}

	public void setThrhldVal(Float thrhldVal) {
		this.thrhldVal = thrhldVal;
	}

}