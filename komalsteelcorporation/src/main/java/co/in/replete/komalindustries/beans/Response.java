package co.in.replete.komalindustries.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	@JsonProperty("responseMessage")
	private ResponseMessage responseMessage;

	public Response() {}
	
	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
}
