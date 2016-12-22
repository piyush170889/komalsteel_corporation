package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequestWrapper extends BaseWrapper {
	
	@JsonProperty("request")
	@Valid
    private AddProductTO addProductTO;	
	
	public ProductRequestWrapper() {}
	
	public AddProductTO getAddProductTO() {
		return addProductTO;
	}
	
	public void setAddProductTO(AddProductTO addProductTO) {
		this.addProductTO = addProductTO;
	}



}
