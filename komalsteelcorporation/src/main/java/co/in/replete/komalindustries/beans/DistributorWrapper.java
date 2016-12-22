package co.in.replete.komalindustries.beans;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistributorWrapper extends BaseWrapper {
	
	@JsonProperty("request")
	@Valid
	@NotNull(message="error.request.required")
	private AssoDistributorTO assoDistList;
	
	public DistributorWrapper(){}

	public AssoDistributorTO getAssoDistList() {
		return assoDistList;
	}

	public void setAssoDistList(AssoDistributorTO assoDistList) {
		this.assoDistList = assoDistList;
	}

		
	

}
