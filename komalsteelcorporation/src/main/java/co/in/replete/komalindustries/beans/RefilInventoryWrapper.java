package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefilInventoryWrapper extends BaseWrapper {
	
	@JsonProperty("request")
	@Valid
	private RefilInventoryTO refilInventoryTO;
	
	public RefilInventoryWrapper(){}

	public RefilInventoryTO getRefilInventoryTO() {
		return refilInventoryTO;
	}

	public void setRefilInventoryTO(RefilInventoryTO refilInventoryTO) {
		this.refilInventoryTO = refilInventoryTO;
	}
	
	

}
