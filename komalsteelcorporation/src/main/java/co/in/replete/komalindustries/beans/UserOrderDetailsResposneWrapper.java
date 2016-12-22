package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserOrderDetailsResposneWrapper extends BaseWrapper {

	
	@JsonProperty("request")
	private List<NewCartDetailsTO> cartDetails;

	@JsonProperty("paginationDetails")
	private PaginationDetailsTO paginationDetails;
	
	public UserOrderDetailsResposneWrapper(){}


	public List<NewCartDetailsTO> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<NewCartDetailsTO> cartDetails) {
		this.cartDetails = cartDetails;
	}

	public PaginationDetailsTO getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetailsTO paginationDetails) {
		this.paginationDetails = paginationDetails;
	}
	
}
