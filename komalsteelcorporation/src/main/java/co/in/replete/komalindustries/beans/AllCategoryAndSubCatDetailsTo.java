package co.in.replete.komalindustries.beans;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.CategoryMaster;

public class AllCategoryAndSubCatDetailsTo {

	private CategoryDetailsTO categoryDetails;
	
	private List<CategoryMaster> subCategoryDetails;
	
	public AllCategoryAndSubCatDetailsTo() {}

	public AllCategoryAndSubCatDetailsTo(CategoryDetailsTO categoryDetails, List<CategoryMaster> subCategoryDetails) {
		this.categoryDetails = categoryDetails;
		this.subCategoryDetails = subCategoryDetails;
	}

	public CategoryDetailsTO getCategoryDetails() {
		return categoryDetails;
	}

	public void setCategoryDetails(CategoryDetailsTO categoryDetails) {
		this.categoryDetails = categoryDetails;
	}

	public List<CategoryMaster> getSubCategoryDetails() {
		return subCategoryDetails;
	}

	public void setSubCategoryDetails(List<CategoryMaster> subCategoryDetails) {
		this.subCategoryDetails = subCategoryDetails;
	}
	
	
}
