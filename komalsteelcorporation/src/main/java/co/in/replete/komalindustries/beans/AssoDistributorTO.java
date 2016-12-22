package co.in.replete.komalindustries.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AssoDistributorTO {

	 @NotNull(message="error.distributor.required")
     @NotEmpty(message="error.distributor.required")
	 private List<SingleValueCommonClass> associatedDistributorsList;
	 
	 public AssoDistributorTO(){}

	public List<SingleValueCommonClass> getAssociatedDistributorsList() {
		return associatedDistributorsList;
	}

	public void setAssociatedDistributorsList(List<SingleValueCommonClass> associatedDistributorsList) {
		this.associatedDistributorsList = associatedDistributorsList;
	}
	 
	 
}
