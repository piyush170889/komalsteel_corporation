package co.in.replete.komalindustries.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequestWrapper extends BaseWrapper 
{
	@NotNull(message="error.request.empty")
	@Valid 
	@JsonProperty("request")
	private UpdateUserTO updateUserTO;
	
	public UpdateRequestWrapper() {}

	public UpdateUserTO getUpdateUserTO() {
		return updateUserTO;
	}

	public void setUpdateUserTO(UpdateUserTO updateUserTO) {
		this.updateUserTO = updateUserTO;
	}

    
}
