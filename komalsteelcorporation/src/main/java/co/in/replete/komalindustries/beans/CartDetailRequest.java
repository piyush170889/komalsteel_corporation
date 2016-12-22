package co.in.replete.komalindustries.beans;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

    public class CartDetailRequest extends BaseWrapper {
	@JsonProperty("request")
	private List<CartDetailsTO> cartDetails;

	public CartDetailRequest(){}
	
	public CartDetailRequest(List<CartDetailsTO> ordersList) {
		super();
		this.cartDetails = ordersList;
	}

	public List<CartDetailsTO> getOrdersList() {
		return cartDetails;
	}

	public void setOrdersList(List<CartDetailsTO> cartDetails) {
		this.cartDetails = cartDetails;
	}
	
}
