package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class AddProductTO {

//	private int itemMasterDtlsId;
	
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

	private MultipartFile itemImage;

	@NotNull(message="error.itemManufacturer.required")
	@NotEmpty(message="error.itemManufacturer.required")
	private String itemManufacturer;

	@NotNull(message="error.itemNm.required")
	@NotEmpty(message="error.itemNm.required")
	private String itemNm;

	@NotNull(message="error.itemPckgInfo.required")
	@NotEmpty(message="error.itemPckgInfo.required")
	private String itemPckgInfo;

	@NotNull(message="error.itemPckgTypeCd.required")
	@NotEmpty(message="error.itemPckgTypeCd.required")
	private String itemPckgType;

	private String itemSubCategory;

	private int offerDtlsId;
             
	private double initialQuantity;

    private double mrp;
    
    private double ThrhldVal;
    
	private double bookedQty;
    
    private double rejectedScrapQty;

    private double rejectedReworkQty;
    
    private int vendorId;
    
    private double perUnitCostPrice;
    
    private double refilPrice;
    
    private int itemsInMasterCarton;
	
	private Float masterCartonPrice;
	
	private String masterCartonQtyRange;
	
	private String masterCartonQtyIncVal;
	
	private String itemNo;
	
	private int hsnDtlsId;
	
	private double perUnitPrice;
	
    public AddProductTO(){}


	public double getPerUnitPrice() {
		return perUnitPrice;
	}


	public void setPerUnitPrice(double perUnitPrice) {
		this.perUnitPrice = perUnitPrice;
	}


	public int getHsnDtlsId() {
		return hsnDtlsId;
	}


	public void setHsnDtlsId(int hsnDtlsId) {
		this.hsnDtlsId = hsnDtlsId;
	}


	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getMasterCartonQtyIncVal() {
		return masterCartonQtyIncVal;
	}
	
	public void setMasterCartonQtyIncVal(String masterCartonQtyIncVal) {
		this.masterCartonQtyIncVal = masterCartonQtyIncVal;
	}

	public String getMasterCartonQtyRange() {
		return masterCartonQtyRange;
	}

	public void setMasterCartonQtyRange(String masterCartonQtyRange) {
		this.masterCartonQtyRange = masterCartonQtyRange;
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

	public String getCmpnyInfoId() {
		return cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
	}
	
   public double getThrhldVal() {
			return ThrhldVal;
		}

  public void setThrhldVal(double thrhldVal) {
			ThrhldVal = thrhldVal;
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

	public MultipartFile getItemImage() {
		return itemImage;
	}

	public void setItemImage(MultipartFile itemImage) {
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

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public double getBookedQty() {
		return bookedQty;
	}

	public void setBookedQty(double bookedQty) {
		this.bookedQty = bookedQty;
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

	public double getPerUnitCostPrice() {
		return perUnitCostPrice;
	}

	public void setPerUnitCostPrice(double perUnitCostPrice) {
		this.perUnitCostPrice = perUnitCostPrice;
	}

	public double getRefilPrice() {
		return refilPrice;
	}

	public void setRefilPrice(double refilPrice) {
		this.refilPrice = refilPrice;
	}
    
	

/*	public int getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(int itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
	}
*/
	
	
	
	
	
}
