package co.in.replete.komalindustries.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Lob;

public class ProductDetailsByCatAndSubCatTO {

	private int itemMasterDtlsId;

	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="ITEM_CATEGORY")
	private int itemCategory;

	@Column(name="ITEM_CONTENT_INFO")
	private String itemContentInfo;

	@Column(name="ITEM_DESC")
	private String itemDesc;

	@Lob
	@Column(name="ITEM_IMAGE")
	private byte[] itemImage;

	@Column(name="ITEM_MANUFACTURER")
	private String itemManufacturer;

	@Column(name="ITEM_NM")
	private String itemNm;

	@Column(name="ITEM_SUB_CATEGORY")
	private int itemSubCategory;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="OFFER_DTLS_ID")
	private int offerDtlsId;
	
	private String itemPrice;
	
	private int itemsInMasterCarton;
	
	private Float masterCartonPrice;
	
	private String uom;

	private String masterCartonQtyRange;
	
	private String masterCartonQtyIncVal;
	
	private String itemNo;
	
	public ProductDetailsByCatAndSubCatTO() {}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getMasterCartonQtyRange() {
		return masterCartonQtyRange;
	}

	public void setMasterCartonQtyRange(String masterCartonQtyRange) {
		this.masterCartonQtyRange = masterCartonQtyRange;
	}

	public String getMasterCartonQtyIncVal() {
		return masterCartonQtyIncVal;
	}

	public void setMasterCartonQtyIncVal(String masterCartonQtyIncVal) {
		this.masterCartonQtyIncVal = masterCartonQtyIncVal;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getItemsInMasterCarton() {
		return itemsInMasterCarton;
	}

	public void setItemsInMasterCarton(int itemsInMasterCarton) {
		this.itemsInMasterCarton = itemsInMasterCarton;
	}


	public Float getMasterCartonPrice() {
		return masterCartonPrice;
	}


	public void setMasterCartonPrice(Float masterCartonPrice) {
		this.masterCartonPrice = masterCartonPrice;
	}


	public String getItemPrice() {
		return itemPrice;
	}


	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}


	public int getItemMasterDtlsId() {
		return this.itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
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

	public int getItemCategory() {
		return this.itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemContentInfo() {
		return this.itemContentInfo;
	}

	public void setItemContentInfo(String itemContentInfo) {
		this.itemContentInfo = itemContentInfo;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public byte[] getItemImage() {
		return this.itemImage;
	}

	public void setItemImage(byte[] itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemManufacturer() {
		return this.itemManufacturer;
	}

	public void setItemManufacturer(String itemManufacturer) {
		this.itemManufacturer = itemManufacturer;
	}

	public String getItemNm() {
		return this.itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public int getItemSubCategory() {
		return this.itemSubCategory;
	}

	public void setItemSubCategory(int itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
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

	public int getOfferDtlsId() {
		return this.offerDtlsId;
	}

	public void setOfferDtlsId(int offerDtlsId) {
		this.offerDtlsId = offerDtlsId;
	}
}
