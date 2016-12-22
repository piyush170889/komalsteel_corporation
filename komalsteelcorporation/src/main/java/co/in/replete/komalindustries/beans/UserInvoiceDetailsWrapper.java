package co.in.replete.komalindustries.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInvoiceDetailsWrapper extends BaseWrapper{

	@JsonProperty("request")
	private List<UserOrderDetailsTO> userInvoiceList;
	
	public UserInvoiceDetailsWrapper(){}

	public List<UserOrderDetailsTO> getUserInvoiceList() {
		return userInvoiceList;
	}

	public void setUserInvoiceList(List<UserOrderDetailsTO> userInvoiceList) {
		this.userInvoiceList = userInvoiceList;
	}
	
	
}
