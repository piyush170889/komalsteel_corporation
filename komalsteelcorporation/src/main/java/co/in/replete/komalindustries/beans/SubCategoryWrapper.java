package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubCategoryWrapper extends BaseWrapper {

	@JsonProperty("subcategory details")
     private List<SubCategoryTo> subCategoryList;
    
	public SubCategoryWrapper(){}

	public List<SubCategoryTo> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubCategoryTo> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
}   
