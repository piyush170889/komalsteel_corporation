package co.in.replete.komalindustries.beans;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewDistributorWrapper extends BaseWrapper{
    
	@JsonProperty("distributorList")
	@NotEmpty(message="error.distributorList.required")
	public List<DistributorTO> distributorList;

	public NewDistributorWrapper() { }
	
	public List<DistributorTO> getDistributorList() {
		return distributorList;
	}

	public void setDistributorList(List<DistributorTO> distributorList) {
		this.distributorList = distributorList;
	}

}
