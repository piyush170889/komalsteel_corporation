package co.in.replete.komalindustries.beans;

import java.util.List;

public class ProductCatAndSubCatDetailsTO {

	private String category;
	
	private List<SingleValueCommonClass> subCategory;
	
	public ProductCatAndSubCatDetailsTO() {}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<SingleValueCommonClass> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<SingleValueCommonClass> subCategory) {
		this.subCategory = subCategory;
	}
	
}
