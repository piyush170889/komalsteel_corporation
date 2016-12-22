package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetailsWrapper extends BaseWrapper {

	@JsonProperty("productDetails")
	private List<ProductDetailsTO> productDetails;
	
	public ProductDetailsWrapper() {}

	public List<ProductDetailsTO> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetailsTO> productDetails) {
		this.productDetails = productDetails;
	}

}
