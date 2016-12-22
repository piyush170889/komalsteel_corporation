package co.in.replete.komalindustries.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCmpnyInfoWrapper extends BaseWrapper{
	
	@JsonProperty("cmpnyDetails")
	private CompanyUpdateDetailsTO comoanyUpdateDetailsTO;
	
	public UpdateCmpnyInfoWrapper(){}

	public CompanyUpdateDetailsTO getComoanyUpdateDetailsTO() {
		return comoanyUpdateDetailsTO;
	}

	public void setComoanyUpdateDetailsTO(CompanyUpdateDetailsTO comoanyUpdateDetailsTO) {
		this.comoanyUpdateDetailsTO = comoanyUpdateDetailsTO;
	}
	
	

}
