package co.in.replete.komalindustries.beans;

import java.sql.Timestamp;
import java.util.List;

public class ProductAllDetailsTO {

	private int itemMasterDtlsId;

	private String cmpnyInfoId;

	private String itemCategory;

	private String itemContentInfo;

	private String itemDesc;

	private String itemImage;

	private String itemManufacturer;

	private String itemNm;

	private String itemPckgInfo;

	private String itemPckgType;

	private String itemSubCategory;
	
	private int itemsInventoryDtlsId;

	private Float avlQty;

	private Float bookedQty;

	private Timestamp lastRefillDt;

	private Float mrp;
	
	private List<RefillTO> refillDetails;
	
	public ProductAllDetailsTO() {}

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

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemContentInfo() {
		return itemContentInfo;
	}

	public void setItemContentInfo(String itemContentInfo) {
		this.itemContentInfo = itemContentInfo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemManufacturer() {
		return itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getItemPckgInfo() {
		return itemPckgInfo;
	}

	public void setItemPckgInfo(String itemPckgInfo) {
		this.itemPckgInfo = itemPckgInfo;
	}

	public String getItemPckgType() {
		return itemPckgType;
	}

	public void setItemPckgType(String itemPckgType) {
		this.itemPckgType = itemPckgType;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public int getItemsInventoryDtlsId() {
		return itemsInventoryDtlsId;
	}

	public void setItemsInventoryDtlsId(int itemsInventoryDtlsId) {
		this.itemsInventoryDtlsId = itemsInventoryDtlsId;
	}

	public Float getAvlQty() {
		return avlQty;
	}

	public void setAvlQty(Float avlQty) {
		this.avlQty = avlQty;
	}

	public Float getBookedQty() {
		return bookedQty;
	}

	public void setBookedQty(Float bookedQty) {
		this.bookedQty = bookedQty;
	}

	public Timestamp getLastRefillDt() {
		return lastRefillDt;
	}

	public void setLastRefillDt(Timestamp lastRefillDt) {
		this.lastRefillDt = lastRefillDt;
	}

	public Float getMrp() {
		return mrp;
	}

	public void setMrp(Float mrp) {
		this.mrp = mrp;
	}

	public List<RefillTO> getRefillDetails() {
		return refillDetails;
	}

	public void setRefillDetails(List<RefillTO> refillDetails) {
		this.refillDetails = refillDetails;
	}
	
}
