package co.in.replete.komalindustries.beans;

public class ItemDetailsTO {
	private String itemMasterDtlsId;

	private String itemContentInfo;

	private String itemDesc;

	private String itemManufacturer;

	private String itemNm;

	private String itemPckgInfo;

	private String itemStrength;

	private String itemCategoryCd;

	private String itemPckgTypeCd;

	private String itemStrengthUnitCd;

	private String itemSubcategoryCd;

	private String offerId;
	
	private String itemDtlsId;

	private String avlQty;

	private String bookedQty;

	private String mrp;

	private String thrshldVal;
	
	public ItemDetailsTO(){}

	public ItemDetailsTO(String itemMasterDtlsId, String itemContentInfo, String itemDesc, String itemManufacturer,
			String itemNm, String itemPckgInfo, String itemStrength, String itemCategoryCd, String itemPckgTypeCd,
			String itemStrengthUnitCd, String itemSubcategoryCd, String offerId, String itemDtlsId, String avlQty,
			String bookedQty, String mrp, String thrshldVal) {
		this.itemMasterDtlsId = itemMasterDtlsId;
		this.itemContentInfo = itemContentInfo;
		this.itemDesc = itemDesc;
		this.itemManufacturer = itemManufacturer;
		this.itemNm = itemNm;
		this.itemPckgInfo = itemPckgInfo;
		this.itemStrength = itemStrength;
		this.itemCategoryCd = itemCategoryCd;
		this.itemPckgTypeCd = itemPckgTypeCd;
		this.itemStrengthUnitCd = itemStrengthUnitCd;
		this.itemSubcategoryCd = itemSubcategoryCd;
		this.offerId = offerId;
		this.itemDtlsId = itemDtlsId;
		this.avlQty = avlQty;
		this.bookedQty = bookedQty;
		this.mrp = mrp;
		this.thrshldVal = thrshldVal;
	}

	public String getItemMasterDtlsId() {
		return itemMasterDtlsId;
	}

	public void setItemMasterDtlsId(String itemMasterDtlsId) {
		this.itemMasterDtlsId = itemMasterDtlsId;
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

	public String getItemStrength() {
		return itemStrength;
	}

	public void setItemStrength(String itemStrength) {
		this.itemStrength = itemStrength;
	}

	public String getItemCategoryCd() {
		return itemCategoryCd;
	}

	public void setItemCategoryCd(String itemCategoryCd) {
		this.itemCategoryCd = itemCategoryCd;
	}

	public String getItemPckgTypeCd() {
		return itemPckgTypeCd;
	}

	public void setItemPckgTypeCd(String itemPckgTypeCd) {
		this.itemPckgTypeCd = itemPckgTypeCd;
	}

	public String getItemStrengthUnitCd() {
		return itemStrengthUnitCd;
	}

	public void setItemStrengthUnitCd(String itemStrengthUnitCd) {
		this.itemStrengthUnitCd = itemStrengthUnitCd;
	}

	public String getItemSubcategoryCd() {
		return itemSubcategoryCd;
	}

	public void setItemSubcategoryCd(String itemSubcategoryCd) {
		this.itemSubcategoryCd = itemSubcategoryCd;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getItemDtlsId() {
		return itemDtlsId;
	}

	public void setItemDtlsId(String itemDtlsId) {
		this.itemDtlsId = itemDtlsId;
	}

	public String getAvlQty() {
		return avlQty;
	}

	public void setAvlQty(String avlQty) {
		this.avlQty = avlQty;
	}

	public String getBookedQty() {
		return bookedQty;
	}

	public void setBookedQty(String bookedQty) {
		this.bookedQty = bookedQty;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getThrshldVal() {
		return thrshldVal;
	}

	public void setThrshldVal(String thrshldVal) {
		this.thrshldVal = thrshldVal;
	}
	
}