package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsWrapper extends BaseWrapper{
	
	@JsonProperty("getUserDetails")
	private List<UserDetailsTO> userDetailsTo;
	
	
	public UserDetailsWrapper(){}

	public List<UserDetailsTO> getUserDetailsTo() {
		return userDetailsTo;
	}

	public void setUserDetailsTo(List<UserDetailsTO> userDetailsTo) {
		this.userDetailsTo = userDetailsTo;
	}
	
	

}
