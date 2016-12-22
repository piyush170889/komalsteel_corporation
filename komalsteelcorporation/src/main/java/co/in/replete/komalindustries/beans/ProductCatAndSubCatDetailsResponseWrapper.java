package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCatAndSubCatDetailsResponseWrapper extends BaseWrapper{

	@JsonProperty("response")
	private List<ProductCatAndSubCatDetailsTO> productCatAndSubCatDetailsTOList;
	
	public ProductCatAndSubCatDetailsResponseWrapper() {}

	public List<ProductCatAndSubCatDetailsTO> getProductCatAndSubCatDetailsTOList() {
		return this.productCatAndSubCatDetailsTOList;
	}

	public void setProductCatAndSubCatDetailsTOList(List<ProductCatAndSubCatDetailsTO> productCatAndSubCatDetailsTOList) {
		this.productCatAndSubCatDetailsTOList = productCatAndSubCatDetailsTOList;
	}
	
}
