package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefillWrapper extends BaseWrapper{
	
	@JsonProperty("request")
	private List<RefillTO> refilList;
	
	public RefillWrapper(){}

	public List<RefillTO> getRefilList() {
		return refilList;
	}

	public void setRefilList(List<RefillTO> refilList) {
		this.refilList = refilList;
	}
	
	

}
