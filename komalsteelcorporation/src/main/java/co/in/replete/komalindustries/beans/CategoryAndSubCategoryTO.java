package co.in.replete.komalindustries.beans;

import java.util.List;

import co.in.replete.komalindustries.beans.entity.CategoryMaster;

public class CategoryAndSubCategoryTO extends BaseWrapper {

	private List<CategoryDetailsTO> categoryDetails;
	
	private List<CategoryMaster> subCategoryDetails;
	
	private CategoryDetailsTO activeCategoryDetails;
	
	public CategoryAndSubCategoryTO() {}

	public CategoryAndSubCategoryTO(List<CategoryDetailsTO> categoryDetails, List<CategoryMaster> subCategoryDetails,
			CategoryDetailsTO activeCategoryDetails) {
		super();
		this.categoryDetails = categoryDetails;
		this.subCategoryDetails = subCategoryDetails;
		this.activeCategoryDetails = activeCategoryDetails;
	}

	public CategoryDetailsTO getActiveCategoryDetails() {
		return activeCategoryDetails;
	}

	public void setActiveCategoryDetails(CategoryDetailsTO activeCategoryDetails) {
		this.activeCategoryDetails = activeCategoryDetails;
	}

	public List<CategoryDetailsTO> getCategoryDetails() {
		return categoryDetails;
	}

	public void setCategoryDetails(List<CategoryDetailsTO> categoryDetails) {
		this.categoryDetails = categoryDetails;
	}

	public List<CategoryMaster> getSubCategoryDetails() {
		return subCategoryDetails;
	}

	public void setSubCategoryDetails(List<CategoryMaster> subCategoryDetails) {
		this.subCategoryDetails = subCategoryDetails;
	}
	
}
