package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsWrapper2 extends BaseWrapper {

	@JsonProperty("request")
	private List<UserDetailsAllTO> userDetail;
	
	public UserDetailsWrapper2(){}

	public List<UserDetailsAllTO> getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(List<UserDetailsAllTO> userDetail) {
		this.userDetail = userDetail;
	}
	
}
