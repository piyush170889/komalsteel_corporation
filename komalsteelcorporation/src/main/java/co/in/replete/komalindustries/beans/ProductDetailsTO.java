package co.in.replete.komalindustries.beans;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import co.in.replete.komalindustries.beans.entity.InventoryRefillDtl;

public class ProductDetailsTO {

	
	private int itemMasterDtlsId;

	@NotNull(message="error.companyinfoid.required")
	@NotEmpty(message="error.companyinfoid.required")
	private String cmpnyInfoId;

	@NotNull(message="error.itemCategoryCd.required")
	@NotEmpty(message="error.itemCategoryCd.required")
	private String itemCategory;

	@NotNull(message="error.itemContentInfo.required")
	@NotEmpty(message="error.itemContentInfo.required")
	private String itemContentInfo;

	@NotNull(message="error.itemDesc.required")
	@NotEmpty(message="error.itemDesc.required")
	@Size(min=10, max=300)
	private String itemDesc;

	private String itemImage;

	@NotNull(message="error.itemManufacturer.required")
	@NotEmpty(message="error.itemManufacturer.required")
	private String itemManufacturer;

	@NotNull(message="error.itemNm.required")
	@NotEmpty(message="error.itemNm.required")
	private String itemNm;

	/*@NotNull(message="error.itemPckgInfo.required")
	@NotEmpty(message="error.itemPckgInfo.required")
	private String itemPckgInfo;

	@NotNull(message="error.itemPckgTypeCd.required")
	@NotEmpty(message="error.itemPckgTypeCd.required")
	private String itemPckgType;*/

	private int itemsInMasterCarton;
	
	private Float masterCartonPrice;
	
	private String uom;
	
	@NotNull(message="error.itemSubCategoryCd.required")
	@NotEmpty(message="error.itemSubCategoryCd.required")
	private String itemSubCategory;

	private String modifiedBy;

	private Timestamp modifiedTs;

	private int offerDtlsId;
             
	@NotNull(message="error.initialQuantity.required")
	@NotEmpty(message="error.initialQuantity.required")
	private double initialQuantity;

	private int pageNum;
	
	private List<InventoryRefillDtl> refilPrice;
	
	public ProductDetailsTO() {}

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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public List<InventoryRefillDtl> getRefilPrice() {
		return refilPrice;
	}

	public void setRefilPrice(List<InventoryRefillDtl> refilPrice) {
		this.refilPrice = refilPrice;
	}

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

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public int getOfferDtlsId() {
		return offerDtlsId;
	}

	public void setOfferDtlsId(int offerDtlsId) {
		this.offerDtlsId = offerDtlsId;
	}

	public double getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(double initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

}
