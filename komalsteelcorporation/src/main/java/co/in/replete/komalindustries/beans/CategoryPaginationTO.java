package co.in.replete.komalindustries.beans;

public class CategoryPaginationTO {

    private int PageNumber;
	
	private int numOfCategory;
	
	private int recordPerPage;
	
	public CategoryPaginationTO(){}

	public int getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(int pageNumber) {
		PageNumber = pageNumber;
	}

	public int getNumOfCategory() {
		return numOfCategory;
	}

	public void setNumOfCategory(int numOfCategory) {
		this.numOfCategory = numOfCategory;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}
	
}
