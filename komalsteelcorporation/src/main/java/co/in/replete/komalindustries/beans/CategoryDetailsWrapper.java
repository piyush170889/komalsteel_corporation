package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDetailsWrapper extends BaseWrapper{

	@JsonProperty("response")
	private List<CategoryTO> categoryList;
	
	@JsonProperty("pagination")
	private CategoryPaginationTO pagination;
	
	public CategoryDetailsWrapper(){}

	public List<CategoryTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryTO> categoryList) {
		this.categoryList = categoryList;
	}

	public CategoryPaginationTO getPagination() {
		return pagination;
	}

	public void setPagination(CategoryPaginationTO pagination) {
		this.pagination = pagination;
	}

	
}
