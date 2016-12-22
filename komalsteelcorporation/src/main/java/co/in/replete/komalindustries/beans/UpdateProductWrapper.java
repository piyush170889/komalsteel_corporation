package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateProductWrapper extends BaseWrapper {
	
	@JsonProperty("request")
	@Valid
	private UpdateProductTO updateProductTO;
	
	public UpdateProductWrapper() {	}

	public UpdateProductTO getUpdateProductTO() {
		return updateProductTO;
	}

	public void setUpdateProductTO(UpdateProductTO updateProductTO) {
		this.updateProductTO = updateProductTO;
	}
}
