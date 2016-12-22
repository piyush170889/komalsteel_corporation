package co.in.replete.komalindustries.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWrapper {
	
	@JsonProperty("responseMessage")
	private ResponseMessage responseMessage;
	
	public BaseWrapper() {}

	public BaseWrapper(ResponseMessage responseMessage) {

		this.responseMessage = responseMessage;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
}
