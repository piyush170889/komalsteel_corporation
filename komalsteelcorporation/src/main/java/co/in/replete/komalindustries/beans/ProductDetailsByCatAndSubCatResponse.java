package co.in.replete.komalindustries.beans;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDetailsByCatAndSubCatResponse extends BaseWrapper{

	@JsonProperty("resposne")
	private List<ProductDetailsByCatAndSubCatTO> itemsList;

	public ProductDetailsByCatAndSubCatResponse() {}

	public List<ProductDetailsByCatAndSubCatTO> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ProductDetailsByCatAndSubCatTO> itemsList) {
		this.itemsList = itemsList;
	}

	
}
