package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class UpdateProductTO {
	
	private int productId;
	
	private String companyInfoId;
	
    @NotNull(message="error.itemName.required")
    @NotEmpty(message="error.itemName.required")
	private String itemNm;
	
    @NotNull(message="error.itemCategory.required")
    @NotEmpty(message="error.itemCategory.required")
	private String itemCategory;
	
    @NotNull(message="error.subCategory.required")
    @NotEmpty(message="error.subCategory.required")
	private String itemSubCategory;
	
    @NotNull(message="error.contentInfo.required")
    @NotEmpty(message="error.contentInfo.required")
	private String itemContentInfo;
	
    @NotNull(message="error.packType.required")
    @NotEmpty(message="error.packType.required")
	private String itemPckgType;
	
    @NotNull(message="error.packInfo.required")
    @NotEmpty(message="error.packInfo.required")
	private String itemPckgInfo;
	
    @NotNull(message="error.manufacturer.required")
    @NotEmpty(message="error.manufacturer.required")
	private String manufacturer;
	
	private int offerDetailsId;
	
    @NotNull(message="error.itemDesc.required")
    @NotEmpty(message="error.itemDesc.required")
	private String itemDesc;
    
    private double availableQty;
    
    private double thresholdVal;
    
    private double mrp;
    
    private double refillPrice;
    
    private double refillQty;
    
    private double perUnitCostPrice;
    
    private int itemsInMasterCarton;
	
	private Float masterCartonPrice;
	
	private String masterCartonQtyRange;
	
	private String masterCartonQtyIncVal;
	
	private MultipartFile itemImage;
	
	private String itemNo;
	
    public UpdateProductTO(){}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public MultipartFile getItemImage() {
		return itemImage;
	}

	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
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


	public double getPerUnitCostPrice() {
		return perUnitCostPrice;
	}


	public void setPerUnitCostPrice(double perUnitCostPrice) {
		this.perUnitCostPrice = perUnitCostPrice;
	}


	public double getRefillPrice() {
		return refillPrice;
	}

	public void setRefillPrice(double refillPrice) {
		this.refillPrice = refillPrice;
	}

	public double getRefillQty() {
		return refillQty;
	}

	public void setRefillQty(double refillQty) {
		this.refillQty = refillQty;
	}

	public String getCompanyInfoId() {
		return companyInfoId;
	}

	public void setCompanyInfoId(String companyInfoId) {
		this.companyInfoId = companyInfoId;
	}


	public double getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(double availableQty) {
		this.availableQty = availableQty;
	}

	public double getThresholdVal() {
		return thresholdVal;
	}


	public void setThresholdVal(double thresholdVal) {
		this.thresholdVal = thresholdVal;
	}


	public double getMrp() {
		return mrp;
	}


	public void setMrp(double mrp) {
		this.mrp = mrp;
	}


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public String getItemContentInfo() {
		return itemContentInfo;
	}

	public void setItemContentInfo(String itemContentInfo) {
		this.itemContentInfo = itemContentInfo;
	}

	public String getItemPckgType() {
		return itemPckgType;
	}

	public void setItemPckgType(String itemPckgType) {
		this.itemPckgType = itemPckgType;
	}

	public String getItemPckgInfo() {
		return itemPckgInfo;
	}

	public void setItemPckgInfo(String itemPckgInfo) {
		this.itemPckgInfo = itemPckgInfo;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getOfferDetailsId() {
		return offerDetailsId;
	}

	public void setOfferDetailsId(int offerDetailsId) {
		this.offerDetailsId = offerDetailsId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
 
    
}
	
  