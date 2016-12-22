package co.in.replete.komalindustries.beans;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyInfoRequestWrapper extends BaseWrapper{


	@JsonProperty("companyDetails")
	@Valid
	private CompanyDetailsTO companyDetailsTO;
	
	public CompanyInfoRequestWrapper() {}

	public CompanyDetailsTO getCompanyDetailsTO() {
		return companyDetailsTO;
	}

	public void setCompanyDetailsTO(CompanyDetailsTO companyDetailsTO) {
		this.companyDetailsTO = companyDetailsTO;
	}
	
	

}
