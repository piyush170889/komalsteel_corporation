package co.in.replete.komalindustries.beans;

import java.util.List;

public class AllCategoryAndSubcatDetailsResposne extends BaseWrapper {

	private List<AllCategoryAndSubCatDetailsTo> categoryAndSubCatDetails;
	
	public AllCategoryAndSubcatDetailsResposne() {}

	public AllCategoryAndSubcatDetailsResposne(List<AllCategoryAndSubCatDetailsTo> categoryAndSubCatDetails) {
		this.categoryAndSubCatDetails = categoryAndSubCatDetails;
	}

	public List<AllCategoryAndSubCatDetailsTo> getCategoryAndSubCatDetails() {
		return categoryAndSubCatDetails;
	}

	public void setCategoryAndSubCatDetails(List<AllCategoryAndSubCatDetailsTo> categoryAndSubCatDetails) {
		this.categoryAndSubCatDetails = categoryAndSubCatDetails;
	}
	
	
}
