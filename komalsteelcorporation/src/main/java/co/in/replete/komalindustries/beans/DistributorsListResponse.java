package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import co.in.replete.komalindustries.beans.entity.UserDtl;

public class DistributorsListResponse extends BaseWrapper {

	@JsonProperty("response")
	private List<UserDtl> distributorsList;
	
	public DistributorsListResponse() {}

	public List<UserDtl> getDistributorsList() {
		return distributorsList;
	}

	public void setDistributorsList(List<UserDtl> distributorsList) {
		this.distributorsList = distributorsList;
	}
	
}
