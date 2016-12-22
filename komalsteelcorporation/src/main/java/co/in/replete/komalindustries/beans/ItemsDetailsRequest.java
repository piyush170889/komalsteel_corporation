package co.in.replete.komalindustries.beans;


public class ItemsDetailsRequest extends BaseWrapper{
	private String categoryCd;
	private String subCategoryCd;
	private String brandName;
	
	public ItemsDetailsRequest() {}

	public ItemsDetailsRequest(String categoryCd, String subCategoryCd, String brandName) {
		this.categoryCd = categoryCd;
		this.subCategoryCd = subCategoryCd;
		this.brandName = brandName;
	}

	public String getCategoryCd() {
		return categoryCd;
	}

	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	public String getSubCategoryCd() {
		return subCategoryCd;
	}

	public void setSubCategoryCd(String subCategoryCd) {
		this.subCategoryCd = subCategoryCd;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}
