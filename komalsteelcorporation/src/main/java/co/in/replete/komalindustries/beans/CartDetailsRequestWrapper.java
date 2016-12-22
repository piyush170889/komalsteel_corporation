package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartDetailsRequestWrapper extends BaseWrapper {

	
	@JsonProperty("cartDetails")
	private List<NewCartDetailsTO> cartDetails;
	
	@JsonProperty("paginationDetails")
	private PaginationDetailsTO paginationDetails;
	
	public CartDetailsRequestWrapper(){}

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
