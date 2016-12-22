package co.in.replete.komalindustries.beans;

public class PaginationDetailsTO {

	private int PageNumber;
	
	private int numOfOrders;
	
	private int recordPerPage;
	
	public PaginationDetailsTO(){}

	public int getPageNumber() {
		return PageNumber;
	}

	public void setPageNumber(int pageNumber) {
		PageNumber = pageNumber;
	}

	public int getNumOfOrders() {
		return numOfOrders;
	}

	public void setNumOfOrders(int numOfOrders) {
		this.numOfOrders = numOfOrders;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}
	
	
}
