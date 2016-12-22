package co.in.replete.komalindustries.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestWrapper extends BaseWrapper {
	
	@NotNull(message="error.request.empty")
	@Valid
	@JsonProperty("userDetails")
	private RegisterDetailsTO registerDetails;
	
    public RegisterRequestWrapper(){}

	public RegisterDetailsTO getRegisterDetails() {
		return registerDetails;
	}

	public void setRegisterDetails(RegisterDetailsTO registerDetails) {
		this.registerDetails = registerDetails;
	}	
    
    

}
